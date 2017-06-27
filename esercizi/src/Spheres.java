import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import com.sun.j3d.utils.geometry.*;



public class Spheres extends Applet {
	
	public Spheres() {
		setLayout(new BorderLayout()); //layout manager del container
		//trova la miglior configurazione grafica per il sistema
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		// Canvas3D: si occupa del rendering 3D on-screen e off-screen
		Canvas3D canvas3D = new Canvas3D(config);
		add("Center", canvas3D);
		BranchGroup scene = createSceneGraph();
		// creazione del sottografo principale
		scene.compile();
		//Creazione del SimpleUniverse
		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
		simpleU.getViewingPlatform().setNominalViewingTransform();
		simpleU.addBranchGraph(scene);
		
		
		
		}
		
	public BranchGroup createSceneGraph() {
		/* inizializzo le variabili che mi serviranno */
		BranchGroup BG = new BranchGroup();
		TransformGroup transform;		
		Transform3D t3d;
		Material material = new Material();
		Appearance app = new Appearance();
		app.setMaterial(material);
		
		/* creo la matrice di sfere */
		for(double i=-.4; i<.6; i+=.2) {
			for(double j= -.4; j<.6; j+=.2) {
				transform = new TransformGroup();
				t3d = new Transform3D();
				t3d.setTranslation(new Vector3d(i,j,0));
				transform.setTransform(t3d);
				transform.addChild(new Sphere(0.08f, Primitive.GENERATE_NORMALS, app)); //aggiungo la sfera al TG
				BG.addChild(transform); //aggiunge l’oggetto TG come figlio del BrachGroup		
			}
		}
		
		transform= new TransformGroup();
		ambientLight(transform);
		directLight(transform);
		BG.addChild(transform);
		
		return BG;
	}
	
	private void ambientLight(TransformGroup node){
		/* il bound definisce lo spazio dell'illuminazione */
		 BoundingSphere bounds = new BoundingSphere(
		 new Point3d(0.d,0.d,0.d),10.d); // dall'origine, un raggio di 10d
		 // creazione di una sorgente di luce
		 AmbientLight lightP1 = new AmbientLight();
		 // Color3f green = new Color3f(0.0f, 1.0f, 0.0f);
	     // lightP1.setColor(green); // coloro la luce
		 lightP1.setInfluencingBounds(bounds);
		 node.addChild(lightP1); // aggiunta della light al BranchGroup
	}
	
	private void directLight(TransformGroup node) {
		BoundingSphere bounds = new BoundingSphere(new Point3d(.2d,.0d,0d),.15d); // definizione del bound
		DirectionalLight lightD1 = new DirectionalLight(); //creazione di una luce direzionale
		BoundingSphere bounds2 = new BoundingSphere(new Point3d(-.2d,0d,0d),.15d); // definizione del bound
		DirectionalLight lightD2 = new DirectionalLight(); //creazione di una luce direzionale
		Color3f green = new Color3f(0.0f, 1.0f, 0.0f);
	    lightD1.setColor(green); // coloro la luce
		lightD1.setInfluencingBounds(bounds);
		lightD1.setDirection(10, 10f, -10f); //definizione della direzione
		node.addChild(lightD1); //aggiunta al BG
		lightD2.setInfluencingBounds(bounds2);
		lightD2.setDirection(10, 10f, -10f); //definizione della direzione
		node.addChild(lightD2); //aggiunta al BG
		
	}

	public static void main(String[] args) {
		new MainFrame(new Spheres(), 1024, 768);
	}

}