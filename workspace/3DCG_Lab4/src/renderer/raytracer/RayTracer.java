package renderer.raytracer;

import geomobj.GeomObj;
import renderer.Intersection;
import renderer.Ray;
import scene.Scene;
import util.Colour;

public class RayTracer {
	
	private Scene scene;
	
	public RayTracer(Scene scene){
		this.scene = scene;
	}
	
	public Colour shade(Ray ray){		
		return ( this.getBestIntersection(ray).hits.isEmpty() ? scene.getBackGround() : new Colour(255,0,0));
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
