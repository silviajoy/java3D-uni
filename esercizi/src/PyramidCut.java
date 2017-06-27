import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Geometry;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransparencyAttributes;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

public class PyramidCut extends Shape3D{
	protected Geometry geometry;
	protected Appearance appearance;
	
	private Point3d p1;
	private Point3d p2; 
	private Point3d p3;
	private Point3d p4;
	private Point3d p5;
	private Point3d p6;
	private Point3d p7;
	private Point3d p8;
	
	public PyramidCut(double height, double sideBottom, double sideTop) {
		/* inizializzo i vertici del tronco di piramide a partire dai parametri in input */
		p1 = new Point3d( sideBottom/2, 0, -sideBottom/2);
		p2 = new Point3d(-sideBottom/2, 0, -sideBottom/2);
		p3 = new Point3d(-sideBottom/2, 0, sideBottom/2);
		p4 = new Point3d ( sideBottom/2, 0, sideBottom/2);
		p5 = new Point3d( sideTop/2, height, -sideTop/2);
		p6 = new Point3d(-sideTop/2, height, -sideTop/2);
		p7 = new Point3d(-sideTop/2, height, sideTop/2);
		p8 = new Point3d ( sideTop/2, height, sideTop/2);
		
		/* creo le facce */
		Point3d[] faces = {
				p4,p1,p2,
				p4,p2,p3,
				p1,p5,p6,
				p1,p6,p2,
				p2,p6,p7,
				p2,p7,p3,
				p4,p8,p3,
				p3,p8,p7,
				p1,p5,p4,
				p4,p5,p8,
				p5,p6,p8,
				p8,p6,p7				
		};

		/* creo il tronco a partire dalle facce */
		geometry=createGeometry(faces); 
		setGeometry(geometry);
		
		appearance=createAppearance();
		setAppearance(appearance);
	}
	
	protected Geometry createGeometry (Point3d[] faces) {
		/* creo il tronco a partire dalle facce */
		TriangleArray triangles ;
		triangles = new TriangleArray(faces.length, TriangleArray.COORDINATES);
		triangles.setCoordinates(0,faces );
	
		return triangles ;
	}
	
	protected Appearance createAppearance() {
		Appearance app = new Appearance () ;
		/* POLYGON_FILL per vedere le facce */
		app.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_NONE,0)	);
		/* per la trasparenza */
		//app.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.BLENDED, 0.75f));
		/* per il colore */
		//app.setColoringAttributes(new ColoringAttributes(0f, 255f, 0f, 1));
		
	 	/* POLYGON_LINE per vedere il wireframe */
		//app.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_NONE,0)	);
		
		return app;
	}
}