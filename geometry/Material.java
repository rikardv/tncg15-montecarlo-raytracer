package geometry;

import utils.ColorRGB;

public class Material {

    public float reflectCoeff;
    public float n;
    public float roughness;
    public MaterialType type;

    public Material() {
        this.reflectCoeff = 0.5f;
        this.n = 1.0f;
        this.roughness = 10.5f;
        this.type = MaterialType.LAMBERTIAN;
    }

    public float getMaterialBRDF() {

        switch (this.type) {
            case LAMBERTIAN: 
            return this.reflectCoeff / (float) Math.PI;
            case MIRROR:
            break;
            case TRANSPARENT:
            break;
            default:
            break;
        }
        return this.reflectCoeff / (float) Math.PI;
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