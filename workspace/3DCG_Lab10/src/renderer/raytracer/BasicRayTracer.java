package renderer.raytracer;

import light.Light;
import renderer.Intersection;
import renderer.Ray;
import scene.Scene;
import util.Colour;
import util.Vector;

public class BasicRayTracer extends RayTracer{

	public BasicRayTracer(Scene scene) {
		super(scene);
	}
	
	protected Colour shadeHit( Ray ray, Intersection inter ){
		Colour colourD = inter.getBestHitMaterial().diffuse;
		Colour colourC = inter.getBestHitMaterial().ambient;
		float R = 0;
		float G = 0;
		float B = 0;
		
		for(Light light : scene.getLights()){
			Vector norms = light.pos.subtract(inter.getBestHitPoint());
			norms.normalize();
			Vector normm = inter.getBestHitNormal();
			float t = norms.dot(normm);
			if( t > 0 ){
				R += ( (colourD.r * light.colour.r * t) + (colourC.r * light.colour.r) );
				G += ( (colourD.g * light.colour.g * t) + (colourC.g * light.colour.g) );
				B += ( (colourD.b * light.colour.b * t) + (colourC.b * light.colour.b) );
			}else{
				R += (colourC.r * light.colour.r);
				G += (colourC.g * light.colour.g);
				B += (colourC.b * light.colour.b);
			}
		}
		
		return new Colour(R,G,B);
	}

}
