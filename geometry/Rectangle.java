package geometry;

import utils.*;

public class Rectangle extends Geometry {

  // vertices for the rectangle
  public Vertex v1;
  public Vertex v2;
  public Vertex v3;
  public Vertex v4;

  /*
   * normal towards you
   * v4-----v3
   * |......|
   * | .....|
   * v1-----v2
   */

  // V2->V3 X V2->V1
  public Vector3d normal;

  // basic default constructor
  public Rectangle() {
    v1 = new Vertex();
    v2 = new Vertex();
    v3 = new Vertex();
    v4 = new Vertex();

    normal = Maths.crossProduct(v1.CreateEdge(v2), v3.CreateEdge(v2));
  }

  public Rectangle(Vertex v1, Vertex v2, Vertex v3, Vertex v4) {
    this.v1 = v1;
    this.v2 = v2;
    this.v3 = v3;
    this.v4 = v4;
    normal = Maths.crossProduct(v3.CreateEdge(v2), v1.CreateEdge(v2));
  }

  public Rectangle(
    Vertex v1,
    Vertex v2,
    Vertex v3,
    Vertex v4,
    double r,
    double g,
    double b
  ) {
    this.v1 = v1;
    this.v2 = v2;
    this.v3 = v3;
    this.v4 = v4;
    this.SetColor(r, g, b);
    normal = Maths.crossProduct(v3.CreateEdge(v2), v1.CreateEdge(v2));
  }

  @Override
  public boolean checkIntersect(
    Vertex rayOrigin,
    Ray ray,
    Vertex outIntersectionPoint
  ) {
    return MollerTrumbore.rayIntersectsRectangle(
      rayOrigin,
      ray,
      this,
      outIntersectionPoint
    );
  }

  @Override
  public Vector3d getNormal() {
    return normal;
  }

  @Override
  public Ray bounceRay(Ray rayIn, Vertex intersectionPoint) {
    //räkna på papper och gör en test på bara funktionen
    Vertex start = intersectionPoint;

    normal = getNormal().invers();
    // R = L - 2(N dot L)N

    double NdotL = 2 * Maths.dotProduct(rayIn.dir.norm(), normal.norm());
    Vector3d R = (rayIn.dir.norm()).sub(normal.norm().Multiply(NdotL));
    Ray rayOut = new Ray(start, R);
    rayIn.setChild(rayOut);
    rayOut.depth = rayIn.depth+1;
    return rayOut;
  }
  @Override
    public boolean hitLight() {
        return false;
    }
}
