package utils;

import geometry.*;

public class SphereIntersection {

    private static final double EPSILON = 0.0000000001;

    public static double rayIntersectsSphere(
            Vertex rayOrigin,
            Ray rayVector,
            Sphere inSphere) {

        double d1, d2, d;

        double c1, c2, c3, arg;

        // C is sphere center
        // D is direction
        // Ray is launced at point S
        // r is radius
        // c1 = D dot D
        // c2 = 2D * (S-C)
        // c3 = (S-C) * (SC)

        rayVector.dir = rayVector.dir.norm();

        c1 = Maths.dotProduct(rayVector.dir, rayVector.dir);
        c2 = Maths.dotProduct(rayVector.dir.Multiply(2), rayVector.start.CreateEdge(inSphere.centerPosition));
        c3 = Maths.dotProduct(rayVector.start.CreateEdge(inSphere.centerPosition),
                rayVector.start.CreateEdge(inSphere.centerPosition)) - Math.pow(inSphere.radius, 2);

        arg = Math.pow(c2, 2) - 4 * c1 * c3;

        // Miss
        if (arg < 0.0) {
            return -1.0;
        }

        // One solution
        else if (arg < EPSILON) {
            d = -((c2 ) / (2.0 * c1))+ Math.sqrt(arg);
        }

        else {
            // Two solutions
            d1 = -((c2 ) / (2.0 * c1))+ Math.sqrt(arg);
            d2 = -((c2 ) / (2.0 * c1))- Math.sqrt(arg);

            if (d2 > d1) {
                d = d1;
            } else {
                d = d2;
            }
        };

     
      return d;

    }

}
