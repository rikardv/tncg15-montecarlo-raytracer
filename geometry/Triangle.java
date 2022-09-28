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
    normal = Maths.crossProduct(v1.CreateEdge(v2), v3.CreateEdge(v2)).norm();
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
    normal = Maths.crossProduct(v1.CreateEdge(v2), v3.CreateEdge(v2)).norm();
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

  public boolean checkIntersect(
    Vertex rayOrigin,
    Ray rayVector,
    Vertex outIntersectionPoint
  ) {
    return MollerTrumbore.rayIntersectsTriangle(
      rayOrigin,
      rayVector,
      this,
      outIntersectionPoint
    );
  }

  @Override
  public Vector3d getNormal() {
    return normal.norm();
  }

  @Override
  public Ray bounceRay(Ray rayIn, Vertex intersectionPoint) {
    //räkna på papper och gör en test på bara funktionen
    Vertex start = intersectionPoint;

    normal = getNormal().invers();
    // R = L - 2(N dot L)N

    double NdotL = 2 * Maths.dotProduct(rayIn.dir.norm(), normal.norm());
    Vector3d R = (rayIn.dir.norm()).sub(normal.norm().Multiply(NdotL));
    Ray rayOut = new Ray(start, R, rayIn);

    return rayOut;
  }

  @Override
    public boolean hitLight() {
        return false;
    }

     @Override
    public ColorRGB calculateDirectLight(Light LightSource, Vertex pointOFIntersection) {
        int shadowRays = 30;
        // var q = u * ( v1 - v0) + v * (v2 - v0);

        ColorRGB L = new ColorRGB(0, 0, 0);
        ColorRGB Ld = new ColorRGB(0, 0, 0);

        Vector3d edge1 = LightSource.v2.CreateEdge(LightSource.v1);
        Vector3d edge2 = LightSource.v3.CreateEdge(LightSource.v1);

        // float A = glm::length(glm::cross(v1 - v0, v3 - v0));
        double LightArea = Maths.crossProduct(edge1, edge2).vectorLength();

        for (int i = 0; i < shadowRays; i++) {
            // random numbers
            double u = new Random().nextDouble();
            double v = new Random().nextDouble();

            // edges of the lightsource
            Vector3d e1 = LightSource.v2.CreateEdge(LightSource.v1).Multiply(u);
            Vector3d e2 = LightSource.v3.CreateEdge(LightSource.v1).Multiply(v);
            Vector3d q = e1.add(e2);

            // float A = glm::length(glm::cross(v1 - v0, v3 - v0));

            // Disnans mellan intersection point och ljuskällan
            // sk är vectorn mellan ljus och puntk
            // di är distansen mellan di = yi - x

            Vector3d di = LightSource.v1.CreateEdge(pointOFIntersection);
            double abs_di = di.vectorLength();

            // eventuellt "-" på sk
            // cos(omegax) = Nx * di / ||di||
            // cos(omegay) = Ny * di / ||di||
            // TODO - fixa normal för triangel
            double cosOmegax = Maths.dotProduct(di.invers().Multiply(1 / abs_di), this.normal);
            double cosOmegay = Maths.dotProduct(di.invers().Multiply(1 / abs_di), LightSource.normal);

            L = L.add(this.color.mult((cosOmegax * cosOmegay) / (abs_di * abs_di)));

        }

        Ld = L.mult(LightArea / (Math.PI *shadowRays));

        // if (!this->isVisible(shadowRay)) Vk = 0.0f;
        // else Vk = 1.0f;

        return Ld.mult(20);
    }
}
