import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.*;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;



public class Projections extends Applet {
	
	public Projections() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas3D = new Canvas3D(config);
		add("Center", canvas3D);
		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
		TransformGroup viewTG = simpleU.getViewingPlatform().getViewPlatformTransform();
		Transform3D viewTransform = new Transform3D();
		viewTG.getTransform(viewTransform);
		
		ViewingPlatform vp = simpleU.getViewingPlatform();
		setTransform(vp, new Point3d (2, 0, 10 ), new Point3d(0,0,0), new Vector3d(0,1,0)); //due punti di fuga
		
		View myView = simpleU.getViewer().getView(); // referenza alla view
		myView.setCompatibilityModeEnable(true);// posso modificare la matrice di proiezione

		Transform3D proj = new Transform3D();

		// ortografica
		double ratio = 1024.0/768.0;
		//proj.ortho(-2*ratio, 2*ratio, -2.0, 2.0, 5, 10);
		// prospettica
		proj.perspective(Math.PI/4, ratio, 9.7, 10.0);
		
		myView.setLeftProjection(proj);
		
		/* Metodi di view
		myView.setFrontClipDistance(1); // impostazione distanza frontale dal piano
		myView.setBackClipDistance(100.0); // impostazione distanza dietro al piano
					
		myView.setFieldOfView(Math.PI/4.0); // impostazione del campo visivo – solo prospettica 
					
		myView.setProjectionPolicy(View.PARALLEL_PROJECTION); // ortografica
		myView.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);// prospettica
		 */

		BranchGroup scene = createSceneGraph();
		scene.compile();
		simpleU.addBranchGraph(scene);

	}
		
	public BranchGroup createSceneGraph() {
		BranchGroup node = new BranchGroup();
		TransformGroup TG = new TransformGroup();;
		double dimCube = 0.5;

		TG.addChild(new ColorCube(dimCube));
		node.addChild(TG);
			
		return node;
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
		new MainFrame(new Projections(), 1024, 768);
	}

}