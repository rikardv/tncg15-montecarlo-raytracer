package scene;

import geometry.Geometry;
import geometry.Rectangle;
import geometry.Triangle;
import geometry.Light;
import java.util.ArrayList;
import java.util.Random;

import utils.Vertex;
import java.util.*;

public class Scene {

  //Rummet
  Rectangle W1 = new Rectangle(
    new Vertex(0, 6, -5),
    new Vertex(10, 6, -5),
    new Vertex(10, 6, 5),
    new Vertex(0, 6, 5),
    new Random().nextDouble(),new Random().nextDouble(),new Random().nextDouble()
  );

  Rectangle W2 = new Rectangle(
    new Vertex(10, 6, -5),
    new Vertex(13, 0, -5),
    new Vertex(13, 0, 5),
    new Vertex(10, 6, 5),
    new Random().nextDouble(),new Random().nextDouble(),new Random().nextDouble()
  );
  Rectangle W3 = new Rectangle(
    new Vertex(13, 0, -5),
    new Vertex(10, -6, -5),
    new Vertex(10, -6, 5),
    new Vertex(13, 0, 5),
    new Random().nextDouble(),new Random().nextDouble(),new Random().nextDouble()
  );
  Rectangle W4 = new Rectangle(
    new Vertex(10, -6, -5),
    new Vertex(0, -6, -5),
    new Vertex(0, -6, 5),
    new Vertex(10, -6, 5),
    new Random().nextDouble(),new Random().nextDouble(),new Random().nextDouble()
  );
  Rectangle W5 = new Rectangle(
    new Vertex(0, -6, -5),
    new Vertex(-3, 0, -5),
    new Vertex(-3, 0, 5),
    new Vertex(0, -6, 5),
    new Random().nextDouble(),new Random().nextDouble(),new Random().nextDouble()
  );
  Rectangle W6 = new Rectangle(
    new Vertex(-3, 0, -5),
    new Vertex(0, 6, -5),
    new Vertex(0, 6, 5),
    new Vertex(-3, 0, 5),
    new Random().nextDouble(),new Random().nextDouble(),new Random().nextDouble()
  );
  Rectangle test = new Rectangle(
    new Vertex(10, -1, -1),
    new Vertex(10, 1, -1),
    new Vertex(10, 1, 1),
    new Vertex(10, -1, 1),
    new Random().nextDouble(),new Random().nextDouble(),new Random().nextDouble()
  );

  /**
   * Floor
   */
  Rectangle Floor_W1 = new Rectangle(
    new Vertex(10, 6, -5),
    new Vertex(0, 6, -5),
    new Vertex(0, -6, -5),
    new Vertex(10, -6, -5),
    1.0,1.0,1.0
  );
  Triangle Floor_W2 = new Triangle(
    new Vertex(0, 6, -5),
    new Vertex(-3, 0, -5),
    new Vertex(0, -6, -5),
    1.0,1.0,1.0
  );
  Triangle Floor_W3 = new Triangle(
    new Vertex(10, -6, -5),
    new Vertex(13, 0, -5),
    new Vertex(10, 6, -5),
    1.0,1.0,1.0
  );

  /**
   * Roof
   */
  Rectangle Roof_W1 = new Rectangle(
    new Vertex(10, 6, 5),
    new Vertex(10, -6, 5),
    new Vertex(0, -6, 5),
    new Vertex(0, 6, 5),
    1.0,1.0,1.0
  );
  Triangle Roof_W2 = new Triangle(
    new Vertex(0, 6, 5),
    new Vertex(0, -6, 5),
    new Vertex(-3, 0, 5),
    1.0,1.0,1.0
  );
  Triangle Roof_W3 = new Triangle(
    new Vertex(10, 6, 5),
    new Vertex(13, 0, 5),
    new Vertex(10, -6, 5),
    1.0,1.0,1.0
  );

  public Light light = new Light();

  public ArrayList<Geometry> sceneObjects = new ArrayList<Geometry>(100);

  public Scene() {
    
    sceneObjects.add(W4);
    W3.setReflectionCoeff(1.0);
    sceneObjects.add(W3);

    
    sceneObjects.add(W2);
    sceneObjects.add(W1);
    // sceneObjects.add(W4);
    sceneObjects.add(W5);
    sceneObjects.add(W6);
    // floor.setReflectionCoeff(1.0);
   
   

    sceneObjects.add(Floor_W1);
    sceneObjects.add(Floor_W2);
    sceneObjects.add(Floor_W3);

   sceneObjects.add(Roof_W1);
    sceneObjects.add(Roof_W2);
    sceneObjects.add(Roof_W3);

    sceneObjects.add(light);
  }

  public void add(Geometry obj) {
    sceneObjects.add(obj);
  }

  public Geometry getObj(int index) {
    return sceneObjects.get(index);
  }
}
