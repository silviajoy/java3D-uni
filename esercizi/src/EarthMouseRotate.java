
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



public class EarthMouseRotate extends Applet {
	
	public EarthMouseRotate() {

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



		//creazione del sottografo principale
		BranchGroup scene = createSceneGraph();

		simpleU.addBranchGraph(scene);
			}
			
			public BranchGroup createSceneGraph() {
				//Crea la radice del branch graph
			    BranchGroup objRoot=new BranchGroup();

			    //Crea un piano
			    QuadArray plane=new QuadArray(4,GeometryArray.COORDINATES|GeometryArray.TEXTURE_COORDINATE_2);
			    Point3f p=new Point3f(-1.0f,1.0f,0.0f);
			    plane.setCoordinate(0,p);
			    p.set(-1.0f,-1.0f,0.0f);
			    plane.setCoordinate(1,p);
			    p.set(1.0f,-1.0f,0.0f);
			    plane.setCoordinate(2,p);
			    p.set(1.0f,1.0f,0.0f);
			    plane.setCoordinate(3,p);

			    //Imposta le coordinate della texture
			    TexCoord2f q=new TexCoord2f(0f,2f);
			    plane.setTextureCoordinate(0,0,q);
			    q.set(0f,0f);
			    plane.setTextureCoordinate(0,1,q);
			    q.set(2f,0f);
			    plane.setTextureCoordinate(0,2,q);
			    q.set(2f,2f);
			    plane.setTextureCoordinate(0,3,q);
			    
			    //texture per fare una Terra
			    Appearance appearance = new Appearance ( ) ;
			  //Caricamento della texture da file.
			  TextureLoader textureLoader = new TextureLoader("/home/silvia/Desktop/uni/immagini e multimedialit/java3d/textures/earth.jpg", null);
			  // Inizializzazione dell ’oggetto Texture.
			  Texture texture = textureLoader.getTexture();
			  // Impostazione dell’aspetto .
			  appearance.setTexture(texture);
			  // Creazione di una primitiva completa di
			  // coordinate per la texture.
			  Sphere earth = new Sphere( 1.0f, Primitive.GENERATE_TEXTURE_COORDS, appearance);

			  /*
			    //Crea l'aspetto del piano
			    Appearance appear=new Appearance();

			    //Carica la texture
			    TextureLoader loader=new TextureLoader("/home/silvia/Desktop/uni/immagini e multimedialit/java3d/textures/stripe.gif",this);
			    
			    //Texture texture= loader.getTexture();
			    
			    ImageComponent2D image=loader.getImage();

			    //Crea la texture
			    Texture2D texture=new Texture2D(Texture.BASE_LEVEL,Texture.RGBA,image.getWidth(),image.getHeight());
			    texture.setImage(0,image);
			    
			    //Imposta il comportamento ai bordi
			    texture.setBoundaryModeS(Texture.WRAP);//orizzontale
			    texture.setBoundaryModeT(Texture.CLAMP); //verticale

			    
			    TextureAttributes	textureAttributes	= new	TextureAttributes ( ) ;
			 // Impostazioni per fondere il colore dell’oggetto con la texture.
			 textureAttributes.setTextureMode( TextureAttributes .COMBINE) ; textureAttributes.setCombineRgbSource(0,
			                                TextureAttributes.COMBINE_TEXTURE_COLOR); textureAttributes.setCombineRgbSource(1,
			                                TextureAttributes.COMBINE_OBJECT_COLOR);
			 //Correzione prospettica .
			 textureAttributes.setPerspectiveCorrectionMode(TextureAttributes.NICEST); 
			 

			    
			    //Imposta la texture nell'aspetto
			    appear.setTexture(texture);
			    appear.setTextureAttributes(textureAttributes) ;
			    
			  appear.setColoringAttributes(new ColoringAttributes(255,0,0,ColoringAttributes.SHADE_FLAT));

			    //Crea l'oggetto
			    Shape3D planeObj=new Shape3D(plane,appear);
			    */
			  
			    
			    
			    TextureLoader myLoader = new TextureLoader( "/home/silvia/Desktop/uni/immagini e multimedialit/java3d/textures/stars.jpg", this );
			    ImageComponent2D myImage = myLoader.getImage( );
			    Background myBack = new Background( );
			    myBack.setImage( myImage );
			    myBack.setImageScaleMode(Background.SCALE_FIT_MAX);
			    BoundingSphere myBounds = new BoundingSphere(new Point3d( ), 1000.0 );
			    myBack.setApplicationBounds( myBounds );
			    objRoot.addChild(myBack);
			    
				
				//Crea un gruppo per le trasformazioni affini
				TransformGroup objRotate=new TransformGroup();
				objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
				objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
				//Aggiunge al gruppo un cubo colorato
				objRotate.addChild(earth);
				//Crea il behavior per ruotare il cubo
				MouseRotate myMouseRotate=new MouseRotate(objRotate);
				//Imposta un raggio d'azione del behavior
				myMouseRotate.setSchedulingBounds(new BoundingSphere());
				//assembla la scena
				objRoot.addChild(myMouseRotate);
				objRoot.addChild(objRotate);
				return objRoot;

			   
			  }
			

			public static void main(String[] args){

				new MainFrame(new EarthMouseRotate(), 1024, 768);
			}
		}

		



/*




TextureLoader tl=new TextureLoader("a.gif",this);
ImageComponent2D image=tl.getImage();

Texture2D tex=new Texture2D();
texture.setImage(0,image);
Appearance app=new Appearance();
app.setTexture(tex);

Point3f p=new Point3f();
p.set(-1,1,0);
flat.setCoordinate(0,p);
p.set(-1,-1,0); flat.setCoordinate(1,p);
p.set(1,-1,0); flat.setCoordinate(2,p);
p.set(1,1,0); flat.setCoordinate(3,p);

TexCoord2f q=new TexCoord2f();
q.set(0,1); flat.setTextureCoordinate(0,0,q);
q.set(0,0); flat.setTextureCoordinate(0,1,q);
q.set(1,0); flat.setTextureCoordinate(0,2,q);
q.set(1,1); flat.setTextureCoordinate(0,3,q);




//Crea un piano
QuadArray plane=new QuadArray(4,GeometryArray.COORDINATES|
GeometryArray.TEXTURE_COORDINATE_2);
Point3f p=new Point3f(-1.0f,1.0f,0.0f);
plane.setCoordinate(0,p);
p.set(-1.0f,-1.0f,0.0f);
[...]
//Imposta le coordinate della texture
TexCoord2f q=new TexCoord2f(0.0f,1.0f);
plane.setTextureCoordinate(0,0,q);
[...]
//Crea l'aspetto del piano
Appearance appear=new Appearance();
//Carica la texture
TextureLoader loader=new TextureLoader("stripe.gif",this);
ImageComponent2D image=loader.getImage();
//Crea la texture
Texture2D texture=new Texture2D(Texture.BASE_LEVEL,
Texture.RGBA,image.getWidth(),image.getHeight());
texture.setImage(0,image);
appear.setTexture(texture);
[...]*/