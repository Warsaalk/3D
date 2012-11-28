package transfo;

public class TranslateTransfo extends Transfo {

	public TranslateTransfo(float x, float y, float z){
		super();
		mat.m[0][0] = 1;
		mat.m[0][3] = x;
		mat.m[1][1] = 1;
		mat.m[1][3] = y;
		mat.m[2][2] = 1;
		mat.m[2][3] = z;
		mat.m[3][3] = 1;
		
		invMat.m[0][0] = 1;
		invMat.m[0][3] = -x;
		invMat.m[1][1] = 1;
		invMat.m[1][3] = -y;
		invMat.m[2][2] = 1;
		invMat.m[2][3] = -z;
		invMat.m[3][3] = 1;
	}
	
}
