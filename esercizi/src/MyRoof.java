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

	class MyRoof extends Shape3D {
	
		protected float roofHeight = .3f;
		protected float roofLength = .8f;
		protected float thickness= .05f;
		protected float border= .015f;
		
		protected Appearance appearance = new Appearance ( ) ;
		protected PolygonAttributes polyAttrbutes = new PolygonAttributes ( ) ;
		protected TriangleStripArray triangleStrip = null;
		protected Point3f v[] = null;
	
		public MyRoof(float length, float height, Appearance app) {
			roofLength = length;
			roofHeight = height;
			appearance = app;
			
			int size = 27;
			
			Point3f p1 = new Point3f(0,roofHeight/2,thickness);
			Point3f p2 = new Point3f(-roofLength/2,-roofHeight/2,thickness);
			Point3f p3 = new Point3f(roofLength/2,-roofHeight/2,thickness);
			Point3f p4 = new Point3f(0,roofHeight/2-border*1.5f,thickness);
			Point3f p5 = new Point3f(-roofLength/2+border*5f,-roofHeight/2+border,thickness);
			Point3f p6 = new Point3f(roofLength/2-border*5f,-roofHeight/2+border,thickness);
			Point3f p7 = new Point3f(0,roofHeight/2,-thickness);
			Point3f p8 = new Point3f(-roofLength/2,-roofHeight/2,-thickness);
			Point3f p9 = new Point3f(roofLength/2,-roofHeight/2,-thickness);
			Point3f p10 = new Point3f(0,roofHeight/2-border*1.5f,-thickness/2);
			Point3f p11 = new Point3f(-roofLength/2+border*5f,-roofHeight/2+border,-thickness/2);
			Point3f p12 = new Point3f(roofLength/2-border*5f,-roofHeight/2+border,-thickness/2);
			
			
			v = new Point3f[size];
			
			v[0] = (p7);
			v[1] = (p8);
			v[2] = (p9);
			v[3] = (p7);
			v[4] = (p1);
			v[5] = (p8);
			v[6] = (p2);
			v[7] = (p9);
			v[8] = (p3);
			v[9] = (p1);
			v[10] = (p4);
			v[11] = (p2);
			v[12] = (p5);
			v[13] = (p3);
			v[14] = (p6);
			v[15] = (p4);
			v[16] = (p4);
			v[17] = (p5);
			v[18] = (p10);
			v[19] = (p11);
			v[20] = (p12);
			v[21] = (p5);
			v[22] = (p6);
			v[23] = (p6);
			v[24] = (p12);
			v[25] = (p4);
			v[26] = (p10);	
			
			int [ ] stripCounts ={size};
			triangleStrip = new TriangleStripArray(size,
					GeometryArray.COORDINATES | GeometryArray.NORMALS | GeometryArray.TEXTURE_COORDINATE_2,stripCounts );
			triangleStrip.setCoordinates(0,v);
	
			polyAttrbutes.setCullFace(PolygonAttributes.CULL_NONE ) ;
			appearance.setPolygonAttributes (polyAttrbutes) ;
			GeometryInfo info = new GeometryInfo(triangleStrip);
			NormalGenerator ng = new NormalGenerator();
			ng.generateNormals(info);
			Geometry geom = info.getGeometryArray();
			
			TexCoordGeneration textureCoordGeneration = new TexCoordGeneration( TexCoordGeneration.OBJECT_LINEAR, TexCoordGeneration.TEXTURE_COORDINATE_2);
			appearance.setTexCoordGeneration(textureCoordGeneration);
			
			setGeometry(geom);
			setAppearance ( appearance ) ;
		}
	}