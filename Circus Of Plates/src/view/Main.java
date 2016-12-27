package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

	private static final int WIDTH = 950, HEIGH = 630;
	
	@Override
	public void start(Stage gameStage) throws Exception {
		initialize(gameStage);
	}

	private void initialize(Stage gameStage) {
		gameStage.setTitle("Circus of Plates");
		SplitPane layout = new GameLayout();
		Scene gameScene = new Scene(layout, WIDTH, HEIGH);
		gameStage.setScene(gameScene);
		gameStage.setResizable(false);
		gameStage.show();
	}

	public static void main(String[] args){
		launch(args);
	}
}
