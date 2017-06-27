import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Group;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Cone;

public class Trottola extends Group {
	static final protected Appearance appearance = new Appearance() ;
	static final protected Transform3D upTransform = new Transform3D (
			new Matrix3d(1.0, 0.0, 0.0, 0.0, 0.5, 0.0, 0.0, 0.0, 1.0),
			new Vector3d(0.0, 0.5, 0.0),
			1.0);
	static final protected Transform3D downTransform = new Transform3D (
			new Matrix3d(1.0, 0.0, 0.0, 0.0, -0.5, 0.0 , 0.0, 0.0, 1.0) ,
			new Vector3d(0.0 , -0.5, 0.0) ,
			1.0);
	protected TransformGroup upTG = new TransformGroup(upTransform);
	protected TransformGroup downTG = new TransformGroup(downTransform);
	protected Cone upCone = new Cone();
	protected Cone downCone = new Cone() ;
	
	public Trottola () {
		
		//trottola sopra
		Appearance appUp = new Appearance();
		appUp.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_NONE, 0));
		appUp.setColoringAttributes(new ColoringAttributes(255, 0, 0, ColoringAttributes.SHADE_FLAT));
		appUp.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.BLENDED, 0.50f));
		
		//trottola sotto
		Appearance appDown = new Appearance();
		//appDown.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_NONE, 0));
		appDown.setColoringAttributes(new ColoringAttributes(0, 60, 0, ColoringAttributes.SHADE_FLAT));
		appDown.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.BLENDED, 0.50f));
		
		upCone.setAppearance ( appUp) ;
		downCone . setAppearance ( appDown ) ;
		upTG.addChild (upCone) ;
		downTG.addChild (downCone) ;
		addChild(upTG) ;
		addChild(downTG ) ;
	}
	
}