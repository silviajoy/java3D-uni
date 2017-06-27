import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
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

public class Solids extends Applet{
	public Solids(){
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas3D = new Canvas3D(config);
		add("Center", canvas3D);

		//creazione del SimpleUniverse
		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

		//accedo all'oggetto view del SimpleUniverse
		View myView = simpleU.getViewer().getView();

		double fieldOfView = myView.getFieldOfView(); // 0.25∗Math.PI
		// Trasformazione applicata alla ViewPlarform
		Transform3D viewTransform = new Transform3D ( ) ;
		double distance = 1.0/Math.tan(fieldOfView/2.0);
		viewTransform.lookAt(new Point3d(1.5, 1.0, distance+5), // definisce quanti punti di fuga
				new Point3d(0.0, 0.0, 0.0), //definisce centro visuaale
				new Vector3d(0.0, 1.0, 0.0)); //su rispetto a dove guardo
		viewTransform.invert();    
		//Attivazione della trasformazione appena ricavata
		ViewingPlatform vp = simpleU.getViewingPlatform();
		TransformGroup vtg = vp.getViewPlatformTransform () ;
		vtg.setTransform (viewTransform);

		//Impostazione della distanza dal piano sullo sfondo
		myView.setBackClipDistance(10.0);
		//Impostazione del clip dal piano frontale
		myView.setFrontClipDistance(0.1);
		//Impostazione del campo visivo
		myView.setFieldOfView(Math.PI/4);
		//Impostazione del tipo di proiezione
		myView.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
		//myView.setProjectionPolicy(View.PARALLEL_PROJECTION);

		//comportamento predefinito di rotazione legata agli eventi del mouse
		MouseRotate rotateBehavior = new MouseRotate();
		//legame fra il comportamento e il TransformGroup
		rotateBehavior.setTransformGroup(vtg);
		//zona in cui tenere conto degli eventi.sfera di raggio 1 e centro in 0
		rotateBehavior.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1.0));

		//creazione del sottografo principale
		BranchGroup scene = createSceneGraph();
		scene.addChild(rotateBehavior);
		simpleU.addBranchGraph(scene);
	}
	
	public BranchGroup createSceneGraph() {
		BranchGroup objRoot = new BranchGroup();
		
		TransformGroup TG;
		Transform3D t3d;
		
		double height = .1;
		double trans = 0;
		
		for(double i=2; i>.6; i-=.2) {
			TG = new TransformGroup();
			t3d = new Transform3D();
			
			t3d.setTranslation(new Vector3d(0,trans,0));
			TG.setTransform(t3d);
			
			TG.addChild(new PyramidCut(height,i,i-.1));
			objRoot.addChild(TG);
			
			trans += height;
						
		}
		
		for(double i=0; i<2; i++) {
			TG = new TransformGroup();
			t3d = new Transform3D();
			
			t3d.setTranslation(new Vector3d(0,trans,0));
			TG.setTransform(t3d);
			
			TG.addChild(new PyramidCut(height,.4,.4));
			objRoot.addChild(TG);
			
			trans += height;
		}
		
		TG = new TransformGroup();
		ambientLight(TG);
		//objRoot.addChild(new PyramidCut(.2,2,1.8));
		return objRoot;
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
	     //lightP1.setColor(green);
		 lightP1.setInfluencingBounds(bounds);
		 node.addChild(lightP1); // aggiunta della light al BranchGroup
		}
	
	private void directLight(TransformGroup node) {
		// creazione del bound
		BoundingSphere bounds = new BoundingSphere(new
		Point3d(0d,0.0d,0d),.05d);
		// creazione di una luce direzionale
		DirectionalLight lightD1 = new DirectionalLight();
		// impostazione del bound
		lightD1.setInfluencingBounds(bounds);
		Color3f green = new Color3f(1f, 1f, 1f);
	    lightD1.setColor(green);
		lightD1.setDirection(10, 10f, -10f);
		// aggiunta al TransformGroup
		node.addChild(lightD1);
		
	}	
	
	public static void main(String[] args){
		new MainFrame(new Solids(), 1024, 768);
	}
}