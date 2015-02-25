package main;

import gui.ImagePanel;
import gui.ProgressReporter;
import gui.RenderFrame;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import lights.Light;
import lights.PointLight;
import materials.Matte;
import math.Point;
import math.Transformation;
import math.Vector;
import shape.Cube;
import tracer.LightTracer;
import tracer.SimpleTracer;
import tracer.Tracer;
import world.World;
import camera.PerspectiveCamera;

/**
 * Entry point of your renderer.
 * 
 * @author Niels Billen
 * @version 1.0
 */
public class Renderer {


	/**
	 * Entry point of your renderer.
	 * 
	 * @param arguments
	 *            command line arguments.
	 */
	public static void main(String[] arguments) {

		//------------------------VIEW_SETTINGS-------------------------------//
		int width = 640;
		int height = 640;
		Point cameraOrigin = new Point(15, 15,15);
		Vector lookAt = new Vector(-1,-1, -1);
		Vector up = new Vector(0,1,0);
		double fov = 90;

		World world = new World();
		Tracer tracer;



		//------------------------PARSE_COMMAND_LINE-------------------------------//
		for (int i = 0; i < arguments.length; ++i) {
			if (arguments[i].startsWith("-")) {
				try {
					if (arguments[i].equals("-width"))
						width = Integer.parseInt(arguments[++i]);
					else if (arguments[i].equals("-height"))
						height = Integer.parseInt(arguments[++i]);
					else if (arguments[i].equals("-help")) {
						System.out.println("usage: "
								+ "[-width  width of the image] "
								+ "[-height  height of the image]");
						return;
					} else {
						System.err.format("unknown flag \"%s\" encountered!\n",
								arguments[i]);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.err.format("could not find a value for "
							+ "flag \"%s\"\n!", arguments[i]);
				}
			} else
				System.err.format("unknown value \"%s\" encountered! "
						+ "This will be skipped!\n", arguments[i]);
		}

		// validate the input
		if (width <= 0)
			throw new IllegalArgumentException("the given width cannot be "
					+ "smaller than or equal to zero!");
		if (height <= 0)
			throw new IllegalArgumentException("the given height cannot be "
					+ "smaller than or equal to zero!");


		//------------------------INITIALIZE_CAMERA_GUI_PROGRESS_REPORTER-------------------------------//
		PerspectiveCamera camera = new PerspectiveCamera(width, height,
				cameraOrigin, lookAt, up, fov);

		// initialize the graphical user interface
		ImagePanel panel = new ImagePanel(width, height);
		RenderFrame frame = new RenderFrame("Sphere", panel);

		// initialize the progress reporter
		ProgressReporter reporter = new ProgressReporter("Rendering", 40, width
				* height, true);
		reporter.addProgressListener(frame);


		//------------------------SET_WORLD-------------------------------//

		world.createWorld4();


		//----------------------------------TRACE------------------------------------//
		tracer = new SimpleTracer(world, panel, camera);

		//render the scene
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				//if(x==0 && y>150){
				//System.out.println("ok go");
				//}
				tracer.trace(x, y);
			}
			reporter.update(height);
		}

		drawLights(world, panel, camera, width, height);
		reporter.done();

		// save the output
		try {
			ImageIO.write(panel.getImage(), "png", new File("output.png"));
		} catch (IOException e) {
		}

	}

	private static void drawLights(World world, ImagePanel panel, PerspectiveCamera camera, double width, double height){
		System.out.println("enter draw lights");
		World world2 = new World();
		world2.bg = world.bg;
		List<Light> lights = world.lights;
		System.out.println("size lights = "+ lights.size());
		for(Light light : lights){ 
			Point point = ((PointLight) light).location;
			Transformation t1 = Transformation.createTranslation(point.x, point.y, point.z);
			Matte matte = new Matte();
			world2.addObject(new Cube(t1, matte, 0.2));
		}
		Tracer tracer = new LightTracer(world2, panel, camera);

		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				//if(x==0 && y>150){
				//System.out.println("ok go");
				//}
				tracer.trace(x, y);
			}
		}
	}
}

