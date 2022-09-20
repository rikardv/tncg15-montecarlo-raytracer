package utils;

public class Vector3d {
    double x;
    double y;
    double z;

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // default constructor
    public Vector3d() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    public void printVector() {
        System.out.println("(" + this.x + " " + this.y + " " + this.z + ")");
    }

    public Vector3d sub(double x, double y, double z) {
        return new Vector3d(this.x - x, this.y - y, this.z - z);
    }

}
