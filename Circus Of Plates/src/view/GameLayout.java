package view;

import java.util.ArrayList;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class GameLayout extends SplitPane {

	private static final int CANVAS_WIDTH = 950, CANVAS_HEIGH = 600;

	private ToolBar toolbar;
	private ArrayList<Node> toolbarNodes = new ArrayList<Node>();
	private Canvas gameCanvas;
	private StackPane graphicsPane, toolbarPane;

	public GameLayout() {
		initialiseToolbarPane();
		initialiseGraphicsPane();
		getItems().addAll(toolbarPane, graphicsPane);
		setOrientation(Orientation.VERTICAL);
		setDividerPositions(0.1f, 0.6f);
		/**
		 * test canvas only and will be removed
		 */
		GraphicsContext gc = gameCanvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGH);
	}

	private void initialiseGraphicsPane() {
		graphicsPane = new StackPane();
		gameCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGH);
		graphicsPane.getChildren().add(gameCanvas);
	}

	private void initialiseToolbarPane() {
		toolbarPane = new StackPane();
		toolbar = new ToolBar();
		initialiseToolBar();
		toolbar.getItems().addAll(toolbarNodes);
		toolbarPane.getChildren().add(toolbar);
	}

	private void initialiseToolBar() {
		ButtonFactory buttonFactory = ButtonFactory.getInstance();
		for (int index = 0; index < buttonFactory.getNodeCount(); index++) {
			toolbarNodes.add(buttonFactory.createNode(index));
		}
	}
}
