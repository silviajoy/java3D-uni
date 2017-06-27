import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Group;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.QuadArray;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;
import javax.vecmath.Vector3d;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;



public class AnimationEarth extends Applet{

	public AnimationEarth() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas3D = new Canvas3D(config);
		add("Center", canvas3D);
		BranchGroup scene = createSceneGraph();
		scene.compile();

		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
		simpleU.getViewingPlatform().setNominalViewingTransform();
		simpleU.addBranchGraph(scene);
	}


	//Crea la scena
	private BranchGroup createSceneGraph() {

		//Crea la radice del branch graph
		
		BranchGroup objRoot=new BranchGroup();

		//texture per simulare la Terra
		Appearance appearance = new Appearance ( ) ;
		//Caricamento della texture da file.
		TextureLoader textureLoader = new TextureLoader("./textures/earth.jpg", null);
		// Inizializzazione dell ’oggetto Texture.
		Texture texture = textureLoader.getTexture();
		// Impostazione dell’aspetto .
		appearance.setTexture(texture);
		// Creazione di una primitiva completa di
		// coordinate per la texture.
		Sphere earth = new Sphere( .5f, Primitive.GENERATE_TEXTURE_COORDS, 40, appearance);

		// Gruppo di trasformazione per la luna
		Transform3D t3dmoon = new Transform3D();
		t3dmoon.setTranslation(new Vector3d(-.7,0,0));
		TransformGroup TGmoon = new TransformGroup(t3dmoon);


		//texture per fare una Luna
		Appearance appMoon = new Appearance ( ) ;
		//Caricamento della texture da file.
		TextureLoader loaderMoon = new TextureLoader("./textures/moon.jpg", null);
		// Inizializzazione dell ’oggetto Texture.
		Texture texMoon = loaderMoon.getTexture();
		// Impostazione dell’aspetto .
		appMoon.setTexture(texMoon);
		// Creazione di una primitiva completa di
		// coordinate per la texture.
		Sphere moon = new Sphere( .1f, Primitive.GENERATE_TEXTURE_COORDS, 40, appMoon);

		TGmoon.addChild(moon);

		// Imposto il background
		objRoot.addChild(getBackground("./textures/stars.jpg"));    
		
		// Imposto le rotazioni
		objRoot.addChild(setRotatorInterpolator(TGmoon, -1, 30000));
		objRoot.addChild(setRotatorInterpolator(earth, -1, 1000));

		return objRoot;
	}

	private Background getBackground(String path) {
		//Inizializza la texture per lo sfondo
		TextureLoader myLoader = new TextureLoader( path , this );
		ImageComponent2D myImage = myLoader.getImage( );
		//Inizializza il Background
		Background myBack = new Background( );

		//Applica la texture sullo sfondo
		myBack.setImage( myImage );
		//Deve coprire tutto lo sfondo
		myBack.setImageScaleMode(Background.SCALE_FIT_MAX);
		//Fino a che limiti si deve estendere
		BoundingSphere myBounds = new BoundingSphere(new Point3d( ), 1000.0 );
		myBack.setApplicationBounds( myBounds );

		return myBack;
	}

	private TransformGroup setRotatorInterpolator(Group node, int loopCount, long timerInterval ) {
		//Crea un gruppo per le trasformazioni affini della terra
		TransformGroup objRotate=new TransformGroup();
		objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		//Aggiunge al gruppo un 
		objRotate.addChild(node);

		//Crea un timer per la rotazione della terra
		Alpha rotationAlpha=new Alpha(loopCount,timerInterval);
		//Crea un interpolatore per le rotazioni collegato con il gruppo di trasformazione
		RotationInterpolator rotator=new RotationInterpolator(rotationAlpha,objRotate);
		//Imposta un raggio d'azione all'interpolatore
		BoundingSphere bounds=new BoundingSphere();
		rotator.setSchedulingBounds(bounds);
		//aggiunge l'interpolatore alla gruppo di trasformazione
		objRotate.addChild(rotator);

		return objRotate;
	}

	public static void main(String[] args) {
		new MainFrame(new AnimationEarth(), 1024, 768);
	}

}
