package saving;

import java.io.Serializable;
import java.util.List;
import shapes.CustomShape;
import model.Stack;

public class StateBundle implements Serializable {

	private Stack[] firstPlayerStack, secondPlayerStack;
	private List<CustomShape> shapesInUse;
	
	public StateBundle(Stack[] firstPlayerStack, Stack[] secondPlayerStack, List<CustomShape> shapesInUse) {
		this.firstPlayerStack = firstPlayerStack;
		this.secondPlayerStack = secondPlayerStack;
		this.shapesInUse = shapesInUse;
	}
	
	public Stack[] getFirstPlayerStack() {
		return this.firstPlayerStack;
	}
	
	public Stack[] getSecondPlayerStack() {
		return this.secondPlayerStack;
	}
	
	public List<CustomShape> getShapesInUse() {
		return this.shapesInUse;
	}
}