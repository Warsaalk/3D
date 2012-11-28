package util;

public class Matrix {
	
	public float[][] m = new float[4][4];
	
	public Matrix(){
		for(int i=0; i<4;i++){
			m[i][i]=1;
			for(int j=i+1;j<4;j++){
				m[i][j] = 0;
				m[j][i] = 0;
			}		
		}
	}
	
	public Matrix(Matrix a){
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				m[i][j] = a.m[i][j];
			}
		}
	}
	
	public Point mult(Point p){		
		float a = m[0][0] * p.x + m[0][1] * p.y + m[0][2] * p.z + m[0][3] * 1;
		float b = m[1][0] * p.x + m[1][1] * p.y + m[1][2] * p.z + m[1][3] * 1;
		float c = m[2][0] * p.x + m[2][1] * p.y + m[2][2] * p.z + m[2][3] * 1;
		
		return new Point(a,b,c);
	}
	
	public Vector mult(Vector v){
		float a = m[0][0] * v.x + m[0][1] * v.y + m[0][2] * v.z;
		float b = m[1][0] * v.x + m[1][1] * v.y + m[1][2] * v.z;
		float c = m[2][0] * v.x + m[2][1] * v.y + m[2][2] * v.z;
		
		return new Vector(a,b,c);
	}
	
	public Matrix getTranspose(){
		Matrix res = new Matrix();
		for(int i=0;i<4;i++){
			res.m[i][i]=m[i][i];
			for(int j=i;j<4;j++){
				res.m[i][j]=m[j][i];
				res.m[j][i]=m[i][j];
			}
		}
		return res;
	}

}
