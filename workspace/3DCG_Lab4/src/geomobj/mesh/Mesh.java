package geomobj.mesh;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import util.Point;
import util.Vector;

public class Mesh {
	
	private ArrayList<Point> verts = new ArrayList<Point>();	    // vertex list
	private ArrayList<Vector> norms = new ArrayList<Vector>();	// normal list
	private ArrayList<Face> faces = new ArrayList<Face>();	    // face list
	
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
				for(int y=0;y<coC;y++){
					vertIn.add(y,sc.nextInt());
				}
				for(int y=0;y<coC;y++){
					normIn.add(y,sc.nextInt());
				}
				faces.add(new Face(vertIn,normIn));
			}
			sc.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found exception!");
		}
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
	
}
