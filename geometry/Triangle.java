package geometry;

import java.util.Random;
import utils.*;

public class Triangle extends Geometry {

  Vertex v1, v2, v3;
  public Vector3d normal;

  /*
   * v3
   * | \
   * | \
   * | \
   * v1---v2
   *
   * normal mot dig
   *
   *
   */

  public Triangle() {
    v1 = new Vertex();
    v2 = new Vertex();
    v3 = new Vertex();
    normal = Maths.crossProduct(v1.CreateEdge(v2), v3.CreateEdge(v2)).norm();
  }

  public Triangle(Vertex V1, Vertex V2, Vertex V3) {
    this.v1 = V1;
    this.v2 = V2;
    this.v3 = V3;
    normal = Maths.crossProduct(v3.CreateEdge(v2), v1.CreateEdge(v2)).norm();
  }

  public Triangle(
    Vertex V1,
    Vertex V2,
    Vertex V3,
    double r,
    double g,
    double b
  ) {
    this.v1 = V1;
    this.v2 = V2;
    this.v3 = V3;
    this.SetColor(r, g, b);
    normal = Maths.crossProduct(v3.CreateEdge(v2), v1.CreateEdge(v2)).norm();
  }

  public Vertex getVertex0() {
    return this.v1;
  }

  public Vertex getVertex1() {
    return this.v2;
  }

  public Vertex getVertex2() {
    return this.v3;
  }

  public double checkIntersect(Vertex rayOrigin, Ray rayVector) {
    return MollerTrumbore.rayIntersectsTriangle(rayOrigin, rayVector, this);
  }

  @Override
  public Vector3d getNormal() {
    return normal.norm();
  }

  @Override
  public boolean hitLight() {
    return false;
  }
}
