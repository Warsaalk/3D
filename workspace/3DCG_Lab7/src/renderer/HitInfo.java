package renderer;

import material.Material;
import util.Point;
import util.Vector;

public class HitInfo {
	
	public float t;
	public Point hitPoint;
	public Vector hitNormal;
	public boolean isEntering;
	public Material hitMaterial;
	
	public HitInfo(float t, Material hitMaterial, Point hitPoint, Vector hitNormal,
				   boolean isEntering) {
		this.t = t;
		this.hitPoint = hitPoint;
		this.hitNormal = hitNormal;
		this.isEntering = isEntering;
		this.hitMaterial = hitMaterial;
	}
}
