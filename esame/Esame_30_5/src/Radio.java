import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Group;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;



public class Radio extends Group {
	
	protected Transform3D[] t3dwood = new Transform3D[5];
	protected Transform3D t3dfront = new Transform3D();
	
	protected TransformGroup[] TGwood = new TransformGroup[5];
	protected TransformGroup TGfront = new TransformGroup();
	
	protected Appearance appWood = new Appearance();
	protected Appearance appFront = new Appearance();
	
	protected static int PRIMFLAGS = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
	
	/* TEXTURES PATH */
	protected static String wood = "./textures/legno.jpg";
	protected static String radio = "./textures/radio.jpg";
	
	protected Box[] wooden = new Box[5];
	protected Box front;
	
	
	public Radio() {
		float width = .02f;
		/* inizializzazione delle parti */
		for(int i=0; i<2; i++) {
			int mul = 1-2*i;
			
			/* lati */
			wooden[i] = new Box(width,.1f, .1f, PRIMFLAGS, appWood );
			t3dwood[i] = new Transform3D();
			t3dwood[i].setTranslation(new Vector3f(mul*.13f, 0f, 0f));
			TGwood[i] = new TransformGroup(t3dwood[i]);
			TGwood[i].addChild(wooden[i]);
			
			/* sopra e sotto */
			wooden[i+2] = new Box(.15f,width, .1f, PRIMFLAGS, appWood );
			t3dwood[i+2] = new Transform3D();
			t3dwood[i+2].setTranslation(new Vector3f(0f, mul*.09f, 0f));
			TGwood[i+2] = new TransformGroup(t3dwood[i+2]);
			TGwood[i+2].addChild(wooden[i+2]);
			
		}
		
		/* retro */
		wooden[4] = new Box(.15f,.11f, width, PRIMFLAGS, appWood );
		t3dwood[4] = new Transform3D();
		t3dwood[4].setTranslation(new Vector3f(0f,0f, -.09f));
		TGwood[4] = new TransformGroup(t3dwood[4]);
		TGwood[4].addChild(wooden[4]);
		
		/* fronte */
		front = new Box(.13f,.08f, width, PRIMFLAGS, appFront );
		t3dfront = new Transform3D();
		t3dfront.setTranslation(new Vector3f(0f,0f, .07f));
		TGfront = new TransformGroup(t3dfront);
		TGfront.addChild(front);
		
		Material m = new Material();
		
		/* imposto la texture per le pareti di legno */
		// Texture
		TextureLoader loader = new TextureLoader(wood, null);
		//Carica la texture
	   	Texture textureW = loader.getTexture();
	   	//Imposta il comportamento ai bordi
	   	textureW.setBoundaryModeS(Texture.WRAP);
	   	textureW.setBoundaryModeT(Texture.WRAP);
	   	//Imposta i colori ai bordi
	   	textureW.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );		   

	    // Imposta gli attributi della texture
	    TextureAttributes texAttr = new TextureAttributes();
	   	texAttr.setTextureMode(TextureAttributes.MODULATE);
	   	appWood.setTextureAttributes(texAttr);
		appWood.setTexture(textureW);
		appWood.setMaterial(m);
		
		/* imposto la texture per la radio */
		// Texture
		TextureLoader loaderR = new TextureLoader(radio, null);
		//Carica la texture
	   	Texture textureR = loaderR.getTexture();
	   	//Imposta il comportamento ai bordi
	   	textureR.setBoundaryModeS(Texture.WRAP);
	   	textureR.setBoundaryModeT(Texture.WRAP);
	   	//Imposta i colori ai bordi
	   	textureR.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );		   
	   	
	   	appFront.setTextureAttributes(texAttr);
		appFront.setTexture(textureR);
		appFront.setMaterial(m);
		
		/* gruppo per la rotazione */
		TransformGroup objRotate=new TransformGroup();
		objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		/* aggiungo al gruppo per la rotazione */
		for(int i=0; i<5; i++) {
			wooden[i].setAppearance(appWood);
			objRotate.addChild(TGwood[i]);
		}
		
		//Applica una rotazione automatica all'oggetto
		rotate(objRotate,10000);
		
		objRotate.addChild(TGfront);
		
		addChild(objRotate);

	}
	
	private void rotate(TransformGroup objRotate, int timer) {
		//Crea un timer
		Alpha rotationAlpha=new Alpha(-1,timer);
		
		//Crea un interpolatore per le rotazioni collegato con il gruppo di trasformazione
		RotationInterpolator rotator=new RotationInterpolator(rotationAlpha,objRotate);
				
		//Imposta un raggio d'azione all'interpolatore
		BoundingSphere bounds=new BoundingSphere();
		rotator.setSchedulingBounds(bounds);
		
		//Aggiunge l'interpolatore alla gruppo di trasformazione
		objRotate.addChild(rotator);
	}
	
	
	
	
	
}