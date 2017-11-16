package codes;

import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class MLine implements IShape {
    private Line line;
    private boolean shapeStarted;
    private boolean shapeEnded;
    private boolean selected;
    private Point lastLineStart;
    private Point lastLineEnd;
    private static Pane canvas;

    public MLine() {
        line = new Line();
        shapeStarted = false;
        shapeEnded = false;
        selected = false;
    }
    public IShape clone() {
    	MLine temp = new MLine();
    	temp.setShapeStarted(this.isStarted());
    	temp.setShapeEnded(this.isEnded());
    	temp.setSelected(this.isSelected());
    	temp.line.setStartX(this.line.getStartX());
    	temp.line.setStartY(this.line.getStartY());
    	temp.line.setEndX(this.line.getEndX());
    	temp.line.setEndY(this.line.getEndY());
    	temp.resetOrigin();    
    	temp.setBoarderWidth(this.line.getStrokeWidth());
    	temp.setBorderColor(this.line.getStroke());
    	temp.setColor(this.line.getFill());
    	return temp;
    }
    
    private void setShapeEnded(boolean ended) {
		this.shapeEnded = ended;
	}

	private void setShapeStarted(boolean started) {
		this.shapeStarted = started;
	}

    public static void setCanvas(Pane canvas) {
        MLine.canvas = canvas;
    }

    public static Button getShapeButton() {
        Image lineIcon = new Image("/styleSheets/icons/tools/line.png");
        Button lineBtn = new Button("", new ImageView(lineIcon));
        return lineBtn;
    }

    public boolean isStarted() {
        return shapeStarted;
    }

    public boolean isEnded() {
        return shapeEnded;
    }

    public void setLastLineStart(double x, double y) {
        lastLineStart = new Point(x, y);
    }

    public void setLastLineEnd(double x, double y) {
        lastLineEnd = new Point(x, y);
    }

    @Override
    public void move(Point p) {
        line.setStartX(lastLineStart.getX() + p.getX());
        line.setStartY(lastLineStart.getY() + p.getY());
        line.setEndX(lastLineEnd.getX() + p.getX());
        line.setEndY(lastLineEnd.getY() + p.getY());
    }

    @Override
    public void setColor(Paint fillColor) {
        line.setFill(fillColor);
    }

    @Override
    public void setBorderColor(Paint boarderColor) {
        line.setStroke(boarderColor);
    }

    @Override
    public void setBoarderWidth(double width) {
        line.setStrokeWidth(width);
    }

    @Override
    public double getMinX() {
        return Math.min(line.getEndX(), line.getStartX());
    }

    @Override
    public double getMinY() {
        return Math.min(line.getEndY(), line.getStartY());
    }

    @Override
    public double getMaxX() {
        return Math.max(line.getEndX(), line.getStartX());
    }

    @Override
    public double getMaxY() {
        return Math.max(line.getEndY(), line.getStartY());
    }

    @Override
    public void resize(Point scale) {
        line.setScaleX(scale.getX());
        line.setScaleY(scale.getY());
        resetOrigin();
    }

    @Override
    public boolean contains(Point p) {
        return line.contains(p.getX(), p.getY());
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public Shape getShape() {
        return line;
    }

    @Override
    public void click(Point p) {
        if (!shapeStarted) {
            System.out.println("Line Started");
            line.setStartX(p.getX());
            line.setStartY(p.getY());
            line.setEndX(p.getX());
            line.setEndY(p.getY());
            lastLineStart = new Point(p.getX(), p.getY());
            lastLineEnd = new Point(p.getX(), p.getY());
            shapeStarted = true;
        } else {
            line.setEndX(p.getX());
            line.setEndY(p.getY());
            lastLineEnd = new Point(p.getX(), p.getY());
            shapeEnded = true;
            System.out.println("Line Ended");
        }
    }

    @Override
    public void drage(Point p) {
        line.setEndX(p.getX());
        line.setEndY(p.getY());
    }

    @Override
    public void resetOrigin() {
        lastLineStart = new Point(line.getStartX(), line.getStartY());
        lastLineEnd = new Point(line.getEndX(), line.getEndY());
    }

    public void draw(List<IShape>shapesList,Paint fillColor,
			Paint borderColor, UndoRedo obj) {
        MLine li = new MLine();
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if (!li.isStarted()) {
                	shapesList.add(li);
                    canvas.getChildren().add(li.getShape());
                }
                if (!li.isEnded()) {
                    li.click(new Point(e.getX(), e.getY()));
                }
                if(li.isEnded()) {
                	canvas.getChildren().remove(li.getShape());
                	MLine cloneObj = (MLine) li.clone();
                	shapesList.set(shapesList.size()-1,cloneObj);
            		obj.addEntry(cloneObj,shapesList.size()-1,"Create");
            		canvas.getChildren().add(cloneObj.getShape());
                }
            }

        });
        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if (!li.isEnded() && li.isStarted()) {
                    li.setColor(fillColor);
                    li.setBorderColor(borderColor);
                    li.drage(new Point(e.getX(), e.getY()));
                }
            }
        });
    }

	@Override
	public Paint getColor() {
		return line.getFill();
	}

	@Override
	public Paint getBorderColor() {
		return line.getStroke();
	}

	@Override
	public double getBoarderWidth() {
		return line.getStrokeWidth();
	}
}
