package source;
import graph.*;

/**
 * Class for modeling two-dimensional functions
 */
class Grapher2D {
	SimpleGraph graph;

	/**
	 * This constructor is used to create an empty {@link SimpleGraph}
	 */
	public Grapher2D() {
		graph = new SimpleGraph();
	}

	/**
	 * This constructor is used to create a {@link SimpleGraph} with a function
	 *
	 * @param eq {@link Equation} to graph
	 */
	public Grapher2D(Equation eq) {
		graph = new SimpleGraph();
		graph.addFunction(x -> eq.calculate(x));
	}

	/**
	 * Adds a {@link Equation} to the graph
	 *
	 * @param eq The Equation to add
	 */
	public void add(Equation eq) {
		graph.addFunction(x -> eq.calculate(x));
	}

	/**
	 * Opens a separate window that displays the {@link SimpleGraph}
	 */
	public void draw() {
		graph.display();
	}
}
