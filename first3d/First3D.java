package first3d;

import java.applet.Applet;
import java.awt.*;
import com.sun.j3d.utils.applet.MainFrame;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;

public class First3D extends Applet {

	public SimpleUniverse universe = null;
	public Canvas3D canvas = null;
	
	public BranchGroup createObjects() {
		BranchGroup root = new BranchGroup();

		TransformGroup transRoot = new TransformGroup();
		Transform3D transform = new Transform3D();
		transform.set(new Vector3d(0.2, 0, 0));
		transRoot.setTransform(transform);
		
		transRoot.addChild(new ColorCube(0.4));
		root.addChild(transRoot);
		root.compile();
		return root;
	}
	
	public void setOrbitBehavior() {
		OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ALL);
		orbit.setSchedulingBounds(new BoundingSphere(new Point3d(0, 0, 0), 100));
		universe.getViewingPlatform().setViewPlatformBehavior(orbit);
	}
	
	public void init() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		canvas = new Canvas3D(config);
		add("Center", canvas);

		universe = new SimpleUniverse(canvas);
		universe.getViewingPlatform().setNominalViewingTransform();

		setOrbitBehavior();
		universe.addBranchGraph(createObjects());
	}

	public static void main(String args[]) {
		new MainFrame(new First3D(), 200, 200);
	}
}
