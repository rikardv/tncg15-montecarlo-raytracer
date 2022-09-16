package utils;

public class Maths {

    // Returns the crossproduct of two vectors. Output is Vector3d
    public static Vector3d crossProduct(Vertex u, Vertex v) {

        double x = u.y * v.z - u.z * v.y;
        double y = u.z * v.x - u.x * v.z;
        double z = u.x * v.y - u.y * v.x;

        return new Vector3d(x, y, z);
    }

    // Returns the dot product of two vectors. Output is Dobule
    public static double dotProduct(Vector3d u, Vector3d v) {

        return u.x * v.x + u.y * v.y + u.z * v.z;
    }

}
