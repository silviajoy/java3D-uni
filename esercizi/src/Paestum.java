import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;

public class Paestum extends Group {

	protected Transform3D t3dBase = new Transform3D();
	protected Transform3D t3dFront = new Transform3D();
	protected Transform3D t3dBack = new Transform3D();
	protected Transform3D t3dLeft = new Transform3D();
	protected Transform3D t3dRight = new Transform3D();

	protected TransformGroup TGBase = new TransformGroup(t3dBase);
	protected TransformGroup TGFront = new TransformGroup(t3dFront);
	protected TransformGroup TGBack = new TransformGroup(t3dBack);
	protected TransformGroup TGLeft = new TransformGroup(t3dLeft);
	protected TransformGroup TGRight = new TransformGroup(t3dRight);

	protected Appearance app = new Appearance();
	int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;		

	protected Steps base = new Steps(3,.7f, .05f, 1.5f, app);
	protected FrontFacePaestum front = new FrontFacePaestum(app);
	protected FrontFacePaestum back = new FrontFacePaestum(app);
	protected SidePaestum left = new SidePaestum(app);
	protected SidePaestum right = new SidePaestum(app);


	public Paestum(Appearance appearance) {
		app = appearance;
		Material material = new Material();
		material.setShininess(127);
		app.setMaterial(material);
		
		Steps base = new Steps(3,.7f, .05f, 1.5f, app);
		FrontFacePaestum front = new FrontFacePaestum(app);
		FrontFacePaestum back = new FrontFacePaestum(app);
		SidePaestum left = new SidePaestum(app);
		SidePaestum right = new SidePaestum(app);
		
		/* Gradini */
		t3dBase.setTranslation(new Vector3d(0,-.4,0));
		TGBase.setTransform(t3dBase);
		TGBase.addChild(base);
		
		addChild(TGBase);

		/* Faccia frontale */
		t3dFront.setTranslation(new Vector3d(0,0, 1.4));
		TGFront.setTransform(t3dFront);
		TGFront.addChild(front);

		addChild(TGFront);

		/* Faccia posteriore */
		t3dBack.rotY(Math.PI);
		t3dBack.setTranslation(new Vector3d(0,0,-1.4));
		TGBack.setTransform(t3dBack);
		TGBack.addChild(back);

		addChild(TGBack);

		/* Lato sinistro */
		t3dLeft.rotY(Math.PI/2);
		t3dLeft.setTranslation(new Vector3d(-.55,0,-.02));
		TGLeft.setTransform(t3dLeft);
		TGLeft.addChild(left);

		addChild(TGLeft);

		/* Lato destro */
		t3dRight.rotY(Math.PI/2);
		t3dRight.setTranslation(new Vector3d(.55,0,-.02));
		TGRight.setTransform(t3dRight);
		TGRight.addChild(right);

		addChild(TGRight);

		/* Texture */
		TextureLoader loader = new TextureLoader("./textures/PietraColonna.jpg", null);
		Texture texture = loader.getTexture();
		texture.setBoundaryModeS(Texture.WRAP);
		texture.setBoundaryModeT(Texture.WRAP);
		texture.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );		   
		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.MODULATE);
		app.setTexture(texture);
		app.setTextureAttributes(texAttr);


	}

}