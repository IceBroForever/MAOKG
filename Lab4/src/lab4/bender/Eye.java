package lab4.bender;

import com.sun.j3d.utils.geometry.Sphere;
import lab4.shapes.CubeGenerator;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

public class Eye extends TransformGroup {
    public Eye(double size) {
        Sphere eye = new Sphere((float) size / 2);
        eye.setAppearance(getEyeAppearance());

        TransformGroup pupilGroup = new TransformGroup();
        Transform3D pupilTransform = new Transform3D();
        pupilTransform.setTranslation(new Vector3f((float) size / 3, 0f, 0f));
        pupilGroup.setTransform(pupilTransform);
        pupilGroup.addChild(new CubeGenerator().setSize((float) size / 6).compile(getPupilAppearance()));

        addChild(pupilGroup);
        addChild(eye);
    }

    public Appearance getEyeAppearance() {
        Appearance appearance = new Appearance();
        appearance.setMaterial(
                new Material(
                        new Color3f(1f, 1f, 0.7969f),
                        new Color3f(0f, 0f, 0f),
                        new Color3f(1f, 1f, 0.7969f),
                        new Color3f(1f, 1f, 1f),
                        70f
                )
        );
        return appearance;
    }

    public Appearance getPupilAppearance() {
        Appearance appearance = new Appearance();
        appearance.setMaterial(
                new Material(
                        new Color3f(0f, 0f, 0f),
                        new Color3f(0f, 0f, 0f),
                        new Color3f(0f, 0f, 0f),
                        new Color3f(1f, 1f, 1f),
                        70f
                )
        );
        return appearance;
    }
}
