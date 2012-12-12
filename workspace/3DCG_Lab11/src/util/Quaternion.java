package util;

public class Quaternion {
	public float a,b,c,d;
	
	public Quaternion(float a, float b, float c, float d){
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	public Quaternion(Point p){
		a = 0;
		b = p.x;
		c = p.y;
		d = p.z;
	}
	
	public Quaternion(double degrees, Vector r){
		double half = Math.toRadians(degrees/2);
		
		a = (float)Math.cos(half);
		b = (float)(Math.sin(half)*r.x);
		c = (float)(Math.sin(half)*r.y);
		d = (float)(Math.sin(half)*r.z);
	}
	
	public Quaternion mult(Quaternion q){
		return new Quaternion(
				a*q.a-b*q.b-c*q.c-d*q.d,
				a*q.b+b*q.a+c*q.d-d*q.c,
				a*q.c-b*q.d+c*q.a+d*q.b,
				a*q.d+b*q.c-c*q.b+d*q.a
		);		
	}
	
	public Quaternion conjugate(Quaternion q){
		return new Quaternion(a,-b,-c,-d);
	}
	
	public Point getPoint(){
		return new Point(b,c,d);
	}
}
