package scene;

import geometry.Rectangle;
import utils.Vertex;

public class Scene {

    //Rummet
    Rectangle W1 = new Rectangle(new Vertex(0,6,-5),new Vertex(10,6,-5),new Vertex(10,6,5),new Vertex(0,6,5));
    Rectangle W2 = new Rectangle(new Vertex(10,6,-5),new Vertex(13,0,-5),new Vertex(13,0,5),new Vertex(10,6,5));
    Rectangle W3 = new Rectangle(new Vertex(13, 0,-5),new Vertex(10,-6, -5),new Vertex(10,-6,5),new Vertex(13,0,5));
    Rectangle W4 = new Rectangle(new Vertex(10,-6,-5),new Vertex(0,-6,-5),new Vertex(0,-6,5),new Vertex(10,-6,5));
    Rectangle W5 = new Rectangle(new Vertex(0,-6,-5),new Vertex(-3,0,-5),new Vertex(-3,0,5),new Vertex(0,-6,5));
    Rectangle W6 = new Rectangle(new Vertex(-3,0,-5),new Vertex(0,6,-5),new Vertex(0,6,5),new Vertex(-3,0,5));

    
}
