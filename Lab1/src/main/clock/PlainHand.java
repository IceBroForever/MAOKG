package main.clock;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Arrays;

public class PlainHand extends Hand {
    private double cx;
    private double cy;
    private double w;
    private double h;
    private double[] points;

    private Polygon p;

    public PlainHand(double x, double y, double width, double height, Color color){
        cx = x;
        cy = y;
        w = width;
        h = height;

        points = new double[]{
                x, y - 0.8 * height,
                x + 0.5 * width, y,
                x, y + 0.2 * height,
                x - 0.5 * width, y
        };

        p = new Polygon(points);
        p.setFill(color);
        getChildren().add(p);
    }

    private Point2D rotatePoint(Point2D p, double angle) {
        final double s = Math.sin(angle);
        final double c = Math.cos(angle);

        double x = p.getX() - this.cx;
        double y = p.getY() - this.cy;

        double nx = x * c - y * s;
        double ny = x * s + y * c;

        return new Point2D(nx + this.cx, ny + this.cy);
    }

    @Override
    public void setAngle(double angle) {
        p.getPoints().clear();

        double[] nps = Arrays.copyOf(points, points.length);

        for(int i = 0; i < 4; i++) {
            Point2D p = rotatePoint(new Point2D(nps[2 * i], nps[2 * i + 1]), angle);
            nps[2 * i] = p.getX();
            nps[2 * i + 1] = p.getY();
        }

        Double[] npsd = new Double[nps.length];
        for(int i = 0; i < nps.length; i++) {
            npsd[i] = nps[i];
        }

        p.getPoints().addAll(npsd);
    }
}
