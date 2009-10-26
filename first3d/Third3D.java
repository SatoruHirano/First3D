package first3d;

import java.applet.Applet;
import java.awt.*;
import javax.media.j3d.*;
import javax.vecmath.*;

import java.io.*;
import java.beans.XMLDecoder;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;

public class Third3D extends Applet {

	public SimpleUniverse universe = null;
	public Canvas3D canvas = null;
	
	public static void main(String args[]) {
		new MainFrame(new Third3D(), 200, 200);
	}
	
	public BranchGroup createObjects() {
		BranchGroup root = new BranchGroup();
		Shape3D shape = null;
		try { 
			InputStream input = new FileInputStream("Models/Model4.xml");
			XMLDecoder dec = new XMLDecoder(input);
			shape = (Shape3D)dec.readObject();
			dec.close();
			input.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		TransformGroup transRoot = new TransformGroup();
		Transform3D transform = new Transform3D();
		transform.set(new Vector3d(0.0, 0.0, 0.0));
		transRoot.setTransform(transform);

		Appearance appearance = new Appearance();
		appearance.setColoringAttributes(new ColoringAttributes(1.0f, 0.0f, 0.0f, ColoringAttributes.FASTEST));
		shape.setAppearance(appearance);
		
		ViewingPlatform vplt = universe.getViewingPlatform();
		Transform3D viewTrans = new Transform3D();
		viewTrans.set(new Vector3d(0.0, 0.0, 9.0));
		vplt.getViewPlatformTransform().setTransform(viewTrans);
	
		transRoot.addChild(shape);
		root.addChild(transRoot);
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
