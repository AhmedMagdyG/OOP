package shapes;

import javafx.scene.paint.Color;

public class SquareShape extends RectangleShape {

	public SquareShape(int x, int y, int width, Color fillColor) {
		super(x, y, width, width, fillColor);
	}
	
	public SquareShape(int x, int y, int width, Color fillColor, Color strokeColor){
		super(x, y, width, width, fillColor, strokeColor);
	}
}
