package main;

import gui.ImagePanel;
import world.World;
import Tracer.Tracer;

public class SimpleTracer extends Tracer {
	//Private/ public??
	World world;
	ImagePanel panel;
	
	public SimpleTracer(World world, ImagePanel panel){
		this.world = world;
		this.panel = panel;
	}
	
	public void trace(){
		
	}

}
