package source;
import org.jzy3d.analysis.AWTAbstractAnalysis;
import org.jzy3d.chart.factories.AWTChartFactory;
import org.jzy3d.chart.factories.AWTPainterFactory;
import org.jzy3d.chart.factories.IChartFactory;
import org.jzy3d.chart.factories.IPainterFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.SurfaceBuilder;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
/**
 * Class for modeling three-dimensional functions
 */
public class Grapher3D extends AWTAbstractAnalysis {
    Equation3D eq;

    /**
     * This constructor is used to feed a function to graph into the Grapher3D.
     *
     * @param eq The function to graph
     */
    public Grapher3D(Equation3D eq) {
        this.eq = eq;
    }

    public void init() {
        Mapper mapper = new Mapper() {
            @Override
            public double f(double x, double y) {
                return eq.calculate(Double.toString(x), Double.toString(y));
            }
        };
        Range range = new Range(-10, 10);
        int steps = 100;
        final Shape surface = new SurfaceBuilder().orthonormal(new OrthonormalGrid(range, steps), mapper);
        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface, new Color(1, 1, 1, .5f)));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(true);
        surface.setWireframeColor(Color.BLACK);
        GLCapabilities c = new GLCapabilities(GLProfile.get(GLProfile.GL2));
        IPainterFactory p = new AWTPainterFactory(c);
        IChartFactory f = new AWTChartFactory(p);
        chart = f.newChart(Quality.Advanced());
        chart.getScene().getGraph().add(surface);
        return;
    }
}