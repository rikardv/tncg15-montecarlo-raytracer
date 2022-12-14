package utils;

import geometry.*;

public class Ray {

  public Vertex start;
  public Vector3d dir;
  public Ray parent = null;
  public Ray child = null;
  public ColorRGB radiance;
  public int depth;
  public Geometry hitObj;
  public Vertex intersectPoint;

  public Ray() {
    this.radiance = new ColorRGB();
    this.start = new Vertex();
    this.dir = new Vector3d();
    this.depth = 0;
  }

  public Ray(Vertex start, Vector3d dir) {
    this.radiance = new ColorRGB();
    this.start = new Vertex(start.x, start.y, start.z);
    this.dir = new Vector3d(dir.x, dir.y, dir.z);
  }

  public Ray(Vertex start, Vector3d dir, Ray Child) {
    this.radiance = new ColorRGB();
    this.start = new Vertex(start.x, start.y, start.z);
    this.dir = new Vector3d(dir.x, dir.y, dir.z);
  }

  public void setChild(Ray child) {
    this.child = child;
  }

  public void setParent(Ray parent) {
    this.parent = parent;
  }

  public void setRadiance(ColorRGB color) {
    this.radiance = color;
  }

  public void setHitObject(Geometry hitObj) {
    this.hitObj = hitObj;
  }

  public void setIntersectionPoint(Vertex intersect) {
    this.intersectPoint = intersect;
  }
}
