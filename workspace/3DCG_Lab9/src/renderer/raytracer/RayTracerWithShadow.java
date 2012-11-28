package renderer.raytracer;

import geomobj.GeomObj;
import light.Light;
import renderer.Intersection;
import renderer.Ray;
import scene.Scene;
import util.Colour;
import util.Point;
import util.Vector;

public class RayTracerWithShadow extends RayTracer {

	public RayTracerWithShadow(Scene scene) {
		super(scene);
	}

	@Override
	protected Colour shadeHit(Ray ray, Intersection inter) {
		Colour colourD = inter.getBestHitMaterial().diffuse;
		Colour colourC = inter.getBestHitMaterial().ambient;
		float R = 0;
		float G = 0;
		float B = 0;
		
		Point start = inter.getBestHitPoint();
		
		Vector temp = new Vector( inter.getBestHitNormal() );
		start.add( temp );
		
		Ray shadowfeeler = new Ray(start);
		
		for(Light light : scene.getLights()){		
			R += (colourC.r * light.colour.r);
			G += (colourC.g * light.colour.g);
			B += (colourC.b * light.colour.b);
			
			Vector dir = light.pos.subtract(start);
			shadowfeeler.dir = dir;

			if( !isInShadow(shadowfeeler) ){
				Vector norms = light.pos.subtract(inter.getBestHitPoint());
				norms.normalize();
				Vector normm = inter.getBestHitNormal();
				float t = norms.dot(normm);
				if( t>0 ){
					R += (colourD.r * light.colour.r * t);
					G += (colourD.g * light.colour.g * t);
					B += (colourD.b * light.colour.b * t);
				}
			}
		}
		
		return new Colour(R,G,B);
	}
	
	private boolean isInShadow(Ray ray ){
		for( GeomObj object : scene.getObjects()){
			if( object.hit(ray) ) return true;
		}
		return false;
	}

}
