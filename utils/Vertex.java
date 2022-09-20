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

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;

    }

    public Vertex(Vertex vertex) {
        this.x = vertex.x;
        this.y = vertex.y;
        this.z = vertex.z;
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

    // takes to a vertex and returns the vector of the edge drawn between them
    public Vector3d CreateEdge(Vertex otherVertex) {
        return new Vector3d(this.x - otherVertex.x, this.y - otherVertex.y, this.z - otherVertex.z);
    }

    public Vertex add(Vertex otherVertex) {
        return new Vertex(this.x + otherVertex.x, this.y + otherVertex.y, this.z + otherVertex.z);
    }

    public void printVertex() {
        System.out.println("(" + this.x + " " + this.y + " " + this.z + ")");
    }
}
