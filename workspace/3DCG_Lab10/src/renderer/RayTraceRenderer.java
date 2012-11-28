package renderer;

import java.util.Properties;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import renderer.raytracer.RayTracer;
import renderer.raytracer.RayTracerFactory;
import scene.Scene;
import util.Colour;
import util.Vector;

public class RayTraceRenderer extends Renderer {
	
	private RayTracer rayTracer;	
	private int nRows, nCols;	

	public RayTraceRenderer(Scene scene, Camera camera, Properties prop) {
		super(scene, camera);	
		nRows = Integer.parseInt(prop.getProperty("canvas.height"));
	    nCols = Integer.parseInt(prop.getProperty("canvas.width"));	    
	    rayTracer = RayTracerFactory.createRayTracer(scene,prop);    
	}

	@Override
	public void init(GLAutoDrawable drawable) {
	}
	
	@Override
	public void render(GLAutoDrawable drawable) {
				
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);		
		GLU glu = new GLU();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluOrtho2D(0, nCols, 0, nRows);		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	    gl.glLoadIdentity();   	   
		gl.glDisable(GL2.GL_LIGHTING);
		
		Ray ray = new Ray(super.camera.eye);
		Colour col = new Colour();
		
		Vector v1 = new Vector(camera.u.multV(-(camera.width/2)).addV( camera.v.multV((camera.height/2)) ));		
		Vector v3 = new Vector(camera.n.multV(-camera.distance).addV(v1));
		
		for( int r=0; r<this.nRows; r++){
			for( int c=0; c<this.nCols; c++){
				ray.dir = new Vector(camera.u.multV(c*(camera.width/this.nCols)));
				ray.dir.add( camera.v.multV(-r*(camera.height/this.nRows)) );
				ray.dir.add( v3 );
				col.set(rayTracer.shade(ray));
				gl. glColor3f ( col.r, col.g, col.b);
				gl.glRecti(c, nRows-r, c+1, nRows-r-1);
			}
		}
		
		gl.glFlush();
	}
	
}
