package model;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.image.Image;
import rail.Allign;
import rail.Rail;
import rail.RailsContainer;
import shapes.CustomShape;

public class GameModel {

	private ArrayList<Avatar> avatars;
	private int playerCount;
	private ShapesPool shapesPool;
	private RailsContainer railsContainer;

	public GameModel(int playerCount) {
		this.playerCount = playerCount;
		this.shapesPool = new ShapesPool();
		railsContainer = new RailsContainer();
		initialiseAvatars();
		initialiseRails();
	}

	private void initialiseAvatars() {
		avatars = new ArrayList<Avatar>();
		for (int i = 0; i < playerCount; i++) {
			avatars.add(new Avatar());
		}
	}

	private void initialiseRails() {
		Image spriteImage = new Image(new File("res" + File.separator + "rail.png").toURI().toString());
		railsContainer.addRail(new Rail(Allign.LEFT_ALLIGN, 0, 30, 200, 10, spriteImage));
		railsContainer.addRail(new Rail(Allign.LEFT_ALLIGN, 0, 70, 200, 10, spriteImage));
		railsContainer.addRail(new Rail(Allign.RIGHT_ALLIGN, 950 - 200, 30, 200, 10, spriteImage));
		railsContainer.addRail(new Rail(Allign.RIGHT_ALLIGN, 950 - 200, 70, 200, 10, spriteImage));
	}

	public void updateData() {

	}

	public void movePlayer(int i, int dist) {
		avatars.get(i).move(dist);
	}

	public ArrayList<Sprite> getSprites() {
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		for (Avatar avatar : avatars) {
			sprites.add(avatar.getAvatarSprite());
			sprites.add(avatar.getLeftStackSprite());
			sprites.add(avatar.getRightStackSprite());
			sprites.addAll(avatar.getLeftStackShapes());
			sprites.addAll(avatar.getRightStackShapes());
		}
		for (CustomShape shape : shapesPool.getInUse()) {
			sprites.add(shape.getSprite());
		}
		for (Rail rail : railsContainer.getRails()) {
			sprites.add(rail.getSprite());
		}
		return sprites;
	}

	public ShapesPool getShapesPool() {
		return shapesPool;
	}

	public RailsContainer getRailsContainer() {
		return railsContainer;
	}
}
