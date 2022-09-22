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
    public Vector3d Multiply(double mult) {
        return new Vector3d(this.x*mult, this.y*mult, this.z*mult);
    }

    public Vector3d invers(){
        return new Vector3d(-this.x,-this.y,-this.z);
    }

    public Vector3d sub(Vector3d other) {
        return new Vector3d(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vector3d norm(){
        double length = Math.sqrt(Math.pow(this.x,2)+Math.pow(this.y,2)+Math.pow(this.z,2));
        return new Vector3d(this.x/length,this.y/length,this.z/length);
    }

}
