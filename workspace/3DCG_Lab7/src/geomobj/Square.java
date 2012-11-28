package geomobj;

import renderer.HitInfo;
import renderer.Intersection;
import renderer.Ray;
import util.Matrix;
import util.Point;
import util.Vector;

public class Square extends Shape {
	
	@Override
	public Intersection intersection(Ray ray) {
		Ray invRay = ray.getInvTransfoRay(transfo.invMat);
		Intersection intersection = new Intersection();
		
		if(Math.abs(invRay.dir.z) < 0.00001){
			return intersection;
		}
		float t = -invRay.start.z/invRay.dir.z;
		if(t<=0.0) {
			return intersection;
		}
		Point hitPoint = ray.getPoint(t);
		Matrix tinvMatrix = transfo.invMat.getTranspose();//Transposed inverse matrix
		Vector hitNormal = tinvMatrix.mult(new Vector(0,0,1));
		hitNormal.normalize();
		Point hitPointInv = invRay.getPoint(t);
		if (hitPointInv.x > 1.0 || hitPointInv.x < -1.0 || hitPointInv.y > 1.0 || hitPointInv.y < -1.0){
			return intersection;
		}
		intersection.add(new HitInfo(t, this.mtrl, hitPoint, hitNormal, true));
		return intersection;	
	}

	
}
