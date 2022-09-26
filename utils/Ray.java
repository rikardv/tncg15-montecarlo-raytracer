package utils;

public class Ray {

  public Vertex start;
  public Vector3d dir;
  public Ray parent = null;
  public Ray child = null;
  public ColorRGB rayColor;
  public int depth;

  public Ray() {
    this.rayColor = new ColorRGB();
    this.start = new Vertex();
    this.dir = new Vector3d();
    this.depth = 0;
  }

  public Ray(Vertex start, Vector3d dir) {
    this.rayColor = new ColorRGB();
    this.start = new Vertex(start.x, start.y, start.z);
    this.dir = new Vector3d(dir.x, dir.y, dir.z);
  }

  public Ray(Vertex start, Vector3d dir, Ray Child) {
    this.rayColor = new ColorRGB();
    this.start = new Vertex(start.x, start.y, start.z);
    this.dir = new Vector3d(dir.x, dir.y, dir.z);
  }

  public void setChild(Ray child) {
    this.child = child;
  }

  public void setColor(double r,double g,double b) {
    this.rayColor = new ColorRGB(r, g, b);
  }
}
