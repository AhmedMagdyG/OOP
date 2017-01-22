package model;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Stack {

	public static final int WIDTH = 50, HEIGHT = 5, RADIUS = 10, SHIFT = 15, BORDER_THICKNESS = 5;

	private int x, y;
	private int playerIndex;
	private int heightSum;

	private ArrayList<Sprite> shapes;
	private int shapeCount;
	
	public Stack(int playerIndex) {
		this.playerIndex = playerIndex;
		y = Util.STAND_HEIGHT;
		this.heightSum = 0;
		this.shapes = new ArrayList<Sprite>();
		this.shapeCount = 0;
	}

	public void setX(int x) {
		this.x = x;
		updateList();
	}

	public Sprite getSprite() {
		return new SpriteShape(x, y, WIDTH, HEIGHT, getStackFillColor(), getStackStrokeColor());
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Sprite> getShapesSprite() {
		return (ArrayList<Sprite>) shapes.clone();
	}

	public void addShape(SpriteShape shape) {
		heightSum += shape.getHeight();
		shape.setY(y + heightSum);
		shape.setX(x + BORDER_THICKNESS);
		shapes.add(shape);
	}

	public int getSize(){
		return shapeCount;
	}
	
	private Color getStackFillColor() {
		if (playerIndex == 1) {
			return Color.RED;
		}
		return Color.CYAN;
	}

	private Color getStackStrokeColor() {
		if (playerIndex == 1) {
			return Color.BLACK;
		}
		return Color.GREEN;
	}
	
	private void updateList() {
		for(int i = 0; i < shapeCount; i++){
			((SpriteShape)shapes.get(i)).setX(x + BORDER_THICKNESS);
		}
	}
}
