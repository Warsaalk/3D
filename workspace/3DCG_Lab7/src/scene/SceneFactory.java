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

import transfo.RotateTransfo;
import transfo.ScaleTransfo;
import transfo.Transfo;
import transfo.TranslateTransfo;
import util.Colour;
import util.Point;

import light.Light;
import material.Material;


public class SceneFactory {
	
	public Scene createScene(Properties prop){
		
		Scene scene = new Scene();
		ArrayList<GeomObj> objects = new ArrayList<GeomObj>();
		ArrayList<Light> lights = new ArrayList<Light>();
		Material currMtrl = new Material();
		Transfo currTransfo = new Transfo();
		String fileName = prop.getProperty("scene.file");		
		Scanner scanner;
		try {
			scanner = new Scanner(new File(fileName));
			scanner.useLocale(Locale.US);
			
			while(scanner.hasNext()){
				
				String token = scanner.next().toUpperCase();
				
				if(token.equals(Token.BACKGROUND.toString())){
					scene.setBackground(scanner.nextFloat(), scanner.nextFloat(), scanner.nextFloat());
				} else if(token.equals(Token.LIGHT.toString())){
					float posx = scanner.nextFloat();
					float posy = scanner.nextFloat();
					float posz = scanner.nextFloat();
					float R = scanner.nextFloat();
					float G = scanner.nextFloat();
					float B = scanner.nextFloat();
					lights.add(new Light(new Point(posx,posy,posz),new Colour(R,G,B)));
				} else if(!processMaterial (token , scanner , currMtrl)) {
					if(!processTransfo(token,scanner,currTransfo)){
						if(token.equals(Token.SPHERE.toString())) {
							objects.add(createSphere(token, scanner, currMtrl, currTransfo));
						} else {
							objects.add(createShape(token, scanner, currMtrl, currTransfo));
						}
					}
				} 
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		scene.setObjects(objects);
		scene.setLights(lights);
		return scene;
	}
	
	private boolean processTransfo(String token, Scanner scanner, Transfo currTransfo) {
		if( token.equals(Token.SCALE.toString()) ){
			float x = scanner.nextFloat();
			float y = scanner.nextFloat();
			float z = scanner.nextFloat();
			Transfo temp = new ScaleTransfo(x,y,z);
			currTransfo.mat = temp.mat;
			currTransfo.invMat = temp.invMat;
			return true;
		}else if( token.equals(Token.TRANSLATE.toString()) ){
			float x = scanner.nextFloat();
			float y = scanner.nextFloat();
			float z = scanner.nextFloat();
			Transfo temp = new TranslateTransfo(x,y,z);
			currTransfo.mat = temp.mat;
			currTransfo.invMat = temp.invMat;
			return true;
		}else if( token.equals(Token.ROTATE.toString()) ){
			double a = scanner.nextDouble();
			float x = scanner.nextFloat();
			float y = scanner.nextFloat();
			float z = scanner.nextFloat();
			Transfo temp = new RotateTransfo(a,x,y,z);
			currTransfo.mat = temp.mat;
			currTransfo.invMat = temp.invMat;
			return true;
		}
		return false;
	}

	private boolean processMaterial (String token , Scanner scanner , Material material){
		if( token.equals(Token.DIFFUSE.toString()) ){
			float R = scanner.nextFloat();
			float G = scanner.nextFloat();
			float B = scanner.nextFloat();
			material.diffuse = new Colour(R,G,B);
			return true;
		}
		if( token.equals(Token.AMBIENT.toString())){
			float R = scanner.nextFloat();
			float G = scanner.nextFloat();
			float B = scanner.nextFloat();
			material.ambient = new Colour(R,G,B);
			return true;		
		}
		return false;
	}
		
	private Shape createShape(String token, Scanner scanner, Material material, Transfo transfo){
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
		shape.transfo = new Transfo(transfo);
		return shape;
	}
	
	private Sphere createSphere(String token, Scanner scanner, Material material, Transfo transfo){
		Sphere sphere = null;
		
		if(token.equals(Token.SPHERE.toString())){
			sphere = new Sphere();
			sphere.mtrl = new Material(material);
			sphere.transfo = new Transfo(transfo);
		} else {
			throw new IllegalStateException("The token " + token + " is not supported by the scene description language!");
		}
		
		return sphere;		
	}

}
