package main;

import gui.ImagePanel;
import gui.ProgressReporter;
import gui.RenderFrame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;
import sampling.Sample;
import shape.Shape;
import shape.Sphere;
import utils.RGBColor;
import world.World;
import Tracer.Tracer;
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
		Point cameraOrigin = new Point(0,0,0);
		Vector lookAt = new Vector(0, 0, 1);
		Vector up = new Vector(0,1,0);
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
		Transformation turn =  Transformation.createRotationX(90);
		Transformation t1 = Transformation.createTranslation(0, 0, 50).append(turn);
		Transformation t2 = Transformation.createTranslation(4, -4, 12);
		Transformation t3 = Transformation.createTranslation(-4, -4, 12);
		Transformation t4 = Transformation.createTranslation(4, 4, 12);
		Transformation t5 = Transformation.createTranslation(-4, 4, 12);
		Transformation identity = Transformation.createIdentity();

		world.addObject(new Sphere(t1, 5));
		world.addObject(new Sphere(t2, 4));
		world.addObject(new Sphere(t3, 4));
		world.addObject(new Sphere(t4, 4));
		world.addObject(new Sphere(t5, 4));
		//shapes.add(new Plane(identity));

		//initialize the lights
		
		
		//TRACER
		tracer = new SimpleTracer(world, panel);
		//render the scene
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				//tracer.trace();
				// create a ray through the center of the pixel.
				Ray ray = camera.generateRay(new Sample(x + 0.5, y + 0.5));
				//HIER moet dat world.background wornden
				RGBColor color = new RGBColor();
				boolean hit = false;
				List<Shape> shapes = world.objects;
				for (Shape shape : shapes){
					Point intersection = shape.intersect(ray);
					if (intersection != null) {
						hit = true;
						color = shape.getColor(intersection);
						break;
					}
				}
				System.out.println(color.r);
				panel.set(x, y, 255, (float)color.r, (float)color.g, (float)color.b);
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

