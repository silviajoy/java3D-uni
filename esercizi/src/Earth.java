
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.QuadArray;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.*;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;



public class Earth extends Applet {
	
	public Earth() {

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
		viewTransform.lookAt(new Point3d(1.0, 1.0, distance+5), // definisce quanti punti di fuga
				new Point3d(0.0, 0.0, 0.0), //definisce centro della visuale
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



		//creazione del sottografo principale
		BranchGroup scene = createSceneGraph();

		simpleU.addBranchGraph(scene);
			}
			
			public BranchGroup createSceneGraph() {
			    //Crea la radice del branch graph
		        BranchGroup objRoot=new BranchGroup();
		    
		        //Texture per simulare la Terra
		        Appearance appearance = new Appearance ( ) ;
		        //Caricamento della texture da file.
			    TextureLoader textureLoader = new TextureLoader("./textures/earth.jpg", null);
			    // Inizializzazione dell ’oggetto Texture.
			    Texture texture = textureLoader.getTexture();
			  
			    TextureAttributes texAttr = new TextureAttributes();
		        texAttr.setTextureMode(TextureAttributes.MODULATE);
			  
			    // Impostazione dell’aspetto .			
		  	    Material m = new Material();
			    appearance.setMaterial(m);
			  
			    appearance.setTexture(texture);
			    appearance.setTextureAttributes(texAttr);
			    // Creazione di una primitiva completa di
			    // coordinate per la texture.
			    int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
			    Sphere earth = new Sphere( 1.0f, primflags, 40, appearance);	  
			    
			    // Imposto il background
			    objRoot.addChild(getBackground("./textures/stars.jpg"));
			    
			    
			    TransformGroup TG = new TransformGroup();
				// Aggiungo la luce diretta per simulare il sole
			    directLight(TG);
				
				TG.addChild(earth);
				objRoot.addChild(TG);
			    
				return objRoot;

			   
			  }
			
			private Background getBackground(String path) {
				//Inizializza la texture per lo sfondo
				TextureLoader myLoader = new TextureLoader( path , this );
			    ImageComponent2D myImage = myLoader.getImage( );
			    //Inizializza il Background
			    Background myBack = new Background( );

			    //Applica la texture sullo sfondo
			    myBack.setImage( myImage );
			    //Deve coprire tutto lo sfondo
			    myBack.setImageScaleMode(Background.SCALE_FIT_MAX);
			    //Fino a che limiti si deve estendere
			    BoundingSphere myBounds = new BoundingSphere(new Point3d( ), 1000.0 );
			    myBack.setApplicationBounds( myBounds );
			    
			    return myBack;
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
			     lightP1.setColor(green);
				 lightP1.setInfluencingBounds(bounds);
				 node.addChild(lightP1); // aggiunta della light al BranchGroup
				}
			
			private void directLight(TransformGroup node) {
				// creazione del bound
				BoundingSphere bounds = new BoundingSphere(new
				Point3d(0d,0.0d,0d),.1d);
				// creazione di una luce direzionale
				DirectionalLight lightD1 = new DirectionalLight();
				// impostazione del bound
				lightD1.setInfluencingBounds(bounds);
				lightD1.setDirection(-1f, -1f, 0f);
				// aggiunta al TransformGroup
				node.addChild(lightD1);
				
			}	
			
			

			public static void main(String[] args){

				new MainFrame(new Earth(), 1024, 768);
			}
		}
