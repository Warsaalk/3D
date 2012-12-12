package geomobj.bool;

import geomobj.GeomObj;
import renderer.HitInfo;
import renderer.Intersection;
import renderer.Ray;
import util.Vector;

public class DifferenceBoolean extends Boolean {

	public DifferenceBoolean(GeomObj left, GeomObj right) {
		super(left, right);
	}

	@Override
	public Intersection intersection(Ray ray) {
		Intersection combInter = new Intersection();
		Intersection leftInter=this.left.intersection(ray);
		if( leftInter.hits.isEmpty() )
			return combInter;
		Intersection rightInter=this.right.intersection(ray);
		if( rightInter.hits.isEmpty() )
			return leftInter;
		boolean leftInside=false,rightInside=false,combInside=false;
		int currentLeft=0,currentRight=0,maxLeft=leftInter.getNumHits(),maxRight=rightInter.getNumHits();
		while(currentLeft<maxLeft && currentRight<maxRight){
			boolean combInsideNew;
			HitInfo hitLeft = leftInter.getHit(currentLeft);
			HitInfo hitRight = rightInter.getHit(currentRight);
			if( hitLeft.t <= hitRight.t  ){
				leftInside = !leftInside;
				combInsideNew = leftInside && !rightInside;
				if( combInsideNew != combInside ){
					combInside = combInsideNew;
					hitLeft.isEntering = combInside;
					combInter.add(hitLeft);
				}
				currentLeft++;
			}else{
				rightInside = !rightInside;
				combInsideNew = leftInside && !rightInside;
				if( combInsideNew != combInside ){
					combInside = combInsideNew;
					hitRight.hitMaterial = hitLeft.hitMaterial;
					if( combInside!=rightInside )
						hitRight.hitNormal.reverse();
					hitRight.isEntering = combInside;
					combInter.add(hitRight);
				}
				currentRight++;
			}
		}
		while( currentLeft < maxLeft ){
			HitInfo hitLeft = leftInter.getHit(currentLeft);
			//hitLeft.isEntering = !combInside;
			//combInside = !combInside;
			combInter.add(hitLeft);
			currentLeft++;
		}
		return combInter;
	}

	@Override
	public boolean hit(Ray ray) {
		Intersection leftInter=this.left.intersection(ray);
		if( leftInter.hits.isEmpty() )
			return false;
		boolean leftInside=false,rightInside=false;
		Intersection rightInter=this.right.intersection(ray);
		int currentLeft=0,currentRight=0,maxLeft=leftInter.getNumHits(),maxRight=rightInter.getNumHits();
		if( rightInter.hits.isEmpty() ){
			if( leftInter.getBestHitTime() < 1 )
				return true;
		}
		while(currentLeft<maxLeft && currentRight<maxRight){
			HitInfo hitLeft = leftInter.getHit(currentLeft);
			HitInfo hitRight = rightInter.getHit(currentRight);
			if( hitLeft.t <= hitRight.t  ){
				leftInside = !leftInside;
				if( leftInside && !rightInside )
					return true;
				currentLeft++;
			}else{
				rightInside = !rightInside;
				if( leftInside && !rightInside )
					return true;
				currentRight++;
			}
		}
		if( currentLeft < maxLeft && leftInter.getHit(currentLeft).t < 1 ){
			return true;
		}
		return false;
	}

}
