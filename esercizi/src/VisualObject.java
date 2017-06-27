import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Geometry;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransparencyAttributes;

abstract class VisualObject extends Shape3D {
	protected Geometry geometry;
	protected Appearance appearance;
	// Impostazione dei NodeComponent .
	public VisualObject() {
		geometry=createGeometry();
		appearance=createAppearance();
		setGeometry(geometry) ;
		setAppearance(appearance) ;
	}
	// Metodo per creare la geometria.
	protected abstract Geometry createGeometry();
	// Metodo per creare l'aspetto .
	protected abstract Appearance createAppearance ();
}