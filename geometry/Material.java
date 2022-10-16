package geometry;

public class Material {

    public float reflectCoeff;
    public float n;
    public float roughness;
    public MaterialType type;

    public Material() {
        this.reflectCoeff = 0.5f;
        this.n = 1.0f;
        this.roughness = 1.0f;
        this.type = MaterialType.LAMBERTIAN;
    }


    public Material(float reflectCoeff, float n, float roughness, MaterialType type){
        this.reflectCoeff = reflectCoeff;
        this.n = n;
        this.roughness = roughness;
        this.type = type;
    }
    float getRoughness() {
        return this.roughness;
    }

    float getReflectionCoeff(){
        return this.reflectCoeff;
    }

    float getN(){
        return this.n;
    }

    void setType(MaterialType type){
        this.type = type;
    }

    void setRougness(float roughness){
        this.roughness = roughness;
    }

    void setReflectionCoeff(float reflection){
        this.reflectCoeff = reflection;
    }

    void setN(float N){
        this.n = N;
    }

     
}