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



public class ReferencePoints extends Applet {
	
	public ReferencePoints() {
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

		ViewingPlatform vp = simpleU.getViewingPlatform();
		/* one reference point */
		setTransform(vp, new Point3d (0, 0, 2), new Point3d(0,0,0), new Vector3d(0,1,0));
		/* two references points */
		//setTransform(vp, new Point3d (1, 0, 2 ), new Point3d(0,0,0), new Vector3d(0,1,0));
		/* three references points */
		//setTransform(vp, new Point3d (1, 1, 2), new Point3d(0,0,0), new Vector3d(0,1,0));
		
		
		

	}
		
	public BranchGroup createSceneGraph() {
		BranchGroup BG = new BranchGroup();
		TransformGroup transform = new TransformGroup(); // crea oggetto TG		
		Transform3D t3d = new Transform3D();
		t3d.setTranslation(new Vector3d(.6d, 0, 0));
		transform.setTransform(t3d);
		transform.addChild(new ColorCube(0.1)); //aggiungo al TG come figlio il cubo
		BG.addChild(transform); //aggiunge l’oggetto TG come figlio del BrachGruop
		return BG;
		}
	
	public void setTransform(ViewingPlatform vp, Point3d p1, Point3d p2, Vector3d vector) {
		TransformGroup vtg = vp.getViewPlatformTransform ( ) ;
		Transform3D t3d2 = new Transform3D();
		vtg.getTransform(t3d2);
		
		t3d2.lookAt(p1,p2,vector);
		t3d2.invert( ) ;
		vtg.setTransform(t3d2);

	}
	
	public static void main(String[] args) {
		new MainFrame(new ReferencePoints(), 1024, 768);
	}

}