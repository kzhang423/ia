package source;
import org.mariuszgromada.math.mxparser.*;

/**
 * Class for single variable functions. Ex. f(x) = x^2
 * <p>
 * This class supports evaluation at a value, derivative at a value, and
 * definite integrals.
 */
public class Equation {
	Function f;
	Expression e1;

	/**
	 * Default constructor for {@link Equation}.
	 */
	public Equation() {
		f = null;
		e1 = null;
	}

	/**
	 * This constructor is used to store a String expression as a {@link Equation}
	 * <p>
	 * Function expressions must have a left-hand side. Ex. f(x)=2*x, NOT 2*x
	 * <p>
	 * All expressions must have all operators typed out. Ex. f(x)=2*x, NOT f(x)=2x
	 *
	 * @param func The function as a String
	 */
	public Equation(String func) {
		f = new Function(func);
	}

	public Equation(Function func) {
		f = func;
	}

	/**
	 * Evaluates an {@link Equation} at a value
	 *
	 * @param x The value to evaluate at
	 * @return The value of the equation at a value as a double
	 */
	public double calculate(double x) {
		e1 = new Expression(f.getFunctionName() + "(" + Double.toString(x) + ")", f);
		return e1.calculate();
	}

	/**
	 * Approximates the nth derivative of an {@link Equation} at a value
	 *
	 * @param x The value to find the derivative at
	 * @param n Order of the derivative
	 * @return An approximation of the nth derivative of the equation at a value as
	 *         a double
	 */
	public double derivativeN(double x, int n) {
		final double inc = 0.001;
		double der = 0;
		for (int i = 0; i <= n; i++) {
			der += Math.pow(-1, i) * binomialCo(n, i) * this.calculate(x + ((((double) n / 2) - i) * inc));
		}
		return der / Math.pow(inc, n);
	}

	/**
	 * Computes the binomial coefficient for a (n k)
	 *
	 * @param n
	 * @param k
	 * @return The binomial coefficient for a (n k)
	 */
	public double binomialCo(int n, int k) {
		return factorial(n) / (factorial(k) * factorial(n - k));
	}

	/**
	 * Find the factorial of a number n
	 *
	 * @param n
	 * @return n factorial
	 */
	public double factorial(int n) {
		int sum = 1;
		for (int i = 1; i <= n; i++) {
			sum *= i;
		}
		return sum;
	}

	/**
	 * Approximates the integral of an {@link Equation} over an interval
	 *
	 * @param lower The lower bound of the interval
	 * @param upper The upper bound of the interval
	 * @return An approximation of the integral of the equation over an interval
	 */
	public double integral(String lower, String upper) {
		e1 = new Expression("int(" + f.getFunctionExpressionString() + "," + f.getParameterName(0) + ", " + lower + ", "
				+ upper + ")");
		return e1.calculate();
	}

	public String toString() {
		return f.getFunctionName() + "(" + f.getParameterName(0) + ")" + " = " + f.getFunctionExpressionString();
	}
}
