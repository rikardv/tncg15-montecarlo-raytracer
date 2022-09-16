package geometry;
import utils.*;

public class Rectangle {

    //vertices for the rectangle
    Vertex v1;
    Vertex v2;
    Vertex v3;
    Vertex v4;

    /*
            normal towards you
     *      v4----v3
     *      |      |
     *      |      |
     *      v1-----v2
     */

     //V2->V3 X V2->V1
    vector3d normal = Maths.crossProduct()

    int test;
    Vector3d position;


    //basic default constructor
    public Rectangle(){
        v1 = new Vertex();
        v2 = new Vertex();
        v3 = new Vertex();
        v4 = new Vertex();

        position = new Vector3d();

    }

    public Rectangle(Vertex v1,Vertex v2,Vertex v3,Vertex v4, Vector3d pos){
        this.v1 = v1;
        this.v1 = v2;
        this.v1 = v3;
        this.v1 = v4;
        this.position = pos;
    }



}
