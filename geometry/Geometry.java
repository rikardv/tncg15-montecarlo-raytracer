package geometry;

import utils.*;
import utils.MollerTrumbore;


public class Geometry {

    public double reflectCoeff = 0.8;

    public ColorRGB color = new ColorRGB();

    public Vector3d normal;
 
       public boolean checkIntersect(Vertex rayOrigin, Ray rayVector, Vertex outIntersectionPoint){
        System.out.print("Something went wrong: checkIntersect not overridden");
        
        return false;
    }

    public Ray bounceRay(Ray rayIn, Vertex intersectionPoint){
        System.out.println("Something went wrong: bounceray not overriden");
        
        return new Ray();
    }

    public Vector3d getNormal(){
        return normal;
    }

    

}

