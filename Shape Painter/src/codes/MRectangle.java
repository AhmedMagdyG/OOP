package codes;

import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class MRectangle implements IShape {
    private Point corner;
    private Rectangle rectangle;
    private boolean shapeStarted;
    private boolean shapeEnded;
    private boolean selected;
    private Point lastCorner;
    private static Pane canvas;

    public MRectangle() {
        shapeStarted = false;
        shapeEnded = false;
        selected = false;
        lastCorner = new Point();
        rectangle = new Rectangle();
    }
    
    public IShape clone() {
    	MRectangle temp = new MRectangle();
    	temp.setShapeStarted(this.isStarted());
    	temp.setShapeEnded(this.isEnded());
    	temp.setSelected(this.isSelected());
    	temp.rectangle.setX(this.rectangle.getX());
    	temp.rectangle.setY(this.rectangle.getY());
    	temp.rectangle.setHeight(this.rectangle.getHeight());
    	temp.rectangle.setWidth(this.rectangle.getWidth());
    	temp.setBoarderWidth(this.rectangle.getStrokeWidth());
    	temp.setBorderColor(this.rectangle.getStroke());
    	temp.setColor(this.rectangle.getFill());
    	temp.resetOrigin();    	
    	return temp;
    }

	private void setShapeEnded(boolean ended) {
		this.shapeEnded = ended;
	}

	public void setShapeStarted(boolean started) {
		this.shapeStarted = started;
	}

	public static void setCanvas(Pane canvas) {
        MRectangle.canvas = canvas;
    }

    public static Button getShapeButton() {
        Image RectangleIcon = new Image(
                "/styleSheets/icons/tools/rectangle.png");
        Button rectangleBtn = new Button("", new ImageView(RectangleIcon));
        return rectangleBtn;
    }

    @Override
    public void draw(List<IShape>shapesList,Paint fillColor,
			Paint borderColor, UndoRedo obj) {
        MRectangle rec = new MRectangle();
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if (!rec.isStarted()) {
                	shapesList.add(rec);
                    canvas.getChildren().add(rec.getShape());
                }
                if (!rec.isEnded()) {
                    rec.click(new Point(e.getX(), e.getY()));
                }
                if(rec.isEnded()) {
                	canvas.getChildren().remove(rec.getShape());
                	MRectangle cloneObj = (MRectangle) rec.clone();
                	shapesList.set(shapesList.size()-1,cloneObj);
            		obj.addEntry(cloneObj,shapesList.size()-1,"Create");
            		canvas.getChildren().add(cloneObj.getShape());
                }
            }

        });
        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if (!rec.isEnded() && rec.isStarted()) {
                    rec.setColor(fillColor);
                    rec.setBorderColor(borderColor);
                    rec.drage(new Point(e.getX(), e.getY()));
                }
            }
        });
    }

    @Override
    public void move(Point p) {
        rectangle.setX(p.getX() + lastCorner.getX());
        rectangle.setY(p.getY() + lastCorner.getY());
    }

    public void setStarted(boolean started) {
        shapeStarted = started;
    }

    @Override
    public boolean isStarted() {
        return shapeStarted;
    }

    public void setEnded(boolean ended) {
        shapeEnded = ended;
    }

    @Override
    public boolean isEnded() {
        return shapeEnded;
    }

    @Override
    public void setColor(Paint fillColor) {
        rectangle.setFill(fillColor);
    }

    @Override
    public void setBorderColor(Paint boarderColor) {
        rectangle.setStroke(boarderColor);
    }

    @Override
    public void setBoarderWidth(double width) {
        rectangle.setStrokeWidth(width);
    }

    @Override
    public double getMinX() {
        return rectangle.getX();
    }

    @Override
    public double getMinY() {
        return rectangle.getY();
    }

    @Override
    public double getMaxX() {
        return rectangle.getX() + rectangle.getWidth();
    }

    @Override
    public double getMaxY() {
        return rectangle.getY() + rectangle.getHeight();
    }

    @Override
    public void resize(Point scale) {
        rectangle.setScaleX(scale.getX());
        rectangle.setScaleY(scale.getY());
        resetOrigin();
    }

    @Override
    public boolean contains(Point p) {
        return rectangle.contains(p.getX(), p.getY());
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    public Shape getShape() {
        return rectangle;
    }

    public void click(Point p) {
        if (!shapeStarted) {
            System.out.println("Rectangle Started");
            corner = p;
            rectangle.setX(p.getX());
            rectangle.setY(p.getY());
            shapeStarted = true;
        } else {
            shapeEnded = true;
            System.out.println("Rectangle Ended");
        }
        lastCorner = new Point(rectangle.getX(), rectangle.getY());
    }

    @Override
    public void drage(Point p) {
        if (p.getX() < corner.getX()) {
            rectangle.setWidth(corner.getX() - p.getX());
            rectangle.setX(p.getX());
        } else {
            rectangle.setWidth(p.getX() - corner.getX());
        }
        if (p.getY() < corner.getY()) {
            rectangle.setHeight(corner.getY() - p.getY());
            rectangle.setY(p.getY());
        } else {
            rectangle.setHeight(p.getY() - corner.getY());
        }
    }

    @Override
    public void resetOrigin() {
        lastCorner = new Point(rectangle.getX(), rectangle.getY());
    }
    
    public void resetOrigin(double x,double y) {
        lastCorner = new Point(x, y);
    }

    public Point getLastCorner() {
        return lastCorner;
    }

    @Override
	public Paint getColor() {
		return rectangle.getFill();
	}

	@Override
	public Paint getBorderColor() {
		return rectangle.getStroke();
	}

	@Override
	public double getBoarderWidth() {
		return rectangle.getStrokeWidth();
	}
}
