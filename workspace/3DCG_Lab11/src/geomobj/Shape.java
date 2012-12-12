package geomobj;

import transfo.Transfo;
import material.Material;

public abstract class Shape implements GeomObj {
	
	public Material mtrl;
	public Transfo transfo; 
	
	public Shape(){
		mtrl = new Material();
		transfo = new Transfo();
	}

	public void setTransfo(Transfo transfo){
		this.transfo = transfo;
	}
	
}
