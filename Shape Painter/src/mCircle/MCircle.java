package mCircle;

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
import javafx.scene.shape.Ellipse;

public class MCircle extends MEllipse {
    private double radius;
    private static Pane canvas;
    private Ellipse ellipse;

    public MCircle() {
        super();
        ellipse = new Ellipse(); 
    }

    public static void setCanvas(Pane canvas) {
        MCircle.canvas = canvas;
    }

    public void draw(List<IShape>shapesList,Paint fillColor,
			Paint borderColor, UndoRedo obj) {
        MCircle circ = new MCircle();
        System.out.println(canvas);
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if (!circ.isStarted()) {
                	shapesList.add(circ);
                    canvas.getChildren().add(circ.getShape());
                }
                if (!circ.isEnded()) {
                    circ.click(new Point(e.getX(), e.getY()));
                }
                if(circ.isEnded()) {
                	canvas.getChildren().remove(circ.getShape());
                	MCircle cloneObj = (MCircle) circ.clone();
                	shapesList.set(shapesList.size()-1,cloneObj);
            		obj.addEntry(cloneObj,shapesList.size()-1,"Create");
            		canvas.getChildren().add(cloneObj.getShape());
                }
            }
        });
        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if (!circ.isEnded() && circ.isStarted()) {
                    circ.setColor(fillColor);
                    circ.setBorderColor(borderColor);
                    circ.drage(new Point(e.getX(), e.getY()));
                }
            }
        });
    }

    public static Button getShapeButton() {
        // Image circleIcon = new Image("circle.png");
        // Button circleBtn = new Button("", new ImageView(circleIcon));
        Button circleBtn = new Button("Circle");
        return circleBtn;
    }

    @Override
    public void click(Point p) {
        if (!isStarted()) {
            System.out.println("Circle Started");
            ellipse.setCenterX(p.getX());
            ellipse.setCenterY(p.getY());
            setShapeStarted(true);
        } else {
            setShapeEnded(true);
            System.out.println("Circle Ended");
        }
    }

    @Override
    public void drage(Point p) {
        radius = new Point(p.getX() - ellipse.getCenterX(), p.getY() - ellipse
                .getCenterY()).getR();
        ellipse.setRadiusX(radius);
        ellipse.setRadiusY(radius);
    }
    
    @Override
    public void resize(Point scale) {
        ellipse.setScaleX(Math.min(scale.getX(),scale.getY()));
        ellipse.setScaleY(Math.min(scale.getX(),scale.getY()));
        resetOrigin();
    }
}
