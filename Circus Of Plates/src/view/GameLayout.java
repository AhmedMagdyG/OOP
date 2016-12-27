package view;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

public class GameLayout extends BorderPane {

	private ToolBar toolbar;
	private ArrayList<Node> toolbarNodes = new ArrayList<Node>();
	private Canvas gameCanvas = new Canvas(400,300);
	
	public GameLayout() {
		toolbar = new ToolBar();
		initialiseToolBar();
		toolbar.getItems().addAll(toolbarNodes);
		setTop(toolbar);
	
	}

	private void initialiseToolBar() {
		ButtonFactory buttonFactory = ButtonFactory.getInstance();
		for (int index = 0; index < buttonFactory.getNodeCount(); index++) {
			toolbarNodes.add(buttonFactory.createNode(index));
		}
	}
}
