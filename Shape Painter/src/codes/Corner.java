package codes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Corner {
    private Rectangle rectangle;
    private static final double cornerSize = 7;
    private Point lastCorner;

    public Corner() {
        rectangle = new Rectangle();
        lastCorner = new Point();
        rectangle.setWidth(cornerSize);
        rectangle.setHeight(cornerSize);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.WHITE);
    }

    public void setPosition(Point p) {
        rectangle.setX(p.getX() - cornerSize / 2);
        rectangle.setY(p.getY() - cornerSize / 2);
    }

    public void setPosition(double X, double Y) {
        rectangle.setX(X - cornerSize / 2);
        rectangle.setY(Y - cornerSize / 2);
    }

    public Rectangle getShape() {
        return rectangle;
    }

    public void setLastCorner(Point p) {
        lastCorner.setPoint(p.getX(), p.getY());
    }

    public Point getLastCorner() {
        return lastCorner;
    }

    public void resetOrigin() {
        lastCorner = new Point(rectangle.getX(), rectangle.getY());
    }

    public void move(Point p) {
        rectangle.setX(lastCorner.getX() + p.getX());
        rectangle.setY(lastCorner.getY() + p.getY());
    }

    public Point getBoxPoint() {
        return new Point(rectangle.getX() + cornerSize / 2, rectangle.getY()
                + cornerSize / 2);
    }
};