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
    ir = (int) (r*255);
    ig = (int) (g*255);
    ib = (int) (b*255);

    if (ir > 255){
      ir = 255;
    }
    if (ig > 255){
      ig = 255;
    }
    if (ib > 255){
      ib = 255;
    }
    if (ir <0){
      ir = 0;
    }
    if (ig < 0){
      ig = 0;
    }
    if (ib < 0){
      ib = 0;
    }

  }

  public void set(double r, double g, double b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  public ColorRGB mult(double mul) {
    return new ColorRGB(
      this.r * mul,
      this.g * mul,
      this.b * mul
    );
  }

  public ColorRGB mult(ColorRGB inRGB) {
    return new ColorRGB(
      this.r * inRGB.r,
      this.g * inRGB.g,
      this.b * inRGB.b
    );
  }

  public ColorRGB add(ColorRGB OtherColor) {
    return new ColorRGB(
      this.r + OtherColor.r,
      this.g + OtherColor.g,
      this.b + OtherColor.b
    );
  }
}
