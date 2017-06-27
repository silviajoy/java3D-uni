import javax.media.j3d.Appearance;
import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.TriangleStripArray;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

class MyCylinder extends Shape3D {
	
	protected float top = 1.0f;
	protected float bottom = -1.0f;
	protected Appearance appearance = new Appearance ( ) ;
	protected PolygonAttributes polyAttrbutes = new PolygonAttributes ( ) ;
	protected TriangleStripArray triangleStrip = null;
	protected Point3f v[] = null;

	public MyCylinder(int steps, float diameter, float height, Appearance app ) {
		
		float size = diameter;
		top = height/2;
		bottom = -height/2;
		appearance = app;
		
		/* creo il cilindro con le dimensioni in input */
		v = new Point3f[(steps+1)*2];
		for(int i=0; i<steps; i++) {
			/*l'angolo per il punto serve a suddividere la circonferenza in parti uguali */
			double angle = 2.0*Math.PI*(double)i/(double)steps;
			float x = size*(float)Math.sin(angle);
			float y = size*(float)Math.cos(angle);
			v[i*2+0] = new Point3f(x*1.2f, top, y*1.2f);
			v[i*2+1] = new Point3f(x,bottom, y);
		}
		/* ultimo giro */
		v[steps*2+0] = new Point3f(0.0f ,top, size*1.2f);
		v[steps*2+1] = new Point3f(0.0f , bottom, size);
		int [ ] stripCounts ={( steps+1)*2};
		/* impostazioni per le texture e le luci */
		triangleStrip = new TriangleStripArray((steps+1)*2, GeometryArray.COORDINATES | GeometryArray.NORMALS | GeometryArray.TEXTURE_COORDINATE_2,stripCounts );
		triangleStrip.setCoordinates(0,v);
		/* disabilita lo scartare di alcune facce */
		polyAttrbutes.setCullFace(PolygonAttributes.CULL_NONE ) ;
		appearance.setPolygonAttributes (polyAttrbutes) ;
		GeometryInfo info = new GeometryInfo(triangleStrip);
		NormalGenerator ng = new NormalGenerator(); // per le luci
		ng.generateNormals(info);
		Geometry geom = info.getGeometryArray();
		/* per le texture*/
		TexCoordGeneration textureCoordGeneration = new TexCoordGeneration( TexCoordGeneration.OBJECT_LINEAR, TexCoordGeneration.TEXTURE_COORDINATE_2);
		appearance.setTexCoordGeneration(textureCoordGeneration);
		
		setGeometry(geom);
		setAppearance ( appearance ) ;
	}
}