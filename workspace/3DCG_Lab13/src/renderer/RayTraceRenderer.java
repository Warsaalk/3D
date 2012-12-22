package renderer;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

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
	private int ssaan; //Number of samples defined by the user + 1 
	private float[][] ssaa; //See comment constructor
	
	public RayTraceRenderer(Scene scene, Camera camera, Properties prop) {
		super(scene, camera);	
		nRows = Integer.parseInt(prop.getProperty("canvas.height"));
	    nCols = Integer.parseInt(prop.getProperty("canvas.width"));	    
	    rayTracer = RayTracerFactory.createRayTracer(scene,prop);
	    ssaan = (rayTracer.supersampling*4)+1;
	    //For each sample we take 2 random numbers between 0 and 0.9999f
	    // this is a very simple way to create SuperSampling
	    //see comment render method for usage
	    ssaa = new float[ssaan][2];
		for( int i=0; i<ssaan; i++ ){
			ssaa[i][0] = new Random().nextFloat();
			ssaa[i][1] = new Random().nextFloat();
		}
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
				ArrayList<Colour> colours = new ArrayList<Colour>(ssaan);
				for( int i=0; i<ssaan; i++ ){
					Colour temp = new Colour();
					//As you know we normally just take the color in the top left corner of the "pixel"
					// that not good enough because the color in the bottom right corner could be totally different.
					// In the constructor we chose 2 random floats to be added to the current row and column respectively (r and c).
					// This gives us a random position in the "pixel" and 'always' returns a different color.
					// Disadvantage, the random positions could be close together and not make a big difference.
					// Also this makes every render different because it are random numbers.
					// A better way should be to divide the pixel in grids and 
					// choose the grids in a way that you maximize the possibility of getting different colors.
					// Example: if you want 4 samples you take the 4 corners of the pixel
					float cc = c + ssaa[i][0];
					float rr = r + ssaa[i][1];
					ray.dir = new Vector(camera.u.multV(cc*(camera.width/this.nCols)));
					ray.dir.add( camera.v.multV(-rr*(camera.height/this.nRows)) );
					ray.dir.add( v3 );
					rayTracer.recursionDepth=0;
					temp.set(rayTracer.shade(ray));
					//We add the color to a array, which contains the different colors from our sampling
					colours.add(temp);
				}
				//We calculate the average of our array of colors
				col.average(colours);
				gl. glColor3f ( col.r, col.g, col.b);
				gl.glRecti(c, nRows-r, c+1, nRows-r-1);
			}
		}
		
		gl.glFlush();
	}
	
}
