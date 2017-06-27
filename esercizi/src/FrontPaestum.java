import java.awt.Container;

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

public class FrontPaestum extends Group {
	protected int numColumns = 6;
	
	protected Transform3D t3dBase = new Transform3D();
	protected Transform3D t3dRoof = new Transform3D();
	protected Transform3D t3dRoofSupport = new Transform3D();
	protected Transform3D[] t3dColumns = new Transform3D[numColumns];
	
	protected TransformGroup TGBase = new TransformGroup(t3dBase);
	protected TransformGroup TGRoof = new TransformGroup(t3dRoof);
	protected TransformGroup TGRoofSupport = new TransformGroup(t3dRoofSupport);
	protected TransformGroup[] TGColumns = new TransformGroup[numColumns];
	
	protected Appearance app = new Appearance();
	int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;		
	
	protected Steps base = new Steps(3,.7f, .05f, .15f, app);
	protected Column[] columns = new Column[numColumns];
	protected MyRoof roof = new MyRoof(1.35f, .25f, app);
	protected Box roofSupport = new Box(.62f, .07f, .03f, primflags, app);
	
	public FrontPaestum() {
		
		t3dBase.setTranslation(new Vector3d(0,-.4,.05));
		TGBase.setTransform(t3dBase);
		TGBase.addChild(base);
		
		addChild(TGBase);
		
		
		float xAxis = 3*.05f-.7f;
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
		
		t3dRoofSupport.setTranslation(new Vector3d(0,-.28+.3f+.125, 0));
		TGRoofSupport.setTransform(t3dRoofSupport);
		TGRoofSupport.addChild(roofSupport);
		
		addChild(TGRoofSupport);
		
		t3dRoof.setTranslation(new Vector3d(0,-.28+.3f+.125+.195, 0));
		TGRoof.setTransform(t3dRoof);
		TGRoof.addChild(roof);
		
		addChild(TGRoof);
		
		Material material = new Material();
		material.setShininess(128f);
		app.setMaterial(material);
		
		// Imposta la texture
		TextureLoader loader = new TextureLoader("./textures/PietraColonna.jpg",
		      null);
		Texture texture = loader.getTexture();
		texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );	   
        // Imposta gli attributi della texture
		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.MODULATE);
		app.setTexture(texture);
		app.setTextureAttributes(texAttr);
		
	}
	
}