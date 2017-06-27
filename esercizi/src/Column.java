import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Group;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Matrix3d;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;
import javax.vecmath.Vector3d;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Column extends Group {
	
	static final protected Appearance appearance = new Appearance() ;
	
	static final protected Transform3D cylinderTransform = new Transform3D ();
	static final protected Transform3D coneTransform = new Transform3D ();
	static final protected Transform3D boxTransform = new Transform3D ();
	protected TransformGroup cylinderTG = new TransformGroup(cylinderTransform);
	protected TransformGroup coneTG = new TransformGroup(coneTransform);
	protected TransformGroup boxTG = new TransformGroup(boxTransform);
	protected Appearance app = new Appearance(); 
	int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
	protected Cylinder cylinder = new Cylinder(.1f, .6f, primflags, app);
	protected MyCylinder cone = new MyCylinder(40, 1f, 2f, app);
	protected Box box = new Box(.15f,.02f,.15f, primflags,  app);
	
	
	public Column(float diameter, float height, Appearance appearance) {
		app = appearance;
		
		/* inizializzazione delle parti */
		cylinder = new Cylinder(diameter, height, primflags, app);
		cone = new MyCylinder(20, diameter, height/6, app);
		box = new Box(diameter+0.5f*diameter,.2f*diameter, diameter+0.5f*diameter, primflags,  app);
		
		/* echino */
		coneTransform.setTranslation(new Vector3d(0,height+height/12,0));
		coneTG = new TransformGroup(coneTransform);
		
		/* cilindro */
		cylinderTransform.setTranslation(new Vector3d(0,height/2, 0));
		cylinderTG = new TransformGroup(cylinderTransform);
		
		/* box */
		boxTransform.setTranslation(new Vector3d(0,height+height/6, 0));
		boxTG = new TransformGroup(boxTransform);
		
		/* imposto l'appearance */
		cylinder.setAppearance ( app) ;
		cone . setAppearance ( app ) ;
		box.setAppearance ( app) ;
		/* aggiungo al TransformGroup */
		cylinderTG.addChild (cylinder) ;
		coneTG.addChild (cone) ;
		boxTG.addChild (box) ;
		/* aggiungo al Group */
		addChild(cylinderTG) ;
		addChild(coneTG ) ;	
		addChild(boxTG);
		
	}
	
	public Appearance getAppearance() {
		return app;
	}

	
	
}