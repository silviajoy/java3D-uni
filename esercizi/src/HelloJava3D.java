import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;



public class HelloJava3D extends Applet{
	
	public HelloJava3D() {
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
	
	//funzione che crea lo scene graph
	public BranchGroup createSceneGraph() {
		BranchGroup node = new BranchGroup();
		TransformGroup TG = new TransformGroup();;
		double dimCube = 0.1;
		Transform3D t3d= new Transform3D();			
		t3d.setTranslation(new Vector3d(-0.5,0,0));
		TG.setTransform(t3d);
		
		TG.addChild(new ColorCube(dimCube));
		node.addChild(TG);
			
		return node;
		
	}
	

	
	
	public static void main(String[] args) {
		new MainFrame(new HelloJava3D(), 1024, 768);
	}

}
