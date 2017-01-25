package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import controller.ShapesController;
import javafx.scene.image.Image;
import rail.Rail;
import rail.RailsContainer;
import shapes.CustomShape;
import sprite.Sprite;

public class GameModel {

	private static final int PLAYER_COUNT = 2;
	private static final int EASY = 0, MEDIUM = 1, HARD = 2;

	private ArrayList<Avatar> avatars;
	private RailsContainer railsContainer;
	private ShapesController shapesController;
	private long prevCycleTime;
	private int shapeCycle;
	private int numberOfRails;
	
	public GameModel(int difficulty) {
		releaseAvatars();
		releaseShapes();
		this.prevCycleTime = System.currentTimeMillis();
		railsContainer = new RailsContainer();
		shapesController = new ShapesController(railsContainer);
		initialiseDifficulty(difficulty);
		initialiseAvatars();
		initialiseRails();
	}

	public RailsContainer getRailsContainer() {
		return railsContainer;
	}
	
	public ArrayList<Avatar> getAvatars() {
		return this.avatars;
	}
	
	public void moveShapes() {
		if (System.currentTimeMillis() - prevCycleTime > shapeCycle) {
			shapesController.startNewShape();
			prevCycleTime = System.currentTimeMillis();
		}
		shapesController.moveShapes();
	}
	
	public void movePlayer(int i, int dist) {
		avatars.get(i).move(dist);
	}

	public ArrayList<Sprite> getSprites() {
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		for (Avatar avatar : avatars) {
			sprites.addAll(avatar.getSprites());
		}
		List<CustomShape> shapes = ShapesPool.getInstance().getInUse();
		for (int j = 0; j < shapes.size(); j++) {
			boolean attached = false;
			CustomShape shape = shapes.get(j);
			for(int i = 0; i < 2 && (!attached); i++){
				attached = avatars.get(i).attach(shape);
			}
			if (attached) {
				ShapesPool.getInstance().removeInUse(shape);
				j--;
			} else {
				sprites.add(shape.getSprite());
			}
		}
		for (Rail rail : railsContainer.getRails()) {
			sprites.add(rail.getSprite());
		}
		return sprites;
	}
	
	private void initialiseDifficulty(int difficulty) {
		switch(difficulty){
		case EASY:
			shapeCycle = 700;
			numberOfRails = 2;
			break;
		case MEDIUM:
			shapeCycle = 500;
			numberOfRails = 2;
			break;
		case HARD:
			shapeCycle = 500;
			numberOfRails = 4;
			break;
		}
	}

	private void initialiseAvatars() {
		avatars = new ArrayList<Avatar>();
		for (int i = 0; i < PLAYER_COUNT; i++) {
			avatars.add(new Avatar());
		}
	}

	private void initialiseRails() {
		Image spriteImage = new Image(new File(Rail.IMAGE).toURI().toString());
		for(int i = 0; i < numberOfRails; i++){
			railsContainer.addRail(new Rail(Rail.allign[i], Rail.xPosition[i], Rail.yPosition[i],
					Rail.WIDTH, Rail.HEIGHT, spriteImage));
		}
	}
	
	private void releaseShapes() {
		ShapesPool.getInstance().releaseAll();
	}

	private void releaseAvatars() {
		if(avatars == null){
			return;
		}
		for(Avatar avatar : avatars){
			avatar.releaseShapes();
		}
	}

	public boolean gameEnded() {
		boolean stacksFull = true;
		for(Avatar avatar : avatars){
			stacksFull = stacksFull && avatar.checkStackFull();
		}
		return stacksFull;
	}

	public int getWinner() {
		return avatars.get(0).getScore() - avatars.get(1).getScore();
	}
}
