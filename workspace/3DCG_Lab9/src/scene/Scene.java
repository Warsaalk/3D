package scene;

import geomobj.GeomObj;
import java.util.ArrayList;

import light.Light;
import util.Colour;

public class Scene {
	
	private ArrayList<GeomObj> objects;	
	private ArrayList<Light> lights;
	private Colour background;
	
	public Scene(){
		objects = new ArrayList<GeomObj>();
		background = new Colour(0,  0,  0);
		lights = new ArrayList<Light>();
	}

	public void setObjects(ArrayList<GeomObj> objects) {
		this.objects = objects;
	}
	
	public ArrayList<GeomObj> getObjects(){
		return objects;
	}
	
	public void setLights(ArrayList<Light> lights){
		this.lights = lights;
	}
	
	public ArrayList<Light> getLights(){
		return this.lights;
	}

	public void setBackground(float r, float g, float b) {
		background.set(r, g, b);
	}
	
	public Colour getBackGround(){
		return background;
	}
}
