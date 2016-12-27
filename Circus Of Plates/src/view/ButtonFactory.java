package view;

import controller.GameController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;

public class ButtonFactory {

	private static ButtonFactory buttonFactory;
	
	private static final int NODE_COUNT = 7;
	private static final String[] btnName = new String[] { "New", "Pause", "Resume", "Quit", null, "Save", "Load" };
	private static final int NEW = 0, PAUSE = 1, RESUME = 2, QUIT = 3, SAVE = 5, LOAD = 6;
	
	public static ButtonFactory getInstance() {
		if(buttonFactory == null){
			buttonFactory = new ButtonFactory();
		}
		return buttonFactory;
	}
	
	public int getNodeCount() {
		return NODE_COUNT;
	}
	
	private boolean isSeparator(int index) {
		return btnName[index] == null;
	}

	public Node createNode(int index) {
		if(isSeparator(index)){
			return new Separator();
		}
		return createButton(index);
	}

	private Node createButton(int index) {
		Button curBtn = new Button(btnName[index]);
		switch(index){
		case NEW:
			curBtn.setOnAction(e -> GameController.getInstance().newGame());
			break;
		case PAUSE:
			curBtn.setOnAction(e -> GameController.getInstance().pauseGame());
			break;
		case RESUME:
			curBtn.setOnAction(e -> GameController.getInstance().resumeGame());
			break;
		case QUIT:
			curBtn.setOnAction(e -> GameController.getInstance().quit());
			break;
		case SAVE:
			curBtn.setOnAction(e -> GameController.getInstance().save());
			break;
		case LOAD:
			curBtn.setOnAction(e -> GameController.getInstance().load());
			break;
		default:
			throw new IllegalStateException();
		}
		return curBtn;
	}
}