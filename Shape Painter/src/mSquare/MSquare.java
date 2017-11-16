package mSquare;

import java.util.List;

import codes.IShape;
import codes.MEllipse;
import codes.MRectangle;
import codes.Point;
import codes.UndoRedo;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class MSquare extends MRectangle {
    private Point origin;
    private Rectangle rectangle;
    private Point lastcorner;
    private static Pane canvas;

    public MSquare() {
        super();
        origin = new Point();
        rectangle = (Rectangle) getShape();
        lastcorner = getLastCorner();
    }

    public static void setCanvas(Pane canvas) {
        MSquare.canvas = canvas;
    }

    public static Button getShapeButton() {
        // Image rectangleIcon = new Image("/square.png");
        // Button rectangleBtn = new Button("", new ImageView(rectangleIcon));
        Button rectangleBtn = new Button("Square");
        return rectangleBtn;
    }

    public void click(Point p) {
        if (!isStarted()) {
            System.out.println("Square Started");
            origin = p;
            rectangle.setX(p.getX());
            rectangle.setY(p.getY());
            setStarted(true);
        } else {
            setEnded(true);
            System.out.println("Square Ended");
        }
        lastcorner.setX(rectangle.getX());
        lastcorner.setY(rectangle.getY());
    }

    public void draw(List<IShape>shapesList,Paint fillColor,
			Paint borderColor, UndoRedo obj) {
        MSquare sq = new MSquare();
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if (!sq.isStarted()) {
                	shapesList.add(sq);
                    canvas.getChildren().add(sq.getShape());
                }
                if (!sq.isEnded()) {
                    sq.click(new Point(e.getX(), e.getY()));
                }
                if(sq.isEnded()) {
                	canvas.getChildren().remove(sq.getShape());
                	MSquare cloneObj = (MSquare) sq.clone();
                	shapesList.set(shapesList.size()-1,cloneObj);
            		obj.addEntry(cloneObj,shapesList.size()-1,"Create");
            		canvas.getChildren().add(cloneObj.getShape());
                }
            }

        });
        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if (!sq.isEnded() && sq.isStarted()) {
                    sq.setColor(fillColor);
                    sq.setBorderColor(borderColor);
                    sq.drage(new Point(e.getX(), e.getY()));
                }
            }
        });
    }

    public void drage(Point p) {
        if (p.getX() < origin.getX() && p.getY() > origin.getY()) {
            rectangle.setX(p.getX());
            rectangle.setWidth(origin.getX() - p.getX());
            rectangle.setY(origin.getY());
            rectangle.setHeight(rectangle.getWidth());
        } else if (p.getX() >= origin.getX() && p.getY() > origin.getY()) {
            rectangle.setX(origin.getX());
            rectangle.setY(origin.getY());
            rectangle.setWidth(p.getX() - origin.getX());
            rectangle.setHeight(rectangle.getWidth());
        } else if (p.getX() < origin.getX() && p.getY() <= origin.getY()) {
            rectangle.setX(p.getX());
            rectangle.setWidth(origin.getX() - p.getX());
            rectangle.setY(origin.getY() - rectangle.getWidth());
            rectangle.setHeight(rectangle.getWidth());
        } else if (p.getX() >= origin.getX() && p.getY() <= origin.getY()) {
            rectangle.setX(origin.getX());
            rectangle.setWidth(p.getX() - origin.getX());
            rectangle.setHeight(rectangle.getWidth());
            rectangle.setY(origin.getY() - rectangle.getWidth());
        }
    }
    
    @Override
    public void resize(Point scale) {
    	rectangle.setScaleX(Math.min(scale.getX(),scale.getY()));
        rectangle.setScaleY(Math.min(scale.getX(),scale.getY()));
        resetOrigin();
    }
}
