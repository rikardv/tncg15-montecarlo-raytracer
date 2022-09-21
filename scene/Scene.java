package scene;

import java.util.ArrayList;

import geometry.Geometry;
import geometry.Rectangle;
import utils.Vertex;

public class Scene {

    //Rummet
    Rectangle W1 = new Rectangle(new Vertex(0,6,-5),new Vertex(10,6,-5),new Vertex(10,6,5),new Vertex(0,6,5),255,0,0);
   
    Rectangle W2 = new Rectangle(new Vertex(10,6,-5),new Vertex(13,0,-5),new Vertex(13,0,5),new Vertex(10,6,5),0,255,0);
    Rectangle W3 = new Rectangle(new Vertex(13, 0,-5),new Vertex(10,-6, -5),new Vertex(10,-6,5),new Vertex(13,0,5),100,0,0);
    Rectangle W4 = new Rectangle(new Vertex(10,-6,-5),new Vertex(0,-6,-5),new Vertex(0,-6,5),new Vertex(10,-6,5),0,0,255);
    Rectangle W5 = new Rectangle(new Vertex(0,-6,-5),new Vertex(-3,0,-5),new Vertex(-3,0,5),new Vertex(0,-6,5),255,255,255);
    Rectangle W6 = new Rectangle(new Vertex(-3,0,-5),new Vertex(0,6,-5),new Vertex(0,6,5),new Vertex(-3,0,5),0,255,0);
    Rectangle test = new Rectangle(new Vertex(10,-1,-1),new Vertex(10,1,-1),new Vertex(10,1,1),new Vertex(10,-1,1),0,0,255);
    Rectangle floor = new Rectangle(new Vertex(-3,-6,-5),new Vertex(13,-6,-5),new Vertex(13,6,-5),new Vertex(-3,6,-5),100,40,40);
    
    public ArrayList<Geometry> sceneObjects= new ArrayList<Geometry>(100);

    public Scene(){
        sceneObjects.add(W4);
         sceneObjects.add(W3);

         sceneObjects.add(W2);
         sceneObjects.add(W1);
        // sceneObjects.add(W4);
         sceneObjects.add(W5);
         sceneObjects.add(W6);
         sceneObjects.add(floor);
    }

    public void add(Geometry obj){
        sceneObjects.add(obj);
    }

    public Geometry getObj(int index){
        return sceneObjects.get(index);
    }
 
    
}
