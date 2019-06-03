package lab5;

import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends JFrame implements KeyListener, ActionListener {

    private double angleDelta = Math.PI / 100;
    private int multiplier = 1;
    private final Timer timer = new Timer(32, this);

    private TransformGroup carModel;

    private Main() throws IOException {
        super("Lab 5");
        setLayout(new BorderLayout());
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.addKeyListener(this);

        SimpleUniverse universe = new SimpleUniverse(canvas);

        BranchGroup branchGroup = createSceneGraph();

        TextureLoader t = new TextureLoader("resources/background.jpg", canvas);
        Background background = new Background(t.getImage());
        background.setImageScaleMode(Background.SCALE_FIT_ALL);
        background.setApplicationBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0));
        branchGroup.addChild(background);

        universe.addBranchGraph(branchGroup);

        Transform3D viewingTransform = new Transform3D();
        viewingTransform.lookAt(new Point3d(4, 1, 0), new Point3d(0, 0, 0), new Vector3d(Math.asin(4d/Math.sqrt(Math.pow(4, 2)+Math.pow(1, 2))), Math.acos(2d/Math.sqrt(Math.pow(4, 2)+Math.pow(1, 2))), 0));
        viewingTransform.invert();
        universe.getViewingPlatform().getViewPlatformTransform().setTransform(viewingTransform);

        add(BorderLayout.CENTER, canvas);
        setVisible(true);

        timer.start();
    }

    private BranchGroup createSceneGraph() throws FileNotFoundException {
        BranchGroup branchGroup = new BranchGroup();

        carModel = ModelLoader.loadModel("resources/model.obj");
        branchGroup.addChild(carModel);

        Bounds lightBounds = new BoundingSphere(new Point3d(0, 0, 0), 5);
        Color3f lightColor = new Color3f(1, 1, 1);
        Vector3f lightDirection = new Vector3f(-1f,-1f,-1f);
        DirectionalLight directionalLight = new DirectionalLight(lightColor, lightDirection);
        directionalLight.setInfluencingBounds(lightBounds);
        branchGroup.addChild(directionalLight);

        return branchGroup;
    }

    private void rotateModel() {
        Transform3D currentTransform = new Transform3D();
        carModel.getTransform(currentTransform);
        Transform3D rotateTransform = new Transform3D();
        rotateTransform.rotY(angleDelta * multiplier);
        currentTransform.mul(rotateTransform);
        carModel.setTransform(currentTransform);
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'p':
            case 'P':{
                if(timer.isRunning()) timer.stop();
                else timer.start();
            } break;
            case 'f':
            case 'F': {
                if (multiplier < 8) multiplier++;
            } break;
            case 's':
            case 'S': {
                if (multiplier > 1) multiplier--;
            } break;
            case 'c':
            case 'C': {
                angleDelta *= -1;
            } break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        rotateModel();
    }
}