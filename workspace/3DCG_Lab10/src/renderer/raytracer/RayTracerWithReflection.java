package renderer.raytracer;

import geomobj.GeomObj;

import java.util.Properties;

import light.Light;

import renderer.Intersection;
import renderer.Ray;
import scene.Scene;
import util.Colour;
import util.Point;
import util.Vector;

public class RayTracerWithReflection extends RayTracer {

	private int maxRecursionDepth;
	
	public RayTracerWithReflection(Scene scene, Properties prop) {
		super(scene);
		maxRecursionDepth = Integer.parseInt(prop.getProperty("raytrace.reflection.maxRecursionDepth"));
	}

	@Override
	protected Colour shadeHit(Ray ray, Intersection inter) {
		Colour colourD = inter.getBestHitMaterial().diffuse;
		Colour colourA = inter.getBestHitMaterial().ambient;	
		Colour col = new Colour(0,0,0);
		Point start = inter.getBestHitPoint();
		//Vector temp = new Vector( inter.getBestHitNormal() );
		Vector temp = new Vector( ray.dir );
		temp.mult(-0.01f);
		start.add( temp );
		Ray shadowfeeler = new Ray(start);
		
		for(Light light : scene.getLights()){
			col.add( colourA.mult( light.colour ));
			
			Vector dir = light.pos.subtract(start);
			shadowfeeler.dir = dir;

			if( !isInShadow(shadowfeeler) ){
				Vector norms = light.pos.subtract(inter.getBestHitPoint());
				norms.normalize();
				Vector normm = inter.getBestHitNormal();
				float t = norms.dot(normm);
				if( t>0 ){
					col.add( colourD.mult( light.colour.mult(t) ) );
				}
			}
		}
		if( this.recursionDepth <= this.maxRecursionDepth && inter.getBestHitMaterial().reflectivity >= 0.1 ){
			this.recursionDepth++; 
			Ray reflRay = this.computeReflectedRay(ray, inter);//Maybe change to start later on
			Colour colr = this.shade(reflRay);
			col.add( colr.mult(inter.getBestHitMaterial().reflectivity) );
		}
		
		return col;
	}
	
	private Ray computeReflectedRay(Ray ray, Intersection best){
		Vector m = best.getBestHitNormal();
		Vector d = ray.dir;
		Vector r = d.substract( m.multV(d.dot(m)*2) );
		return new Ray(best.getBestHitPoint(),r);
	}
	
	private boolean isInShadow(Ray ray ){
		for( GeomObj object : scene.getObjects()){
			if( object.hit(ray) ) return true;
		}
		return false;
	}

}
