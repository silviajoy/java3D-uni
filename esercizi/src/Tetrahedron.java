import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Geometry;
import javax.media.j3d.TransparencyAttributes;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3d;

public class Tetrahedron extends VisualObject{
	private static final Point3d P1 = new Point3d( 1.0, 1.0, 1.0);
	private static final Point3d P2 = new Point3d(-1.0, -1.0, 1.0);
	private static final Point3d P3 = new Point3d(-1.0, 1.0, -1.0);
	private static final Point3d P4 = new Point3d ( 1.0 , -1.0, -1.0);
	private static final Point3d [ ] faces = {
			P1,P3,P2,
			P1,P2,P4,
			P2,P3,P4,
			P1,P4,P3
	};
	
	protected Geometry createGeometry ( ) {
		TriangleArray triangles ;
		//TriangleArray.COORDINATES mi dice che i triangoli sono formati da un ARRAY DI COORDINATE
		triangles = new TriangleArray(faces.length, TriangleArray.COORDINATES);
		triangles.setCoordinates(0,faces );
		return triangles ;
	}
	
	protected Appearance createAppearance() {
		Appearance app = new Appearance ( ) ;
		//POLYGON_LINE - the polygon is rendered as lines drawn between consecutive vertices.
		//CULL_BACK - culls all front-facing polygons. The default.
		//app.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_BACK,0)	);
		//appDown.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_NONE, 0));
		app.setColoringAttributes(new ColoringAttributes(0, 60, 0, ColoringAttributes.SHADE_FLAT));
		app.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.BLENDED, 0.50f));
		return app;
	}
}