package codes;

public class Point {
    private double _X;
    private double _Y;

    public Point() {
        _X = 0;
        _Y = 0;
    }

    public Point(double X, double Y) {
        this._X = X;
        this._Y = Y;
    }

    public double getX() {
        return _X;
    }

    public double getY() {
        return _Y;
    }

    public void setX(double X) {
        _X = X;
    }

    public void setY(double Y) {
        _Y = Y;
    }

    public void setPoint(double X, double Y) {
        _X = X;
        _Y = Y;
    }

    public double getR() {
        return Math.sqrt(_X * _X + _Y * _Y);
    }

}
