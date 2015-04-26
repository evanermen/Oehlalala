package world;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lights.Ambient;
import lights.AreaLight;
import lights.Light;
import lights.PointLight;
import materials.Emissive;
import materials.Material;
import materials.Matte;
import materials.Phong;
import math.Point;
import math.Transformation;
import math.Vector;
import shape.Cube;
import shape.Hourglass;
import shape.Plane;
import shape.Rectangle;
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
	public Ambient ambientLight = new Ambient(new Emissive(0, new RGBColor(0,0,0)));
	
	public World(){
		bg = new RGBColor();
		ambientLight = new Ambient();
		objects = new ArrayList<Shape>();
		lights = new ArrayList<Light>();	
	}
	
	
	// Cube + Triangle + Hourglass + sphere hier zijn geen lichten of andere kleuren alles is gewoon groen en lelijk
	//PLANE KAPOT
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
		//addObject(new Plane(identity, matte));
	}
	
	//spheremesh met 1 pointlight
	public void createWorld2(){
		Parser parser = new Parser("sphere");
		Transformation t6 = Transformation.createTranslation(1, 1, 0);
		Transformation turn =  Transformation.createRotationX(70);
		Transformation identity =  Transformation.createIdentity();
		//Material mat = new Matte(new ConstantColor(new RGBColor(1,1,0)), 0.2,0.2);
		Texture tex = new ConstantColor(new RGBColor(0,0.8,1));
		Phong mat = new Phong(tex, 0.6, 0.2, 0.5, 20 );
		try {
			parser.processLineByLine();
			parser.triangleMesh.setTransformation(identity);
			parser.triangleMesh.setMaterial(mat);
			addObject(parser.triangleMesh);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//Matte mat = new Matte(tex);
		//Phong mat = new Phong(tex, 0.6, 0.2, 0.5, 20 );
		Rectangle rectangle = new Rectangle(t6,mat, new Point(0,0,0), new Vector(0,1,0), new Vector(1,0,0));
		//Cube rectangle = new Cube(t6, matte, 2);
		//addObject(rectangle);
		
		Emissive emissive1 = new Emissive(1, new RGBColor(0.0,1.0,1.0));
		addLight(new PointLight(emissive1, new Point(2,2,2)));
		Emissive emissive2 = new Emissive(1, new RGBColor(1.0,1.0,1.0));
		this.ambientLight = new Ambient(emissive2);

		//addLight(new PointLight(new RGBColor(1,1,1), 1, new Point(-7,4,-7)));
		
	}
	
	//teapot met 2 pointlights
	public void createWorld3(){
		Parser parser = new Parser("teapot");
		Transformation turn =  Transformation.createIdentity();
		//Matte material = new Matte(new ConstantColor(new RGBColor(1,0,0)), 0.8, 0.2);
		Phong material = new Phong(new ConstantColor(new RGBColor(1,0,0)), 0.8, 0.2, 0.5, 50);
		try {
			parser.processLineByLine();
			parser.triangleMesh.setTransformation(turn);
			parser.triangleMesh.setMaterial(material);
			addObject(parser.triangleMesh);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		Emissive emissive1 = new Emissive(2, new RGBColor(1.0,1.0,1.0));
		addLight(new PointLight(emissive1, new Point(7,4,0)));
		Emissive emissive2 = new Emissive(3, new RGBColor(1.0,1.0,1.0));
		addLight(new PointLight(emissive2, new Point(-7,4,-7)));
		
		Emissive emissive = new Emissive(1, new RGBColor(1.0,1.0,1.0));
		this.ambientLight = new Ambient(emissive);

		
	}
	
	//rood hourglass op gele plane
	public void createWorld4(){
		Transformation identity = Transformation.createIdentity();

		Transformation turn =  Transformation.createRotationX(-90);
		Transformation t1 = Transformation.createTranslation(0.1,0.1, 0); 
		Transformation scale = Transformation.createScale(2, 2, 1);
		Matte matte = new Matte(new ConstantColor(new RGBColor(1.0,0.0,0.0)), 0.5, 0.2);
		Phong matte3 = new Phong(new ConstantColor(new RGBColor(0.0,1.0,1.0)), 0.5,0.2,0.3, 10);
		addObject(new Hourglass(scale.append(scale), matte, Math.PI/3, 1));
		//addObject(new Cube(identity, matte3, 0.2));
		//addObject(new Sphere(scale, matte3, 0.2));
		
		Emissive emissive = new Emissive(2, new RGBColor(1.0,1.0,1.0));
		addLight(new PointLight(emissive, new Point(3,1,0)));
		Emissive emissive2 = new Emissive(2, new RGBColor(1.0,1.0,1.0));
		addLight(new PointLight(emissive, new Point(-7,4,-7)));
		//addObject(new Sphere(identity, matte, 4));
		
		Transformation t = Transformation.createTranslation(-8, -8, -8); 
		//Matte matte2 =  new Matte(new ConstantColor(new RGBColor(1.0,1.0,0.0)), 0.5,0.2);
		Phong matte2 = new Phong(new ConstantColor(new RGBColor(1,1,0)), 0.2,0.2,0.3, 10);
		addObject(new Plane(turn, matte2));
		
		Emissive emissive3 = new Emissive(2, new RGBColor(1.0,1.0,1.0));
		this.ambientLight = new Ambient(emissive3);
	}
	
	//weer teapot met 2  pointlights
	public void createWorld5a() throws IOException{
		Parser parser = new Parser("teapot");
		Transformation turn =  Transformation.createRotationX(90);

		Transformation identity =  Transformation.createIdentity();
		
		Matte material1 = new Matte(new ConstantColor(new RGBColor(0.5,0.5,0)), 0.8, 0.2);
		try {
			parser.processLineByLine();
			parser.triangleMesh.setTransformation(turn);
			parser.triangleMesh.setMaterial(material1);
			addObject(parser.triangleMesh);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Emissive emissive = new Emissive(1.2, new RGBColor(1.0,1.0,1.0));
		this.ambientLight = new Ambient(emissive);
		Emissive emissive1 = new Emissive(1.2, new RGBColor(1.0,1.0,1.0));
		addLight(new PointLight(emissive1, new Point(0,3,3)));
		addLight(new PointLight(emissive1, new Point(-2,4,-3)));
		//addLight(new PointLight(new RGBColor(1,1,1), 1.2, new Point(0,1.2,1.2)));
		//addLight(new PointLight(new RGBColor(1,1,1), 1.2, new Point(1.2,0,-1.2)));
				
	}
	
	//dubbele teapot met texture en met 2 pointlights
	public void createWorld5() throws IOException{
		Parser parser = new Parser("teapot");
		Transformation t6 = Transformation.createTranslation(0, 1, 0.4);
		Transformation turn =  Transformation.createRotationX(10);
		Transformation turn3 =  Transformation.createRotationY(-90);
		Transformation identity =  Transformation.createIdentity();
		//Phong material = new Phong(new ConstantColor(new RGBColor(1,0,0)), 0.8, 0.2 , 0.4, 50);
		Matte material = new Matte(new ConstantColor(new RGBColor(1.0,0,0)), 0.8, 0.2);
		
		Matte material1 = new Matte(new ImageTexture("theepot_texture2.jpg"), 0.8, 0.2);
		Matte material3 = new Matte(new ImageTexture("theepot_texture3.jpg"), 0.8, 0.2);
		try {
			parser.processLineByLine();
			parser.triangleMesh.setTransformation(turn);
			parser.triangleMesh.setMaterial(material1);
			addObject(parser.triangleMesh);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Parser parser2 = new Parser("teapot");
		//Matte material1 = new Matte(new ImageTexture("theepot_texture2.jpg"), 0.8, 0.2);
		
		try {
			parser2.processLineByLine();
			parser2.triangleMesh.setTransformation(turn3.append(t6));
			parser2.triangleMesh.setMaterial(material1);
			addObject(parser2.triangleMesh);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		Emissive emissive = new Emissive(1.2, new RGBColor(1.0,1.0,1.0));
		this.ambientLight = new Ambient(emissive);
		addLight(new PointLight(emissive, new Point(0,3,3)));
		addLight(new PointLight(emissive, new Point(-2,4,-3)));
		//addLight(new PointLight(new RGBColor(1,1,1), 1.2, new Point(0,1.2,1.2)));
		//addLight(new PointLight(new RGBColor(1,1,1), 1.2, new Point(1.2,0,-1.2)));
		
		Transformation turn2 =  Transformation.createRotationX(-90);
		Transformation t = Transformation.createTranslation(-8, -8, -8); 
		Matte matte2 =  new Matte(new ConstantColor(new RGBColor(0,1,1)), 0.5,0.2);
		//Phong matte2 = new Phong(new RGBColor(0,1,1), 0.5, 0.2, 0.1, 50);
		//addObject(new Plane(turn2.append(t), matte2));
		
	}
	
	//witte plane rode bol 2 pointlights
	public void createWorld6(){
		Transformation identity = Transformation.createIdentity();
		Transformation t1 = Transformation.createTranslation(4, 7, -7); 
		Matte matte = new Matte(new ConstantColor(new RGBColor(1,0,0)), 0.8, 0.2);
		addObject(new Sphere(identity, matte,1));
		Emissive emissive1 = new Emissive(4, new RGBColor(1.0,1.0,1.0));
		addLight(new PointLight(emissive1, new Point(-4,4,0)));
		Emissive emissive = new Emissive(2, new RGBColor(1.0,1.0,1.0));
		addLight(new PointLight(emissive, new Point(4,4,0)));
		
		Rectangle rect2 = new Rectangle(identity, matte, new Point(-1,3,-1), new Vector(1,1,0), new Vector(0,1,1));
		addObject(rect2);
		
		Transformation turn =  Transformation.createRotationX(-90);
		Transformation t = Transformation.createTranslation(-8, -8, -8); 
		Matte matte2 =  new Matte(new ConstantColor(new RGBColor(1,1,1)), 0.5,0.2);
		//Phong matte2 =  new Phong(new ConstantColor(new RGBColor(1,1,1)), 0.5,0.2, 0.2,10);
		addObject(new Plane(turn.append(t), matte2));
		
		Emissive emissive2 = new Emissive(0.1, new RGBColor(1.0,1.0,1.0));
		this.ambientLight = new Ambient(emissive2);
	}
	
	//drie huisjes
	public void createWorld7() throws IOException{
		
		
		Transformation scale1 =  Transformation.createScale(3,3,3);
		Transformation t1 =  Transformation.createTranslation(1, 1, 1);
		Transformation rotation1 =  Transformation.createRotationY(30);
		Transformation scale2 =  Transformation.createScale(3,3,3);
		Transformation turn2 =  Transformation.createTranslation(1, 1, 1);
		Transformation rotation2 =  Transformation.createRotationX(40); 
		Transformation rotation3 =  Transformation.createRotationZ(40); 

		Transformation identity = Transformation.createIdentity();
		Transformation t2 =  Transformation.createTranslation(-2, 1, 0);
		Transformation t3 =  Transformation.createTranslation(1, 1, -1);
		Transformation t4 =  Transformation.createTranslation(-1, 2, 1);
		
		
		Material material = new Matte(new ImageTexture("house_texture.jpg"),0.8,0.2);
		
		Parser parser = new Parser("house.obj");	
		try {
			parser.processLineByLine();
			parser.triangleMesh.setTransformation(identity);
			parser.triangleMesh.setMaterial(material);
			addObject(parser.triangleMesh);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Parser parser2 = new Parser("house.obj");	
		try {
			parser2.processLineByLine();
			parser2.triangleMesh.setTransformation(rotation2.append(t2));
			parser2.triangleMesh.setMaterial(material);
			addObject(parser2.triangleMesh);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Parser parser3 = new Parser("house.obj");	
		try {
			parser3.processLineByLine();
			parser3.triangleMesh.setTransformation(rotation3.append(t3));
			parser3.triangleMesh.setMaterial(material);
			addObject(parser3.triangleMesh);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		/**Parser parser4 = new Parser("apple.obj");	
		try {
			parser4.processLineByLine();
			parser4.triangleMesh.setTransformation(rotation1.append(t4));
			parser4.triangleMesh.setMaterial(material);
			addObject(parser4.triangleMesh);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		*/
		
		
		Emissive emissive1 = new Emissive(1, new RGBColor(1.0,1.0,1.0));
		this.ambientLight = new Ambient(emissive1);
		Emissive emissive2 = new Emissive(2, new RGBColor(1.0,1.0,1.0));
		addLight(new PointLight(emissive2, new Point(7,4,0)));		
		Emissive emissive3 = new Emissive(3, new RGBColor(1.0,1.0,1.0));
		addLight(new PointLight(emissive3, new Point(-7,4,-7)));
	}
	
	//appeltje
	public void createWorld8() throws IOException{
		Parser parser = new Parser("apple.obj");
		Transformation identity =  Transformation.createIdentity();
		//Transformation identity =  Transformation.createTranslation(1, 1, 1);
		//Transformation identity =  Transformation.createRotationY(30);
		//System.out.println("angle " + (double)Math.PI*1/3);
		//Material material = new Phong(new ImageTexture("house_texture.jpg"),0.8,0.3,0.2,20);
		Material material = new Matte(new ImageTexture("apple_texture.jpg"),0.8,0.2);
		//Material material = new Phong(new ConstantColor(new RGBColor(1,0,0)),0.8,0.3,0.2,20);
		try {
			parser.processLineByLine();
			parser.triangleMesh.setTransformation(identity);
			parser.triangleMesh.setMaterial(material);
			addObject(parser.triangleMesh);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Emissive emissive1 = new Emissive(3, new RGBColor(1.0,1.0,1.0));
		this.ambientLight = new Ambient(emissive1);
		Emissive emissive2 = new Emissive(2, new RGBColor(1.0,1.0,1.0));
		addLight(new PointLight(emissive2, new Point(7,4,0)));		
		Emissive emissive3 = new Emissive(3, new RGBColor(1.0,1.0,1.0));
		addLight(new PointLight(emissive3, new Point(-7,4,-7)));
		
	}
	
	//plane - bol - arealight
	public void createWorld9(){
		Transformation id = Transformation.createIdentity();
		Transformation t6 = Transformation.createTranslation(0,2, 0);
		Material mat = new Matte(new ConstantColor(new RGBColor(0.5,0,1)), 0.8,0.2);
		Sphere sphere = new Sphere(t6, mat, 1);
		addObject(sphere);
		
		Matte matte3 =  new Matte(new ConstantColor(new RGBColor(0,0.7,0)), 0.5,0.2);
		Emissive emissive1 = new Emissive(2, new RGBColor(1.0,1.0,1.0));
		Rectangle rect = new Rectangle(id,emissive1, new Point(-2,6,-2), new Vector(4,0,0), new Vector(0,0,2));
		Rectangle rect2 = new Rectangle(id, matte3, new Point(-1,3,-1), new Vector(1,1,0), new Vector(0,1,1));
		//addObject(rect2);
		addObject(rect);
		AreaLight arealight = new AreaLight(emissive1, rect); 
		addLight(arealight);
		//addLight(new PointLight(emissive1, new Point(0,5,0)));
		Emissive emissive2 = new Emissive(2, new RGBColor(1.0,1.0,1.0));
		this.ambientLight = new Ambient(emissive2);

		Transformation turn2 =  Transformation.createRotationX(-90);
		Matte matte2 =  new Matte(new ConstantColor(new RGBColor(1.0,0.3,0.3)), 0.5,0.2);
		//Phong matte2 = new Phong(new ConstantColor(new RGBColor(1,1,0)), 0.2,0.2,0.3, 10);
		addObject(new Plane(turn2, matte2));
		//addLight(new PointLight(new RGBColor(1,1,1), 1, new Point(-7,4,-7)));
	}
	
	//plane met arealight er loodrecht op
	public void createWorld10(){
		Transformation id = Transformation.createIdentity();
		Transformation t6 = Transformation.createTranslation(0, 2.5, 0);
		Material mat = new Matte(new ConstantColor(new RGBColor(0,0.5,1)), 0.2,0.2);
		Sphere sphere = new Sphere(t6, mat, 0.8);
		//addObject(sphere);
		
		Matte matte3 =  new Matte(new ConstantColor(new RGBColor(0,0.7,0)), 0.5,0.2);
		Emissive emissive1 = new Emissive(3, new RGBColor(1.0,1.0,1.0));
		Rectangle rect = new Rectangle(id,emissive1, new Point(-2,0,0), new Vector(4,0,0), new Vector(0,4,0));
		Rectangle rect2 = new Rectangle(id, matte3, new Point(-1,3,-1), new Vector(1,1,0), new Vector(0,1,1));
		//addObject(rect2);
		addObject(rect);
		AreaLight arealight = new AreaLight(emissive1, rect); //aargh waarom hier ook emissive?????????????? en rect??
		addLight(arealight);
		//addLight(new PointLight(emissive1, new Point(0,5,0)));
		Emissive emissive2 = new Emissive(0.5, new RGBColor(1.0,1.0,1.0));
		this.ambientLight = new Ambient(emissive2);

		Transformation turn2 =  Transformation.createRotationX(-90);
		Matte matte2 =  new Matte(new ConstantColor(new RGBColor(1.0,0.3,0.3)), 0.5,0.2);
		//Phong matte2 = new Phong(new ConstantColor(new RGBColor(1,1,0)), 0.2,0.2,0.3, 10);
		addObject(new Plane(turn2, matte2));
		//addLight(new PointLight(new RGBColor(1,1,1), 1, new Point(-7,4,-7)));
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
		//Emissive mat = new Emissive(new ConstantColor(light.));
		//Point point = ((PointLight)light).location;
		//Transformation transf = Transformation.createTranslation(point.x, point.y, point.z);
		//this.addObject(new Sphere(transf, light.emissive, 0.1));
	}
}
