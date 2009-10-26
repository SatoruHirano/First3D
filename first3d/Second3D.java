package first3d;

import java.applet.Applet;
import java.awt.*;
import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;

public class Second3D extends Applet {

	public SimpleUniverse universe = null;
	public Canvas3D canvas = null;
	
	public static void main(String args[]) {
		new MainFrame(new Second3D(), 200, 200);
	}
	
	public BranchGroup createObjects() {
		BranchGroup root = new BranchGroup();

		Transform3D transform1 = new Transform3D();
		Transform3D transform2 = new Transform3D();
		Transform3D transform3 = new Transform3D();
		
		TransformGroup transnode1 = new TransformGroup();
		TransformGroup transnode2 = new TransformGroup();
		TransformGroup transnode3 = new TransformGroup();
		
		transform1.set(new Vector3d(1.0, 1.0, -1.0));
		transform2.set(new Vector3d(0, 0, -1.0));
		transform3.set(new Vector3d(-1.0, -1.0, -1.0));
		
		transnode1.setTransform(transform1);
		transnode2.setTransform(transform2);
		transnode3.setTransform(transform3);
		
		Appearance appearance = new Appearance();
		appearance.setColoringAttributes(
				new ColoringAttributes(1.0f, 1.0f, 1.0f, ColoringAttributes.FASTEST));

		transnode1.addChild(new ColorCube(0.4));
		transnode2.addChild(new Sphere(0.4f, appearance));
		transnode3.addChild(new Box(0.4f, 0.4f, 0.4f, appearance));
		
		root.addChild(transnode1);
		root.addChild(transnode2);
		root.addChild(transnode3);
		root.compile();
		
		return root;
	}
	
	public void setOrbitBehavior() {
		OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ALL);
		orbit.setSchedulingBounds(new BoundingSphere(new Point3d(0, 0, 0), 100.0));
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
}
