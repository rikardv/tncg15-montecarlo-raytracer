package utils;

public class Vertex {
    //stores the coodinat of a vertex in world space
    Vector3d vertex = new Vector3d();

    //default 
    public Vertex(){
        this.vertex.x = 0.0;
        this.vertex.y = 0.0;
        this.vertex.z = 0.0;
    }

    public Vertex(double x,double y,double z){
        this.vertex.x = x;
        this.vertex.y = y;
        this.vertex.z = z;
    }

    public Vertex add(double x, double y, double z) {
        return new Vertex(vertex.x+x, vertex.y+y, vertex.z+z);
    }

    public Vertex sub(double x, double y, double z) {
        return new Vertex(vertex.x-x, vertex.y-y, vertex.z-z);
    }
}
