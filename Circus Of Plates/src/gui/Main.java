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
import view.GraphicsDrawer;

public class Main extends Application {

	private static final int WIDTH = 950, HEIGH = 630;
	public static final int CANVAS_WIDTH = 950, CANVAS_HEIGH = 600;
	private static final int PLAYER_COUNT = 2;

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
		gameCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGH);
		initialiseGame(gameCanvas);
		SplitPane layout = new GameLayout(gameCanvas, gameController);
		Scene gameScene = new Scene(layout, WIDTH, HEIGH);
		move(gameScene);
		setStageProperties(gameStage, gameScene);
	}

	void move(Scene gameScene) {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				gameController.moveAvatars(event.getCode().toString());
			}
		});
	}

	void initialiseGame(Canvas gameCanvas) {
		graphicsDrawer = new GraphicsDrawer(gameCanvas);
		gameModel = new GameModel(PLAYER_COUNT);
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
