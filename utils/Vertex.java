package utils;

public class Vertex {
    // stores the coodinat of a vertex in world space

    double x;
    double y;
    double z;

    // default
    public Vertex() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    public Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vertex add(double x, double y, double z) {
        return new Vertex(this.x + x, this.y + y, this.z + z);
    }

    public Vertex sub(double x, double y, double z) {
        return new Vertex(this.x - x, this.y - y, this.z - z);
    }

    public Vertex sub(Vertex otherVertex) {
        return new Vertex(this.x - otherVertex.x, this.y - otherVertex.y, this.z - otherVertex.z);
    }

    public Vertex add(Vertex otherVertex) {
        return new Vertex(this.x + otherVertex.x, this.y + otherVertex.y, this.z + otherVertex.z);
    }
}
