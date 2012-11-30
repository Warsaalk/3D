package geomobj;

import renderer.HitInfo;
import renderer.Intersection;
import renderer.Ray;
import util.Matrix;
import util.Point;
import util.Vector;

public class Sphere extends Shape {
	
	@Override
	public Intersection intersection(Ray ray) {
		Ray invRay = ray.getInvTransfoRay(transfo.invMat);
		Intersection intersection = new Intersection();
		
		float a = invRay.dir.dot(invRay.dir);
		float b = invRay.dir.dot(new Vector(invRay.start)) * 2;
		float c = new Vector(invRay.start).dot(new Vector(invRay.start)) - 1;
		float D = (float) (Math.pow(b, 2) - 4 * a * c);
		float t1 = 0;
		float t2 = 0;
		if( D < 0 )
			return intersection;
		else if( D > 0 ){
			t1 = (float) (-(b - Math.sqrt(D)) / (2 * a));
			t2 = (float) (-(b + Math.sqrt(D)) / (2 * a));
			
			if(t1<0 || t1>t2){//When you're inside the object
				float tt = t1;
				t1=t2;
				t2=tt;
			}
		}else
			t1 = -b / (2*a);
		
		Matrix tinvMatrix = transfo.invMat.getTranspose();//Transposed inverse matrix
		if( t1 > 0 ){
			Point one = ray.getPoint(t1);
			Point oneInv = invRay.getPoint(t1);
			Vector vone = tinvMatrix.mult(new Vector(oneInv));
			vone.normalize();
			intersection.add( new HitInfo(t1, this.mtrl, one, vone, true) );
		}
		if( t2 > 0 ){
			Point one = ray.getPoint(t2);
			Point oneInv = invRay.getPoint(t2);
			Vector vone = tinvMatrix.mult(new Vector(oneInv));
			vone.normalize();
			intersection.add( new HitInfo(t2, this.mtrl, one, vone, true) );
		}
		return intersection;	
	}
	
	@Override
	public boolean hit(Ray ray) {
		Ray invRay = ray.getInvTransfoRay(transfo.invMat);
		
		float a = invRay.dir.dot(invRay.dir);
		float b = invRay.dir.dot(new Vector(invRay.start)) * 2;
		float c = new Vector(invRay.start).dot(new Vector(invRay.start)) - 1;
		float D = (float) (Math.pow(b, 2) - 4 * a * c);
		float t = 0;
		if( D < 0 )
			return false;
		else if( D > 0 ){
			float t1 = (float) (-(b - Math.sqrt(D)) / (2 * a));
			float t2 = (float) (-(b + Math.sqrt(D)) / (2 * a));
			
			t = ( t1>0 && t1<t2?t1:t2 );
		}else
			t = -b / (2*a);
		
		if( t > 0 && t < 1 ){
			return true;
		}
		return false;	
	}
	
}
