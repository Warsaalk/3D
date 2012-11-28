package renderer.raytracer;

import light.Light;
import geomobj.GeomObj;
import renderer.Intersection;
import renderer.Ray;
import scene.Scene;
import util.Colour;
import util.Vector;

public class RayTracer {
	
	private Scene scene;
	
	public RayTracer(Scene scene){
		this.scene = scene;
	}
	
	public Colour shade(Ray ray){		
		Intersection inter = this.getBestIntersection(ray);
		return ( inter.hits.isEmpty() ? scene.getBackGround() : this.getColour(inter));
	}
	
	private Colour getColour( Intersection inter ){
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
	
	private Intersection getBestIntersection(Ray ray) {		
		Intersection best = null;
		for( GeomObj object : scene.getObjects() ){
			if( object.intersection(ray).getNumHits() > 0 )
				if( best==null || object.intersection(ray).getBestHitTime() < best.getBestHitTime() )
					best = object.intersection(ray);
		}
		return ( best==null ? new Intersection() : best );
	}

}
