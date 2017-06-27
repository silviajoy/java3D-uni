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
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class ShowColumn extends Applet{
	public ShowColumn(){
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
		simpleU.getViewingPlatform().setNominalViewingTransform();
		simpleU.addBranchGraph(scene);
	}
	
	public BranchGroup createSceneGraph() {
		BranchGroup objRoot = new BranchGroup();
		TransformGroup TG = new TransformGroup();
		Transform3D t3d = new Transform3D();
		t3d.setTranslation(new Vector3d(0,-.45,0));
		TG.setTransform(t3d);
		
		Appearance app = new Appearance();
		Material m = new Material();
		app.setMaterial(m);
		/* imposto la texture */
		setTexture(app, "./textures/PietraColonna.jpg");
		
		Column column = new Column(.15f,.8f,app); // inizializzo la colonna
				
		TG.addChild(column);
		objRoot.addChild(TG);

		/* illuminazione */
		TG = new TransformGroup();
		ambientLight(TG);
		directLight(TG);
		objRoot.addChild(TG);
		
		return objRoot;
	}

	
	private void ambientLight(TransformGroup node){
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
		Point3d(0d,0.0d,0d),10.d);
		// creazione di una luce direzionale
		DirectionalLight lightD1 = new DirectionalLight();
		// impostazione del bound
		lightD1.setInfluencingBounds(bounds);
		Color3f green = new Color3f(.7f, .7f, .1f);
	    //lightD1.setColor(green);
		lightD1.setDirection(-5f, -2f, -1f);
		// aggiunta al TransformGroup
		node.addChild(lightD1);
		
	}	
	
	public void setTexture(Appearance app, String path) {
		TextureLoader loader = new TextureLoader(path, null);

		Texture texture = loader.getTexture();
		/*comportamento ai bordi */
		texture.setBoundaryModeS(Texture.WRAP);
		texture.setBoundaryModeT(Texture.WRAP);		   

	    TextureAttributes texAttr = new TextureAttributes();
	    texAttr.setTextureMode(TextureAttributes.REPLACE	); // il colore del materiale è modulato con quello della texture 

	    app.setTexture(texture);
	    app.setTextureAttributes(texAttr);
	}
	
	public static void main(String[] args){
		new MainFrame(new ShowColumn(), 1024, 768);
	}
}