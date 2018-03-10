package icsd.patternlock;

/**
 * Created by Argiris Mouratidis on 10-Mar-18.
 */

public class Point {
    private double X;
    private double Y;
    private float pressure;

    public Point(double x, double y, float pressure) {
        X = x;
        Y = y;
        this.pressure = pressure;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }
}