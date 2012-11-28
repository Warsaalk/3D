package geomobj;

import material.Material;

public abstract class Shape implements GeomObj {
	
	public Material mtrl;
	
	public Shape(){
		mtrl = new Material();
	}

}
