package textures;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import utils.Intersection;
import utils.RGBColor;

public class ImageTexture extends Texture {
	
	int hres;
	int vres;
	Mapping mapping;
	BufferedImage image;
	
	public ImageTexture(String pathString, Mapping mapping) throws IOException{
		Path path = Paths.get(pathString);
		this.image = ImageIO.read(path.toFile());
		this.vres = this.image.getHeight();
		this.hres = this.image.getWidth();
		this.mapping = mapping;		
	}
	
	
	public ImageTexture(String pathString) throws IOException{
		Path path = Paths.get(pathString);
		this.image = ImageIO.read(path.toFile());
		this.vres = this.image.getHeight();
		this.hres = this.image.getWidth();
		this.mapping = new RectangularMap();		
	}
	
	
	@Override
	public RGBColor getColor(Intersection intersection) {
		int row = (this.vres-1)-(int)((intersection.v)*(vres-1));
		int column= (int)((intersection.u)*(hres-1));
		//int color = image.getRGB(row, column);
		//Color c = new Color(color);
		double[] c = new double[3];
		c = image.getRaster().getPixel(column, row, c);
		return new RGBColor(c[0]/255,c[1]/255,c[2]/255);
	}

}
