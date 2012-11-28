package renderer;

import util.Matrix;
import util.Point;
import util.Vector;

public class Ray {
	
	public Point start;
	public Vector dir;
	
	public Ray(Point start){
		this.start = new Point(start);
		this.dir = new Vector();
	}
	
	public Ray(Point start, Vector dir){
		this.start = new Point(start);
		this.dir = new Vector(dir);
	}	
	
	public Point getPoint(float t){
		return new Point((start.addP(dir.multV(t))));
	}

	public Ray getInvTransfoRay ( Matrix invMat ){
		float px = ( invMat.m[0][0] * start.x ) +
				( invMat.m[0][1] * start.y ) +
				( invMat.m[0][2] * start.z ) +
				( invMat.m[0][3] * 1 );
		float py = ( invMat.m[1][0] * start.x ) +
				( invMat.m[1][1] * start.y ) +
				( invMat.m[1][2] * start.z ) +
				( invMat.m[1][3] * 1 );
		float pz = ( invMat.m[2][0] * start.x ) +
				( invMat.m[2][1] * start.y ) +
				( invMat.m[2][2] * start.z ) +
				( invMat.m[2][3] * 1 );
		/*float ph = ( invMat.m[3][0] * start.x ) +
				( invMat.m[3][1] * start.y ) +
				( invMat.m[3][2] * start.z ) +
				( invMat.m[3][3] * 1 );*/
		
		float vx = ( invMat.m[0][0] * dir.x ) +
				( invMat.m[0][1] * dir.y ) +
				( invMat.m[0][2] * dir.z ) +
				( invMat.m[0][3] * 0 );
		float vy = ( invMat.m[1][0] * dir.x ) +
				( invMat.m[1][1] * dir.y ) +
				( invMat.m[1][2] * dir.z ) +
				( invMat.m[1][3] * 0 );
		float vz = ( invMat.m[2][0] * dir.x ) +
				( invMat.m[2][1] * dir.y ) +
				( invMat.m[2][2] * dir.z ) +
				( invMat.m[2][3] * 0 );
		/*float vh = ( invMat.m[3][0] * dir.x ) +
				( invMat.m[3][1] * dir.y ) +
				( invMat.m[3][2] * dir.z ) +
				( invMat.m[3][3] * 0 ); */
		
		return new Ray( new Point(px,py,pz), new Vector(vx,vy,vz) );
	}
	
}
