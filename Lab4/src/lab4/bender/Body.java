package lab4.bender;

import lab4.shapes.FrustumGenerator;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

public class Body extends TransformGroup {
    public Body(double size) {
        TransformGroup up = new FrustumGenerator()
                .setHeight(size / 6)
                .setInnerRadius(size / 4)
                .setOuterRadius(size / 2)
                .compile(getAppearance());
        Transform3D upTransform = new Transform3D();
        upTransform.rotX(Math.PI);
        upTransform.setTranslation(new Vector3f(0, 0, 5 * (float) size / 12));
        up.setTransform(upTransform);
        addChild(up);

        TransformGroup down = new FrustumGenerator()
                .setHeight(5 * size / 6)
                .setInnerRadius(size / 3)
                .setOuterRadius(size / 2)
                .compile(getAppearance());
        Transform3D downTransform = new Transform3D();
        downTransform.setTranslation(new Vector3f(0, 0, (float) -size / 12));
        down.setTransform(downTransform);
        addChild(down);
    }

    public Appearance getAppearance() {
        Appearance appearance = new Appearance();
        appearance.setMaterial(
                new Material(
                        new Color3f(0.4453f, 0.4453f, 0.4453f),
                        new Color3f(0f, 0f, 0f),
                        new Color3f(0.4453f, 0.4453f, 0.4453f),
                        new Color3f(1f, 1f, 1f),
                        70f
                )
        );
        return appearance;
    }
}
