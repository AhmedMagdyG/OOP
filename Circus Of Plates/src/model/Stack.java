package model;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import shapes.CustomShape;

public class Stack {

	public static final int WIDTH = 50, HEIGHT = 5, RADIUS = 10, SHIFT = 15, BORDER_THICKNESS = 5;

	private static final int EPS = 20;

	private int x, y;
	private int playerIndex;
	private int heightSum;

	private ArrayList<Sprite> shapes;

	public Stack(int playerIndex) {
		this.playerIndex = playerIndex;
		y = Util.STAND_HEIGHT;
		this.heightSum = y;
		this.shapes = new ArrayList<Sprite>();
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
		heightSum -= shape.getHeight();
		shape.setY(heightSum);
		shape.setX(x + BORDER_THICKNESS);
		shapes.add(shape);
	}

	public int getSize() {
		return shapes.size();
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
		for (int i = 0; i < shapes.size(); i++) {
			((SpriteShape) shapes.get(i)).setX(x + BORDER_THICKNESS);
		}
	}

	public boolean attach(CustomShape shape) {
		if (canAttach(shape)) {
			addShape((SpriteShape) shape.getSprite());
			return true;
		}
		return false;
	}

	private boolean canAttach(CustomShape shape) {
		int baseHeight = (int) (shape.getYPosition() - shape.getHeight());
		int centrePosition = (int) (shape.getXPosition() + (shape.getWidth() / 2));
		int realCentre = x + (WIDTH / 2);
		if (baseHeight >= heightSum - EPS && baseHeight <= heightSum + EPS
				&& centrePosition >= realCentre - EPS && centrePosition <= realCentre + EPS) {
			return true;
		}
		return false;
	}
}
