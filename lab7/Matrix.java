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
		// to do: implement
		return null;
	}
	
	public Vector mult(Vector v){
		// to do implement
		return null;
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
