package avatar;

import shapes.CustomShape;

public class FullStack implements StackState {

	@Override
	public boolean canAttach(CustomShape shape, int xPosition, int heightSum) {
		return false;
	}

}
