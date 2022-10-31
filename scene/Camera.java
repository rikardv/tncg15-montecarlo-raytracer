package scene;

import geometry.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import utils.*;

public class Camera extends Thread {

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

  public ColorRGB computeRadianceFlow(Ray ray, Scene scene) {


    int depth = ray.depth;

    double scalingFactor = 1.0;

    if (ray.hitObj == null) {
      ray.setRadiance(new ColorRGB(0, 0, 0));
    }

    else if (ray.hitObj.material.type == MaterialType.LIGHT_SOURCE) {
      ray.setRadiance(new ColorRGB(1.0, 1.0, 1.0));
    }

    else if (ray.hitObj.material.type == MaterialType.LAMBERTIAN) {
      ray.setRadiance(ray.hitObj.calculateDirectLight(scene.light, ray.intersectPoint, 5, scene.sceneObjects));
    }

    while (ray.parent != null) {

      // ** If lambertian Material **
      // Multiply radiance of incoming ray with surface color
      // Compute direct light
      // Sum these together
      // Store in ray and go to next parent


        ColorRGB indirect = ray.parent.hitObj.color.mult(ray.radiance).mult(ray.hitObj.material.reflectCoeff);

        ColorRGB direct = ray.parent.hitObj.calculateDirectLight(scene.light, ray.parent.intersectPoint, 5,
            scene.sceneObjects);
        ColorRGB sum = direct.add(indirect);
        ray.parent.setRadiance(sum);



      // ** If mirror **
      // Copy value from parent ray
      // Store in ray and go to next parent
      if (ray.parent.hitObj.material.type == MaterialType.MIRROR) {
        ray.parent.setRadiance(ray.radiance);
      }

      ray = ray.parent;
    }

    scalingFactor = depth > 0 ? (1 / (double) depth) : 1;

    ColorRGB scaled = ray.radiance.mult(scalingFactor);

    return scaled;
  }

  public Ray buildRayPath(Scene scene, Vertex currentPoint, Ray currentRay, Vertex outIntersectionPoint) {
    Ray finalRay = new Ray();
    Ray reflectedRay = new Ray();

    for (int i = 0; i < scene.sceneObjects.size(); i++) {
      Geometry obj = scene.sceneObjects.get(i);
      double t = obj.checkIntersect(currentPoint, currentRay);
      if (t > 0.0) {

        Vertex outPoint = new Vertex(currentPoint);
        outPoint.add(currentRay.dir.x * t,
            currentRay.dir.y * t,
            currentRay.dir.z * t);

        currentRay.setHitObject(obj);
        currentRay.setIntersectionPoint(outPoint);
        // We are done - traverse ray path and add upp light contribution
        if (((currentRay.depth > 5) || obj.material.type == MaterialType.LIGHT_SOURCE)
            && obj.material.type != MaterialType.MIRROR) {
          finalRay = currentRay;
          break;

          // return finalColor;
        }

        // Store hit info in ray and go to next
        else {
          if (obj.material.type == MaterialType.LAMBERTIAN) {
            reflectedRay = obj.getRandomDirection(currentRay, outPoint);
          }
  
          else {
            reflectedRay = obj.bounceRay(currentRay, outPoint);
          }
          reflectedRay.setParent(currentRay);
          finalRay = buildRayPath(scene, outPoint, reflectedRay, outPoint);
        }

      }
      // ray did not hit any geometry
      else {
        if (!(this instanceof Camera)) {
          System.out.println("Something went wrong: Ray did not hit any geometry");
        }
        ;
      }
    }

    return finalRay;
    // return new ColorRGB();
  }

  public void Render(Scene scene) {
    File image = new File("renders/Image6.png");
    BufferedImage buffer = new BufferedImage(
        width,
        height,
        BufferedImage.TYPE_INT_RGB);
    double highest_value = 0.0;
    int counter = 0;

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

        ColorRGB pixelColor = new ColorRGB();

        Ray tempRay = buildRayPath(scene, currentPoint, currentRay, outIntersectionPoint);

        ColorRGB calcColor = computeRadianceFlow(tempRay, scene);

        pixelColor.set(calcColor);

        if (pixelColor.r > highest_value) {
          highest_value = pixelColor.r;
        }

        if (pixelColor.g > highest_value) {
          highest_value = pixelColor.g;
        }

        if (pixelColor.b > highest_value) {
          highest_value = pixelColor.b;
        }

        pixelGrid.add(pixelColor);
      }

    }

    System.out.println("Highest value: " + highest_value);

    for (int z = -height / 2; z < height / 2; z++) {
      for (int x = -width / 2; x < width / 2; x++) {

        ColorRGB normalized_color = pixelGrid.get(counter).mult(1.0 / highest_value);

        // normalized_color = normalized_color.mult(10);
        normalized_color.intColor();
        Color formattedColor = new Color(normalized_color.ir, normalized_color.ig, normalized_color.ib);
        int rgb = formattedColor.getRGB();

        buffer.setRGB(
            (width - 1) - (x + width / 2),
            (height - 1) - (z + height / 2),
            rgb);
        counter++;
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
