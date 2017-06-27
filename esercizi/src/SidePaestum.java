import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;

public class SidePaestum extends Group {
	protected int numColumns = 12;
	
	protected Transform3D t3dRoof = new Transform3D();
	protected Transform3D t3dRoofSupport = new Transform3D();
	protected Transform3D[] t3dColumns = new Transform3D[numColumns];
	
	protected TransformGroup TGRoof = new TransformGroup(t3dRoof);
	protected TransformGroup TGRoofSupport = new TransformGroup(t3dRoofSupport);
	protected TransformGroup[] TGColumns = new TransformGroup[numColumns];
	
	protected Appearance app = new Appearance();
	int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;		
	
	protected Column[] columns = new Column[numColumns];
	protected Box roof = new Box(1.429f, .005f, .08f, primflags, app);
	protected Box roofSupport = new Box(1.429f, .07f, .03f, primflags, app);
	
	public SidePaestum(Appearance appearance) {		
		app = appearance;
		float xAxis = -1.45f +.22f; // spazio tra le colonne
		/* Colonne */
		for(int i=0; i<numColumns; i++) {
			columns[i] = new Column(.05f, .3f, app);
			t3dColumns[i] = new Transform3D();
			TGColumns[i] = new TransformGroup();
			t3dColumns[i].setTranslation(new Vector3d(xAxis, -.28, 0));
			
			TGColumns[i].setTransform(t3dColumns[i]);
			TGColumns[i].addChild(columns[i]);
			
			addChild(TGColumns[i]);
			
			xAxis+=.22f;
		}
		
		/* Tetto */
		t3dRoofSupport.setTranslation(new Vector3d(-.02,-.28+.3f+.13, 0));
		TGRoofSupport.setTransform(t3dRoofSupport);
		roofSupport.setAppearance(app);
		TGRoofSupport.addChild(roofSupport);
		
		addChild(TGRoofSupport);
		
		t3dRoof.setTranslation(new Vector3d(0,-.28+.3f+.205, 0.02));
		TGRoof.setTransform(t3dRoof);
		roof.setAppearance(app);
		TGRoof.addChild(roof);
		
		addChild(TGRoof);
		
	}

}