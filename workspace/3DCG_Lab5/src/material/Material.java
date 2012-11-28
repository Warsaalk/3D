package material;

import util.Colour;

public class Material {
	public Colour colour;

	public Material() {
		this.colour = new Colour(255,0,0);
	}
	
	public Material(Material m) {
		this.colour = m.colour;
	}
	
}
