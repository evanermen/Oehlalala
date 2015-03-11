package world;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lights.Ambient;
import lights.Light;
import lights.PointLight;
import materials.LightMaterial;
import materials.Material;
import materials.Matte;
import materials.Phong;
import math.Point;
import math.Transformation;
import shape.Cube;
import shape.Hourglass;
import shape.Plane;
import shape.Shape;
import shape.Sphere;
import textures.ConstantColor;
import textures.ImageTexture;
import textures.Texture;
import utils.Parser;
import utils.RGBColor;

public class World {

	public RGBColor bg;
	//private Light ambient;
	public List<Shape> objects;
	public List<Light> lights;
	public Ambient ambientLight = new Ambient(new RGBColor(0,0,0), 0);
	
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
		Texture tex = new ConstantColor(new RGBColor(0,0.5,0));
		Matte matte = new Matte(tex);
		Cube cube = new Cube(t1.append(turn3), matte, 1);
		addObject(cube);
		addObject(new Hourglass(identity.append(turn2).append(t3), matte, Math.PI/6, 8));
		addObject(new Sphere(t6, matte, 1));
		addObject(new Plane(identity, matte));
	}
	
	public void createWorld2(){
		Parser parser = new Parser("sphere");
		Transformation t6 = Transformation.createTranslation(1, 1, 1);
		Transformation turn =  Transformation.createRotationY(70);
		Transformation identity =  Transformation.createIdentity();
		Material mat = new Matte(new ConstantColor(new RGBColor(1,1,0)), 0.2,0.2);
		try {
			parser.processLineByLine();
			parser.triangleMesh.setTransformation(identity);
			parser.triangleMesh.setMaterial(mat);
			addObject(parser.triangleMesh);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		addLight(new PointLight(new RGBColor(1,1,1),3, new Point(7,4,0)));
		//addLight(new PointLight(new RGBColor(1,1,1), 1, new Point(-7,4,-7)));
		
	}
	
	public void createWorld3(){
		Parser parser = new Parser("bunny");
		Transformation turn =  Transformation.createIdentity();
		Matte material = new Matte(new ConstantColor(new RGBColor(1,1,0)), 0.8, 0.2);
		try {
			parser.processLineByLine();
			parser.triangleMesh.setTransformation(turn);
			parser.triangleMesh.setMaterial(material);
			addObject(parser.triangleMesh);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		addLight(new PointLight(new RGBColor(1.0,1.0,1.0),4, new Point(7,4,0)));
		addLight(new PointLight(new RGBColor(1.0,1,1), 3, new Point(-7,4,-7)));
		
	}
	
	public void createWorld4(){
		Transformation identity = Transformation.createIdentity();

		Transformation turn =  Transformation.createRotationX(-90);
		Transformation t1 = Transformation.createTranslation(4, 0, 0); 
		Transformation turn5 =  Transformation.createRotationZ(-70);
		Matte matte = new Matte(new ConstantColor(new RGBColor(1.0,0.0,0.0)), 0.5, 0.2);
		Phong matte3 = new Phong(new ConstantColor(new RGBColor(0.0,1.0,1.0)), 0.5,0.2,0.3, 10);
		addObject(new Hourglass(identity, matte, Math.PI/6, 3));
		//addObject(new Cube(identity, matte3, 0.2));
		addObject(new Cube(t1, matte3, 0.2));
		
		
		addLight(new PointLight(new RGBColor(1.0,1.0,1.0),4, new Point(7,4,0)));
		addLight(new PointLight(new RGBColor(1.0,1.0,1.0), 3, new Point(-7,4,-7)));
		//addObject(new Sphere(identity, matte, 4));
		
		Transformation t = Transformation.createTranslation(-8, -8, -8); 
		Matte matte2 =  new Matte(new ConstantColor(new RGBColor(1.0,1.0,0.0)), 0.5,0.2);
		//Phong matte2 = new Phong(new RGBColor(1,1,0), 0.5,0.2,0.3, 10);
		addObject(new Plane(turn, matte2));
		this.ambientLight = new Ambient(new RGBColor(1.0,1.0,1.0),0.8);
	}
	
	public void createWorld5(){
		Parser parser = new Parser("sphere");
		Transformation t6 = Transformation.createTranslation(0, 2, 1);
		Transformation turn =  Transformation.createRotationX(90);
		Transformation identity =  Transformation.createIdentity();
		//Phong material = new Phong(new ConstantColor(new RGBColor(1,0,0)), 0.8, 0.2 , 0.4, 50);
		Matte material = new Matte(new ConstantColor(new RGBColor(1.0,0,0)), 0.8, 0.2);
		try {
			parser.processLineByLine();
			parser.triangleMesh.setTransformation(identity);
			parser.triangleMesh.setMaterial(material);
			addObject(parser.triangleMesh);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.ambientLight = new Ambient(new RGBColor(1,1,1),1);
		addLight(new PointLight(new RGBColor(1,1,1), 1.2, new Point(0,3,3)));
		addLight(new PointLight(new RGBColor(1,1,1), 1.2, new Point(-2,4,-3)));
		
		Transformation turn2 =  Transformation.createRotationX(-90);
		Transformation t = Transformation.createTranslation(-8, -8, -8); 
		Matte matte2 =  new Matte(new ConstantColor(new RGBColor(0,1,1)), 0.5,0.2);
		//Phong matte2 = new Phong(new RGBColor(0,1,1), 0.5, 0.2, 0.1, 50);
		//addObject(new Plane(turn2.append(t), matte2));
		
	}
	
	public void createWorld6(){
		Transformation identity = Transformation.createIdentity();
		Transformation t1 = Transformation.createTranslation(4, 7, -7); 
		Matte matte = new Matte(new ConstantColor(new RGBColor(1,0,0)), 0.8, 0.2);
		addObject(new Sphere(identity, matte,1));
		addLight(new PointLight(new RGBColor(1,1,1), 2, new Point(0,7,0)));
		addLight(new PointLight(new RGBColor(1,1,1), 3, new Point(-4,0,0)));
		
		Transformation turn =  Transformation.createRotationX(-90);
		Transformation t = Transformation.createTranslation(-8, -8, -8); 
		//Matte matte2 =  new Matte(new RGBColor(0,1,1), 0.5,0.2);
		Phong matte2 =  new Phong(new ConstantColor(new RGBColor(1,1,1)), 0.5,0.2, 0.2,10);
		addObject(new Plane(turn.append(t), matte2));
		
		this.ambientLight = new Ambient(new RGBColor(1,1,1),0.5);
	}
	
	public void createWorld7() throws IOException{
		Parser parser = new Parser("house.obj");
		//Transformation identity =  Transformation.createIdentity();
		//Transformation identity =  Transformation.createTranslation(1, 1, 1);
		Transformation identity =  Transformation.createRotationY(30);
		System.out.println("angle " + (double)Math.PI*1/3);
		//Material material = new Phong(new ImageTexture("house_texture.jpg"),0.8,0.3,0.2,20);
		Material material = new Matte(new ImageTexture("house_texture.jpg"),0.8,0.2);
		//Material material = new Phong(new ConstantColor(new RGBColor(1,0,0)),0.8,0.3,0.2,20);
		try {
			parser.processLineByLine();
			parser.triangleMesh.setTransformation(identity);
			parser.triangleMesh.setMaterial(material);
			addObject(parser.triangleMesh);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.ambientLight = new Ambient(new RGBColor(1,1,1),3);
		addLight(new PointLight(new RGBColor(1.0,1.0,1.0),2, new Point(7,4,0)));
		addLight(new PointLight(new RGBColor(1.0,1,1), 3, new Point(-7,4,-7)));
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
		LightMaterial mat = new LightMaterial(new ConstantColor(light.color));
		Point point = ((PointLight)light).location;
		Transformation transf = Transformation.createTranslation(point.x, point.y, point.z);
		this.addObject(new Sphere(transf, mat, 0.1));
	}
}
