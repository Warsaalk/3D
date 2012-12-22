package geomobj.mesh;

import geomobj.BoxExtent;
import geomobj.Shape;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import renderer.HitInfo;
import renderer.Intersection;
import renderer.Ray;

import util.Matrix;
import util.Point;
import util.Vector;

public class Mesh extends Shape {
	
	private ArrayList<Point> verts = new ArrayList<Point>();	    // vertex list
	private ArrayList<Vector> norms = new ArrayList<Vector>();	// normal list
	private ArrayList<Face> faces = new ArrayList<Face>();	    // face list
	private BoxExtent genBoxExtent;
	
	public Mesh(String fileName){
		readFile(fileName);
	}
	
	private void readFile(String fileName){
		try {
			Scanner sc = new Scanner(new File(fileName));
			sc.useLocale(Locale.US);
			int vertsC = sc.nextInt();
			int normsC = sc.nextInt();
			int facesC = sc.nextInt();
			
			for(int i=0;i<vertsC;i++){
				verts.add(i, new Point(sc.nextFloat(),sc.nextFloat(),sc.nextFloat()));
			}

			for(int i=0;i<normsC;i++){
				norms.add(i,new Vector(sc.nextFloat(),sc.nextFloat(),sc.nextFloat()));
			}
			for(int i=0;i<facesC;i++){
				int coC = sc.nextInt();
				ArrayList<Integer> vertIn = new ArrayList<Integer>(coC);
				ArrayList<Integer> normIn = new ArrayList<Integer>(coC);
				boolean equal = true;
				for(int y=0;y<coC;y++){
					vertIn.add(y,sc.nextInt());
				}
				for(int y=0;y<coC;y++){
					normIn.add(y,sc.nextInt());
					if( y>0 && equal )
						equal = norms.get(normIn.get(y-1)) .equals( norms.get(normIn.get(y)) );
				}
				Face gezicht = new Face(vertIn,normIn);
				if( equal ){
					gezicht.oneNormal = true;
					gezicht.facePlaneNormal = norms.get(normIn.get(0));
				}else{
					gezicht.oneNormal = false;
					//http://stackoverflow.com/questions/2035659/normal-vector-of-three-points
					Vector a = verts.get(vertIn.get(0)).subtract(verts.get(vertIn.get(1)));
					Vector b = verts.get(vertIn.get(1)).subtract(verts.get(vertIn.get(2)));
					gezicht.facePlaneNormal = a.getCross(b);
				}
				faces.add( gezicht );
			}
			sc.close();
			makeGenBoxExtent ();
		} catch (FileNotFoundException e) {
			System.out.println("File not found exception!");
		}
	}
	
	private void makeGenBoxExtent() {
		float left = Float.MAX_VALUE;
		float right = Float.MIN_VALUE;
		float top = Float.MIN_VALUE;
		float bottom = Float.MAX_VALUE;
		float front = Float.MIN_VALUE;
		float back = Float.MAX_VALUE;
		for( Face f : faces ){
			for( int i : f.vertIndices ){
				Point p = verts.get(i);
				if( p.x > right ) right = p.x;
				if( p.x < left ) left = p.x;
				if( p.y > top ) top = p.y;
				if( p.y < bottom) bottom = p.y;
				if( p.z > front ) front = p.z;
				if( p.z < back ) back = p.z;
			}
		}
		this.genBoxExtent = new BoxExtent(left, top, right, bottom, front, back);
	}

	public void draw(GLAutoDrawable drawable){
		GL2 gl = drawable.getGL().getGL2();
		for(Face face: faces){
			gl.glBegin(GL2.GL_POLYGON);  
			for(int i=0; i<face.vertIndices.size();i++){
				Vector normal = norms.get(face.vertIndices.get(i));   // to do
				Point vertex = verts.get(face.normIndices.get(i));	// to do
				gl.glNormal3f(normal.x, normal.y,normal.z);
				gl.glVertex3f(vertex.x, vertex.y,vertex.z);
			}
            gl.glEnd();
		}
	}

	@Override
	public Intersection intersection(Ray ray) {
		Ray invRay = ray.getInvTransfoRay(transfo.invMat);
		Intersection intersection = new Intersection();
		if( !genBoxExtent.hit(invRay) )
			return intersection;
		for( Face face : faces ){
			Vector m = face.facePlaneNormal;//norms.get(face.normIndices.get(0));
			
			if( !(Math.abs(m.dot(invRay.dir)) < 0.0000001) ){
				Point a = verts.get(face.vertIndices.get(0));
				
				float thit = (m.dot(a.subtract(invRay.start)))/(m.dot(invRay.dir));
				Point hit = ray.getPoint(thit);
				Point pointInv = invRay.getPoint(thit);
				if( thit >= 0 ){
					Point b;
					Boolean ok = true;
					for(int i = 0; i<face.vertIndices.size(); i++ ){
						if( i == face.vertIndices.size()-1 ){
							a = verts.get(face.vertIndices.get(i));
							b = verts.get(face.vertIndices.get(0));
						}else{
							a = verts.get(face.vertIndices.get(i));
							b = verts.get(face.vertIndices.get(i+1));
						}
						Vector v1 = b.subtract(a);
						Vector v2 = pointInv.subtract(a);
						Vector v3 = v1.getCross(v2);
						if( v3.dot(m) <= 0 ){
							ok = false;
							break;
						}
					}
					
					if( ok ){
						if( !face.oneNormal ){
							m = this.interpolation(face, pointInv);
						}
						Matrix tInv = transfo.invMat.getTranspose();
						Vector newm = tInv.mult(m);
						newm.normalize();
						
						HitInfo hi = new HitInfo(thit,this.mtrl,hit,newm,true);
						int index = 0;
						for( HitInfo loop : intersection.hits ){
							if( hi.t < loop.t )
								break;
							index++;
						}
						intersection.insert(index, hi);
					}
				}
			}
		}
		
		return intersection;
	}
	
	private Vector interpolation(Face f, Point p){
		
		Point p1 = verts.get(f.vertIndices.get(0));
		Point p2 = verts.get(f.vertIndices.get(1));
		Point p3 = verts.get(f.vertIndices.get(2));
		
		float x03 = p.x - p3.x;
		float y03 = p.y - p3.y;
		float z03 = p.z - p3.z;
		float x13 = p1.x - p3.x;
		float y13 = p1.y - p3.y;
		float z13 = p1.z - p3.z;
		float x23 = p2.x - p3.x;
		float y23 = p2.y - p3.y;
		float z23 = p2.z - p3.z;
		
		float a = (float) (Math.pow(x13,2) + Math.pow(y13,2) + Math.pow(z13,2));
		float b = x13*x23 + y13*y23 + z13*z23;
		float c = (float) (Math.pow(x23,2) + Math.pow(y23,2) + Math.pow(z23,2));
		float d = (float) ((a*c)-Math.pow(b,2));
		
		float l1 = ((c*x13 - b*x23)*x03 + (c*y13 - b*y23)*y03 + (c*z13 - b*z23)*z03)/ d;
		float l2 = ((-b*x13 + a*x23)*x03 + (-b*y13 + a*y23)*y03 + (-b*z13 + a*z23)*z03)/ d;
		float l3 = 1 - l1 - l2;
		
		Vector v1 = new Vector( norms.get(f.normIndices.get(0)) );
		Vector v2 = new Vector( norms.get(f.normIndices.get(1)) );
		Vector v3 = new Vector( norms.get(f.normIndices.get(2)) );
		
		v1.mult(l1);
		v2.mult(l2);
		v3.mult(l3);
		
		Vector v = v1.addV(v2);
		v.add(v3);
		v.normalize();
		return v;
	}
	
	@Override
	public boolean hit(Ray ray) {
		Ray invRay = ray.getInvTransfoRay(transfo.invMat);

		for( Face face : faces ){
			Vector m = face.facePlaneNormal;//norms.get(face.normIndices.get(0));
			
			if( !(Math.abs(m.dot(invRay.dir)) < 0.0000001) ){
				Point a = verts.get(face.vertIndices.get(0));
				
				float thit = (m.dot(a.subtract(invRay.start)))/(m.dot(invRay.dir));
				Point pointInv = invRay.getPoint(thit);
				if( thit >= 0 && thit < 1 ){
					Point b;
					Boolean ok = true;
					for(int i = 0; i<face.vertIndices.size(); i++ ){
						if( i == face.vertIndices.size()-1 ){
							a = verts.get(face.vertIndices.get(i));
							b = verts.get(face.vertIndices.get(0));
						}else{
							a = verts.get(face.vertIndices.get(i));
							b = verts.get(face.vertIndices.get(i+1));
						}
						Vector v1 = b.subtract(a);
						Vector v2 = pointInv.subtract(a);
						Vector v3 = v1.getCross(v2);
						if( v3.dot(m) <= 0 ){
							ok = false;
							break;
						}
					}
					
					if( ok ){
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
}
