package geomobj;

import renderer.HitInfo;
import renderer.Intersection;
import renderer.Ray;
import util.Vector;

public class Sphere extends Shape {
	
	@Override
	public Intersection intersection(Ray ray) {
		Intersection intersection = new Intersection();
		
		float a = ray.dir.dot(ray.dir);
		float b = ray.dir.dot(new Vector(ray.start)) * 2;
		float c = new Vector(ray.start).dot(new Vector(ray.start)) - 1;
		float D = (float) (Math.pow(b, 2) - 4 * a * c);
		if( D < 0 )
			return intersection;
		
		float t1 = (float) (-(b - Math.sqrt(D)) / (2 * a));
		float t2 = (float) (-(b + Math.sqrt(D)) / (2 * a));
		
		if( t1 >= 0 && t2 >= 0 ){
			if( t1 <= t2 ){
				intersection.add( new HitInfo(t1, this, ray.getPoint(t1), new Vector(0,0,1), true) );
				intersection.add( new HitInfo(t2, this, ray.getPoint(t2), new Vector(0,0,1), false) );
			}else{
				intersection.add( new HitInfo(t2, this, ray.getPoint(t2), new Vector(0,0,1), true) );
				intersection.add( new HitInfo(t1, this, ray.getPoint(t1), new Vector(0,0,1), false) );
			}
		}else if( t1 < 0 && t2 >= 0 )
			intersection.add( new HitInfo(t2, this, ray.getPoint(t2), new Vector(0,0,1), false) );
		else if( t1 >= 0 && t2 < 0 )
			intersection.add( new HitInfo(t1, this, ray.getPoint(t1), new Vector(0,0,1), false) );
			
		return intersection;	
	}
	
}
