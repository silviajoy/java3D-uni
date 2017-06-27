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

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class ShowTemple extends Applet{
	public ShowTemple(){
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
		ViewingPlatform vp = simpleU.getViewingPlatform();
		/* three references points */
		setTransform(vp, new Point3d (2, 2, 5), new Point3d(0,0,0), new Vector3d(0,1,0));
		
		
		simpleU.addBranchGraph(scene);
	}
	
	public BranchGroup createSceneGraph() {
		BranchGroup objRoot = new BranchGroup();
		TransformGroup TG = new TransformGroup();
		Transform3D t3d = new Transform3D();
		t3d.setTranslation(new Vector3d(0,-.3,0));
		TG.setTransform(t3d);
		Temple temple = new Temple();
		
		TG.addChild(temple);
		//objRoot.addChild(TG);

		TG = new TransformGroup();
		//objRoot.addChild(TG);
		objRoot.addChild(new PyramidCut(.3,1.7,1.4));
		return objRoot;
	}
	
	public void setTransform(ViewingPlatform vp, Point3d p1, Point3d p2, Vector3d vector) {
		TransformGroup vtg = vp.getViewPlatformTransform ( ) ;
		Transform3D t3d2 = new Transform3D();
		vtg.getTransform(t3d2);
		
		t3d2.lookAt(p1,p2,vector);
		t3d2.invert( ) ;
		vtg.setTransform(t3d2);

	}
	
	public static void main(String[] args){
		new MainFrame(new ShowTemple(), 1024, 768);
	}
}