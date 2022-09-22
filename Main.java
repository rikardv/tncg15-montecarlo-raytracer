import java.awt.image.BufferedImage;
import java.io.File;
import utils.*;

import javax.imageio.ImageIO;

import geometry.Rectangle;
import scene.Camera;
import scene.Scene;
import utils.Vertex;

import java.util.*;
import java.awt.Color;

// Nästa steg, Superclass geonemty så vi kan stega igenom alla typer av object i scenen;

/*
 * 1.Loop through all pixels.
 * 2.Shoot a ray through each pixel.
 * 3.Compute its intersection point with the scene boundary.
 * 4.Give the color of that rectangle or triangle to the pixel.
 * 5.After the loop over all pixels finished, you find the largestdouble precision value in all of the pixels and you divide the r,g, b values of all pixels by this maximum value.
 * 6.You map the double values to the RGB range 0-255 andcreate an image with these values.
 */


 /*
  * NÄSTA GÅNG! 
    1. Gå igenom funtionerna i rad 89-99 och se så att dom faktiskt fungerar som dom ska
    2. 


  */

public class Main {
    public static void main(String[] args) {

        /**
        Initialize program timer */
        long start = System.nanoTime();

        

        Scene testScene = new Scene();
        Camera camera = new Camera();
        camera.Render(testScene);
        //System.out.println(testScene.getObj(0).checkIntersect(rayOrigin, rayVector, outIntersectionPoint));
        

        /**
         * Print time for program to execute
         */
        long end = System.nanoTime();
        System.out.println("Time for program to execute: " + (end - start) / 1000000000.0F);
    }
}
