package model;

import shapes.CustomShape;

public class EmptyStack implements StackState {

	@Override
	public boolean canAttach(CustomShape shape, int xPosition, int heightSum) {
		int baseHeight = (int) (shape.getYPosition() + shape.getHeight());
		int centrePosition = (int) (shape.getXPosition() + (shape.getWidth() / 2));
		int realCentre = xPosition + (Stack.WIDTH / 2);
		if (baseHeight <= heightSum && baseHeight >= heightSum - Stack.VERT_EPS && centrePosition >= realCentre - Stack.HORT_EPS
				&& centrePosition <= realCentre + Stack.HORT_EPS) {
			return true;
		}
		return false;
	}

}
