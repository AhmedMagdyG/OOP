package gui;

import java.util.ArrayList;

import controller.GameController;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.StackPane;

public class GameLayout extends SplitPane {


	private ToolBar toolbar;
	private ArrayList<Node> toolbarNodes = new ArrayList<Node>();
	private StackPane graphicsPane, toolbarPane;
	private Canvas gameCanvas;

	public GameLayout(Canvas gameCanvas, GameController gameController) {
		this.gameCanvas = gameCanvas;
		initialiseToolbarPane(gameController);
		initialiseGraphicsPane();
		getItems().addAll(toolbarPane, graphicsPane);
		setOrientation(Orientation.VERTICAL);
		setDividerPositions(0.1f, 0.6f);
	}

	private void initialiseGraphicsPane() {
		graphicsPane = new StackPane();
		graphicsPane.getChildren().add(gameCanvas);
	}

	private void initialiseToolbarPane(GameController gameController) {
		toolbarPane = new StackPane();
		toolbar = new ToolBar();
		ButtonFactory buttonFactory = ButtonFactory.getInstance();
		for (int index = 0; index < buttonFactory.getNodeCount(); index++) {
			toolbarNodes.add(buttonFactory.createNode(index, gameController));
		}
		toolbar.getItems().addAll(toolbarNodes);
		toolbarPane.getChildren().add(toolbar);
	}

}
