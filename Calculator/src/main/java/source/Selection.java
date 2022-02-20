package source;
import java.text.DecimalFormat;
import java.util.*;

import org.jzy3d.analysis.AnalysisLauncher;

public class Selection{
	static DecimalFormat format = new DecimalFormat(".#####");
	Object selected;
	static Scanner kb = new Scanner(System.in);

	/**
	 * This constructor is used to store the selection
	 *
	 * @param input The chosen expression
	 * @throws Exception 
	 */
	public Selection(Object input) throws Exception {
		selected = input;
		if(selected instanceof Equation || selected instanceof Equation3D) runEq();
		else if(selected instanceof VectorScalar || selected instanceof VectorFunction) runVec();
	}

	/**
	 * Equation menu
	 *
	 * @throws Exception
	 */
	public void runEq() throws Exception {
		String output = (selected instanceof Equation) ? "a - Graph\ns - Evaluate\nd - Nth Derivative\nf - Integral"
				: "a - Graph\ns - Evaluate\nd - Partial Derivative at point\nf - Double Integral";
		System.out.println(output);
		String answer = kb.nextLine();
		switch (answer) {
		case "a":
			runGraph(selected);
			break;
		case "s":
			runEvaluate(selected);
			break;
		case "d":
			runDerivative(selected);
			break;
		case "f":
			runIntegral(selected);
			break;
		default:
			System.out.println("Not an option");
			break;
		}
	}

	/**
	 * Vector menu
	 */
	public void runVec() {
		String output = (selected instanceof VectorScalar)
				? ("a - Add with another vector\ns - Multiply by scalar\nd - Dot product with another vector\nf - Cross product with another vector\ng - Compute unit vector\nh - Find magnitude\nj - Scalar projection\nk - Vector projection")
				: ("a - Evaluate\ns - Unit tangent");
		System.out.println(output);
		String answer = kb.nextLine();
		switch (answer) {
		case "a": {
			if (selected instanceof VectorScalar)
				runAdd(selected);
			else
				vectorFEval(selected);
			break;
		}
		case "s": {
			if (selected instanceof VectorScalar)
				runMultiply(selected);
			else
				unitTangent(selected);
			break;
		}
		case "d":
			if (selected instanceof VectorScalar)
				runDot(selected);
			break;
		case "f":
			if (selected instanceof VectorScalar)
				runCross(selected);
			break;
		case "g":
			if (selected instanceof VectorScalar)
				runUnit(selected);
			break;
		case "h":
			if (selected instanceof VectorScalar)
				runMag(selected);
			break;
		case "j":
			if (selected instanceof VectorScalar)
				runSProject(selected);
			break;
		case "k":
			if (selected instanceof VectorScalar)
				runVProject(selected);
			break;
		default:
			System.out.println("Invalid option");
			break;
		}
	}
	// Equations

	/**
	 * Runs the graph for the selected expression
	 *
	 * @param in
	 * @throws Exception
	 */
	public void runGraph(Object in) throws Exception {
		if (in instanceof Equation) {
			Grapher2D graph = new Grapher2D((Equation) in);
			graph.draw();
		} else if (in instanceof Equation3D) {
			Grapher3D draw = new Grapher3D((Equation3D) in);
			AnalysisLauncher.open(draw);
		}
	}

	/**
	 * Runs the evaluation menu for the selected expression
	 *
	 * @param in
	 */
	public void runEvaluate(Object in) {
		if (in instanceof Equation) {
			Equation temp = (Equation) in;
			System.out.println(temp.f.getParameterName(0) + "=");
			String input = kb.nextLine();
			double calc = temp.calculate(Double.parseDouble(input));
			System.out.println(temp.e1.getExpressionString() + " = " + format.format(calc));
		} else if (in instanceof Equation3D) {
			Equation3D temp = (Equation3D) in;
			System.out.println(temp.f.getParameterName(0) + "=");
			String x = kb.nextLine();
			System.out.println(temp.f.getParameterName(1) + "=");
			String y = kb.nextLine();
			double calc = temp.calculate(x, y);
			System.out.println(temp.e1.getExpressionString() + " = " + format.format(calc));
		}
	}

	/**
	 * Runs the derivative menu for the selected expression
	 *
	 * @param in
	 */
	public void runDerivative(Object in) {
		if (in instanceof Equation) {
			Equation temp = (Equation) in;
			System.out.println(temp.f.getParameterName(0) + "=");
			String input = kb.nextLine();
			System.out.println("n=");
			String n = kb.nextLine();
			System.out.println("Derivative at x = " + input + " is: "
					+ format.format(temp.derivativeN(Double.parseDouble(input), Integer.parseInt(n))));
		} else if (in instanceof Equation3D) {
			Equation3D temp = ((Equation3D) in);
			System.out.println(
					"Derivative with respect to " + temp.f.getParameterName(0) + " or " + temp.f.getParameterName(1));
			String input = kb.nextLine();
			if (input.equals(temp.f.getParameterName(0))) {
				System.out.println(temp.f.getParameterName(0) + "=");
				String x = kb.nextLine();
				System.out.println(temp.f.getParameterName(1) + "=");
				String y = kb.nextLine();
				System.out.println("Derivative at (" + x + "," + y + ") is " + format.format(temp.partialX(x, y)));
			} else if (input.equals(temp.f.getParameterName(1))) {
				System.out.println(temp.f.getParameterName(0) + "=");
				String x = kb.nextLine();
				System.out.println(temp.f.getParameterName(1) + "=");
				String y = kb.nextLine();
				System.out.println("Derivative at (" + x + "," + y + ") is " + format.format(temp.partialY(x, y)));
			} else {
				System.out.println("Invalid selection");
			}
		}
	}

	/**
	 * Runs the integral menu for the selected expression
	 *
	 * @param in
	 */
	public void runIntegral(Object in) {
		if (in instanceof Equation) {
			Equation temp = (Equation) in;
			System.out.println("Lower bound");
			String lower = kb.nextLine();
			System.out.println("Upper bound");
			String upper = kb.nextLine();
			System.out.println("Integral of " + temp + " from " + Double.parseDouble(lower) + " to "
					+ Double.parseDouble(upper) + " is: " + format.format(temp.integral(lower, upper)));
		} else if (in instanceof Equation3D) {
			Equation3D temp = (Equation3D) in;
			System.out.println("Order? (d" + temp.f.getParameterName(1) + "d" + temp.f.getParameterName(0) + "/d"
					+ temp.f.getParameterName(0) + "d" + temp.f.getParameterName(1) + ")");
			String order = kb.nextLine();
			if (!order.equals("d" + temp.f.getParameterName(1) + "d" + temp.f.getParameterName(0))
					&& !order.equals("d" + temp.f.getParameterName(0) + "d" + temp.f.getParameterName(1))) {
				System.out.println("Not an option");
				return;
			}
			System.out.println((order.equals("d" + temp.f.getParameterName(1) + "d" + temp.f.getParameterName(0)))
					? "d" + temp.f.getParameterName(1) + " lower bound"
					: "d" + temp.f.getParameterName(0) + " lower bound");
			String a = kb.nextLine();
			System.out.println((order.equals("d" + temp.f.getParameterName(1) + "d" + temp.f.getParameterName(0)))
					? "d" + temp.f.getParameterName(1) + " upper bound"
					: "d" + temp.f.getParameterName(0) + " upper bound");
			String b = kb.nextLine();
			System.out.println((order.equals("d" + temp.f.getParameterName(1) + "d" + temp.f.getParameterName(0)))
					? "d" + temp.f.getParameterName(0) + " lower bound"
					: "d" + temp.f.getParameterName(1) + " lower bound");
			String c = kb.nextLine();
			System.out.println((order.equals("d" + temp.f.getParameterName(1) + "d" + temp.f.getParameterName(0)))
					? "d" + temp.f.getParameterName(0) + " upper bound"
					: "d" + temp.f.getParameterName(1) + " upper bound");
			String d = kb.nextLine();
			System.out.println((order.equals("d" + temp.f.getParameterName(1) + "d" + temp.f.getParameterName(0)))
					? "Double integral of " + temp + " over the region is: "
							+ format.format(temp.integralYX(c, d, a, b))
					: "Double integral of " + temp + " over the region is: "
							+ format.format(temp.integralXY(a, b, c, d)));
		}
	}

	// Vectors
	/**
	 * Runs the vector addition menu for the selected {@link VectorScalar}
	 *
	 * @param in
	 */
	public void runAdd(Object in) {
		VectorScalar temp;
		temp = VectorScalar.create(kb.nextLine());
		Double valid = temp.mag();
		if (valid.equals(Double.NaN)) {
			System.out.println("Unsupproted/Invalid expression");
			return;
		} else if (!(temp.twoD == ((VectorScalar) in).twoD)) {
			System.out.println("Dimensions not the same");
			return;
		}
		((VectorScalar) in).add(temp);
		System.out.println(in);
	}

	/**
	 * Runs the scalar multiplication menu for the selected {@link VectorScalar}
	 *
	 * @param in
	 */
	public void runMultiply(Object in) {
		Double scalar = 0.0;
		try {
			scalar = Double.parseDouble(kb.nextLine());
		} catch (Exception e) {
			System.out.println("Not a scalar");
			return;
		}
		((VectorScalar) in).multiplyS(scalar);
	}

	/**
	 * Runs the dot product menu for the selected {@link VectorScalar}
	 *
	 * @param in
	 */
	public void runDot(Object in) {
		VectorScalar temp;
		temp = VectorScalar.create(kb.nextLine());
		Double valid = temp.mag();
		if (valid.equals(Double.NaN)) {
			System.out.println("Unsupproted/Invalid expression");
			return;
		} else if (!(temp.twoD == ((VectorScalar) in).twoD)) {
			System.out.println("Dimensions not the same");
			return;
		}
		System.out.println(((VectorScalar) in).dot(temp));
	}

	/**
	 * Runs the cross product menu for the selected {@link VectorScalar}
	 *
	 * @param in
	 */
	public void runCross(Object in) {
		VectorScalar temp;
		temp = VectorScalar.create(kb.nextLine());
		Double valid = temp.mag();
		if (valid.equals(Double.NaN)) {
			System.out.println("Unsupproted/Invalid expression");
			return;
		} else if (!(temp.twoD == ((VectorScalar) in).twoD)) {
			System.out.println("Dimensions not the same");
			return;
		}
		System.out.println(((VectorScalar) in).cross(temp));
	}

	/**
	 * Prints out the unit vector for the selected {@link VectorScalar}
	 *
	 * @param in
	 */
	public void runUnit(Object in) {
		System.out.println(((VectorScalar) in).returnUnit());
	}

	/**
	 * Prints out the magnitude for the selected {@link VectorScalar}
	 *
	 * @param in
	 */
	public void runMag(Object in) {
		System.out.println(((VectorScalar) in).mag());
	}

	/**
	 * Runs the scalar projection menu for the selected {@link VectorScalar}
	 *
	 * @param in
	 */
	public void runSProject(Object in) {
		VectorScalar temp;
		System.out.println("Vector to project onto");
		temp = VectorScalar.create(kb.nextLine());
		Double valid = temp.mag();
		if (valid.equals(Double.NaN)) {
			System.out.println("Unsupproted/Invalid expression");
			return;
		} else if (!(temp.twoD == ((VectorScalar) in).twoD)) {
			System.out.println("Dimensions not the same");
			return;
		}
		System.out.println(((VectorScalar) in).scalarProject(temp));
	}

	/**
	 * Runs the vector projection menu for the selected {@link VectorScalar}
	 *
	 * @param in
	 */
	public void runVProject(Object in) {
		VectorScalar temp;
		System.out.println("Vector to project onto");
		temp = VectorScalar.create(kb.nextLine());
		Double valid = temp.mag();
		if (valid.equals(Double.NaN)) {
			System.out.println("Unsupproted/Invalid expression");
			return;
		} else if (!(temp.twoD == ((VectorScalar) in).twoD)) {
			System.out.println("Dimensions not the same");
			return;
		}
		System.out.println(((VectorScalar) in).vectorProject(temp));
	}

	/**
	 * Runs the evaluation menu for the selected {@link VectorFunction}
	 *
	 * @param in
	 */
	public void vectorFEval(Object in) {
		VectorFunction temp = (VectorFunction) in;
		switch (temp.param.size()) {
		case 1: {
			System.out.println(temp.param.get(0) + " = ");
			String x = kb.nextLine();
			System.out.println(temp.calculate(x));
			break;
		}
		case 2: {
			System.out.println(temp.param.get(0) + " = ");
			String x = kb.nextLine();
			System.out.println(temp.param.get(1) + " = ");
			String y = kb.nextLine();
			System.out.println(temp.calculate(x, y));
			break;
		}
		case 3: {
			System.out.println(temp.param.get(0) + " = ");
			String x = kb.nextLine();
			System.out.println(temp.param.get(1) + " = ");
			String y = kb.nextLine();
			System.out.println(temp.param.get(2) + " = ");
			String z = kb.nextLine();
			System.out.println(temp.calculate(x, y, z));
			break;
		}
		default:
			break;
		}
	}

	/**
	 * Runs the unit tangent menu for the selected {@link VectorFunction}
	 *
	 * @param in
	 */
	public void unitTangent(Object in) {
		VectorFunction temp = (VectorFunction) in;
		if (temp.param.size() == 1) {
			System.out.println(temp.param.get(0) + " = ");
			double t = Double.parseDouble(kb.nextLine());
			System.out.println(temp.unitTangent(t));
		} else {
			System.out.println("Not a space curve");
		}
	}
}
