package utils;

import geometry.*;

public class MollerTrumbore {
  //9 nollor är idealt
  private static final double EPSILON = 0.0000000001;

  public static boolean rayIntersectsTriangle(
    Vertex rayOrigin,
    Ray rayVector,
    Triangle inTriangle,
    Vertex outIntersectionPoint
  ) {
    Vertex vertex0 = inTriangle.getVertex0();
    Vertex vertex1 = inTriangle.getVertex1();
    Vertex vertex2 = inTriangle.getVertex2();
    Vector3d edge1 = new Vector3d();
    Vector3d edge2 = new Vector3d();
    Vector3d h = new Vector3d();
    Vector3d s = new Vector3d(
      rayOrigin.x - vertex0.x,
      rayOrigin.y - vertex0.y,
      rayOrigin.z - vertex0.z
    );
    Vector3d q = new Vector3d();
    double a, f, u, v;
    edge1 = vertex1.CreateEdge(vertex0);
    edge2 = vertex2.CreateEdge(vertex0);
    h = Maths.crossProduct(rayVector.dir, edge2);
    a = Maths.dotProduct(edge1, h);
    if (a > -EPSILON && a < EPSILON) {
      return false; // This ray is parallel to this triangle.
    }
    f = 1.0 / a;

    double sDotH = Maths.dotProduct(s, h);

    u = f * sDotH;
    if (u < 0.0 || u > 1.0) {
      return false;
    }
    q = Maths.crossProduct(s, edge1);
    double RVdotQ = Maths.dotProduct(rayVector.dir, q);
    v = f * RVdotQ;
    if (v < 0.0 || u + v > 1.0) {
      return false;
    }
    // At this stage we can compute t to find out where the intersection point is on the line.
    double edge2dotQ = Maths.dotProduct(edge2, q);
    double t = f * edge2dotQ;
    if (
      t > EPSILON
    ) { // ray intersection
      // outIntersectionPoint.set(0.0, 0.0, 0.0);
      // outIntersectionPoint.scaleAdd(t, rayVector, rayOrigin);

      outIntersectionPoint.set(rayOrigin);

      outIntersectionPoint.add(
        rayVector.dir.x * t,
        rayVector.dir.y * t,
        rayVector.dir.z * t
      );
      return true;
    } else { // This means that there is a line intersection but not a ray intersection.
      return false;
    }
  }

  public static boolean rayIntersectsRectangle(
    Vertex rayOrigin,
    Ray rayVector,
    Rectangle inRectangle,
    Vertex outIntersectionPoint
  ) {
    Triangle Triangle1 = new Triangle(
      inRectangle.v1,
      inRectangle.v2,
      inRectangle.v3
    );
    Triangle Triangle2 = new Triangle(
      inRectangle.v1,
      inRectangle.v3,
      inRectangle.v4
    );

    if (
      MollerTrumbore.rayIntersectsTriangle(
        rayOrigin,
        rayVector,
        Triangle1,
        outIntersectionPoint
      ) ||
      MollerTrumbore.rayIntersectsTriangle(
        rayOrigin,
        rayVector,
        Triangle2,
        outIntersectionPoint
      )
    ) {
      return true;
    }
    return false;
  }


  public static boolean rayIntersectsSphere(
    Vertex rayOrigin,
    Ray rayVector,
    Sphere inSphere,
    Vertex outIntersectionPoint
  ){
   
    double b, c, x, d1, d2, d;

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
    c3 = Maths.dotProduct(rayVector.start.CreateEdge(inSphere.centerPosition), rayVector.start.CreateEdge(inSphere.centerPosition)) - Math.pow(inSphere.radius, 2);
    
    
  
    b = Maths.dotProduct(rayVector.dir.Multiply(2), (rayVector.start.CreateEdge(inSphere.centerPosition)));
	  c = Maths.dotProduct((rayVector.start.CreateEdge(inSphere.centerPosition)), (rayVector.start.CreateEdge(inSphere.centerPosition).invers())) - Math.pow(inSphere.radius, 2.0f);

    arg = Math.pow(c2,2) - 4 * c1 * c3;
    x = Math.pow((b / 2.0), 2.0) - c;

    // Miss
    if (arg < 0.0) {
      return false;
    }

    // One solution
    else if (arg < EPSILON) {
       d1 = -(c2 + Math.sqrt(arg)) / (2.0 * c1);
       d = d1;
       // outIntersectionPoint = rayVector.start.translate(rayVector.dir).mult(d);
       outIntersectionPoint.set(rayVector.start.translate(rayVector.dir).mult(d));
       return true;
    };

    
    // Two solutions
    d1 = -(c2 + Math.sqrt(arg)) / (2.0 * c1);
	  d2 =-(c2 - Math.sqrt(arg)) / (2.0 * c1);

    if (d2 > d1) {
      d = d1;
    }
    else {
      d = d2;
    }
    
	  

    // if (d1 <= 0 && d2 <= 0) return false;
	  // else if (d1 < 0) d = d2;
	  // else if (d2 < 0) d = d1;
	  // else if (d1 < d2) d = d1;
	  // else d = d2;

    //Check with distance if we got a hit
	  if (d < EPSILON || d > 1000.0f) return false;
	  // outIntersectionPoint = rayVector.start.translate(rayVector.dir).mult(d);
    outIntersectionPoint.set(rayVector.start.translate(rayVector.dir).mult(d));

    Vector3d normal = outIntersectionPoint.CreateEdge(inSphere.centerPosition).norm();
    inSphere.setNormal((normal));

    // if(!inside)
    if (Maths.dotProduct(rayVector.dir, normal) > 0.0) return false;
		// if (glm::dot(ray.getDirection(), normal) > 0.0f) return false;

    return true;
    
  }

  /*
   * 
   * glm::vec3 rayStart = ray.getStartPoint();
	glm::vec3 direction = ray.getDirection();
	float b, c, d1, d2, d, x = 0.0f;

	b = glm::dot(2.0f*direction, (rayStart - this->center));
	c = glm::dot((rayStart - this->center), (rayStart - this->center)) - pow(this->radius, 2.0f);

	x = pow((b / 2.0f), 2.0f) - c;

	if (x < FLT_EPSILON) return false;

	d1 = -(b / 2.0f) + sqrt(x);
	d2 = -(b / 2.0f) - sqrt(x);
	
	if (d1 <= 0 && d2 <= 0) return false;
	else if (d1 < 0) d = d2;
	else if (d2 < 0) d = d1;
	else if (d1 < d2) d = d1;
	else d = d2;

	//Check with distance if we got a hit
	if (d < FLT_EPSILON || d > 1000.0f) return false;
	intersection = rayStart + direction * d;

	glm::vec3 normal = glm::normalize(intersection - this->center);

	//Check if sphere is infront of ray
	if(!inside)
		if (glm::dot(ray.getDirection(), normal) > 0.0f) return false;
   */


}
