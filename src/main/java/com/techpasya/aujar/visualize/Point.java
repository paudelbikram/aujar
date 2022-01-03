package com.techpasya.aujar.visualize;

import java.util.Objects;

public class Point {

  private double x;
  private double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Point)) {
      return false;
    }
    Point point = (Point) o;
    return Double.compare(point.getX(), getX()) == 0 &&
        Double.compare(point.getY(), getY()) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getX(), getY());
  }
}
