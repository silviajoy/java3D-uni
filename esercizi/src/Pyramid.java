import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Geometry;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransparencyAttributes;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3d;

public class Pyramid extends Shape3D{
	protected Geometry geometry;
	protected Appearance appearance;
	
	private Point3d p1;
	private Point3d p2; 
	private Point3d p3;
	private Point3d p4;
	private Point3d p5;
	

	// Impostazione dei NodeComponent .
	
	public Pyramid(double side) {
		p1 = new Point3d( side/2, 0, -side/2);
		p2 = new Point3d(-side/2, 0, -side/2);
		p3 = new Point3d(-side/2, 0, side/2);
		p4 = new Point3d ( side/2, 0, side/2);
		p5 = new Point3d ( 0 , side, 0);
		
		Point3d[] faces = {
				p1,p5,p2,
				p1,p2,p4,
				p3,p2,p5,
				p1,p5,p4,
				p3,p4,p2,
				p5,p3,p4
		};
		
		
		geometry=createGeometry(faces);
		appearance=createAppearance();
		setGeometry(geometry) ;
		setAppearance(appearance) ;
	}
	
	protected Geometry createGeometry (Point3d[] faces) {
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