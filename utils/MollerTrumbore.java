package utils;

import geometry.*;

public class MollerTrumbore {
  // 9 nollor är idealt
  private static final double EPSILON = 0.0000000001;

  public static double rayIntersectsTriangle(
      Vertex rayOrigin,
      Ray rayVector,
      Triangle inTriangle) {
    Vertex vertex0 = inTriangle.getVertex0();
    Vertex vertex1 = inTriangle.getVertex1();
    Vertex vertex2 = inTriangle.getVertex2();
    Vector3d edge1 = new Vector3d();
    Vector3d edge2 = new Vector3d();
    Vector3d h = new Vector3d();
    Vector3d s = new Vector3d(
        rayOrigin.x - vertex0.x,
        rayOrigin.y - vertex0.y,
        rayOrigin.z - vertex0.z);
    Vector3d q = new Vector3d();
    double a, f, u, v;
    edge1 = vertex1.CreateEdge(vertex0);
    edge2 = vertex2.CreateEdge(vertex0);
    h = Maths.crossProduct(rayVector.dir, edge2);
    a = Maths.dotProduct(edge1, h);
    if (a > -EPSILON && a < EPSILON) {
      return -1.0; // This ray is parallel to this triangle.
    }
    f = 1.0 / a;

    double sDotH = Maths.dotProduct(s, h);

    u = f * sDotH;
    if (u < 0.0 || u > 1.0) {
      return -1.0;
    }
    q = Maths.crossProduct(s, edge1);
    double RVdotQ = Maths.dotProduct(rayVector.dir, q);
    v = f * RVdotQ;
    if (v < 0.0 || u + v > 1.0) {
      return -1.0;
    }
    // At this stage we can compute t to find out where the intersection point is on
    // the line.
    double edge2dotQ = Maths.dotProduct(edge2, q);
    double t = f * edge2dotQ;
    if (t > EPSILON) { // ray intersection
      return t;
    } else { // This means that there is a line intersection but not a ray intersection.
      return -1.0;
    }
  }

  public static double rayIntersectsRectangle(
      Vertex rayOrigin,
      Ray rayVector,
      Rectangle inRectangle) {
    Triangle Triangle1 = new Triangle(
        inRectangle.v1,
        inRectangle.v2,
        inRectangle.v3);
    Triangle Triangle2 = new Triangle(
        inRectangle.v1,
        inRectangle.v3,
        inRectangle.v4);

    double firstTriangleIntersect = MollerTrumbore.rayIntersectsTriangle(
        rayOrigin,
        rayVector,
        Triangle1);

    double secondTriangleIntersect = MollerTrumbore.rayIntersectsTriangle(
        rayOrigin,
        rayVector,
        Triangle2);

    if (firstTriangleIntersect > 0.0) {
      return firstTriangleIntersect;
    }

    else if (secondTriangleIntersect > 0.0) {
      return secondTriangleIntersect;
    }

    else {
      return -1.0;
    }
  }

}
