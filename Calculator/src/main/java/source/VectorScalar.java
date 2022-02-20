package source;
import static java.lang.Math.*;
import java.text.DecimalFormat;

/**
 * Class for scalar vectors. Ex. <2,3,4>
 */
public class VectorScalar {
	static DecimalFormat format = new DecimalFormat(".#####");
	double x;
	double y;
	double z;
	boolean twoD;

	/**
	 * This constructor is used to create a three-dimensional {@link VectorScalar}
	 *
	 * @param x x component
	 * @param y y component
	 * @param z z component
	 */
	public VectorScalar(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		twoD = false;
	}

	/**
	 * This constructor is used to create a two-dimensional {@link VectorScalar}
	 *
	 * @param x x component
	 * @param y y component
	 */
	public VectorScalar(double x, double y) {
		this.x = x;
		this.y = y;
		z = 0;
		twoD = true;
	}

	/**
	 * Adds another {@link VectorScalar} to this {@link VectorScalar}
	 *
	 * @param other {@link VectorScalar} to add
	 */
	public void add(VectorScalar other) {
		x += other.x;
		y += other.y;
		z += other.z;
	}

	/**
	 * Multiplies this {@link VectorScalar} by a constant
	 *
	 * @param s A constant
	 */
	public void multiplyS(double s) {
		x *= s;
		y *= s;
		z *= s;
	}

	/**
	 * Dot products this {@link VectorScalar} with another {@link VectorScalar}
	 *
	 * @param other {@link VectorScalar} to dot product with
	 * @return The dot product of two scalar vectors as a double
	 */
	public double dot(VectorScalar other) {
		return (x * other.x) + (y * other.y) + (z * other.z);
	}

	/**
	 * Cross products this {@link VectorScalar} with another {@link VectorScalar}
	 *
	 * @param other {@link VectorScalar} to cross product with
	 * @return The cross product of two scalar vectors as a VectorScalar
	 */
	public VectorScalar cross(VectorScalar other) {
		return new VectorScalar(((y * other.z) - (z * other.y)), ((z * other.x) - (x * other.z)),
				((x * other.y) - (y * other.x)));
	}

	/**
	 * Computes the magnitude of this {@link VectorScalar}
	 *
	 * @return Magnitude as a double
	 */
	public double mag() {
		return sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
	}

	/**
	 * Computes the unit vector for this {@link VectorScalar}
	 *
	 * @return The unit vector for this VectorScalar as a VectorScalar
	 */
	public VectorScalar returnUnit() {
		double mag = mag();
		return new VectorScalar(x / mag, y / mag, z / mag);
	}

	/**
	 * Computes the scalar projection of this {@link VectorScalar} onto another
	 *
	 * @param other {@link VectorScalar} to project on
	 * @return The scalar projection of a vector onto another vector as a double
	 */
	public double scalarProject(VectorScalar other) {
		return abs(dot(other) / other.mag());
	}

	/**
	 * Computes the vector projection of this {@link VectorScalar} onto another
	 *
	 * @param other {@link VectorScalar} to project on
	 * @return The vector projection of a vector onto another vector as a
	 *         VectorScalar
	 */
	public VectorScalar vectorProject(VectorScalar other) {
		VectorScalar temp = other.returnUnit();
		temp.multiplyS(this.scalarProject(other));
		return temp;
	}

	public String toString() {
		return (twoD) ? "<" + format.format(x) + ", " + format.format(y) + ">"
				: "<" + format.format(x) + ", " + format.format(y) + ", " + format.format(z) + ">";
	}

	/**
	 * Creates a {@link VectorScalar} from a String expression of a scalar vector
	 *
	 * @param ex Scalar vector expression as a String
	 * @return A VectorScalar
	 */
	public static VectorScalar create(String ex) {
		ex = ex.replaceAll("\\s+", "");
		String[] components = (ex.substring(1, ex.length() - 1)).split(",");
		VectorScalar temp;
		switch (components.length) {
		case 3:
			temp = new VectorScalar(Double.parseDouble(components[0]), Double.parseDouble(components[1]),
					Double.parseDouble(components[2]));
			break;
		case 2:
			temp = new VectorScalar(Double.parseDouble(components[0]), Double.parseDouble(components[1]));
			break;
		default:
			temp = new VectorScalar(Double.NaN, Double.NaN, Double.NaN);
			break;
		}
		return temp;
	}
}
