package codes;

import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

public class MEllipse implements IShape {
	private boolean shapeStarted;
	private boolean shapeEnded;
	private boolean selected;
	protected Ellipse ellipse;
	private Point topLeft;
	private Point lastCenter;
	private static Pane canvas;

	public IShape clone() {
		MEllipse temp = new MEllipse();
		temp.setShapeStarted(this.isStarted());
		temp.setShapeEnded(this.getShapeEnded());
		temp.setSelected(this.isSelected());
		temp.ellipse = new Ellipse(this.ellipse.getCenterX(), this.ellipse.getCenterY(), this.ellipse.getRadiusX(),
				ellipse.getRadiusY());
		temp.topLeft = new Point(this.ellipse.getCenterX() - this.ellipse.getRadiusX(),
				this.ellipse.getCenterY() - this.ellipse.getRadiusY());
		temp.resetOrigin();
		temp.setBoarderWidth(this.ellipse.getStrokeWidth());
		temp.setBorderColor(this.ellipse.getStroke());
		temp.setColor(this.ellipse.getFill());
		return temp;
	}

	public MEllipse() {
		ellipse = new Ellipse();
		shapeStarted = false;
		shapeEnded = false;
		selected = false;
	}

	public static void setCanvas(Pane canvas) {
		MEllipse.canvas = canvas;
	}

	public static Button getShapeButton() {
		Image ellipseIcon = new Image("/styleSheets/icons/tools/ellipse.png");
		Button ellipseBtn = new Button("", new ImageView(ellipseIcon));
		return ellipseBtn;
	}

	public boolean getShapeEnded() {
		return this.shapeEnded;
	}

	public void setShapeStarted(boolean set) {
		shapeStarted = set;
	}

	public void setShapeEnded(boolean set) {
		shapeEnded = set;
	}

	@Override
	public void move(Point p) {
		ellipse.setCenterX(lastCenter.getX() + p.getX());
		ellipse.setCenterY(lastCenter.getY() + p.getY());
	}

	@Override
	public boolean isStarted() {
		return shapeStarted;
	}

	public void setLastCenter(double x, double y) {
		lastCenter = new Point(x, y);
	}

	@Override
	public boolean isEnded() {
		return shapeEnded;
	}

	@Override
	public void setColor(Paint fillColor) {
		ellipse.setFill(fillColor);
	}

	@Override
	public void setBorderColor(Paint boarderColor) {
		ellipse.setStroke(boarderColor);
	}

	@Override
	public void setBoarderWidth(double width) {
		ellipse.setStrokeWidth(width);
	}

	@Override
	public double getMinX() {
		return ellipse.getCenterX() - ellipse.getRadiusX();
	}

	@Override
	public double getMinY() {
		return ellipse.getCenterY() - ellipse.getRadiusY();
	}

	@Override
	public double getMaxX() {
		return ellipse.getCenterX() + ellipse.getRadiusX();
	}

	@Override
	public double getMaxY() {
		return ellipse.getCenterY() + ellipse.getRadiusY();
	}

	@Override
	public void resize(Point scale) {
		ellipse.setScaleX(scale.getX());
		ellipse.setScaleY(scale.getY());
		resetOrigin();
	}

	@Override
	public boolean contains(Point p) {
		return ellipse.contains(p.getX(), p.getY());
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Shape getShape() {
		return ellipse;
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	@Override
	public void click(Point p) {
		if (!shapeStarted) {
			System.out.println("Ellipse Started");
			topLeft = p;
			shapeStarted = true;
		} else {
			shapeEnded = true;
			lastCenter = new Point(ellipse.getCenterX(), ellipse.getCenterY());
			System.out.println("Ellipse Ended");
		}
	}

	@Override
	public void drage(Point p) {
		ellipse.setCenterX(topLeft.getX() + (p.getX() - topLeft.getX()) / 2);
		ellipse.setCenterY(topLeft.getY() + (p.getY() - topLeft.getY()) / 2);
		ellipse.setRadiusX(Math.abs(p.getX() - topLeft.getX()) / 2);
		ellipse.setRadiusY(Math.abs(p.getY() - topLeft.getY()) / 2);
	}

	@Override
	public void resetOrigin() {
		lastCenter = new Point(ellipse.getCenterX(), ellipse.getCenterY());
	}

	@Override
	public void draw(List<IShape>shapesList,Paint fillColor,
			Paint borderColor, UndoRedo obj) {
		MEllipse ell = new MEllipse();
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (!ell.isStarted()) {
					shapesList.add(ell);
					canvas.getChildren().add(ell.getShape());
				}
				if (!ell.isEnded()) {
					ell.click(new Point(e.getX(), e.getY()));
				}
				if(ell.isEnded()) {
                	System.out.println("here");
                	canvas.getChildren().remove(ell.getShape());
                	MEllipse cloneObj = (MEllipse) ell.clone();
                	shapesList.set(shapesList.size()-1,cloneObj);
            		obj.addEntry(cloneObj,shapesList.size()-1,"Create");
            		canvas.getChildren().add(cloneObj.getShape());
                }
			}
		});
		canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (!ell.isEnded() && ell.isStarted()) {
					ell.setColor(fillColor);
					ell.setBorderColor(borderColor);
					ell.drage(new Point(e.getX(), e.getY()));
				}
			}
		});
	}

	@Override
	public Paint getColor() {
		return ellipse.getFill();
	}

	@Override
	public Paint getBorderColor() {
		return ellipse.getStroke();
	}

	@Override
	public double getBoarderWidth() {
		return ellipse.getStrokeWidth();
	}

}
