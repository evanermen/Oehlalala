package main;

import gui.ImagePanel;
import gui.ProgressReporter;
import gui.RenderFrame;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import math.Point;
import math.Transformation;
import math.Vector;
import shape.Cube;
import shape.Hourglass;
import shape.Sphere;
import shape.Triangle;
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
		int width = 640;
		int height = 640;
		Point cameraOrigin = new Point(0 ,-10 ,-10);
		Vector lookAt = new Vector(0,1, 1);
		Vector up = new Vector(0,0,1);
		double fov = 90;
		
		World world = new World();
		Tracer tracer;
		
		
		

		// parse the command line arguments
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

		// initialize the camera
		PerspectiveCamera camera = new PerspectiveCamera(width, height,
				cameraOrigin, lookAt, up, fov);

		// initialize the graphical user interface
		ImagePanel panel = new ImagePanel(width, height);
		RenderFrame frame = new RenderFrame("Sphere", panel);

		// initialize the progress reporter
		ProgressReporter reporter = new ProgressReporter("Rendering", 40, width
				* height, false);
		reporter.addProgressListener(frame);
		
		// initialize the scene
		Transformation turn =  Transformation.createRotationX(30);
		Transformation turn2 =  Transformation.createRotationZ(23);
		Transformation turn3 = Transformation.createRotationY(50);
		Transformation t1 = Transformation.createTranslation(0, 0, 0).append(turn).append(turn2).append(turn3);
		Transformation t2 = Transformation.createTranslation(4, -4, 12);
		Transformation t3 = Transformation.createTranslation(15, 15, 0);
		Transformation t4 = Transformation.createTranslation(4, 4, 12);
		Transformation t5 = Transformation.createTranslation(-4, 4, 12);
		Transformation t6 = Transformation.createTranslation(0, 0, 10);
		Transformation identity = Transformation.createIdentity();
		Transformation scale = Transformation.createScale(10, 10, 10);

		//world.addObject(new Cube(t1.append(t6).append(turn3), 5));
		//world.addObject(new Triangle(identity, new Point(0,-10,-10), new Point(0,10,20), new Point(10,0,5)));
		//world.addObject(new Cube(identity, 5));
		//world.addObject(new Sphere(t4, 4));
		world.addObject(new Hourglass(identity, 6, 5));
		//world.addObject(new Sphere(t5.append(t6), 4));
		//world.addObject(new Plane(turn3));

		//initialize the lights
		
		
		//TRACER
		tracer = new SimpleTracer(world, panel, camera);
		
		//render the scene
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				tracer.trace(x, y);
				}
			reporter.update(height);
		}
		reporter.done();

		// save the output
		try {
			ImageIO.write(panel.getImage(), "png", new File("output.png"));
		} catch (IOException e) {
		}
	}
}

