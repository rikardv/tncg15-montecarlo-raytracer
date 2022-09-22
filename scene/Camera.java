package scene;

import utils.*;
import utils.Vector3d;
import utils.Vertex;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.awt.Color;
import geometry.*;

import javax.imageio.ImageIO;

public class Camera {

    Vertex eyePosition = new Vertex(-1, 0.0, 0);
    double dist2Display;
    int[][] pixelGrid;
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
        pixelGrid = new int[height][width];
    }

    public Camera(int height, int width) {
        pixelGrid = new int[height][width];
    }

    public void Render(Scene scene) {
        File image = new File("Image1.png");
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int z = -height / 2; z < height / 2; z++) {
            for (int x = -width / 2; x < width / 2; x++) {
                ColorRGB pixelColor = new ColorRGB();

                Vertex currentCameraVertex = new Vertex(planeCenter.x, planeCenter.y + (float) x / (width / 2),
                        planeCenter.z + (float) z / (height / 2));
                Vector3d RayVector = currentCameraVertex.CreateEdge(eyePosition);
                Ray CurrentRay = new Ray(eyePosition, RayVector);
                Vertex outIntersectionPoint = new Vertex();
                Vertex currentPoint = eyePosition;


                

               

                for (int i = 0; i < scene.sceneObjects.size(); i++) {
                    Geometry obj = scene.sceneObjects.get(i);
                    if (obj.checkIntersect(currentPoint, CurrentRay, outIntersectionPoint)) {
                        
                        // Om vi får en studs
                        if (obj.reflectCoeff == 1) {


                            Ray reflectedRay = obj.bounceRay(CurrentRay, outIntersectionPoint);
                            Vertex pointOfReflection = outIntersectionPoint;
                            
                            // Geometry targeGeometry = LoopReflect(reflectedRay, pointOfReflection, scene.sceneObjects);

                            for (int j = 0; j < scene.sceneObjects.size(); j++) {
                                Geometry loopobj = scene.sceneObjects.get(j);
                                outIntersectionPoint = new Vertex();
                                if (loopobj.checkIntersect(pointOfReflection, reflectedRay, outIntersectionPoint)) {
                                    System.out.print("we got reflection");
                                    pixelColor = loopobj.color;
                    
                                }
                            } 
                            
                            
                            // outIntersectionPoint nollställs i moller så vi kan låta den vara kvar
                            // System.out.println("Reflection!");
                            //pixelColor = obj.color;
                        }
                        
                        else{pixelColor = obj.color;}
                        // System.out.println("Color of intersected oj r:"+ obj.color.r+ " g:" +
                        // obj.color.g + " b:" +obj.color.b);

                    } else {

                    }

                }
               
                pixelColor.intColor();
                Color myColour = new Color(pixelColor.ir, pixelColor.ig, pixelColor.ib);
                int rgb = myColour.getRGB();
                buffer.setRGB((width - 1) - (x + width / 2), (height - 1) - (z + height / 2), rgb);

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