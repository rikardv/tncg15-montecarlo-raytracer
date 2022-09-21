import java.awt.image.BufferedImage;
import java.io.File;
import utils.*;

import javax.imageio.ImageIO;

import geometry.Rectangle;
import utils.Vertex;

// just test to push

/*
 * 1.Loop through all pixels.
 * 2.Shoot a ray through each pixel.
 * 3.Compute its intersection point with the scene boundary.
 * 4.Give the color of that rectangle or triangle to the pixel.
 * 5.After the loop over all pixels finished, you find the largestdouble precision value in all of the pixels and you divide the r,g, b values of all pixels by this maximum value.
 * 6.You map the double values to the RGB range 0-255 andcreate an image with these values.
 */

public class Main {
    public static void main(String[] args) {

        Vertex v1 = new Vertex(-1, 0, -20);
        Vertex v2 = new Vertex(1, 0, -20);
        Vertex v3 = new Vertex(1, 0, -10);
        Vertex v4 = new Vertex(-1, 0, -10);

        /**
         * Test MollerTrumbore
         */
        Rectangle inRectangle = new Rectangle(v1, v2, v3, v4);
        Vertex rayOrigin = new Vertex(0, -1, 0);
        Vertex outIntersectionPoint = new Vertex();
        Vector3d rayVector = new Vector3d(0, 1, 0);
        boolean test = MollerTrumbore.rayIntersectsRectangle(rayOrigin, rayVector, inRectangle, outIntersectionPoint);

        System.out.println("Intersect?: " + (test ? "yes" : "no"));
        outIntersectionPoint.printVertex();

    }
}

/**
 * Template for generating an image
 */
// Random random = new Random();

// long start = System.nanoTime();
// int width = 800;
// int height = 800;

// File image = new File("Image.png");
// BufferedImage buffer = new BufferedImage(width, height,
// BufferedImage.TYPE_INT_RGB);

// for (int y = 0; y < height; y++){
// for (int x = 0; x < width; x++){
// buffer.setRGB(x, y, random.nextInt());
// }
// }

// try{
// ImageIO.write(buffer, "PNG", image);
// } catch (Exception e) {
// System.out.println("Could not write image");
// System.exit(1);
// }

// long end = System.nanoTime();
// System.out.println("Loop time: " + (end-start)/1000000000.0F);
// }