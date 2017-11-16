package mTriangle;

import java.util.List;

import codes.IShape;
import codes.MEllipse;
import codes.Point;
import codes.UndoRedo;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class MTriangle implements IShape {
	private Polygon triangle;
	private int doneCorners;
	private boolean shapeEnded;
	private final int NUMOFCORNERS = 3;
	private boolean selected;
	private Point cornerOne;
	private Point cornerTwo;
	private Point cornerThree;
	private static Pane canvas;

	public MTriangle() {
		doneCorners = 0;
		shapeEnded = false;
		selected = false;
		triangle = new Polygon();
	}
	
    public IShape clone() {
    	MTriangle temp = new MTriangle();
    	temp.setShapeEnded(this.isEnded());
    	temp.setSelected(this.isSelected());
    	temp.triangle.getPoints().set(0, this.cornerOne.getX());
		temp.triangle.getPoints().set(1, this.cornerOne.getY());
		temp.triangle.getPoints().set(2, this.cornerTwo.getX());
		temp.triangle.getPoints().set(3, this.cornerTwo.getY());
		temp.triangle.getPoints().set(4, this.cornerThree.getX());
		temp.triangle.getPoints().set(5, this.cornerThree.getY());
		temp.doneCorners = NUMOFCORNERS;
    	temp.resetOrigin();  
    	temp.setBoarderWidth(this.triangle.getStrokeWidth());
    	temp.setBorderColor(this.triangle.getStroke());
    	temp.setColor(this.triangle.getFill());
    	return temp;
    }

	private void setShapeEnded(boolean ended) {
		this.shapeEnded = ended;
	}

	public static void setCanvas(Pane canvas) {
		MTriangle.canvas = canvas;
	}

	public static Button getShapeButton() {
		// Image triangleIcon = new Image("/triangle.png");
		// Button triangleBtn = new Button("", new ImageView(triangleIcon));
		Button triangleBtn = new Button("Triangle");
		return triangleBtn;
	}

	public void draw(List<IShape>shapesList,Paint fillColor,
			Paint borderColor, UndoRedo obj) {
		MTriangle temp = new MTriangle();
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (!temp.isStarted()) {
					shapesList.add(temp);
					canvas.getChildren().add(temp.getShape());
				}
				if (!temp.isEnded()) {
					temp.click(new Point(e.getX(), e.getY()));
					System.out.println("ellipse Done");
				}
				if(temp.isEnded()) {
					canvas.getChildren().remove(temp.getShape());
					MTriangle cloneObj = (MTriangle) temp.clone();
                	shapesList.set(shapesList.size()-1,cloneObj);
            		obj.addEntry(cloneObj,shapesList.size()-1,"Create");
            		canvas.getChildren().add(cloneObj.getShape());
                }
			}
		});
		canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (!temp.isEnded() && temp.isStarted()) {
					temp.setColor(fillColor);
					temp.setBorderColor(borderColor);
					temp.drage(new Point(e.getX(), e.getY()));
				}
			}
		});
	}

	@Override
	public void move(Point p) {
		triangle.getPoints().set(0, cornerOne.getX() + p.getX());
		triangle.getPoints().set(1, cornerOne.getY() + p.getY());
		triangle.getPoints().set(2, cornerTwo.getX() + p.getX());
		triangle.getPoints().set(3, cornerTwo.getY() + p.getY());
		triangle.getPoints().set(4, cornerThree.getX() + p.getX());
		triangle.getPoints().set(5, cornerThree.getY() + p.getY());
	}
	

	@Override
	public boolean isStarted() {
		return doneCorners != 0;
	}

	@Override
	public boolean isEnded() {
		return shapeEnded;
	}

	@Override
	public void setColor(Paint fillColor) {
		triangle.setFill(fillColor);
	}

	@Override
	public void setBorderColor(Paint boarderColor) {
		triangle.setStroke(boarderColor);
	}

	@Override
	public void setBoarderWidth(double width) {
		triangle.setStrokeWidth(width);
	}

	public double getMinX() {
		double ret = Double.MAX_VALUE;
		for (int i = 0; i < NUMOFCORNERS; i++) {
			ret = Math.min(ret, triangle.getPoints().get(i * 2));
		}
		return ret;
	}

	public double getMinY() {
		double ret = Double.MAX_VALUE;
		for (int i = 0; i < NUMOFCORNERS; i++) {
			ret = Math.min(ret, triangle.getPoints().get(i * 2 + 1));
		}
		return ret;
	}

	public double getMaxX() {
		double ret = 0;
		for (int i = 0; i < NUMOFCORNERS; i++) {
			ret = Math.max(ret, triangle.getPoints().get(i * 2));
		}
		return ret;
	}

	public double getMaxY() {
		double ret = 0;
		for (int i = 0; i < NUMOFCORNERS; i++) {
			ret = Math.min(ret, triangle.getPoints().get(i * 2 + 1));
		}
		return ret;
	}

	@Override
    public void resize(Point scale) {
        triangle.setScaleX(scale.getX());
        triangle.setScaleY(scale.getY());
        resetOrigin();
    }

	public Shape getShape() {
		return triangle;
	}

	@Override
	public boolean contains(Point p) {
		return triangle.contains(p.getX(), p.getY());
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	public void click(Point p) {
		if (doneCorners == 0) {
			System.out.println("Triangle Started");
			for (int i = 0; i < NUMOFCORNERS; i++) {
				triangle.getPoints().add(p.getX());
				triangle.getPoints().add(p.getY());
				cornerOne = p;
			}
			doneCorners++;
		} else {
			triangle.getPoints().set(2 * doneCorners, p.getX());
			triangle.getPoints().set(2 * doneCorners + 1, p.getY());
			doneCorners++;
			if (doneCorners == 2) {
				cornerTwo = p;
			} else {
				cornerThree = p;
			}
		}
		if (doneCorners == NUMOFCORNERS) {
			shapeEnded = true;
			System.out.println("Triangle Ended");
		}
	}

	@Override
	public void drage(Point p) {
		triangle.getPoints().set(2 * doneCorners, p.getX());
		triangle.getPoints().set(2 * doneCorners + 1, p.getY());
	}

	@Override
	public void resetOrigin() {
		cornerOne = new Point(triangle.getPoints().get(0), triangle.getPoints().get(1));
		cornerTwo = new Point(triangle.getPoints().get(2), triangle.getPoints().get(3));
		cornerThree = new Point(triangle.getPoints().get(4), triangle.getPoints().get(5));
	}

	@Override
	public Paint getColor() {
		return triangle.getFill();
	}

	@Override
	public Paint getBorderColor() {
		return triangle.getStroke();
	}

	@Override
	public double getBoarderWidth() {
		return triangle.getStrokeWidth();
	}
}
