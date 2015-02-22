package utils;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import math.Point;
import math.Vector;
import shape.TriangleM;
import shape.TriangleMesh;


public class Parser {

	TriangleMesh mesh = new TriangleMesh();
	
	ArrayList<Point> vertices = new ArrayList<Point>();
	ArrayList<Vector> normals = new ArrayList<Vector>();


	public final void processLineByLine() throws IOException  {
		Path fFilePath = Paths.get("C:\\Users\\eline vanermen\\Desktop\\master\\CG project\\WavefrontFile.txt");
		System.out.println(fFilePath.toString());
		try (Scanner scanner =  new Scanner(fFilePath)){
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
			double x; double y; double z;
			if(scanner.hasNext()){x = Double.parseDouble(scanner.next());}
			else throw new IllegalArgumentException("The Wavefront File is incorrect. ");
			if(scanner.hasNext()){y = Double.parseDouble(scanner.next());}
			else throw new IllegalArgumentException("The Wavefront File is incorrect. ");
			if(scanner.hasNext()){z = Double.parseDouble(scanner.next());}
			else throw new IllegalArgumentException("The Wavefront File is incorrect. ");
			Point vertex = new Point(x,y,z);
			vertices.add(vertex); 
			break;
		}
		case "vn": {
			double x; double y; double z;
			if(scanner.hasNext()){x = Double.parseDouble(scanner.next());}
			else throw new IllegalArgumentException("The Wavefront File is incorrect.");
			if(scanner.hasNext()){y = Double.parseDouble(scanner.next());}
			else throw new IllegalArgumentException("The Wavefront File is incorrect.");
			if(scanner.hasNext()){z = Double.parseDouble(scanner.next());}
			else throw new IllegalArgumentException("The Wavefront File is incorrect.");
			Vector normal = new Vector(x,y,z);
			normals.add(normal); 
			break;
		}
		case "f": {
			String[] verts;
			String[] texts;
			String[] norms;
			if(scanner.hasNext()){ verts = scanner.next().split("/");}
			else throw new IllegalArgumentException("The Wavefront File is incorrect. ");
			if(scanner.hasNext()){texts = scanner.next().split("/");}
			else throw new IllegalArgumentException("The Wavefront File is incorrect. ");
			if(scanner.hasNext()){norms = scanner.next().split("/");}
			else throw new IllegalArgumentException("The Wavefront File is incorrect. ");
			TriangleM triangle = new TriangleM(vertices.get(Integer.parseInt(verts[0])-1), vertices.get(Integer.parseInt(verts[1])-1), vertices.get(Integer.parseInt(verts[2])-1), normals.get(Integer.parseInt(norms[0])-1), normals.get(Integer.parseInt(norms[1])-1), normals.get(Integer.parseInt(norms[2])-1));
			mesh.mesh.add(triangle);
		}
		case "vt": break;
		default: {throw new IllegalArgumentException("The Wavefront File is incorrect. This is causing the error: " + item);}
		}
	}

}
