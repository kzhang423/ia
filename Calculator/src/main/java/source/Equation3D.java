package source;
import org.mariuszgromada.math.mxparser.*;

/**
 * Class for functions with two variables. Ex. f(x,y)=2*x*y
 * <p>
 * This class supports evaluation at a point, partial derivatives at a point,
 * and double integrals
 */
public class Equation3D {
	Function f;
	Expression e1;

	/**
	 * Default constructor for {@link Equation3D}
	 */
	public Equation3D() {
		f = null;
		e1 = null;
	}

	/**
	 * This constructor is used to store a String expression as a {@link Equation3D}
	 * <p>
	 * Function expressions must have a left-hand side. Ex. f(x,y)=x^(2*y), NOT
	 * x^(2*y)
	 * <p>
	 * All expressions must have all operators typed out. Ex. f(x,y)=2*x, NOT
	 * f(x,y)=2x
	 *
	 * @param func The function as a String
	 */
	public Equation3D(String func) {
		f = new Function(func);
	}

	/**
	 * Evaluates a {@link Equation3D} at a point (x,y)
	 *
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return The value of the equation at (x,y) as a double
	 */
	public double calculate(String x, String y) {
		e1 = new Expression(f.getFunctionName() + "(" + x + "," + y + ")", f);
		return e1.calculate();
	}

	/**
	 * Approximates the change in x at a point (x,y)
	 *
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return An approximation of the change in x at the point (x,y) as a double
	 */
	public double partialX(String x, String y) {
		Function temp = new Function("f(" + f.getParameterName(0) + ")="
				+ f.getFunctionExpressionString().replaceAll(f.getParameterName(1), y));
		Expression e1 = new Expression(
				"der(" + temp.getFunctionExpressionString() + "," + temp.getParameterName(0) + "," + x + ")");
		return e1.calculate();
	}

	/**
	 * Approximates the change in y at a point (x,y)
	 *
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return An approximation of the change in y at the point (x,y) as a double
	 */
	public double partialY(String x, String y) {
		Function temp = new Function("f(" + f.getParameterName(1) + ")="
				+ f.getFunctionExpressionString().replaceAll(f.getParameterName(0), x));
		Expression e1 = new Expression(
				"der(" + temp.getFunctionExpressionString() + "," + temp.getParameterName(0) + "," + y + ")");
		return e1.calculate();
	}

	/**
	 * Approximates a double integral with order DYDX
	 * <p>
	 * Bounds cannot have left-hand side. Ex. x^2, NOT f(x)=x^2
	 *
	 * @param xl DX lower bound
	 * @param xr DX upper bound
	 * @param yb DY lower bound
	 * @param yt DY upper bound
	 * @return An approximation of the integral of the equation over a region as a
	 *         double
	 */
	public double integralYX(String xl, String xr, String yb, String yt) {
		Expression temp = new Expression("int(int(" + f.getFunctionExpressionString() + "," + f.getParameterName(1)
				+ "," + yb + "," + yt + ")," + f.getParameterName(0) + "," + xl + "," + xr + ")");
		return temp.calculate();
	}

	/**
	 * Approximates a double integral with order DXDY
	 * <p>
	 * Bounds cannot have left-hand side. Ex. x^2, NOT f(x)=x^2
	 *
	 * @param xl DX lower bound
	 * @param xr DX upper bound
	 * @param yb DY lower bound
	 * @param yt DY upper bound
	 * @return An approximation of the integral of the equation over a region as a
	 *         double
	 */
	public double integralXY(String xl, String xr, String yb, String yt) {
		Expression temp = new Expression("int(int(" + f.getFunctionExpressionString() + "," + f.getParameterName(0)
				+ "," + xl + "," + xr + ")," + f.getParameterName(1) + "," + yb + "," + yt + ")");
		return temp.calculate();
	}

	public String toString() {
		return f.getFunctionName() + "(" + f.getParameterName(0) + "," + f.getParameterName(1) + ")" + " = "
				+ f.getFunctionExpressionString();
	}
}
