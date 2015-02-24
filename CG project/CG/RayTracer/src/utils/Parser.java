package utils;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import materials.Matte;
import math.Point;
import math.Transformation;
import math.Vector;
import shape.TriangleM;
import shape.TriangleMesh;


public class Parser {

	public TriangleMesh triangleMesh = new TriangleMesh(Transformation.createIdentity(), new Matte(new RGBColor(255,255,255)));
	
	ArrayList<Point> vertices = new ArrayList<Point>();
	ArrayList<Vector> normals = new ArrayList<Vector>();


	public final void processLineByLine() throws IOException  {
		Path path = Paths.get("Mesh");
		//System.out.println(path.toString());
		try (Scanner scanner =  new Scanner(path)){
			while (scanner.hasNextLine()){
				processLine(scanner.nextLine());
			}      
		}
	}

	public void processLine(String line){
		Scanner scanner = new Scanner(line);
	

		String item = scanner.next();
		switch(item){
		case "v" : {
			readVertices(scanner); 
			break;
		}
		case "vn": {
			readNormals(scanner); 
			break;
		}
		case "f": {
			readTriangle(scanner);
			break;
		}
		case "#" : break;
		case "" : break;
		case "vt": break;
		default: {throw new IllegalArgumentException("The Wavefront File is incorrect. This is causing the error: " + item);}
		}
	}

	private void readTriangle(Scanner scanner) {
		String[] a;
		String[] b;
		String[] c;
		if(scanner.hasNext()){ a = scanner.next().split("/");}
		else throw new IllegalArgumentException("The Wavefront File is incorrect. ");
		if(scanner.hasNext()){b = scanner.next().split("/");}
		else throw new IllegalArgumentException("The Wavefront File is incorrect. ");
		if(scanner.hasNext()){c = scanner.next().split("/");}
		else throw new IllegalArgumentException("The Wavefront File is incorrect. ");
		TriangleM triangle = new TriangleM(vertices.get(Integer.parseInt(a[0])-1), vertices.get(Integer.parseInt(b[0])-1), vertices.get(Integer.parseInt(c[0])-1), normals.get(Integer.parseInt(a[2])-1), normals.get(Integer.parseInt(b[2])-1), normals.get(Integer.parseInt(c[2])-1));
		//System.out.println("TriangleM to add is " + vertices.get(Integer.parseInt(a[0])-1) + ", " +  vertices.get(Integer.parseInt(b[0])-1) + ",  " + vertices.get(Integer.parseInt(c[0])-1));
		triangleMesh.triangles.add(triangle);
		//System.out.println("triangle = " + triangle);
	}

	
	private void readNormals(Scanner scanner) {
		double x; double y; double z;
		if(scanner.hasNext()){x = Double.parseDouble(scanner.next());}
		else throw new IllegalArgumentException("The Wavefront File is incorrect.");
		if(scanner.hasNext()){y = Double.parseDouble(scanner.next());}
		else throw new IllegalArgumentException("The Wavefront File is incorrect.");
		if(scanner.hasNext()){z = Double.parseDouble(scanner.next());}
		else throw new IllegalArgumentException("The Wavefront File is incorrect.");
		Vector normal = new Vector(x,y,z);
		normals.add(normal);
	}

	
	private void readVertices(Scanner scanner) {
		double x; double y; double z;
		if(scanner.hasNext()){x = Double.parseDouble(scanner.next());}
		else throw new IllegalArgumentException("The Wavefront File is incorrect. ");
		if(scanner.hasNext()){y = Double.parseDouble(scanner.next());}
		else throw new IllegalArgumentException("The Wavefront File is incorrect. ");
		if(scanner.hasNext()){z = Double.parseDouble(scanner.next());}
		else throw new IllegalArgumentException("The Wavefront File is incorrect. ");
		Point vertex = new Point(x,y,z);
		vertices.add(vertex);
	}

}
