package material;

import util.Colour;

public class Material {
	public Colour diffuse;
	public Colour ambient;
	public float reflectivity;

	public Material() {
		this.diffuse = new Colour(1,0,0);
		this.ambient = new Colour((float)0.2,0,0);
		this.reflectivity = 0;
	}
	
	public Material(Material m) {
		this.diffuse = m.diffuse;
		this.ambient = m.ambient;
		this.reflectivity = m.reflectivity;
	}
	
}
