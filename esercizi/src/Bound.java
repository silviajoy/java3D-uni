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



public class Bound extends Applet {
	
	public Bound() {
		setLayout(new BorderLayout()); //layout manager del container
		//trova la miglior configurazione grafica per il sistema
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		// Canvas3: si occupa del rendering 3D on-screen e off-screen
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
		BranchGroup BG = new BranchGroup();
		TransformGroup transform = new TransformGroup(); // crea oggetto TG		
		/*Transform3D t3d = new Transform3D();
		t3d.setTranslation(new Vector3d(0,0,10));
		Transform3D t3d2 = new Transform3D();
		t3d2.rotY(Math.PI/4);
		//t3d2.mul(t3d);
		transform.setTransform(t3d2);*/
		Material material = new Material();
		Appearance app = new Appearance();
		app.setMaterial(material);
		transform.addChild(new Sphere(0.1f, Primitive.GENERATE_NORMALS, app)); //aggiungo al TG come figlio il cubo
		ambientLight(transform);
		directLight(transform);
		BG.addChild(transform); //aggiunge l’oggetto TG come figlio del BrachGroup
		
		return BG;
		}
	
	private void ambientLight(TransformGroup node){
		/*reazione del bound definisce lo spazio dell'illuminazione mi
		 dice quali sono gli
		 oggetti che posso illuminare*/
		 BoundingSphere bounds = new BoundingSphere(
		 new Point3d(0.d,0.d,0.d),10.d);
		 // creazione di una sorgente di luce
		 AmbientLight lightP1 = new AmbientLight();
		 Color3f green = new Color3f(0.0f, 1.0f, 0.0f);
	     lightP1.setColor(green);
		 lightP1.setInfluencingBounds(bounds);
		 node.addChild(lightP1); // aggiunta della light al BranchGroup
		}
	
	private void directLight(TransformGroup node) {
		// creazione del bound
		BoundingSphere bounds = new BoundingSphere(new
		Point3d(0.0d,0.0d,0.0d),50.0d);
		// creazione di una luce direzionale
		DirectionalLight lightD1 = new DirectionalLight();
		// impostazione del bound
		lightD1.setInfluencingBounds(bounds);
		lightD1.setDirection(10f, 10f, 10f);
		// aggiunta al TransformGroup
		node.addChild(lightD1);
		
	}

	public static void main(String[] args) {
		new MainFrame(new Bound(), 1024, 768);
	}

}