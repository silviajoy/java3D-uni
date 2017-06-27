import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Group;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;



public class Tavolo extends Group {
	
static final protected Appearance appearance = new Appearance() ;
	
	static final protected Transform3D cylinderTransform = new Transform3D ();
	static final protected Transform3D coneTransform = new Transform3D ();
	static final protected Transform3D tableTransform = new Transform3D ();
	
	protected TransformGroup cylinderTG = new TransformGroup(cylinderTransform);
	protected TransformGroup coneTG = new TransformGroup(coneTransform);
	protected TransformGroup tableTG = new TransformGroup(tableTransform);
	
	protected Appearance appBase = new Appearance();
	protected Appearance appTable = new Appearance();
	
	protected int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
	
	/* TEXTURES PATH */
	protected static String baseTexture = "./textures/marmoPanna.jpg";
	
	protected Cylinder cylinder = new Cylinder(.1f, .6f, primflags, appBase);
	protected MyCylinder cone = new MyCylinder(40, 1f, 2f, appBase);
	protected Cylinder table = new Cylinder(.5f,.05f, primflags,  appBase);
	
	
	public Tavolo(float diameter, float height) {		
		/* inizializzazione delle parti */
		cylinder = new Cylinder(diameter, height, primflags, appBase);
		cone = new MyCylinder(20, diameter, height/6, appBase);
		table = new Cylinder(3f*diameter, .05f, primflags,  appBase);
		
		/* echino */
		coneTransform.setTranslation(new Vector3d(0,height/12,0));
		coneTG = new TransformGroup(coneTransform);
		
		/* cilindro */
		cylinderTransform.setTranslation(new Vector3d(0,-height/2, 0));
		cylinderTG = new TransformGroup(cylinderTransform);
		
		/* tavolo */
		tableTransform.setTranslation(new Vector3d(0,height/6, 0));
		tableTG = new TransformGroup(tableTransform);
		
		/* imposto l'appearance per la base*/
		Material mBase = new Material();
		appBase.setMaterial(mBase);
		
		/* imposto la texture per la base */
		// Texture
		TextureLoader loader = new TextureLoader(baseTexture, null);
		//Carica la texture
	   	Texture textureB = loader.getTexture();
	   	//Imposta il comportamento ai bordi
	   	textureB.setBoundaryModeS(Texture.WRAP);
	   	textureB.setBoundaryModeT(Texture.WRAP);
	   	//Imposta i colori ai bordi
	   	textureB.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );		   

	    // Imposta gli attributi della texture
	    TextureAttributes texAttr = new TextureAttributes();
	   	texAttr.setTextureMode(TextureAttributes.MODULATE);
	   	appBase.setTextureAttributes(texAttr);
		appBase.setTexture(textureB);
		
		/* imposto l'appearance per il tavolo*/
		Material mTable = new Material();
		
		appTable.setMaterial(mTable);
		
		appTable.setColoringAttributes(new ColoringAttributes(0, 0, 200, ColoringAttributes.SHADE_FLAT));
		appTable.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.BLENDED, .25f));
		
		/* aggiungo le appearance */
		cylinder.setAppearance ( appBase) ;
		cone . setAppearance ( appBase ) ;
		table.setAppearance ( appTable) ;
		/* aggiungo al TransformGroup */
		cylinderTG.addChild (cylinder) ;
		coneTG.addChild (cone) ;
		tableTG.addChild (table) ;
		
		/* aggiungo la rotazione */
		TransformGroup objRotate=new TransformGroup();
		objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		objRotate.addChild(cylinderTG);
		objRotate.addChild(coneTG);
		objRotate.addChild(tableTG);
		
		//Applica una rotazione automatica all'oggetto
		rotate(objRotate,10000);
		
		addChild(objRotate);
		
	}	
	
	
	private void rotate(TransformGroup objRotate, int timer) {
		//Crea un timer
		Alpha rotationAlpha=new Alpha(-1,timer);
		
		//Crea un interpolatore per le rotazioni collegato con il gruppo di trasformazione
		RotationInterpolator rotator=new RotationInterpolator(rotationAlpha,objRotate);
				
		//Imposta un raggio d'azione all'interpolatore
		BoundingSphere bounds=new BoundingSphere();
		rotator.setSchedulingBounds(bounds);
		
		//Aggiunge l'interpolatore alla gruppo di trasformazione
		objRotate.addChild(rotator);
	}
	
}