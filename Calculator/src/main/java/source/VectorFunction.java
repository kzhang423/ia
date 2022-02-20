package source;
import java.util.*;
import org.mariuszgromada.math.mxparser.*;

/**
 * Class for vector functions. Supports up to three-dimensional vector
 * functions.
 */
public class VectorFunction {
	ArrayList<String> param = new ArrayList<String>();
	Function x;
	Function y;
	Function z;

	/**
	 * This constructor is used to store a String representing a vector function.
	 *
	 * @param in The vector function to store
	 */
	public VectorFunction(String in) {
		Function temp = new Function(in);
		for (int i = 0; i < temp.getParametersNumber(); i++) {
			param.add(temp.getParameterName(i));
		}
		String noSpace = in.replaceAll("\\s+", "");
		String[] components = noSpace.substring(noSpace.indexOf("<") + 1, noSpace.length() - 1).split(",");
		switch (components.length) {
		case 3: {
			// 3D
			switch (param.size()) {
			case 1: {
				x = new Function(temp.getFunctionName() + "(" + param.get(0) + ")=" + components[0]);
				y = new Function(temp.getFunctionName() + "(" + param.get(0) + ")=" + components[1]);
				z = new Function(temp.getFunctionName() + "(" + param.get(0) + ")=" + components[2]);
				break;
			}
			case 2: {
				x = new Function(
						temp.getFunctionName() + "(" + param.get(0) + "," + param.get(1) + ")=" + components[0]);
				y = new Function(
						temp.getFunctionName() + "(" + param.get(0) + "," + param.get(1) + ")=" + components[1]);
				z = new Function(
						temp.getFunctionName() + "(" + param.get(0) + "," + param.get(1) + ")=" + components[2]);
				break;
			}
			case 3: {
				x = new Function(temp.getFunctionName() + "(" + param.get(0) + "," + param.get(1) + "," + param.get(2)
						+ ")=" + components[0]);
				y = new Function(temp.getFunctionName() + "(" + param.get(0) + "," + param.get(1) + "," + param.get(2)
						+ ")=" + components[1]);
				z = new Function(temp.getFunctionName() + "(" + param.get(0) + "," + param.get(1) + "," + param.get(2)
						+ ")=" + components[2]);
				break;
			}
			default:
				x = new Function("f(x)=NaN");
				y = new Function("f(x)=NaN");
				z = new Function("f(x)=NaN");
				break;
			}
			break;
		}
		// 2D
		case 2: {
			switch (param.size()) {
			case 1: {
				x = new Function(temp.getFunctionName() + "(" + param.get(0) + ")=" + components[0]);
				y = new Function(temp.getFunctionName() + "(" + param.get(0) + ")=" + components[1]);
				z = null;
				break;
			}
			case 2: {
				x = new Function(
						temp.getFunctionName() + "(" + param.get(0) + "," + param.get(1) + ")=" + components[0]);
				y = new Function(
						temp.getFunctionName() + "(" + param.get(0) + "," + param.get(1) + ")=" + components[1]);
				z = null;
				break;
			}
			case 3: {
				x = new Function(temp.getFunctionName() + "(" + param.get(0) + "," + param.get(1) + "," + param.get(2)
						+ ")=" + components[0]);
				y = new Function(temp.getFunctionName() + "(" + param.get(0) + "," + param.get(1) + "," + param.get(2)
						+ ")=" + components[1]);
				z = null;
				break;
			}
			default:
				x = new Function("f(x)=NaN");
				y = new Function("f(x)=NaN");
				z = new Function("f(x)=NaN");
				break;
			}
			break;
		}
		default: {
			x = new Function("f(x)=NaN");
			y = new Function("f(x)=NaN");
			z = new Function("f(x)=NaN");
			break;
		}
		}

	}

	/**
	 * Evaluates a vector function with three parameters at a point
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @return A {@link VectorScalar} representing the vector function at (x,y,z)
	 */
	public VectorScalar calculate(String x, String y, String z) {
		Expression e1 = new Expression(this.x.getFunctionName() + "(" + x + "," + y + "," + z + ")", this.x);
		Expression e2 = new Expression(this.y.getFunctionName() + "(" + x + "," + y + "," + z + ")", this.y);
		Expression e3 = (z == null) ? null
				: new Expression(this.z.getFunctionName() + "(" + x + "," + y + "," + z + ")", this.z);
		double xc = e1.calculate();
		double yc = e2.calculate();
		double zc = (z == null) ? 0 : e3.calculate();
		return (z == null) ? new VectorScalar(xc, yc) : new VectorScalar(xc, yc, zc);
	}

	/**
	 * Evaluates a vector function with two parameters at a point
	 *
	 * @param x
	 * @param y
	 * @return A {@link VectorScalar} representing the vector function at (x,y)
	 */
	public VectorScalar calculate(String x, String y) {
		Expression e1 = new Expression(this.x.getFunctionName() + "(" + x + "," + y + ")", this.x);
		Expression e2 = new Expression(this.y.getFunctionName() + "(" + x + "," + y + ")", this.y);
		Expression e3 = (z == null) ? null : new Expression(this.z.getFunctionName() + "(" + x + "," + y + ")", this.z);
		double xc = e1.calculate();
		double yc = e2.calculate();
		double zc = (z == null) ? 0 : e3.calculate();
		return (z == null) ? new VectorScalar(xc, yc) : new VectorScalar(xc, yc, zc);
	}

	/**
	 * Evaluates a vector function with one parameter at a value
	 *
	 * @param x
	 * @return A {@link VectorScalar} representing the vector function at a value x
	 */
	public VectorScalar calculate(String x) {
		Expression e1 = new Expression(this.x.getFunctionName() + "(" + x + ")", this.x);
		Expression e2 = new Expression(this.y.getFunctionName() + "(" + x + ")", this.y);
		Expression e3 = (z == null) ? null : new Expression(this.z.getFunctionName() + "(" + x + ")", this.z);
		double xc = e1.calculate();
		double yc = e2.calculate();
		double zc = (z == null) ? 0 : e3.calculate();
		return (z == null) ? new VectorScalar(xc, yc) : new VectorScalar(xc, yc, zc);
	}

	/**
	 * Computes the unit tangent vector at a time t
	 * <p>
	 * Only works for vector functions with one parameter
	 *
	 * @param t
	 * @return The unit tangent vector at a time t as a {@link VectorScalar}
	 */
	public VectorScalar unitTangent(double t) {
		Equation tempX = new Equation(x);
		Equation tempY = new Equation(y);
		Equation tempZ = (z == null) ? null : new Equation(z);
		VectorScalar result = (tempZ == null) ? new VectorScalar(tempX.derivativeN(t, 1), tempY.derivativeN(t, 1))
				: new VectorScalar(tempX.derivativeN(t, 1), tempY.derivativeN(t, 1), tempZ.derivativeN(t, 1));
		return result.returnUnit();
	}

	public String toString() {
		switch (param.size()) {
		case 1:
			return x.getFunctionName() + "(" + param.get(0) + ") = <" + x.getFunctionExpressionString() + ", "
					+ y.getFunctionExpressionString()
					+ ((z == null) ? ">" : ", " + z.getFunctionExpressionString() + ">");
		case 2:
			return x.getFunctionName() + "(" + param.get(0) + "," + param.get(1) + ") = <"
					+ x.getFunctionExpressionString() + ", " + y.getFunctionExpressionString()
					+ ((z == null) ? ">" : ", " + z.getFunctionExpressionString() + ">");
		case 3:
			return x.getFunctionName() + "(" + param.get(0) + "," + param.get(1) + "," + param.get(2) + ") = <"
					+ x.getFunctionExpressionString() + ", " + y.getFunctionExpressionString()
					+ ((z == null) ? ">" : ", " + z.getFunctionExpressionString() + ">");
		default:
			return "";
		}
	}
}
