import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class ShowPaestum extends Applet {
	public ShowPaestum() {
		setLayout(new BorderLayout()); // layout manager del container
		// trova la miglior configurazione grafica per il sistema
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();
		// Canvas3D: si occupa del rendering 3D on-screen e off-screen
		Canvas3D canvas3D = new Canvas3D(config);
		add("Center", canvas3D);
		BranchGroup scene = createSceneGraph();
		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
		simpleU.getViewingPlatform().setNominalViewingTransform();

		ViewingPlatform vp = simpleU.getViewingPlatform();
		TransformGroup vtg = vp.getViewPlatformTransform();
		Transform3D t3d2 = new Transform3D();
		vtg.getTransform(t3d2);

		/* three reference points */
		t3d2.lookAt(new Point3d(3, 4, 12), new Point3d(0, .4, 0),
				new Vector3d(0, 1, 0));
		t3d2.invert();
		vtg.setTransform(t3d2);

		// Creo un behavior per la navigazione da tastiera
		KeyNavigatorBehavior keyNavBeh = new KeyNavigatorBehavior(vtg);
		// Imposto il bound del behavior
		keyNavBeh.setSchedulingBounds(new BoundingSphere(new Point3d(), 10000.0));
		// Aggiungo il behavior alla scena
		scene.addChild(keyNavBeh);
		// creazione del sottografo principale
		scene.compile();
		simpleU.addBranchGraph(scene);

	}

	public BranchGroup createSceneGraph() {
		BranchGroup objRoot = new BranchGroup();
		TransformGroup TG = new TransformGroup();
		Transform3D t3d = new Transform3D();
		t3d.setScale(2);
		TG.setTransform(t3d);
		Appearance app = new Appearance();
		// create temple to show
		Paestum paestum =  new Paestum(app);
		TG.addChild(paestum);
		
		/* Gruppo di trasformazione per le luci */
		TransformGroup TG1 = new TransformGroup();
		ambientLight(TG1);
		directLight(TG1);
		objRoot.addChild(TG1);

		/* Gruppo di trasformazione per le trasformazioni affini */
		TransformGroup objRotate = new TransformGroup();
		objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		// Aggiunge al gruppo il gruppo di trasformazione del tempio
		objRotate.addChild(TG);
		// Crea il behavior per ruotarlo
		MouseRotate myMouseRotate = new MouseRotate(objRotate);
		// Imposta un raggio d'azione del behavior
		myMouseRotate.setSchedulingBounds(new BoundingSphere());
		// assembla la scena
		objRoot.addChild(myMouseRotate);
		objRoot.addChild(objRotate);

		return objRoot;

	}

	private void ambientLight(TransformGroup node) {
		/*
		 * reazione del bound definisce lo spazio dell'illuminazione mi dice
		 * quali sono gli oggetti che posso illuminare
		 */
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.d, 0.d, 0.d),
				10.d);
		// creazione di una sorgente di luce
		AmbientLight lightP1 = new AmbientLight();
		Color3f green = new Color3f(0.0f, 1.0f, 0.0f);
		// lightP1.setColor(green);
		lightP1.setInfluencingBounds(bounds);
		node.addChild(lightP1); // aggiunta della light al BranchGroup
	}

	private void directLight(TransformGroup node) {
		// creazione del bound
		BoundingSphere bounds = new BoundingSphere(new Point3d(0d, 0.0d, 0d),
				10.d);
		// creazione di una luce direzionale
		DirectionalLight lightD1 = new DirectionalLight();
		// impostazione del bound
		lightD1.setInfluencingBounds(bounds);
		Color3f green = new Color3f(.7f, .7f, .1f);
		//lightD1.setColor(green);
		lightD1.setDirection(-1f, -1f, -1f);
		// aggiunta al TransformGroup
		node.addChild(lightD1);

	}

	public static void main(String[] args) {
		new MainFrame(new ShowPaestum(), 1024, 768);
	}
}