package renderer.raytracer;

import java.util.Properties;
import scene.Scene;

public class RayTracerFactory {

	public static RayTracer createRayTracer( Scene scene, Properties prop ){
		String raytraceMode = prop.getProperty("raytrace.mode");
		RayTracer tracer = null;
		if( raytraceMode.equals("shadow") )
			tracer = new RayTracerWithShadow ( scene );
		else if( raytraceMode.equals("reflection") )
			tracer = new RayTracerWithReflection ( scene, prop );
		else
			tracer = new BasicRayTracer ( scene );
		String ssaa = prop.getProperty("raytrace.supersampling");
		if( ssaa != null )
			tracer.supersampling = Integer.parseInt(ssaa);
		return tracer;
	}
	
}
