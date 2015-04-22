package samplers;

import java.util.ArrayList;

import math.Ray;
import sampling.Sample;
import camera.Camera;

public class Jittered extends Sampler {

	//probleem hier is als ge een rechthoek hebt die veel langer dan breed is of omgekeerd
	//U opdeling gaat dan ook zo zijn 
	
	
	public Jittered(int ns) {
		super(ns);
		if (ns < 0){
			throw new IllegalArgumentException("The number of rays should be a positive number");
		}
		long tst = (long)(Math.sqrt(ns) + 0.5);
		if (tst*tst != ns) {
			throw new IllegalArgumentException("The number of rays should be a square");
		}
		
 	}

	@Override
	public ArrayList<Ray> getRays(int x, int y, Camera camera) {
		ArrayList<Ray> rays = new ArrayList<Ray>();

		if(nbSamples == 1){
			rays.add(camera.generateRay(new Sample(x + 0.5, y + 0.5)));
		}
		else{ 
			for(int i=0; i < nbSamples; i++){
				int root = (int)Math.sqrt(nbSamples);
				double spx = 1.0/root; //subpixel width
				double m = i%root;
				double q = i/root;
				double dx = Math.random()*spx;
				double dy = Math.random()*spx;
				rays.add(camera.generateRay(new Sample(x + m*spx + dx , y + q*spx + dy )));
			}
		}
		return rays;
	}

	@Override
	public ArrayList<Sample> getSamples(double x, double y) {
		ArrayList<Sample> samples = new ArrayList<Sample>();
		if(nbSamples == 1){
			samples.add(new Sample(x * 0.5, y * 0.5));
		}
		else{ 
			for(int i=0; i < nbSamples; i++){
				int root = (int)Math.sqrt(nbSamples);
				double spx = x/root; //subpixel width
				double spy = y/root;
				double m = i%root; //kolom
				double q = i/root;  //rij
				double dx = Math.random()*spx;
				double dy = Math.random()*spy;
				samples.add(new Sample(m*spx + dx , q*spy + dy ));
			}
		}
		return samples;
	}

}
