package world;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lights.Ambient;
import lights.Light;
import lights.PointLight;
import materials.Matte;
import math.Point;
import math.Transformation;
import shape.Cube;
import shape.Hourglass;
import shape.Plane;
import shape.Shape;
import shape.Sphere;
import shape.Triangle;
import utils.Parser;
import utils.RGBColor;

public class World {

	public RGBColor bg;
	//private Light ambient;
	public List<Shape> objects;
	public List<Light> lights;
	public Ambient ambientLight;
	
	public World(){
		bg = new RGBColor();
		ambientLight = new Ambient();
		objects = new ArrayList<Shape>();
		lights = new ArrayList<Light>();	
	}
	
	/**
	//----------------OBJECTS----------------//
	
	Transformation turn =  Transformation.createRotationX(40);
	Transformation turn2 =  Transformation.createRotationZ(60);
	Transformation turn3 = Transformation.createRotationY(60);
	Transformation t1 = Transformation.createTranslation(0, 0, 0).append(turn).append(turn2).append(turn3);
	Transformation t2 = Transformation.createTranslation(4, -4, 12);
	Transformation t3 = Transformation.createTranslation(15, 15, 0);
	Transformation t4 = Transformation.createTranslation(4, 4, 12);
	Transformation t5 = Transformation.createTranslation(-4, 4, 12);
	Transformation t6 = Transformation.createTranslation(0, 0, 10);
	Transformation identity = Transformation.createIdentity();
	Transformation scale = Transformation.createScale(10, 10, 10);

	Parser parser = new Parser();
	try {
		parser.processLineByLine();
		parser.triangleMesh.setTransformation(turn);
		world.addObject(parser.triangleMesh);
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	
	
	//world.addObject(new Cube(t1.append(t6).append(turn3), 5));
	//world.addObject(new Triangle(identity.append(turn2), new Point(0,-10,-10), new Point(0,10,0), new Point(0,0,10)));
	//world.addObject(new Cube(t4, 1));
	//world.addObject(new Sphere(t2, 4));
	//world.addObject(new Hourglass(identity.append(turn3), Math.PI/6, 8));
	//world.addObject(new TriangleM());
	//world.addObject(new Sphere(t5.append(t6), 4));
	//world.addObject(new Plane(turn3));
	
	//world.addObject(new TriangleMesh());
	
	
	//----------------LIGHTS----------------//
	
	
	
	*/
	
	// Cube + Triangle + Hourglass + sphere
	public void createWorld1(){
		Transformation identity = Transformation.createIdentity();
		Transformation turn =  Transformation.createRotationX(40);
		Transformation turn2 =  Transformation.createRotationZ(90);
		Transformation turn3 = Transformation.createRotationY(60);
		Transformation t1 = Transformation.createTranslation(1, 0, 0);
		Transformation t3 = Transformation.createTranslation(-5,-5, -5);
		Transformation t6 = Transformation.createTranslation(1, 1, 1);
		Matte matte = new Matte(new RGBColor(20,20,20));
		Cube cube = new Cube(t1.append(turn3), matte, 3);
		addObject(cube);
		addObject(new Triangle(turn3.append(t3), matte, new Point(0,-5,-5), new Point(0,4,0), new Point(0,0,4)));
		addObject(new Hourglass(identity.append(turn2).append(t3), matte, Math.PI/6, 8));
		addObject(new Sphere(t6, matte, 3));
		addObject(new Plane(identity, matte));
	}
	
	public void createWorld2(){
		Parser parser = new Parser("sphere");
		Transformation t6 = Transformation.createTranslation(1, 1, 1);
		Transformation turn =  Transformation.createRotationY(70);
		Transformation identity =  Transformation.createIdentity();
		try {
			parser.processLineByLine();
			parser.triangleMesh.setTransformation(turn);
			addObject(parser.triangleMesh);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void createWorld3(){
		Parser parser = new Parser("teapot");
		Transformation turn =  Transformation.createRotationY(60);
		try {
			parser.processLineByLine();
			parser.triangleMesh.setTransformation(turn);
			addObject(parser.triangleMesh);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void createWorld4(){
		Transformation identity = Transformation.createIdentity();
		Matte matte = new Matte(new RGBColor(0,255,255), 0.8, 0.2);
		addObject(new Cube(identity, matte, 3));
		addLight(new PointLight(new RGBColor(255,255,255), 1, new Point(0,7,15)));
		
		Transformation turn =  Transformation.createRotationX(90);
		Transformation t = Transformation.createTranslation(0, -4, 0);
		Matte matte2 =  new Matte(new RGBColor(255,255,0), 0.8,0.2);
		addObject(new Plane(turn.append(t), matte2));
		
		this.ambientLight = new Ambient(new RGBColor(255,255,255),5);
	}
	
	public World(RGBColor backgroundColor, ArrayList<Shape> objects, ArrayList<Light> lights ){
		this.bg = backgroundColor;
		this.objects = objects;
		this.lights = lights;
	}
	
	public void addObject(Shape shape){
		this.objects.add(shape);
		shape.setWorld(this);
	}
	
	public void addLight(Light light){
		this.lights.add(light);
	}
}
