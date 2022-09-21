package geometry;

import utils.*;
import utils.MollerTrumbore;


public class Geometry {
    public ColorRGB color = new ColorRGB();

       public boolean checkIntersect(Vertex rayOrigin, Vector3d rayVector, Vertex outIntersectionPoint){
        System.out.print("Something went wrong: checkIntersect not overridden");
        
        return false;
    }

    

}

