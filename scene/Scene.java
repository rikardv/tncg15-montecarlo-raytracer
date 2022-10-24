package scene;

import geometry.Geometry;
import geometry.Rectangle;
import geometry.Triangle;
import geometry.Sphere;
import geometry.Light;
import geometry.Material;
import geometry.MaterialType;
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
    1.0,1.0,1.0
  );

  Rectangle W2 = new Rectangle(
    new Vertex(10, 6, -5),
    new Vertex(13, 0, -5),
    new Vertex(13, 0, 5),
    new Vertex(10, 6, 5),
    1.0,1.0,1.0
  );
  Rectangle W3 = new Rectangle(
    new Vertex(13, 0, -5),
    new Vertex(10, -6, -5),
    new Vertex(10, -6, 5),
    new Vertex(13, 0, 5),
    1.0,1.0,1.0
  );
  Rectangle W4 = new Rectangle(
    new Vertex(10, -6, -5),
    new Vertex(0, -6, -5),
    new Vertex(0, -6, 5),
    new Vertex(10, -6, 5),
    1.0,1.0,1.0
  );
  Rectangle W5 = new Rectangle(
    new Vertex(0, -6, -5),
    new Vertex(-3, 0, -5),
    new Vertex(-3, 0, 5),
    new Vertex(0, -6, 5),
    1.0,1.0,1.0
  );
  Rectangle W6 = new Rectangle(
    new Vertex(-3, 0, -5),
    new Vertex(0, 6, -5),
    new Vertex(0, 6, 5),
    new Vertex(-3, 0, 5),
    1.0,1.0,1.0
  );
  Rectangle test = new Rectangle(
    new Vertex(10, -1, -1),
    new Vertex(10, 1, -1),
    new Vertex(10, 1, 1),
    new Vertex(10, -1, 1),
    1.0,1.0,1.0
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

  Sphere firstSphere = new Sphere(
    new Vertex(9,0,-1),
    2.0,
    1.0,1.0,1.0
  );

  Rectangle lightBlock = new Rectangle(
    new Vertex(8, -1, 1.5),
    new Vertex(10, -1, -1.5),
    new Vertex(10, 1, -4),
    new Vertex(8, 1, -1),
    new Random().nextDouble(),new Random().nextDouble(),new Random().nextDouble()
  );

  

  public Light light = new Light();

  public ArrayList<Geometry> sceneObjects = new ArrayList<Geometry>(100);

  public Scene() {
    
    sceneObjects.add(W4);
    Material mirror = new Material(1.0f, 1, 1, MaterialType.MIRROR);
   // W3.setMaterial(mirror);
    // W3.setReflectionCoeff(9.0);
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

    // firstSphere.setReflectionCoeff(1.0);
  
    sceneObjects.add(firstSphere);
    lightBlock.setMaterial(mirror);
    //sceneObjects.add(lightBlock);
    
  }

  public void add(Geometry obj) {
    sceneObjects.add(obj);
  }

  public Geometry getObj(int index) {
    return sceneObjects.get(index);
  }
}
