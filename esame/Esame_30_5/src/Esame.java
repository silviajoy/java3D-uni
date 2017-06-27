import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class Esame extends Applet{
	/* TEXTURES PATH */
	static String bgTexture = "./textures/museo2.jpg";
	
	public Esame(){
		//layout manager del container
		setLayout(new BorderLayout()); 
		
		//trova la miglior configurazione grafica per il sistema
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		
		// Canvas3D: si occupa del rendering 3D on-screen e off-screen
		Canvas3D canvas3D = new Canvas3D(config);
		add("Center", canvas3D);
				
		//Creazione del SimpleUniverse
		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
		ViewingPlatform vp = simpleU.getViewingPlatform();

		// creazione del sottografo principale
		BranchGroup scene = createSceneGraph();
		
		//Crea il sottografo per la vista
		createViewBranch(vp, scene);

		scene.compile();
		
		simpleU.addBranchGraph(scene);
	}
	
	
	private void createViewBranch(ViewingPlatform vp, BranchGroup scene) {
		//Inizializza i gruppi per le trasformazioni
		TransformGroup vtg = vp.getViewPlatformTransform();
		Transform3D t3d = new Transform3D();
		//Inserisce in t3d le Trasformazioni attuali
		vtg.getTransform(t3d);
		
		// Creo un behavior per la navigazione da tastiera
		KeyNavigatorBehavior keyNavBeh = new KeyNavigatorBehavior(vtg);
		// Imposto il bound del behavior
		keyNavBeh.setSchedulingBounds(new BoundingSphere(new Point3d(), 10000.0));
		// Aggiungo il behavior alla scena
		scene.addChild(keyNavBeh);
		//Esegui la trasformazione
		t3d.lookAt(new Point3d(0, 0, 3), new Point3d(0, 0, 0), new Vector3d(0, 1, 0));
		t3d.invert();
		
		//Applica le trasformazioni
		vtg.setTransform(t3d);
	}
	
	
	private	 BranchGroup createSceneGraph() {
		//Inizializza le componenti
		BranchGroup objRoot = new BranchGroup();
		TransformGroup TGtable = new TransformGroup();
		TransformGroup TG = new TransformGroup();
	
		//Inizializza il gestore delle trasformazioni
		Transform3D t3dTable = new Transform3D();
		Transform3D t3d = new Transform3D();
		
		t3dTable.setTranslation(new Vector3d(0, -.22, 0));
		t3d.setTranslation(new Vector3d(0,-.2, 0));
		
		//Applica la trasformazione
		TGtable.setTransform(t3dTable);
		TG.setTransform(t3d);
	
		//Inizializza l'oggetto in primo piano
		Radio radio = new Radio(); 
		Tavolo table = new Tavolo(.1f, .6f);
		
		TGtable.addChild(table);

		//Crea un gruppo per il controllo tramite il mouse
		TransformGroup objRotate=new TransformGroup();
		objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		//Aggiunge al gruppo il nostro oggetto
		objRotate.addChild(radio);
		objRotate.addChild(TGtable);

		//Crea il behavior per ruotare il nostro oggetto
		MouseRotate myMouseRotate=new MouseRotate(objRotate);
		
		//Imposta un raggio d'azione del behavior
		myMouseRotate.setSchedulingBounds(new BoundingSphere());
		
		//Assembla la scena
		objRoot.addChild(myMouseRotate);
		TG.addChild(objRotate);
		objRoot.addChild(TG);
		
		//Crea un gruppo per l'illuminazione
		TransformGroup TGLights = new TransformGroup();
		
		//Applica le luci
		ambientLight(TGLights);
		directLight(TGLights);
		
		//Assembla la scena
		objRoot.addChild(TGLights);		
		
		//Crea lo sfondo
		Background back = setBackImage(bgTexture); 
		
		//Assembla la scena
		objRoot.addChild(back);
		
		
		return objRoot;
	}

	
	private void ambientLight(TransformGroup node){
		 //Inizializza i confini della luce (fino a dove illumina)
		 BoundingSphere bounds = new BoundingSphere(new Point3d(0.d,0.d,0.d),10.d);
		 
		 //Crea una sorgente di luce
		 AmbientLight lightP1 = new AmbientLight();
		 
		 //Applica i confini della luce
		 lightP1.setInfluencingBounds(bounds);
		 
		 //Assembla la scena
		 node.addChild(lightP1);
		 
		}
	
	private void directLight(TransformGroup node) {
		//Inizializza i confini della luce (fino a dove illumina)
		BoundingSphere bounds = new BoundingSphere(new Point3d(0d,0.0d,0d),3.d);
		
		//Crea una luce direzionale
		DirectionalLight lightD1 = new DirectionalLight();
		
		//Applica i confini del bound
		lightD1.setInfluencingBounds(bounds);
		
		//Iposta la direzione della luce
		lightD1.setDirection(0.5f, 1f, -1f);
		
		//Assembla la scena
		node.addChild(lightD1);
		
	}	

	
	private Background setBackImage(String path) {
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
	
	
	public static void main(String[] args){
		new MainFrame(new Esame(), 1024, 768);
	}
}