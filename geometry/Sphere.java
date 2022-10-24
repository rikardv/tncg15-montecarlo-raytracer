package geometry;

import utils.*;

import java.util.ArrayList;
import java.util.Random;

public class Sphere extends Geometry {

    public Vertex centerPosition;
    public double radius; 

    
    public Sphere(){
        centerPosition = new Vertex();
        radius = 0.0;

    }

    public Sphere(Vertex Position, double radius){
        this.centerPosition = Position;
        this.radius = radius;

    }
    public Sphere(Vertex Position, double radius, double r, double g, double b){
        this.centerPosition = Position;
        this.radius = radius;
        this.SetColor(r, g, b);
    }

    @Override
    public Ray bounceRay(Ray rayIn, Vertex intersectionPoint) {
        Vertex start = intersectionPoint;

        normal = getNormal(intersectionPoint);
        // R = L - 2(N dot L)N

        double NdotL = 2 * Maths.dotProduct(rayIn.dir.norm(), normal.norm());
        Vector3d R = (rayIn.dir.norm()).sub(normal.norm().Multiply(NdotL));
        Ray rayOut = new Ray(start, R);
        rayIn.setChild(rayOut);
        rayOut.depth = rayIn.depth + 1;
        return rayOut;
    }

    @Override
    public double checkIntersect(Vertex rayOrigin, Ray ray) {
        return SphereIntersection.rayIntersectsSphere(rayOrigin, ray, this);
    }
    
    public Vector3d getNormal(Vertex pointOfIntersection) {
        return pointOfIntersection.add(centerPosition).CreateEdge(centerPosition).norm();
    }

    @Override
    public Vector3d getNormal() {
        System.out.println("Something went wrong: Entered wrong sphere getnormal");
        return new Vector3d();
    }

    @Override
    public boolean hitLight() {
        return false;
    }

    

}
