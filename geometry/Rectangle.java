package geometry;

import utils.*;

public class Rectangle {

    // vertices for the rectangle
    public Vertex v1;
    public Vertex v2;
    public Vertex v3;
    public Vertex v4;
    ColorRGB color = new ColorRGB();

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

        normal = Maths.crossProduct(v1.CreateEdge(v2), v3.CreateEdge(v2));

    }

    public Rectangle(Vertex v1, Vertex v2, Vertex v3, Vertex v4) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
        normal = Maths.crossProduct(v3.CreateEdge(v2), v1.CreateEdge(v2));

    }

    public void SetColor(double R, double G, double B) {
        this.color = new ColorRGB(R, G, B);
    }

}
