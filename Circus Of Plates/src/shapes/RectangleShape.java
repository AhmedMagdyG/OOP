package shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.Sprite;
import model.SpriteShape;

public class RectangleShape extends CustomShape {

	protected int width, height;
	protected Rectangle rectangle; 
	
	public RectangleShape(int width, int height, Color color) {
		super(color);
		this.width = width;
		this.height = height;
		this.rectangle = new Rectangle();
		rectangle.setStroke(Color.BLACK);
		rectangle.setWidth(this.width);
		rectangle.setHeight(this.height);
	}
	
	public Shape getShape() { 
		updateRectangle();
		return this.rectangle;
	}
	
	private void updateRectangle() {
		rectangle.setX(super.xPosition);
		rectangle.setY(super.yPosition);
		rectangle.setFill(super.color);
	}

	@Override
	public Sprite getSprite() {
		return new SpriteShape(super.xPosition, super.yPosition, width, height, super.color);
	}


}
