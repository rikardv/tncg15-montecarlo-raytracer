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
    pixelGrid = new ArrayList<ColorRGB>(height*width);
  }

  public Camera(int height, int width) {
    pixelGrid = new ArrayList<ColorRGB>(height*width);
  }

  public void followRay(Scene scene, Vertex currentPoint, Ray currentRay, Vertex outIntersectionPoint){

    //testing max value of direct light
    ColorRGB test = new ColorRGB();

            for (int i = 0; i < scene.sceneObjects.size(); i++) {
          Geometry obj = scene.sceneObjects.get(i);
          if (
            obj.checkIntersect(currentPoint, currentRay, outIntersectionPoint)
          ) {
            //träffar vi ljus, Klart, Eller för djupt, KLART
            if (obj.hitLight() || currentRay.depth > 5){
              //test, vit pixel om den träffar ljuskällan inom 5 studs;
              currentRay.rayColor = obj.color;
              return;
            }
          
            // Om vi får en studs
            if (obj.reflectCoeff == 1) {
              Ray reflectedRay = obj.bounceRay(
                currentRay,
                outIntersectionPoint
              );

              Vertex pointOfReflection = outIntersectionPoint;

              followRay(scene, pointOfReflection, reflectedRay, outIntersectionPoint);
            }

            else {
              currentRay.rayColor = obj.calculateDirectLight(scene.light, outIntersectionPoint);
              if(currentRay.rayColor.r > test.r){
                test = new ColorRGB(currentRay.rayColor.r,currentRay.rayColor.g,currentRay.rayColor.b);
              }
            }
           
            // System.out.println("Color of intersected oj r:"+ obj.color.r+ " g:" +
            // obj.color.g + " b:" +obj.color.b);

          }
          // ray did not hit any geometry
          else {
            
          }
        }
       // System.out.println("Max color from direct light = " + test.r);
  }

  public void Render(Scene scene) {
    File image = new File("renders/Image3.png");
    BufferedImage buffer = new BufferedImage(
      width,
      height,
      BufferedImage.TYPE_INT_RGB
    );

    for (int z = -height / 2; z < height / 2; z++) {
      for (int x = -width / 2; x < width / 2; x++) {
        ColorRGB pixelColor = new ColorRGB();

        Vertex currentCameraVertex = new Vertex(
          planeCenter.x,
          planeCenter.y + (float) x / (width / 2),
          planeCenter.z + (float) z / (height / 2)
        );
        Vector3d RayVector = currentCameraVertex.CreateEdge(eyePosition);
        Ray currentRay = new Ray(eyePosition, RayVector);
        Vertex outIntersectionPoint = new Vertex();
        Vertex currentPoint = eyePosition;

        followRay(scene, currentPoint, currentRay, outIntersectionPoint);

        while(currentRay.child != null){
          currentRay = currentRay.child;
        }

        if (currentRay.rayColor == null){
          System.out.println("x");
        }
        pixelColor = currentRay.rayColor;

        pixelColor.intColor();
        
        Color myColour = new Color(pixelColor.ir, pixelColor.ig, pixelColor.ib);
        pixelGrid.add(pixelColor);
        int rgb = myColour.getRGB();
        buffer.setRGB(
          (width - 1) - (x + width / 2),
          (height - 1) - (z + height / 2),
          rgb
        );
        // Scene[alla scene object];

        // Scene.forEach(obj=> obj.checkintersect())

        // Vertex för kameraplanspunkten = centrum kameraplan + x/(height/2)
        // Vekorn blir då kameraplanspunkt -ögacoord

        // sista steg
        // buffer.setRGB(x, y, 'värde på pixel');
      }
    }

    try {
      ImageIO.write(buffer, "PNG", image);
    } catch (Exception e) {
      System.out.println("Could not write image");
      System.exit(1);
    }
  }

  

  
  // sudo för render
  /*
   * for (heigt)
   * // for (int y = -height/2; z < height/2; y++){
   * for (int x = -width/2; x < width/2; x++){
   * ColorRGB pixelColor = new ColorRGB();
   * Vertex CurrentCameraVertex = new Vertex(planeCenter.x+
   * x/(width/2),0,planeCenter.z+ z/height/2);
   * Vector3d RayVector = currentCameraVertex.sub(eyePosition);
   *
   * Scene[alla scene object];
   *
   * Scene.forEach(obj=> obj.checkintersect())
   *
   * Vertex för kameraplanspunkten = centrum kameraplan + x/(height/2)
   * Vekorn blir då kameraplanspunkt -ögacoord
   *
   * sista steg
   * buffer.setRGB(x, y, 'värde på pixel');
   * }
   * }
   *
   */

}
