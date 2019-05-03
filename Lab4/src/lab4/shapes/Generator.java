package lab4.shapes;

import javax.media.j3d.Appearance;
import javax.media.j3d.Geometry;
import javax.media.j3d.TransformGroup;

public interface Generator {
    TransformGroup compile(Appearance appearance);
    Geometry compileGeometry();
}
