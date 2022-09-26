package geometry;

import utils.*;
import utils.Vertex;

public class Light extends Rectangle {

    public Light() {
        this.SetColor(255.0, 255.0, 255.0);
        v4 = new Vertex(8, -1, 5);
        v3 = new Vertex(10, -1, 5);
        v2 = new Vertex(10, 1, 5);
        v1 = new Vertex(8, 1, 5);
    }

    // @Override
    // public boolean checkIntersect(Vertex rayOrigin, Ray ray, Vertex outIntersectionPoint) {
    //     return false;
    // }

    @Override
    public boolean hitLight() {
        return true;
    }

}
