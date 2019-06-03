package pasha1ruh;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.util.Hashtable;

public class Main extends JFrame {
    public Main() {
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        SimpleUniverse universe = new SimpleUniverse(canvas3D);
        universe.getViewingPlatform().setNominalViewingTransform();

        createSceneGraph(universe);

        addLight(universe);

        OrbitBehavior ob = new OrbitBehavior(canvas3D);
        ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE));
        universe.getViewingPlatform().setViewPlatformBehavior(ob);

        setTitle("Scrat");
        setSize(640,480);
        getContentPane().add("Center", canvas3D);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createSceneGraph(SimpleUniverse universe) {
        ObjectFile f = new ObjectFile(ObjectFile.RESIZE);
        Scene scratScene = null;
        try
        {
            scratScene = f.load("resources/scrat.obj");
        }
        catch (Exception e)
        {
            System.out.println("File loading failed:" + e);
        }

        Transform3D tfScrat = new Transform3D();
        TransformGroup tgScrat = new TransformGroup(tfScrat);

        Hashtable scratSceneNamedObjects = scratScene.getNamedObjects();

        Shape3D nut = (Shape3D)scratSceneNamedObjects.get("nut");
        Shape3D leftHand = (Shape3D)scratSceneNamedObjects.get("left_hand");
        Shape3D rightHand = (Shape3D)scratSceneNamedObjects.get("right_hand");
        Shape3D tale = (Shape3D)scratSceneNamedObjects.get("tale");

        Shape3D[] scrat = new Shape3D[]{
                (Shape3D)scratSceneNamedObjects.get("nose"),
                (Shape3D)scratSceneNamedObjects.get("objsphere12"),
                (Shape3D)scratSceneNamedObjects.get("objobject07"),
                (Shape3D)scratSceneNamedObjects.get("objsphere09"),
                (Shape3D)scratSceneNamedObjects.get("left_eye"),
                (Shape3D)scratSceneNamedObjects.get("objobject06"),
                (Shape3D)scratSceneNamedObjects.get("right_eye1"),
                (Shape3D)scratSceneNamedObjects.get("objobject05"),
                (Shape3D)scratSceneNamedObjects.get("body"),
                (Shape3D)scratSceneNamedObjects.get("left_eye1"),
                (Shape3D)scratSceneNamedObjects.get("right_eye")
        };

        for(Shape3D shape3D: scrat) {
            tgScrat.addChild(shape3D.cloneTree());
        }

        // Nut
        Appearance nutAppearance = new Appearance();
        nutAppearance.setMaterial(new Material(
                new Color3f(0.33f, 0.21f, 0f),
                new Color3f(0, 0, 0),
                new Color3f(0.33f, 0.21f, 0f),
                new Color3f(0.55f, 0.35f, 0f),
                70
        ));

        Shape3D nutShape3D = (Shape3D)nut.cloneTree();
        nutShape3D.setAppearance(nutAppearance);

        Transform3D nutToCenterTransform = new Transform3D();
        nutToCenterTransform.setTranslation(new Vector3d(0.85, 0.15, 0));

        TransformGroup nutInCenterTransformGroup = new TransformGroup(nutToCenterTransform);
        nutInCenterTransformGroup.addChild(nutShape3D);

        Transform3D nutInCenterRotationTransform3D = new Transform3D();
        nutInCenterRotationTransform3D.rotX(Math.PI / 2);
        TransformGroup nutInCenterRotationTransformGroup = new TransformGroup();
        Alpha nutRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0, 1000, 0, 0, 0, 0, 0);
        RotationInterpolator nutRotationInterpolator = new RotationInterpolator(
                nutRotationAlpha,
                nutInCenterRotationTransformGroup,
                nutInCenterRotationTransform3D,
                0,
                (float)Math.PI * 2f);
        nutRotationInterpolator.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0), 2));
        nutInCenterRotationTransformGroup.addChild(nutInCenterTransformGroup);
        nutInCenterRotationTransformGroup.addChild(nutRotationInterpolator);
        nutInCenterRotationTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        TransformGroup nutInCenterRotationAndBouncingGroup = new TransformGroup();
        Alpha nutBouncingAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, 0, 0, 300, 0, 0, 200, 0, 0);
        Transform3D nutInCenterBouncingTransform = new Transform3D();
        nutInCenterBouncingTransform.rotZ(Math.PI / 2);
        PositionInterpolator nutBouncingInterpolator = new PositionInterpolator(nutBouncingAlpha, nutInCenterRotationAndBouncingGroup, nutInCenterBouncingTransform, -0.2f, 0.2f);
        nutBouncingInterpolator.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0), 2));
        nutInCenterRotationAndBouncingGroup.addChild(nutInCenterRotationTransformGroup);
        nutInCenterRotationAndBouncingGroup.addChild(nutBouncingInterpolator);
        nutInCenterRotationAndBouncingGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        Transform3D nutFromCenterTransform = new Transform3D();
        nutFromCenterTransform.setTranslation(new Vector3d(-0.85, -0.15, 0));
        TransformGroup nutFromCenterTransformGroup = new TransformGroup(nutFromCenterTransform);
        nutFromCenterTransformGroup.addChild(nutInCenterRotationAndBouncingGroup);

        // Tale
        Transform3D taleToCenterTransform = new Transform3D();
        TransformGroup taleInCenterTransformGroup = new TransformGroup(taleToCenterTransform);
        taleInCenterTransformGroup.addChild(tale.cloneTree());

        // Left hand
        Transform3D leftHandToCenterTransform = new Transform3D();
        leftHandToCenterTransform.setTranslation(new Vector3d(-0.055, -0.055, -0.055));
        TransformGroup leftHandInCenterTransformGroup = new TransformGroup(leftHandToCenterTransform);
        leftHandInCenterTransformGroup.addChild(leftHand.cloneTree());

        Transform3D leftHandRotateTransform = new Transform3D();
        leftHandRotateTransform.rotZ(-Math.PI / 2);
        Transform3D leftHandRotateTransform2 = new Transform3D();
        leftHandRotateTransform2.rotY(-Math.PI / 2);
        leftHandRotateTransform.mul(leftHandRotateTransform2, leftHandRotateTransform);
        Transform3D leftHandRotateTransform3 = new Transform3D();
        leftHandRotateTransform3.rotZ(Math.PI / 6);
        leftHandRotateTransform.mul(leftHandRotateTransform3, leftHandRotateTransform);
        TransformGroup leftHandRotatedGroup = new TransformGroup(leftHandRotateTransform);
        leftHandRotatedGroup.addChild(leftHandInCenterTransformGroup);

        Transform3D leftHandRotationAxes = new Transform3D();
        leftHandRotationAxes.rotX(Math.PI / 2);
        TransformGroup leftHandRotationTransformGroup = new TransformGroup();
        Alpha leftHandRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, 0, 0, 200, 0, 0, 200, 0, 0);
        RotationInterpolator leftHandRotationInterpolator = new RotationInterpolator(
                leftHandRotationAlpha,
                leftHandRotationTransformGroup,
                leftHandRotationAxes,
                0,
                (float)Math.PI / 6
        );
        leftHandRotationInterpolator.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0), Double.MAX_VALUE));
        leftHandRotationTransformGroup.addChild(leftHandRotatedGroup);
        leftHandRotationTransformGroup.addChild(leftHandRotationInterpolator);
        leftHandRotationTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        Transform3D leftHandFromCenterTransform = new Transform3D();
        leftHandFromCenterTransform.setTranslation(new Vector3d(0.13, 0.1, 0.11));
        TransformGroup leftHandFromCenterTransformGroup = new TransformGroup(leftHandFromCenterTransform);
        leftHandFromCenterTransformGroup.addChild(leftHandRotationTransformGroup);

        // Right hand
        Transform3D rightHandToCenterTransform = new Transform3D();
        rightHandToCenterTransform.setTranslation(new Vector3d(-0.055, -0.055, 0.055));
        TransformGroup rightHandInCenterTransformGroup = new TransformGroup(rightHandToCenterTransform);
        rightHandInCenterTransformGroup.addChild(rightHand.cloneTree());

        Transform3D rightHandRotateTransform = new Transform3D();
        rightHandRotateTransform.rotZ(-Math.PI / 2);
        Transform3D rightHandRotateTransform2 = new Transform3D();
        rightHandRotateTransform2.rotY(Math.PI / 2);
        rightHandRotateTransform.mul(rightHandRotateTransform2, rightHandRotateTransform);
        Transform3D rightHandRotateTransform3 = new Transform3D();
        rightHandRotateTransform3.rotZ(Math.PI / 6);
        rightHandRotateTransform.mul(rightHandRotateTransform3, rightHandRotateTransform);
        TransformGroup rightHandRotatedGroup = new TransformGroup(rightHandRotateTransform);
        rightHandRotatedGroup.addChild(rightHandInCenterTransformGroup);

        Transform3D rightHandRotationAxes = new Transform3D();
        rightHandRotationAxes.rotX(Math.PI / 2);
        TransformGroup rightHandRotationTransformGroup = new TransformGroup();
        Alpha rightHandRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, 200, 0, 200, 0, 0, 200, 0, 0);
        RotationInterpolator rightHandRotationInterpolator = new RotationInterpolator(
                rightHandRotationAlpha,
                rightHandRotationTransformGroup,
                rightHandRotationAxes,
                0,
                (float)Math.PI / 6
        );
        rightHandRotationInterpolator.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0), 2));
        rightHandRotationTransformGroup.addChild(rightHandRotatedGroup);
        rightHandRotationTransformGroup.addChild(rightHandRotationInterpolator);
        rightHandRotationTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        Transform3D rightHandFromCenterTransform = new Transform3D();
        rightHandFromCenterTransform.setTranslation(new Vector3d(0.13, 0.1, -0.11));
        TransformGroup rightHandFromCenterTransformGroup = new TransformGroup(rightHandFromCenterTransform);
        rightHandFromCenterTransformGroup.addChild(rightHandRotationTransformGroup);

        BranchGroup scene = new BranchGroup();
        scene.addChild(tgScrat);
        scene.addChild(nutFromCenterTransformGroup);
        scene.addChild(taleInCenterTransformGroup);
        scene.addChild(rightHandFromCenterTransformGroup);
        scene.addChild(leftHandFromCenterTransformGroup);

        Background bg = new Background(new Color3f(0.25f,0.73f,1.0f));
        bg.setApplicationBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0), Double.MAX_VALUE));
        scene.addChild(bg);
        scene.compile();

        universe.addBranchGraph(scene);
    }

    private void addLight(SimpleUniverse universe)
    {
        BranchGroup bgLight = new BranchGroup();

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
        Color3f lightColour1 = new Color3f(1.0f,1.0f,1.0f);
        Vector3f lightDir1 = new Vector3f(1.0f,0.0f,-0.5f);
        DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
        light1.setInfluencingBounds(bounds);

        bgLight.addChild(light1);
        universe.addBranchGraph(bgLight);
    }

    public static void main(String[] argv) { new Main(); }
}
