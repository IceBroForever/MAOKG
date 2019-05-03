package lab4.bender;

import com.sun.j3d.utils.geometry.Sphere;
import lab4.shapes.CubeGenerator;
import lab4.shapes.FrustumGenerator;
import lab4.shapes.HalfSphereGenerator;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class Head extends TransformGroup {

    public Head(double height) {
        TransformGroup cylinderGroup = new FrustumGenerator()
                .setHeight(height / 2)
                .setInnerRadius(height / 4)
                .setOuterRadius(height / 4)
                .compile(getAppearance());
        addChild(cylinderGroup);

        TransformGroup halfSphere1 = new HalfSphereGenerator()
                .setType(HalfSphereGenerator.Type.OPENED)
                .setRadius(height / 4)
                .compile(getAppearance());
        Transform3D halfSphere1Transform = new Transform3D();
        halfSphere1Transform.setTranslation(new Vector3d(0, 0, height / 4));
        halfSphere1.setTransform(halfSphere1Transform);
        addChild(halfSphere1);

        TransformGroup halfSphere2 = new HalfSphereGenerator()
                .setType(HalfSphereGenerator.Type.OPENED)
                .setRadius(height / 16)
                .compile(getAppearance());
        Transform3D halfSphere2Transform = new Transform3D();
        halfSphere2Transform.setScale(new Vector3d(1, 1, 0.5));
        halfSphere2Transform.setTranslation(new Vector3d(0, 0, height / 2 - height / 90));
        halfSphere2.setTransform(halfSphere2Transform);
        addChild(halfSphere2);

        TransformGroup antena = new FrustumGenerator()
                .setHeight(height / 4)
                .setInnerRadius(height / 64)
                .setOuterRadius(height / 32)
                .compile(getAppearance());
        Transform3D antenaTransform = new Transform3D();
        antenaTransform.rotX(Math.PI);
        antenaTransform.setTranslation(new Vector3d(0, 0, height / 2 + height / 8));
        antena.setTransform(antenaTransform);
        addChild(antena);

        TransformGroup antenaSphereGroup = new TransformGroup();
        Sphere antenaSphere = new Sphere((float) height / 32);
        antenaSphere.setAppearance(getAppearance());
        antenaSphereGroup.addChild(antenaSphere);
        Transform3D antenaSphereTransform = new Transform3D();
        antenaSphereTransform.setTranslation(new Vector3f(0, 0, 3 * (float)height / 4));
        antenaSphereGroup.setTransform(antenaSphereTransform);
        addChild(antenaSphereGroup);

        TransformGroup topEyes = new CubeGenerator()
                .setSize(height / 4)
                .compile(getAppearance());
        Transform3D topEyesTransform = new Transform3D();
        topEyesTransform.setTranslation(new Vector3f( 3 * (float) height / 16, 0, (float) height / 4));
        topEyesTransform.setScale(new Vector3d(0.75, 1, 0.02));
        topEyes.setTransform(topEyesTransform);
        addChild(topEyes);

        TransformGroup bottomEyes = new CubeGenerator()
                .setSize(height / 4)
                .compile(getAppearance());
        Transform3D bottomEyesTransform = new Transform3D();
        bottomEyesTransform.setTranslation(new Vector3f( 3 * (float) height / 16, 0, (float) height / 16));
        bottomEyesTransform.setScale(new Vector3d(0.75, 1, 0.02));
        bottomEyes.setTransform(bottomEyesTransform);
        addChild(bottomEyes);

        TransformGroup leftEyes = new CubeGenerator()
                .setSize(height / 4)
                .compile(getAppearance());
        Transform3D leftEyesTransform = new Transform3D();
        leftEyesTransform.setTranslation(new Vector3f(
                3 * (float) height / 16,
                (float) height / 4 - 0.02f * (float) height / 4,
                5 * (float) height / 32));
        leftEyesTransform.setScale(new Vector3d(0.75, 0.02, 0.39));
        leftEyes.setTransform(leftEyesTransform);
        addChild(leftEyes);

        TransformGroup rightEyes = new CubeGenerator()
                .setSize(height / 4)
                .compile(getAppearance());
        Transform3D rightEyesTransform = new Transform3D();
        rightEyesTransform.setTranslation(new Vector3f(
                3 * (float) height / 16,
                (float) -height / 4 + 0.02f * (float) height / 4,
                5 * (float) height / 32));
        rightEyesTransform.setScale(new Vector3d(0.75, 0.02, 0.39));
        rightEyes.setTransform(rightEyesTransform);
        addChild(rightEyes);

        Eye leftEye = new Eye(3 * (float) height / 16);
        Transform3D leftEyeTransform = new Transform3D();
        leftEyeTransform.setTranslation(new Vector3f(
                9 * (float) height / 32,
                (float) height / 8,
                5 * (float) height / 32
        ));
        leftEye.setTransform(leftEyeTransform);
        addChild(leftEye);

        Eye rightEye = new Eye(3 * (float) height / 16);
        Transform3D rightEyeTransform = new Transform3D();
        rightEyeTransform.setTranslation(new Vector3f(
                9 * (float) height / 32,
                (float) -height / 8,
                5 * (float) height / 32
        ));
        rightEye.setTransform(rightEyeTransform);
        addChild(rightEye);

        TransformGroup backEyes = new CubeGenerator()
                .setSize(height / 4)
                .compile(leftEye.getPupilAppearance());
        Transform3D backEyesTransform = new Transform3D();
        backEyesTransform.setTranslation(new Vector3f(
                (float) height / 4,
                0,
                5 * (float) height / 32));
        backEyesTransform.setScale(new Vector3d(0.02, 0.98, 0.39));
        backEyes.setTransform(backEyesTransform);
        addChild(backEyes);
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
