package geometry;

import utils.*;
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
    public boolean checkIntersect(Vertex rayOrigin, Ray ray,Vertex outIntersectionPoint) {
        return MollerTrumbore.rayIntersectsSphere(rayOrigin, ray, this, outIntersectionPoint);
    }
    
    public Vector3d getNormal(Vertex pointOfIntersection) {
        return pointOfIntersection.CreateEdge(this.centerPosition).norm();
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

    @Override
    public ColorRGB calculateDirectLight(Light LightSource, Vertex pointOFIntersection) {
        int shadowRays = 50;
        // var q = u * ( v1 - v0) + v * (v2 - v0);
    
        ColorRGB L = new ColorRGB(0, 0, 0);
        ColorRGB Ld = new ColorRGB(0, 0, 0);
    
        Vector3d edge1 = LightSource.v2.CreateEdge(LightSource.v1);
        Vector3d edge2 = LightSource.v3.CreateEdge(LightSource.v1);
    
        // float A = glm::length(glm::cross(v1 - v0, v3 - v0));
        double LightArea = Maths.crossProduct(edge1, edge2).vectorLength();
    
        for (int i = 0; i < shadowRays; i++) {
            // random numbers
            double u = new Random().nextDouble();
            double v = new Random().nextDouble();
    
            // edges of the lightsource
            Vector3d e1 = LightSource.v2.CreateEdge(LightSource.v1).Multiply(u);
            Vector3d e2 = LightSource.v3.CreateEdge(LightSource.v1).Multiply(v);
            Vector3d summedVector = e1.add(e2);
            Vertex yi = LightSource.v1.translate(summedVector);
    
            // float A = glm::length(glm::cross(v1 - v0, v3 - v0));
    
            // Disnans mellan intersection point och ljuskällan
            // sk är vectorn mellan ljus och puntk
            // di är distansen mellan di = yi - x
    
            Vector3d di = pointOFIntersection.CreateEdge(yi);
            //test

            
            double abs_di = Math.abs(di.vectorLength());
    
            // eventuellt "-" på sk
            // cos(omegax) = Nx * di / ||di||
            // cos(omegay) = Ny * di / ||di||
            // TODO - fixa normal för triangel
            double cosOmegax = Maths.dotProduct(di.Multiply(1 / abs_di), this.getNormal(pointOFIntersection));
            // TODO - varför fungerar det bara utan negativt tecken i formel nedan?
            double cosOmegay = Maths.dotProduct(di.Multiply(1 / abs_di), LightSource.normal);
            
    
            L = L.add(this.color.mult((cosOmegax * cosOmegay) / (abs_di * abs_di)));
    
        }
    
        Ld = L.mult(LightArea / (Math.PI *shadowRays));
        
        // if (!this->isVisible(shadowRay)) Vk = 0.0f;
        // else Vk = 1.0f;
    
        return Ld.mult(20);
    }
    
}
