package geometry;

import java.util.Random;

import utils.*;

public class Geometry {

  public double reflectCoeff = 0.8;

  public ColorRGB color = new ColorRGB();

  public Vector3d normal;

  public boolean checkIntersect(
    Vertex rayOrigin,
    Ray rayVector,
    Vertex outIntersectionPoint
  ) {
    System.out.print("Something went wrong: checkIntersect not overridden");

    return false;
  }

  public Ray bounceRay(Ray rayIn, Vertex intersectionPoint) {
    System.out.println("Something went wrong: bounceray not overriden");

    return new Ray();
  }

  public Vector3d getNormal() {
    return normal;
  }

  public void setReflectionCoeff(double coeff) {
    reflectCoeff = coeff;
  }

  public void SetColor(double R, double G, double B) {
    color = new ColorRGB(R, G, B);
  }

  public boolean hitLight(){
    System.out.println("Something went wrong: hitLight not overriden");
    return false;
  }

  public ColorRGB calculateDirectLight(Light LightSource, Vertex pointOFIntersection) {
    int shadowRays = 60;
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
        Vector3d summedVector = e1.add(e2);
        Vertex yi = LightSource.v1.translate(summedVector);

        // float A = glm::length(glm::cross(v1 - v0, v3 - v0));

        // Disnans mellan intersection point och ljuskällan
        // sk är vectorn mellan ljus och puntk
        // di är distansen mellan di = yi - x

        Vector3d di = yi.CreateEdge(pointOFIntersection);
        double abs_di = di.vectorLength();

        // eventuellt "-" på sk
        // cos(omegax) = Nx * di / ||di||
        // cos(omegay) = Ny * di / ||di||
        // TODO - fixa normal för triangel
        double cosOmegax = Maths.dotProduct(di.Multiply(1 / abs_di), this.getNormal());
        double cosOmegay = Maths.dotProduct(di.invers().Multiply(1 / abs_di), LightSource.normal);

        L = L.add(this.color.mult((cosOmegax * cosOmegay) / (abs_di * abs_di)));

    }

    Ld = L.mult(LightArea / (Math.PI *shadowRays));

    // if (!this->isVisible(shadowRay)) Vk = 0.0f;
    // else Vk = 1.0f;

    return Ld.mult(20);
}
}




