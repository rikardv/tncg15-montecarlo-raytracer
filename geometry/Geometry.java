package geometry;

import java.beans.Visibility;
import java.util.ArrayList;
import java.util.Random;

import scene.Scene;
import utils.*;

public class Geometry {

  

  public ColorRGB color = new ColorRGB();

  public Vector3d normal;

  public Material material = new Material();

  public double checkIntersect(
      Vertex rayOrigin,
      Ray rayVector) {
    System.out.print("Something went wrong: checkIntersect not overridden");

    return -1.0;
  }

  public Ray bounceRay(Ray rayIn, Vertex intersectionPoint) {
    // räkna på papper och gör en test på bara funktionen
    Vertex start = intersectionPoint;

    normal = getNormal();
    // R = L - 2(N dot L)N

    double NdotL = 2 * Maths.dotProduct(rayIn.dir.norm(), normal.norm());
    
    Vector3d R = (rayIn.dir.norm()).sub(normal.norm().Multiply(NdotL));
    Ray rayOut = new Ray(start, R);
    // rayIn.setChild(rayOut);
    rayOut.depth = rayIn.depth + 1;
    return rayOut;
  }

  public Vector3d getNormal() {
    return normal;
  }

  public Vector3d getNormal(Vertex pointOfIntersection) {
    System.out.println("Something went wrong: getNormal(Vertex) not overriden");
    return new Vector3d();
  }

  public void setNormal(Vector3d normal) {
    this.normal = normal;
  }


  public void SetColor(double R, double G, double B) {
    color = new ColorRGB(R, G, B);
  }

  public boolean hitLight() {
    System.out.println("Something went wrong: hitLight not overriden");
    return false;
  }

  public ColorRGB calculateDirectLight(Light LightSource, Vertex pointOFIntersection, int nrShadowRays,
      ArrayList<Geometry> sceneObjects) {
    int shadowRays = nrShadowRays;
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

      Vector3d di = pointOFIntersection.CreateEdge(yi);
      double abs_di = Math.abs(di.vectorLength());

      // eventuellt "-" på sk
      // cos(omegax) = Nx * di / ||di||
      // cos(omegay) = Ny * di / ||di||
      // TODO - fixa normal för triangel
      double cosOmegax = Maths.dotProduct(di.Multiply(1 / abs_di),
          this instanceof Sphere ? this.getNormal(pointOFIntersection) : this.getNormal());
      double cosOmegay = -Maths.dotProduct(di.Multiply(1 / abs_di), LightSource.normal);

      // Testar med dot approachen, här är den gamla ifall vi vill gå tillbaka till
      // den :P
      // L = L.add(this.color.mult((cosOmegax * cosOmegay) / (abs_di * abs_di)));

      // Något är lurt
      this.setNormal(this instanceof Sphere ? this.getNormal(pointOFIntersection) : this.getNormal());
      Ray shadowRay = new Ray(yi, di);

      double isVis = 1.0;

      if (!isVisible(shadowRay, sceneObjects)) {
        isVis = 0.0;

        // L = new ColorRGB(1,0,0);
        // break;

      }

      L = L.add(this.color.mult(((cosOmegax * cosOmegay) / (abs_di * abs_di)) * isVis));

    }

    float brdfCoeff = this.material.getMaterialBRDF();

    Ld = L.mult((LightArea / (Math.PI * shadowRays)));

    // if (!this->isVisible(shadowRay)) Vk = 0.0f;
    // else Vk = 1.0f;

    return Ld.mult(1);
  }

  public void setMaterial(Material material) {
    this.material = material;
  }

  // inte så bökig funktion. vi kollar vectorn som bildas mellan intersection av
  // ett annat obj
  // blir mindre än vectorn som går från ljuskällan till punkten av vårat obj
  // om vi hittar en kortare vektor betyder det att objsektet är skymt
  // VERKAR INTE FUNGERA
  // på något sett så missar den intersecten eller något...
  public boolean isVisible(Ray shadowRay, ArrayList<Geometry> sceneObjects) {
    Vertex pointOnSphere = shadowRay.start.translate(shadowRay.dir);

    if (pointOnSphere.z < 0 && this instanceof Sphere) {
      // System.out.println("hej");
    }

    double distance = Math.abs(shadowRay.dir.vectorLength());

    double minDist = 1000.0;
    shadowRay.dir = shadowRay.dir.norm();

    for (int i = 0; i < sceneObjects.size(); i++) {
      Geometry obj = sceneObjects.get(i);
      double t = obj.checkIntersect(shadowRay.start, shadowRay);
      // VI KANSKE SKA TA IN ETT NYTT VÄRDE RAY START?

      if (t > 0.0) {
        Vertex intPoint = new Vertex(shadowRay.start);
        intPoint.add(shadowRay.dir.x * t,
            shadowRay.dir.y * t,
            shadowRay.dir.z * t);
        // System.out.println("vi intersektar med något");

        if (Math.abs(intPoint.CreateEdge(shadowRay.start).vectorLength()) < minDist) {
          minDist = Math.abs(intPoint.CreateEdge(shadowRay.start).vectorLength());
        }
      }

    }

    if (minDist + 0.005 < distance) {

      return false;
    }

    return true;
  }

  public Ray getRandomDirection(Ray rayIn, Vertex intersectionPoint) {
        double p = 0.25;
    Random rand = new Random();
    double yi = rand.nextDouble();
    double yi_next = rand.nextDouble();
    double azimuth = (2 * Math.PI * yi) / p;
    double inclination = Math.acos(Math.sqrt(yi_next));

    // Conversion of hemispherical into cartesian
    double x0 = Math.cos(azimuth) * Math.sin(inclination);
    double y0 = Math.sin(azimuth) * Math.sin(inclination);
    double z0 = Math.cos(inclination);

    // Cartesian local system to world coordinate system
    Vector3d N = this instanceof Sphere ? getNormal(intersectionPoint) : getNormal();
    Vector3d X = N.Multiply(Maths.dotProduct(N, rayIn.dir)).sub(rayIn.dir).norm();
    Vector3d Z = N;
    Vector3d Y = Maths.crossProduct(Z, X);

    //Calculate x0*XL + y0*YL + z0*ZL
    double x = x0 * X.x + y0 * Y.x + z0 * Z.x;
    double y = x0 * X.y + y0 * Y.y + z0 * Z.y;
    double z = x0 * X.z + y0 * Y.z + z0 * Z.z;

    Vector3d directionRay = new Vector3d(x, y, z);
   

    // if (azimuth <= 2 * Math.PI) {
    //   // don't know how to handle this case yet
    //   Vector3d invalidRay = new Vector3d(-100, -100, -100);
    //   return new Ray(intersectionPoint, invalidRay);
    // }

    Ray rayOut = new Ray(intersectionPoint, directionRay);
    rayOut.depth = rayIn.depth + 1;

    return rayOut;
  }
}
