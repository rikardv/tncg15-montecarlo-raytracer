package utils;

public class Maths {

  // Returns the crossproduct of two vectors. Output is Vector3d
  public static Vector3d crossProduct(Vector3d u, Vector3d v) {
    double x = u.y * v.z - u.z * v.y;
    double y = u.z * v.x - u.x * v.z;
    double z = u.x * v.y - u.y * v.x;

    return new Vector3d(x, y, z);
  }

  // Returns the dot product of two vectors. Output is Dobule
  public static double dotProduct(Vector3d u, Vector3d v) {
    return u.x * v.x + u.y * v.y + u.z * v.z;
  }

  public static Vector3d rotateVectorCC(
    Vector3d vec,
    Vector3d axis,
    double theta
  ) {
    double x, y, z;
    double u, v, w;
    x = vec.x;
    y = vec.y;
    z = vec.z;
    u = axis.x;
    v = axis.y;
    w = axis.z;
    double xPrime =
      u *
      (u * x + v * y + w * z) *
      (1d - Math.cos(theta)) +
      x *
      Math.cos(theta) +
      (-w * y + v * z) *
      Math.sin(theta);
    double yPrime =
      v *
      (u * x + v * y + w * z) *
      (1d - Math.cos(theta)) +
      y *
      Math.cos(theta) +
      (w * x - u * z) *
      Math.sin(theta);
    double zPrime =
      w *
      (u * x + v * y + w * z) *
      (1d - Math.cos(theta)) +
      z *
      Math.cos(theta) +
      (-v * x + u * y) *
      Math.sin(theta);
    return new Vector3d(xPrime, yPrime, zPrime);
  }
}
