package codes;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public interface IShape {

    public static Button getShapeButton() {
        return null;
    }

    public static void setCanvas(Pane canvas) {
    }

    public void draw(List<IShape>shapesList,Paint paint, Paint paint2, UndoRedo obj);

    public void move(Point p);

    public boolean isStarted();

    public boolean isEnded();

    public double getMinX();

    public double getMinY();

    public double getMaxX();

    public double getMaxY();

    public void setColor(Paint color);

    public void setBorderColor(Paint color);

    public void setBoarderWidth(double width);
    
    public Paint getColor();

    public Paint getBorderColor();

    public double getBoarderWidth();

    public void resize(Point scale);

    public void setSelected(boolean Selected);

    public boolean isSelected();

    public boolean contains(Point p);

    public void click(Point p);

    public void drage(Point p);

    public void resetOrigin();

    public Shape getShape();
    
    public IShape clone();

}
