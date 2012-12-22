package transfo;

public class RotateTransfo extends Transfo {

	public RotateTransfo(double angle, float x, float y, float z){
		super();
		//Math.cos en Math.sin moeten maar 1 keer berekend worden!
		angle = Math.toRadians(angle);
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		mat.m[0][0] = (float) (cos + Math.pow(x, 2) * (1-cos));
		mat.m[0][1] = (float) ((x*y*(1-cos))-(z*sin));
		mat.m[0][2] = (float) ((x*z*(1-cos))+(y*sin));
		mat.m[1][0] = (float) ((x*y*(1-cos))+(z*sin));;
		mat.m[1][1] = (float) (cos + Math.pow(y, 2) * (1-cos));;
		mat.m[1][2] = (float) ((y*z*(1-cos))-(x*sin));		
		mat.m[2][0] = (float) ((x*z*(1-cos))-(y*sin));
		mat.m[2][1] = (float) ((y*z*(1-cos))+(x*sin));;
		mat.m[2][2] = (float) (cos + Math.pow(z, 2) * (1-cos));;
		mat.m[3][3] = 1;
		
		invMat.m[0][0] = (float) (cos + Math.pow(x, 2) * (1-cos));
		invMat.m[0][1] = (float) ((x*y*(1-cos))+(z*sin));
		invMat.m[0][2] = (float) ((x*z*(1-cos))-(y*sin));
		invMat.m[1][0] = (float) ((x*y*(1-cos))-(z*sin));;
		invMat.m[1][1] = (float) (cos + Math.pow(y, 2) * (1-cos));;
		invMat.m[1][2] = (float) ((y*z*(1-cos))+(x*sin));		
		invMat.m[2][0] = (float) ((x*z*(1-cos))+(y*sin));
		invMat.m[2][1] = (float) ((y*z*(1-cos))-(x*sin));;
		invMat.m[2][2] = (float) (cos + Math.pow(z, 2) * (1-cos));;
		invMat.m[3][3] = 1;
	}
	
}
