package geometry;

import utils.*;

public class Triangle {
    Vertex v1, v2, v3;
    public Vector3d normal = Maths.crossProduct(v1.sub(v2), v3.sub(v2));

    public Triangle() {
        v1 = new Vertex();
        v2 = new Vertex();
        v3 = new Vertex();
    }

}