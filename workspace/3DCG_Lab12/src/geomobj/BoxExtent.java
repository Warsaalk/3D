package geomobj;

import renderer.Ray;
import util.Point;

public class BoxExtent {
	public float left;
	public float top;
	public float right;
	public float bottom;
	public float front;
	public float back;
	
	public BoxExtent(float left, float top, float right, float bottom,
			float front, float back) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.front = front;
		this.back = back;
	}
	
	public boolean hit(Ray ray){
		float denomz = ray.dir.z;
		if( !(Math.abs(denomz) < 0.0000001) ){
			// FRONT FACE
			float numerf = front - ray.start.z;
			float tHitf = numerf / denomz ; // t- value of hitPoint between ray and front face plane
			if( tHitf > 0 ){			
				Point hitPoint = ray.getPoint(tHitf);
				if( hitPoint.x <= right && hitPoint.x >= left &&
						hitPoint.y <= top && hitPoint.y >= bottom )
					return true;
			}
		}
		float denomy = ray.dir.y;
		if( !(Math.abs(denomy) < 0.0000001) ){
			// TOP FACE
			float numert = top - ray.start.y;
			float tHitt = numert / denomy ; // t- value of hitPoint between ray and front face plane
			if( tHitt > 0 ){			
				Point hitPoint = ray.getPoint(tHitt);
				if( hitPoint.x <= right && hitPoint.x >= left &&
						hitPoint.z <= front && hitPoint.z >= back )
					return true;
			}
			// BOTTOM FACE
			float numerb = bottom - ray.start.y;
			float tHitb = numerb / denomy ; // t- value of hitPoint between ray and front face plane
			if( tHitb > 0 ){			
				Point hitPoint = ray.getPoint(tHitb);
				if( hitPoint.x <= right && hitPoint.x >= left &&
						hitPoint.z <= front && hitPoint.z >= back )
					return true;
			}
		}
		float denomx = ray.dir.x;
		if( !(Math.abs(denomx) < 0.0000001) ){
			// LEFT FACE
			float numerl = left - ray.start.x;
			float tHitl = numerl / denomx ; // t- value of hitPoint between ray and front face plane
			if( tHitl > 0 ){			
				Point hitPoint = ray.getPoint(tHitl);
				if( hitPoint.y <= top && hitPoint.y >= bottom &&
						hitPoint.z <= front && hitPoint.z >= back )
					return true;
			}
			// RIGHT FACE
			float numerr = right - ray.start.x;
			float tHitr = numerr / denomx ; // t- value of hitPoint between ray and front face plane
			if( tHitr > 0 ){			
				Point hitPoint = ray.getPoint(tHitr);
				if( hitPoint.y <= top && hitPoint.y >= bottom &&
						hitPoint.z <= front && hitPoint.z >= back )
					return true;
			}
		}
		if( !(Math.abs(denomz) < 0.0000001) ){
			// BACK FACE
			float numerba = back - ray.start.z;
			float tHitba = numerba / denomz ; // t- value of hitPoint between ray and front face plane
			if( tHitba > 0 ){			
				Point hitPoint = ray.getPoint(tHitba);
				if( hitPoint.x <= right && hitPoint.x >= left &&
						hitPoint.y <= top && hitPoint.y >= bottom )
					return true;
			}
		}
		return false;
	}
}
