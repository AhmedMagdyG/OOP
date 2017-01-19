package gui;

import javafx.event.EventHandler;
import controller.GameController;
import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import model.GameModel;
import model.Util;
import view.GraphicsDrawer;

public class Main extends Application {

	private Canvas gameCanvas;
	private GameController gameController;
	private GraphicsDrawer graphicsDrawer;
	private GameModel gameModel;

	@Override
	public void start(Stage gameStage) throws Exception {
		initialize(gameStage);
		gameController.start();
	}

	private void initialize(Stage gameStage) {
		gameCanvas = new Canvas(Util.CANVAS_WIDTH, Util.CANVAS_HEIGH);
		initialiseGame(gameCanvas);
		SplitPane layout = new GameLayout(gameCanvas, gameController);
		Scene gameScene = new Scene(layout, Util.SCENE_WIDTH, Util.SCENE_HEIGH);
		move(gameScene);
		setStageProperties(gameStage, gameScene);
	}

	void move(Scene gameScene) {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				gameController.moveAvatars(event.getCode());
			}
		});
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				gameController.stopMovingAvatars(event.getCode());
			}
		});
	}

	void initialiseGame(Canvas gameCanvas) {
		graphicsDrawer = new GraphicsDrawer(gameCanvas);
		gameModel = new GameModel(Util.PLAYER_COUNT);
		gameController = new GameController(graphicsDrawer, gameModel);
	}

	void setStageProperties(Stage gameStage, Scene gameScene) {
		gameStage.setTitle("Circus of Plates");
		gameStage.setScene(gameScene);
		gameStage.setResizable(false);
		gameStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
