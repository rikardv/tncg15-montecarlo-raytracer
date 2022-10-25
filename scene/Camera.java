package scene;

import geometry.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import utils.*;

public class Camera {

  Vertex eyePosition = new Vertex(-1, 0.0, 0);
  double dist2Display;
  ArrayList<ColorRGB> pixelGrid;
  Vertex planeCenter = new Vertex(0, 0, 0);

  /*
   * Display
   * / |-
   * / | -
   * Ö-d2d--| -
   * \ | -
   * \ |-
   *
   *
   * |
   * b
   *
   * @|
   * Ö---a----
   *
   * a^2 + b^2 = c^2
   *
   *
   *
   *
   * Ö---------*
   *
   * Camera plane size 2X2 Gobal space coords
   *
   *
   */

  int height;
  int width;

  public Camera() {
    height = 800;
    width = 800;
    pixelGrid = new ArrayList<ColorRGB>(height * width);
  }

  public Camera(int height, int width) {
    pixelGrid = new ArrayList<ColorRGB>(height * width);
  }

  public ColorRGB traverseRayPath(Ray ray) {
    while (ray.child != null) {
      ray = ray.child;
    }
    return ray.radiance;
  }

  public void followRay(Scene scene, Vertex currentPoint, Ray currentRay, Vertex outIntersectionPoint) {

    for (int i = 0; i < scene.sceneObjects.size(); i++) {
      Geometry obj = scene.sceneObjects.get(i);
      double t = obj.checkIntersect(currentPoint, currentRay);
      if (t > 0.0) {

        Vertex outPoint = new Vertex(currentPoint);
        outPoint.add(currentRay.dir.x * t,
            currentRay.dir.y * t,
            currentRay.dir.z * t);

        // We are done
        if (currentRay.depth > 10) {
          return;
        }

        if (obj.material.type == MaterialType.LIGHT_SOURCE) {
          ColorRGB light = new ColorRGB(1.0, 1.0, 1.0);
          currentRay.setRadiance(light);
          return;
        }

        // Compute direct light and store into ray
        if (obj.material.type == MaterialType.LAMBERTIAN) {
          ColorRGB directLight = obj.calculateDirectLight(scene.light, outPoint, 1,scene.sceneObjects);
          currentRay.setRadiance(directLight);
          return;
        }

        // Copy radiance from previous ray
        else if (obj.material.type == MaterialType.MIRROR || obj.material.type == MaterialType.TRANSPARENT) {
          ColorRGB prevRadiance = currentRay.child.radiance;
          currentRay.setRadiance(prevRadiance);
        }

        Ray reflectedRay = obj.bounceRay(currentRay, outPoint);

        Vertex pointOfReflection = outPoint;


        followRay(scene, pointOfReflection, reflectedRay, outPoint);

      }
      // ray did not hit any geometry
      else {
        if (!(this instanceof Camera)){
          System.out.println("Something went wrong: Ray did not hit any geometry");
        }
      }
    }
  }

  public void Render(Scene scene) {
    File image = new File("renders/Image5.png");
    BufferedImage buffer = new BufferedImage(
        width,
        height,
        BufferedImage.TYPE_INT_RGB);

    for (int z = -height / 2; z < height / 2; z++) {
      for (int x = -width / 2; x < width / 2; x++) {

        Vertex currentCameraVertex = new Vertex(
            planeCenter.x,
            planeCenter.y + (float) x / (width / 2),
            planeCenter.z + (float) z / (height / 2));
        Vector3d RayVector = currentCameraVertex.CreateEdge(eyePosition);
        Ray currentRay = new Ray(eyePosition, RayVector);
        Vertex outIntersectionPoint = new Vertex();
        Vertex currentPoint = eyePosition;

        followRay(scene, currentPoint, currentRay, outIntersectionPoint);
        ColorRGB pixelColor = traverseRayPath(currentRay);

        if (currentRay.radiance == null) {
          System.out.println("x");
        }

        pixelColor.intColor();

        Color myColour = new Color(pixelColor.ir, pixelColor.ig, pixelColor.ib);
        pixelGrid.add(pixelColor);
        int rgb = myColour.getRGB();
        buffer.setRGB(
            (width - 1) - (x + width / 2),
            (height - 1) - (z + height / 2),
            rgb);
      }
    }

    try {
      ImageIO.write(buffer, "PNG", image);
    } catch (Exception e) {
      System.out.println("Could not write image");
      System.exit(1);
    }
  }
}
