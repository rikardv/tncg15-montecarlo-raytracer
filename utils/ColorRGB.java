package utils;

public class ColorRGB {

  public double r, g, b;
  public int ir, ig, ib;
  public int RGB;

  public ColorRGB(double r, double g, double b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  public ColorRGB() {
    this.r = 0.0;
    this.g = 0.0;
    this.b = 0.0;
  }

  public void intColor() {
    ir = (int) r;
    ig = (int) g;
    ib = (int) b;
  }

  public void set(double r, double g, double b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }
}
