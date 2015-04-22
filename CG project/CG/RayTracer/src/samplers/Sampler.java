package samplers;

import java.util.ArrayList;

import math.Ray;
import sampling.Sample;
import camera.Camera;

public abstract class Sampler {

	public int nbSamples;
	
	
	public Sampler(int ns ){
		this.nbSamples = ns;
	}
	
	/**
	 * x en y zijn de coordinaten van de pixel waarvoor ge wilt samplen
	 */
	public abstract ArrayList<Ray> getRays(int x, int y, Camera camera);
	
	/**
	 * x en y zijn de dimensies van de rechthoek waarin ge wilt samplen
	 */
	public abstract ArrayList<Sample> getSamples(double x, double y);
}
