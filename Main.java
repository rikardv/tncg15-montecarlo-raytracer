import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args){

        Random random = new Random();

        long start = System.nanoTime();
        int width = 1600;
        int height = 800;

        File image = new File("Image.png");
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                buffer.setRGB(x, y, random.nextInt());
            }
        }

        try{
            ImageIO.write(buffer, "PNG", image);
        } catch (Exception e) {
            System.out.println("Could not write image");
            System.exit(1);
        }

        long end = System.nanoTime();
        System.out.println("Loop time: " + (end-start)/1000000000.0F);
    }
}