package utils;

public class Ray {
    public Vertex start;
    public Vector3d dir;
    public Ray parent = null;
    public Ray child = null;

    public Ray() {
        this.start = new Vertex();
        this.dir = new Vector3d();

    }

    public Ray(Vertex start, Vector3d dir) {
        this.start = new Vertex(start.x, start.y, start.z);
        this.dir = new Vector3d(dir.x, dir.y, dir.z);

    }

    public Ray(Vertex start, Vector3d dir, Ray Child) {
        this.start = new Vertex(start.x, start.y, start.z);
        this.dir = new Vector3d(dir.x, dir.y, dir.z);

    }
    public void setChild(Ray child){
        this.child = child;
    }
}