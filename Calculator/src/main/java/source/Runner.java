package source;
import java.util.*;
import java.util.regex.Pattern;
import org.mariuszgromada.math.mxparser.Function;
/**
 * The runner class for Calculator
 * @author kzhang
 */
public class Runner {
	static Scanner kb = new Scanner(System.in);
	static ArrayList<Object> store = new ArrayList<Object>();
	static Pattern types = Pattern.compile("[a-zA-Z]+");

	/**
	 * Determines if an expression/vector is valid
	 *
	 * @param in
	 * @return true if valid, false if invalid
	 */
	public static boolean validity(Object in) {
		if (in instanceof Equation) {
			Equation temp = (Equation) in;
			Double test = temp.integral("-10", "10");
			return (test.equals(Double.NaN)) ? false : true;
		} else if (in instanceof Equation3D) {
			Equation3D temp = (Equation3D) in;
			Double test = temp.integralYX("-10", "-10", "-10", "-10");
			return (test.equals(Double.NaN)) ? false : true;
		} else if (in instanceof VectorScalar) {
			VectorScalar temp = (VectorScalar) in;
			Double test = temp.mag();
			return (test.equals(Double.NaN)) ? false : true;
		} else if (in instanceof VectorFunction) {
			VectorFunction temp = (VectorFunction) in;
			switch (temp.param.size()) {
			case 1: {
				Double test = temp.calculate("10").mag();
				return (test.equals(Double.NaN)) ? false : true;
			}
			case 2: {
				Double test = temp.calculate("10", "10").mag();
				return (test.equals(Double.NaN)) ? false : true;
			}
			case 3: {
				Double test = temp.calculate("10", "10", "10").mag();
				return (test.equals(Double.NaN)) ? false : true;
			}
			default:
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws Exception {
		System.gc();
		boolean run = true;
		while (run) {
			System.out.println();
			System.out.println("Current expressions:");
			int count = 1;
			for (Object c : store) {
				System.out.println(count + ". " + c);
				count++;
			}
			System.out.println();
			System.out.println("a - Select an expression\ns - Add an expression\nd - Remove an expression\nf - Exit");
			String input = "";
			input = kb.nextLine().toLowerCase();
			switch (input) {
			case "a": {
				if (store.size() == 0) {
					System.out.println("Nothing to select");
					break;
				}
				int answer = 0;
				try {
					answer = Integer.parseInt(kb.nextLine());
					store.get(answer - 1);
				} catch (Exception e) {
					System.out.println("Invalid selection");
					break;
				}
				@SuppressWarnings("unused")
				Selection instance = new Selection(store.get(answer - 1));
				break;
			}
			case "s": {
				String temp = kb.nextLine();
				// Vector
				if ((!types.matcher(temp).find()) && temp.contains("<") && temp.contains(">")) {
					store.add(VectorScalar.create(temp));
					if (!validity(store.get(store.size() - 1))) {
						System.out.println("Unsupported/Invalid expression");
						store.remove(store.size() - 1);
					}
					break;

				} else if (types.matcher(temp).find() && temp.contains("<") && temp.contains(">")) {
					store.add(new VectorFunction(temp));
					if (!validity(store.get(store.size() - 1))) {
						System.out.println("Unsupported/Invalid expression");
						store.remove(store.size() - 1);

					}
					break;

				}
				//
				Function tempF = new Function(temp);
				switch (tempF.getParametersNumber()) {

				case 1: {
					store.add(new Equation(temp));
					break;

				}

				case 2: {
					store.add(new Equation3D(temp));
					break;

				}

				default: {
					store.add(new Equation(temp));
					break;
				}

				}
				if (!validity(store.get(store.size() - 1))) {
					System.out.println("Unsupported/Invalid expression");
					store.remove(store.size() - 1);

				}
				break;

			}

			case "d": {
				if (store.size() == 0) {
					System.out.println("Nothing to remove");

				} else {
					String answer = kb.nextLine();
					try {
						store.remove(Integer.parseInt(answer) - 1);

					} catch (Exception e) {
						System.out.println("Cannot remove");

					}

				}
				break;

			}

			case "f": {
				System.exit(0);

			}

			default: {
				System.out.println("Invalid option");
				break;

			}

			}

		}

	}
}
