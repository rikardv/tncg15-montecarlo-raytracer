package scene;

import geometry.Geometry;
import geometry.Light;
import geometry.Material;
import geometry.MaterialType;
import geometry.Rectangle;
import geometry.Sphere;
import geometry.Triangle;
import java.util.*;
import java.util.ArrayList;
import java.util.Random;
import utils.Vertex;

public class Scene {

  /**
   * Walls
   */
  Rectangle W1 = new Rectangle(
    new Vertex(0, 6, -5),
    new Vertex(10, 6, -5),
    new Vertex(10, 6, 5),
    new Vertex(0, 6, 5),
    1.0,
    0.4,
    0.4
  );

  Rectangle W2 = new Rectangle(
    new Vertex(10, 6, -5),
    new Vertex(13, 0, -5),
    new Vertex(13, 0, 5),
    new Vertex(10, 6, 5),
    1.0,
    1.0,
    1.0
  );
  Rectangle W3 = new Rectangle(
    new Vertex(13, 0, -5),
    new Vertex(10, -6, -5),
    new Vertex(10, -6, 5),
    new Vertex(13, 0, 5),
    0.4,
    0.7,
    1.0
  );
  Rectangle W4 = new Rectangle(
    new Vertex(10, -6, -5),
    new Vertex(0, -6, -5),
    new Vertex(0, -6, 5),
    new Vertex(10, -6, 5),
    0.7,
    1.0,
    0.4
  );
  Rectangle W5 = new Rectangle(
    new Vertex(0, -6, -5),
    new Vertex(-3, 0, -5),
    new Vertex(-3, 0, 5),
    new Vertex(0, -6, 5),
    1.0,
    0.7,
    0.4
  );
  Rectangle W6 = new Rectangle(
    new Vertex(-3, 0, -5),
    new Vertex(0, 6, -5),
    new Vertex(0, 6, 5),
    new Vertex(-3, 0, 5),
    1.0,
    0.7,
    0.4
  );

  /**
   * Floor
   */
  Rectangle Floor_W1 = new Rectangle(
    new Vertex(10, 6, -5),
    new Vertex(0, 6, -5),
    new Vertex(0, -6, -5),
    new Vertex(10, -6, -5),
    0.7,
    0.7,
    0.7
  );
  Triangle Floor_W2 = new Triangle(
    new Vertex(0, 6, -5),
    new Vertex(-3, 0, -5),
    new Vertex(0, -6, -5),
    0.7,
    0.7,
    0.7
  );
  Triangle Floor_W3 = new Triangle(
    new Vertex(10, -6, -5),
    new Vertex(13, 0, -5),
    new Vertex(10, 6, -5),
    0.7,
    0.7,
    0.7
  );

  /**
   * Roof
   */
  Rectangle Roof_W1 = new Rectangle(
    new Vertex(10, 6, 5),
    new Vertex(10, -6, 5),
    new Vertex(0, -6, 5),
    new Vertex(0, 6, 5),
    0.7,
    0.7,
    0.7
  );
  Triangle Roof_W2 = new Triangle(
    new Vertex(0, 6, 5),
    new Vertex(0, -6, 5),
    new Vertex(-3, 0, 5),
    0.7,
    0.7,
    0.7
  );
  Triangle Roof_W3 = new Triangle(
    new Vertex(10, 6, 5),
    new Vertex(13, 0, 5),
    new Vertex(10, -6, 5),
    0.7,
    0.7,
    0.7
  );

  /**
   * External
   */

  Sphere firstSphere = new Sphere(new Vertex(9, -1, -2), 2.0, 1.0, 1.0, 1.0);

  Rectangle lightBlock = new Rectangle(
    new Vertex(8, -1, 1.5),
    new Vertex(10, -1, -1.5),
    new Vertex(10, 1, -4),
    new Vertex(8, 1, -1),
    1.0,
    1.0,
    1.0
  );

  Vertex _v0 = new Vertex(7, 4, -4);
  Vertex v0 = new Vertex(_v0.x, _v0.y, 3 + _v0.z);
  Vertex v1 = new Vertex(-1 + _v0.x, _v0.y, -1 + _v0.z);
  Vertex v2 = new Vertex(_v0.x, -1 + _v0.y, -1 + _v0.z);
  Vertex v3 = new Vertex(_v0.x, 1 + _v0.y, -1 + _v0.z);
  Triangle Tetra_W1 = new Triangle(v0, v1, v2, 1.0, 1.0, 1.0);
  Triangle Tetra_W2 = new Triangle(v0, v3, v1, 1.0, 1.0, 1.0);
  Triangle Tetra_W3 = new Triangle(v0, v2, v3, 1.0, 1.0, 1.0);
  Triangle Tetra_W4 = new Triangle(v1, v3, v2, 1.0, 1.0, 1.0);


  public Light light = new Light();

  public ArrayList<Geometry> sceneObjects = new ArrayList<Geometry>(100);

  public Scene() {
    // Create materials for objects
    Material mirror = new Material(1.0f, 1, 1, MaterialType.MIRROR);
    Material lightMaterial = new Material(
      1.0f,
      1,
      1,
      MaterialType.LIGHT_SOURCE
    );
    Material specific = new Material(1.0f, 1, 1, MaterialType.LAMBERTIAN);

    // Set materials
    light.setMaterial(lightMaterial);
    W2.setMaterial(mirror);

    // Add objects to scene
    sceneObjects.add(light);
    sceneObjects.add(W2);
    sceneObjects.add(W4);
    sceneObjects.add(W3);
    sceneObjects.add(W1);
    sceneObjects.add(W5);
    sceneObjects.add(W6);
    sceneObjects.add(Floor_W1);
    sceneObjects.add(Floor_W2);
    sceneObjects.add(Floor_W3);
    sceneObjects.add(Roof_W1);
    sceneObjects.add(Roof_W2);
    sceneObjects.add(Roof_W3);
    sceneObjects.add(Tetra_W1);
    sceneObjects.add(Tetra_W2);
    sceneObjects.add(Tetra_W3);
    sceneObjects.add(firstSphere);
  }

  public void add(Geometry obj) {
    sceneObjects.add(obj);
  }

  public Geometry getObj(int index) {
    return sceneObjects.get(index);
  }
}
