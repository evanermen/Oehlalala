package samplers;

import java.util.ArrayList;

import sampling.Sample;
import camera.Camera;
import math.Point;
import math.Ray;

public class Random extends Sampler {

	public Random(int ns) {
		super(ns);
	}



	@Override
	public ArrayList<Ray> getRays(int x, int y, Camera camera) {
		ArrayList<Ray> rays = new ArrayList<Ray>();
		for(int i=0; i < nbSamples; i++){
			double dx = Math.random();
			double dy = Math.random();
			rays.add(camera.generateRay(new Sample(x + dx , y + dy )));
		}
		return rays;
	}



	@Override
	public ArrayList<Sample> getSamples(double x, double y) {
		ArrayList<Sample> samples = new ArrayList<Sample>();
		for(int i=0; i < nbSamples; i++){
			double dx = Math.random();
			double dy = Math.random();
			samples.add(new Sample(x*dx, y*dy));
		}
		return samples;
	}

	

}
