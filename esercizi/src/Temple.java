import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Group;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Matrix3d;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;
import javax.vecmath.Vector3d;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Temple extends Group {	
	
	public Temple() {
		TransformGroup TG;
		Transform3D t3d;
		
		double height = .1;
		double trans = 0;
		
		for(double i=2; i>.6; i-=.2) {
			TG = new TransformGroup();
			t3d = new Transform3D();
			
			/* imposta la traslazione a partire dalla variabile trans che aumenta ad ogni iterazione */
			t3d.setTranslation(new Vector3d(0,trans,0));
			TG.setTransform(t3d);
			/* il nuovo tronco di piramide si basa sui valori della i che sono decrescenti */
			TG.addChild(new PyramidCut(height,i,i-.1)); // aggiungo il nuovo tronco di piramide
			addChild(TG);
			
			trans += height;
						
		}
		/* le ultime due parti sono della stessa dimensione */
		for(double i=0; i<2; i++) {
			TG = new TransformGroup();
			t3d = new Transform3D();
			
			t3d.setTranslation(new Vector3d(0,trans,0));
			TG.setTransform(t3d);
			
			TG.addChild(new PyramidCut(height,.4,.4));
			addChild(TG);
			
			trans += height;
		}
		
	}

	
}