package transfo;

public class ScaleTransfo extends Transfo {
	
	public ScaleTransfo(float x, float y, float z){
		super();
		mat.m[0][0] = x;
		mat.m[1][1] = y;
		mat.m[2][2] = z;
		mat.m[3][3] = 1;
		
		invMat.m[0][0] = 1/x;
		invMat.m[1][1] = 1/y;
		invMat.m[2][2] = 1/z;
		invMat.m[3][3] = 1;
	}
	
}
