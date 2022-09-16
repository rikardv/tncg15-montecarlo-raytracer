package geometry;

import utils.*;

public class Rectangle {

    // vertices for the rectangle
    Vertex v1;
    Vertex v2;
    Vertex v3;
    Vertex v4;
    ColorRGB  color = new ColorRGB();

    /*
     * normal towards you
     * v4-----v3
     * |......|
     * | .....|
     * v1-----v2
     */

    // V2->V3 X V2->V1
    public Vector3d normal;

    // basic default constructor
    public Rectangle() {
        v1 = new Vertex();
        v2 = new Vertex();
        v3 = new Vertex();
        v4 = new Vertex();
        normal = Maths.crossProduct(v1.sub(v2),v3.sub(v2));

    }

    public Rectangle(Vertex v1, Vertex v2, Vertex v3, Vertex v4) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
        normal = Maths.crossProduct(v3.sub(v2),v1.sub(v2));

    }

}
