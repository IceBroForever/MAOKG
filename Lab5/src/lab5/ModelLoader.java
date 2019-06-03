package lab5;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import java.io.FileNotFoundException;
import java.util.Map;

public class ModelLoader {
    public static TransformGroup loadModel(String location) throws FileNotFoundException {
        ObjectFile loader = new ObjectFile(ObjectFile.RESIZE);
        Scene modelScene = loader.load(location);
        BranchGroup modelBranchGroup = modelScene.getSceneGroup();
        Map<String, Shape3D> modelNameMap = modelScene.getNamedObjects();
        TransformGroup modelGroup = new TransformGroup();
        for (String name : modelNameMap.keySet()) {
            Shape3D part = modelNameMap.get(name);
            modelBranchGroup.removeChild(part);
            modelGroup.addChild(part);
        }
        modelGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        return modelGroup;
    }
}
