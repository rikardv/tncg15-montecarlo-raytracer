package geometry;

import utils.*;

public class Triangle extends Geometry{
    Vertex v1, v2, v3;
    public Vector3d normal;

    /*
     * v3
     * | \
     * | \
     * | \
     * v1---v2
     * 
     * normal mot dig
     * 
     * 
     */

    public Triangle() {
        v1 = new Vertex();
        v2 = new Vertex();
        v3 = new Vertex();
        normal = Maths.crossProduct(v1.CreateEdge(v2), v3.CreateEdge(v2));
    }

    public Triangle(Vertex V1, Vertex V2, Vertex V3) {
        this.v1 = V1;
        this.v2 = V2;
        this.v3 = V3;
        normal = Maths.crossProduct(v1.CreateEdge(v2), v3.CreateEdge(v2));
    }

    public Vertex getVertex0() {
        return this.v1;
    }

    public Vertex getVertex1() {
        return this.v2;
    }

    public Vertex getVertex2() {
        return this.v3;
    }

    public boolean checkIntersect(Vertex rayOrigin, Ray rayVector, Vertex outIntersectionPoint){
        return MollerTrumbore.rayIntersectsTriangle(rayOrigin, rayVector, this, outIntersectionPoint);
    }

    @Override
    public Vector3d getNormal(){
        return normal;
    }

        @Override
        public Ray bounceRay(Ray rayIn, Vertex intersectionPoint){
        Vertex start = intersectionPoint;
        // R = 2(N dot L)N-L
        
        double NdotL = 2*Maths.dotProduct(rayIn.dir, normal);
        Vector3d R = normal.Multiply(NdotL).sub(rayIn.dir);
        Ray rayOut= new Ray(start,R,rayIn); 

        return rayOut;
    }

}