package view;

import controller.GameModel;
import javafx.animation.AnimationTimer;

public class GameController extends AnimationTimer {

	private GraphicsDrawer graphicsDrawer;
	private GameModel gameModel;
	
	public GameController(GraphicsDrawer graphicsDrawer, GameModel gameController) {
		this.graphicsDrawer = graphicsDrawer;
		this.gameModel = gameController;
	}
	
	@Override
	public void handle(long now) {
		gameModel.updateData();
		graphicsDrawer.draw();
	}

	public void newGame(){
		System.out.println("NEW GAME CLICk");
	}

	public void save() {
		System.out.println("SAVE GAME");
	}

	public void quit() {
		// TODO Auto-generated method stub
		
	}

	public void pauseGame() {
		stop();
	}

	public void resumeGame() {
		start();
	}

	public void load() {
		// TODO Auto-generated method stub
		
	}

	public void ChangeSettings() {
		// TODO Auto-generated method stub
		
	}

	public void updateData() {
		// TODO Auto-generated method stub
		
	}
}