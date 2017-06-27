import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;



public class Behavior1 extends Applet{
	
	public Behavior1() {
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
	
	
	//Crea la scena
	private BranchGroup createSceneGraph()
	{
	//Crea la radice del branch graph
	BranchGroup objRoot=new BranchGroup();
	//Crea un gruppo per le trasformazioni affini
	TransformGroup objRotate=new TransformGroup();
	objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	//Aggiunge al gruppo un cubo colorato
	objRotate.addChild(new ColorCube(0.4));
	//Crea il behavior per ruotare il cubo
	MouseRotate myMouseRotate=new MouseRotate(objRotate);
	//Imposta un raggio d'azione del behavior
	myMouseRotate.setSchedulingBounds(new BoundingSphere());
	//assembla la scena
	objRoot.addChild(myMouseRotate);
	objRoot.addChild(objRotate);
	return objRoot;
	}
	
	/*
	//funzione che crea il sottografo
	private BranchGroup createSceneGraph()
	{
	//Crea la radice del branch graph
	BranchGroup objRoot=new BranchGroup();
	//Crea un gruppo per le trasformazioni affini
	TransformGroup objSpin=new TransformGroup();
	//Imposta la capacita' di scrivere la trasformazione
	objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	//Aggiunge al gruppo un cubo colorato
	objSpin.addChild(new ColorCube(0.4));
	//Crea un behavior
	SimpleBehavior_1 rotator=new SimpleBehavior_1(objSpin);
	//Imposta un raggio d'azione del behavior
	BoundingSphere bounds=new BoundingSphere();
	rotator.setSchedulingBounds(bounds);
	//aggiunge l'interpolatore alla gruppo di trasformazione
	objSpin.addChild(rotator);
	//Aggiunge alla radice il gruppo
	objRoot.addChild(objSpin);
	return objRoot;
	}
	*/
	
	
	public static void main(String[] args) {
		new MainFrame(new Behavior1(), 1024, 768);
	}

}
