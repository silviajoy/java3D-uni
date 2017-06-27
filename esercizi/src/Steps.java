import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Group;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;

public class Steps extends Group {
	protected int stepsNumber = 3;
	protected float baseLength = .8f;
	protected float stepHeight = .1f;
	protected float baseDeep = .2f;
	
	int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
	protected Box[] steps = new Box[stepsNumber];
	
	
	public Steps(int stepsN, float lengthIn, float height, float deep, Appearance app){
		stepsNumber = stepsN;
		baseLength = lengthIn;
		stepHeight = height;
		baseDeep = deep;
		
		steps = new Box[stepsNumber];
		TransformGroup[] TG = new TransformGroup[stepsNumber];
		Transform3D[] t3d = new Transform3D[stepsNumber];
		float yOffset = 0;
		float length = baseLength;
		for(int i=0; i<stepsNumber; i++) {		    
			steps[i] = new Box(length, baseDeep-yOffset/2, stepHeight/2,  primflags, app);
			t3d[i] = new Transform3D();
			TG[i] = new TransformGroup();
			t3d[i].rotX(-Math.PI/2);
			t3d[i].setTranslation(new Vector3d(0,yOffset,0));
			TG[i].setTransform(t3d[i]);
			TG[i].addChild(steps[i]);
			addChild(TG[i]);
			
			length-=stepHeight;
			yOffset+=stepHeight;

		}
		
	}
	
}