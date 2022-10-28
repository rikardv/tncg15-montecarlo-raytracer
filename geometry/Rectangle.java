package geometry;

import java.util.Random;

import utils.*;

public class Rectangle extends Geometry {

    // vertices for the rectangle
    public Vertex v1;
    public Vertex v2;
    public Vertex v3;
    public Vertex v4;

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

        normal = Maths.crossProduct(v1.CreateEdge(v2), v3.CreateEdge(v2)).norm();
    }

    public Rectangle(Vertex v1, Vertex v2, Vertex v3, Vertex v4) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
        normal = Maths.crossProduct(v3.CreateEdge(v2), v1.CreateEdge(v2)).norm();
    }

    public Rectangle(
            Vertex v1,
            Vertex v2,
            Vertex v3,
            Vertex v4,
            double r,
            double g,
            double b) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
        this.SetColor(r, g, b);
        normal = Maths.crossProduct(v3.CreateEdge(v2), v1.CreateEdge(v2)).norm();
    }

    @Override
    public double checkIntersect(
            Vertex rayOrigin,
            Ray ray) {
        return MollerTrumbore.rayIntersectsRectangle(
                rayOrigin,
                ray,
                this);
    }

    @Override
    public Vector3d getNormal() {
        return normal.norm();
    }


    @Override
    public boolean hitLight() {
        return false;
    }

}
