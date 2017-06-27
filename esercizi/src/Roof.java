/*import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;

public class Roof extends Group {
	protected float thickness = .05f;
	protected float baseLength = .8f;
	protected float height = .3f;
	
	int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
	protected Appearance app = new Appearance();
	protected Box[] steps = new Box[stepsNumber];
	
	public Steps(int stepsN){
		stepsNumber = stepsN;
		steps = new Box[stepsNumber];
		TransformGroup[] TG = new TransformGroup[stepsNumber];
		Transform3D[] t3d = new Transform3D[stepsNumber];
		float yOffset = 0;
		float length = baseLength;
		for(int i=0; i<stepsNumber; i++) {
			steps[i] = new Box(length, stepHeight/2, .2f-yOffset/2, primflags, app);
			t3d[i] = new Transform3D();
			TG[i] = new TransformGroup();
			t3d[i].setTranslation(new Vector3d(0,yOffset,-yOffset/2));
			TG[i].setTransform(t3d[i]);
			TG[i].addChild(steps[i]);
			addChild(TG[i]);
			
			length-=.1;
			yOffset+=stepHeight;

		}
	}
	
	public Appearance getAppearance() {
		return app;
	}
}*/