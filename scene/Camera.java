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

    double counter = 1;
    if (ray.hitObj == null) {
      return new ColorRGB(0, 0, 0);

    }

    // direct hit on light source
    // THIS PART DOES NOT WORK
    // else {
    //   ray.setRadiance(new ColorRGB(1, 1, 1));
    // }

    // else {
    //   ColorRGB initRadiance = ray.hitObj.calculateDirectLight(scene.light,
    //       ray.intersectPoint,
    //       5,
    //       scene.sceneObjects,
    //       1);
    //   ray.setRadiance(initRadiance);
    // }

    while (ray.parent != null) {
      if (ray.parent.hitObj.material.type == MaterialType.LAMBERTIAN) {
        ColorRGB indirect = new ColorRGB();

        indirect = ray.parent.hitObj.color
            .mult(ray.radiance);

        ColorRGB direct = ray.parent.hitObj.calculateDirectLight(
            scene.light,
            ray.parent.intersectPoint,
            5,
            scene.sceneObjects,
            6);

        ColorRGB sum = direct.add(indirect);
        ray.parent.setRadiance(sum);
      }

      else if (ray.parent.hitObj.material.type == MaterialType.MIRROR) {
        ray.parent.setRadiance(ray.radiance);
      }
      ray = ray.parent;
      counter++;
    }

    return ray.radiance.mult(counter);
  }

  public Ray buildRayPath(
      Scene scene,
      Vertex currentPoint,
      Ray currentRay,
      Vertex outIntersectionPoint) {
    Ray finalRay = new Ray();
    Ray reflectedRay = new Ray();

    for (int i = 0; i < scene.sceneObjects.size(); i++) {
      Geometry obj = scene.sceneObjects.get(i);
      double t = obj.checkIntersect(currentPoint, currentRay);
      if (t > 0.0) {
        Vertex outPoint = new Vertex(currentPoint);
        outPoint.add(
            currentRay.dir.x * t,
            currentRay.dir.y * t,
            currentRay.dir.z * t);
        currentRay.setHitObject(obj);
        currentRay.setIntersectionPoint(outPoint);

        if (obj.material.type == MaterialType.MIRROR) {
          reflectedRay = obj.bounceRay(currentRay, outPoint);
        }

        else {
          reflectedRay = obj.getRandomDirection(currentRay, outPoint);
        }
        // We are done - traverse ray path and add upp light contribution
        if (((reflectedRay.dir.x == -100) ||
            obj.material.type == MaterialType.LIGHT_SOURCE) &&
            obj.material.type != MaterialType.MIRROR) {
          finalRay = currentRay;
          break;
        }
        // Store hit info in ray and go to next
        else {
          reflectedRay.setParent(currentRay);
          finalRay = buildRayPath(scene, outPoint, reflectedRay, outPoint);
        }
      }
      // ray did not hit any geometry
      else {
      }
    }

    return finalRay;
  }

  public void Render(Scene scene) {
    File image = new File("renders/Image6.png");
    BufferedImage buffer = new BufferedImage(
        width,
        height,
        BufferedImage.TYPE_INT_RGB);
    int counter = 0;
    double highest_value = 0.0;

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

        if (currentRay.radiance == null) {
          System.out.println("x");
        }

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

        ColorRGB prevColor = pixelGrid.get(counter);
        ColorRGB normalized_color = prevColor;

        normalized_color = normalized_color.mult(1/highest_value).mult(5);
        normalized_color.intColor();
        Color formattedColor = new Color(normalized_color.ir, normalized_color.ig, normalized_color.ib).brighter();
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
