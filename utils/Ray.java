package utils;

public class Ray
{
    public Vertex start;
    public Vector3d dir;
    public Ray parent;
    public Ray child;
    

    public Ray(Vertex start, Vector3d dir){
        this.start = new Vertex(start.x, start.y, start.z);
        this.dir = new Vector3d(dir.x, dir.y, dir.z);

        
    }
}