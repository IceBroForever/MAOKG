package lab4.shapes;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

import javax.media.j3d.*;
import javax.vecmath.Point3d;

public class CubeGenerator implements Generator {
    public static final double DEFAULT_SIZE = 1f;

    private double size = DEFAULT_SIZE;

    public double getSize() {
        return size;
    }

    public CubeGenerator setSize(double size) {
        this.size = size;
        return this;
    }

    @Override
    public TransformGroup compile(Appearance appearance) {
        TransformGroup transformGroup = new TransformGroup();

        Shape3D cube = new Shape3D();
        cube.setGeometry(compileGeometry());
        cube.setAppearance(appearance);

        transformGroup.addChild(cube);

        return transformGroup;
    }

    @Override
    public Geometry compileGeometry() {
        QuadArray array = (QuadArray)new ColorCube(size).getGeometry();
        int size = array.getVertexCount();

        Point3d[] coords = new Point3d[size];
        for(int i=0; i < size; i++){
            coords[i] = new Point3d();
        }
        array.getCoordinates(0, coords);

        QuadArray newArray = new QuadArray(size, QuadArray.COORDINATES);
        newArray.setCoordinates(0, coords);

        GeometryInfo geometryInfo = new GeometryInfo(newArray);
        NormalGenerator normalGenerator = new NormalGenerator();
        normalGenerator.generateNormals(geometryInfo);
        return geometryInfo.getGeometryArray();
    }
}
