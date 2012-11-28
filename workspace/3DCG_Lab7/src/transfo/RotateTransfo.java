package transfo;

public class RotateTransfo extends Transfo {

	public RotateTransfo(double angle, float x, float y, float z){
		super();
		mat.m[0][0] = (float) (Math.cos(angle) + Math.pow(x, 2) * (1-Math.cos(angle)));
		mat.m[0][1] = (float) ((x*y*(1-Math.cos(angle)))-(z*Math.sin(angle)));
		mat.m[0][2] = (float) ((x*z*(1-Math.cos(angle)))+(y*Math.sin(angle)));
		mat.m[1][0] = (float) ((x*y*(1-Math.cos(angle)))+(z*Math.sin(angle)));;
		mat.m[1][1] = (float) (Math.cos(angle) + Math.pow(y, 2) * (1-Math.cos(angle)));;
		mat.m[1][2] = (float) ((y*z*(1-Math.cos(angle)))-(x*Math.sin(angle)));		
		mat.m[2][0] = (float) ((x*z*(1-Math.cos(angle)))-(y*Math.sin(angle)));
		mat.m[2][1] = (float) ((y*z*(1-Math.cos(angle)))+(x*Math.sin(angle)));;
		mat.m[2][2] = (float) (Math.cos(angle) + Math.pow(z, 2) * (1-Math.cos(angle)));;
		mat.m[3][3] = 1;
		
		invMat.m[0][0] = (float) (Math.cos(angle) + Math.pow(x, 2) * (1-Math.cos(angle)));
		invMat.m[0][1] = (float) ((x*y*(1-Math.cos(angle)))+(z*Math.sin(angle)));
		invMat.m[0][2] = (float) ((x*z*(1-Math.cos(angle)))-(y*Math.sin(angle)));
		invMat.m[1][0] = (float) ((x*y*(1-Math.cos(angle)))-(z*Math.sin(angle)));;
		invMat.m[1][1] = (float) (Math.cos(angle) + Math.pow(y, 2) * (1-Math.cos(angle)));;
		invMat.m[1][2] = (float) ((y*z*(1-Math.cos(angle)))+(x*Math.sin(angle)));		
		invMat.m[2][0] = (float) ((x*z*(1-Math.cos(angle)))+(y*Math.sin(angle)));
		invMat.m[2][1] = (float) ((y*z*(1-Math.cos(angle)))-(x*Math.sin(angle)));;
		invMat.m[2][2] = (float) (Math.cos(angle) + Math.pow(z, 2) * (1-Math.cos(angle)));;
		invMat.m[3][3] = 1;
	}
	
}
