package scene;

import geomobj.GeomObj;
import geomobj.Shape;
import geomobj.Sphere;
import geomobj.Square;
import geomobj.mesh.Mesh;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

import util.Colour;

import material.Material;


public class SceneFactory {
	
	public Scene createScene(Properties prop){
		
		Scene scene = new Scene();
		ArrayList<GeomObj> objects = new ArrayList<GeomObj>();
		Material currMtrl = new Material();
		String fileName = prop.getProperty("scene.file");		
		Scanner scanner;
		try {
			scanner = new Scanner(new File(fileName));
			scanner.useLocale(Locale.US);
			
			while(scanner.hasNext()){
				
				String token = scanner.next().toUpperCase();
				
				if(token.equals(Token.BACKGROUND.toString())){
					scene.setBackground(scanner.nextFloat(), scanner.nextFloat(), scanner.nextFloat());
				} else if(!processMaterial (token , scanner , currMtrl)) {	
					if(token.equals(Token.SPHERE.toString())) {
						objects.add(createSphere(token, scanner, currMtrl));
					} else {
						objects.add(createShape(token, scanner, currMtrl));
					}
				} 
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		scene.setObjects(objects);		
		return scene;
	}
	
	private boolean processMaterial (String token , Scanner scanner , Material material){
		if( token.equals(Token.COLOUR.toString()) ){
			int R = scanner.nextInt()*255;
			int G = scanner.nextInt()*255;
			int B = scanner.nextInt()*255;
			material.colour = new Colour(R,G,B);
			return true;
		}
		return false;
	}
		
	private Shape createShape(String token, Scanner scanner, Material material){
		Shape shape = null;
		
		if(token.equals(Token.SQUARE.toString())){
			shape = new Square();
		} else if(token.equals(Token.MESH.toString())) {
			String filename = scanner.next();
			shape = new Mesh(filename);
		} else {
			throw new IllegalStateException("The token " + token + " is not supported by the scene description language!");
		}
		shape.mtrl = new Material(material);
		return shape;
	}
	
	private Sphere createSphere(String token, Scanner scanner, Material material){
		Sphere sphere = null;
		
		if(token.equals(Token.SPHERE.toString())){
			sphere = new Sphere();
			sphere.mtrl = new Material(material);
		} else {
			throw new IllegalStateException("The token " + token + " is not supported by the scene description language!");
		}
		
		return sphere;		
	}

}
