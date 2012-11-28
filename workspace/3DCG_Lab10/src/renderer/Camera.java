package renderer;
import util.Point;
import util.Quaternion;
import util.Vector;
import java.util.Properties;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

public class Camera {
	
	public Point eye;
	public Point look;
	public Vector up;
	public float distance;
	public float width;
	public float height;
	
	public Vector u,v,n;
	
	public Camera(Properties prop){
		eye = new Point(Float.parseFloat(prop.getProperty("camera.eye.x")),
					     Float.parseFloat(prop.getProperty("camera.eye.y")),
					     Float.parseFloat(prop.getProperty("camera.eye.z")));
		look = new Point(Float.parseFloat(prop.getProperty("camera.look.x")),
						  Float.parseFloat(prop.getProperty("camera.look.y")),
						  Float.parseFloat(prop.getProperty("camera.look.z")));
		up = new Vector(Float.parseFloat(prop.getProperty("camera.up.x")),
						 Float.parseFloat(prop.getProperty("camera.up.y")),
						 Float.parseFloat(prop.getProperty("camera.up.z")));
		distance = Float.parseFloat(prop.getProperty("camera.worldwindow.distance"));
		height = Float.parseFloat(prop.getProperty("camera.worldwindow.height"));
		width = Float.parseFloat(prop.getProperty("camera.worldwindow.width"));
		n = new Vector(eye.subtract(look));
		n.normalize();
		u = new Vector(up.getCross(n));
		u.normalize();
		v = new Vector(n.getCross(u));
	}
	
	public void setPositionAndOrientationOpenGL(GLAutoDrawable drawable){
		GL2 gl = drawable.getGL().getGL2();
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	    gl.glLoadIdentity();    
	    GLU glu = new GLU();
    	glu.gluLookAt(eye.x, eye.y, eye.z, look.x, look.y, look.z, v.x, v.y, v.z);
	}
	
	public void forward(){
		eye.subtract(n);
	}
	
	public void backward(){
		eye.add(n);
	}
	
	public void up(){
		rotateVertical(0.5);
	}
	
	public void down(){
		rotateVertical(-0.5);
	}
	
	private void rotateVertical(double degrees){
		Quaternion q = new Quaternion((float)degrees,u);
		Quaternion p = new Quaternion(eye);
		Quaternion qc = q.conjugate(q);
		
		Quaternion rotate = q.mult(p);
		rotate = rotate.mult(qc);
		eye = rotate.getPoint();
		n = new Vector(eye.subtract(look));
		n.normalize();
		v = new Vector(n.getCross(u));
	}
	
	public void right(){
		rotateHorizontal(0.5);
	}
	
	public void left(){
		rotateHorizontal(-0.5);
	}
	
	private void rotateHorizontal(double degrees){
		Quaternion q = new Quaternion((float)degrees,v);
		Quaternion p = new Quaternion(eye);
		Quaternion qc = q.conjugate(q);
		
		Quaternion rotate = q.mult(p);
		rotate = rotate.mult(qc);
		eye = rotate.getPoint();
		n = new Vector(eye.subtract(look));
		n.normalize();
		u = new Vector(v.getCross(n));
	}
}

