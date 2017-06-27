import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;



public class HelloViewBranch extends Applet {
	
	public HelloViewBranch() {
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
		

		/*Transform3D t3d = new Transform3D();
		t3d.setTranslation(new Vector3d(0,0,10));
		Transform3D t3d2 = new Transform3D();
		t0.03d2.rotY(Math.PI/4);
		t3d2.invert();
		t3d2.mul(t3d);
		/*TransformGroup vtg = simpleU.getViewingPlatform().getViewPlatformTransform();
		vtg.setTransform(t3d2);*/
		
		ViewingPlatform vp = simpleU.getViewingPlatform();
		TransformGroup vtg = vp.getViewPlatformTransform ( ) ;
		Transform3D t3d2 = new Transform3D();
		vtg.getTransform(t3d2);
		System.out.println(t3d2);
		
		/* one reference point */
		t3d2.lookAt(new Point3d (0, 1, 2),
				new Point3d(0,0,0),
				new Vector3d(0,1,0));
		t3d2.invert( ) ;
		vtg.setTransform(t3d2);
		
		/* two reference points */
		t3d2.lookAt(new Point3d (1, 1, 2),
				new Point3d(0,0,0),
				new Vector3d(0,1,0));
		t3d2.invert( ) ;
		vtg.setTransform(t3d2);
		
		/* three reference points */
		t3d2.lookAt(new Point3d (1, 2, 1),
				new Point3d(0,-0.5,0),
				new Vector3d(0,1,0));
		t3d2.invert( ) ;
		vtg.setTransform(t3d2);
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
		transform.addChild(new ColorCube(0.3)); //aggiungo al TG come figlio il cubo
		BG.addChild(transform); //aggiunge l’oggetto TG come figlio del BrachGruop
		return BG;
		}

}