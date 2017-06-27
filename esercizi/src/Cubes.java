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



public class Cubes extends Applet{
	
	public Cubes() {
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
		TransformGroup TG;
		double dimCube = 0.04;
		int numCubes = 8;
		Transform3D t3d;
		Matrix3d transformMatrix;
		Matrix3d axisVector = new Matrix3d(0,0,0,0,0,0,1,0,0);
		Vector3d translation = new Vector3d();
		
		for(double i=0; i<2*Math.PI; i= i+Math.PI/(numCubes/2)) {
			TG = new TransformGroup();
			t3d = new Transform3D();
			
			transformMatrix = new Matrix3d(1, 0, Math.cos(i)/4, 0, 1, Math.sin(i)/4, 0, 0, 1);
			
			transformMatrix.mul(axisVector);
			transformMatrix.getColumn(0, translation);
			
			t3d.setTranslation(translation);
			TG.setTransform(t3d);
			
			TG.addChild(new ColorCube(dimCube));
			node.addChild(TG);
		}
	
		return node;
		
	}
	

	
	
	public static void main(String[] args) {
		new MainFrame(new Cubes(), 1024, 768);
	}

}
