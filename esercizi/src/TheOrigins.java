import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class TheOrigins extends Applet {
	
	public TheOrigins() {
		setLayout(new BorderLayout());
		GraphicsConfiguration conf = SimpleUniverse.getPreferredConfiguration();
		Canvas3D c3d = new Canvas3D(conf);
		add("Center", c3d);
		BranchGroup scene = new BranchGroup();
		TransformGroup tg = new TransformGroup();
		Transform3D t3d = new Transform3D();
		t3d.setScale(new Vector3d(1d, 1d, -1.5d));
		tg.setTransform(t3d);
		ColorCube cube = new ColorCube(0.1);
		tg.addChild(cube);
		scene.addChild(tg);
		
		scene.compile();
				
		SimpleUniverse universe = new SimpleUniverse(c3d);		
		universe.getViewingPlatform().setNominalViewingTransform();
		
		universe.addBranchGraph(scene);
		
		
	}
	
	
	public static void main(String[] args) {
		TheOrigins test = new TheOrigins();
		new MainFrame(test, 1024, 768);
	}
	
	
	
}