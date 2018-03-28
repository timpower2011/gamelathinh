package com.example.dainguyen.gametrucxanh;

/**
 * Created by anhkh on 24/03/2017.
 */

public class Line {
    Point startPoint;
    Point endPoint;

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public Line(Point startPoint, Point endPoint) {

        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }
}
