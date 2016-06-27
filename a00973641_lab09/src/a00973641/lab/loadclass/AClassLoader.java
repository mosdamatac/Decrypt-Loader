/**
 * Project: 3613_lab10_start
 * File: AClassLoader.java
 * Date: Jun 22, 2016
 * Time: 11:47:18 AM
 */
package a00973641.lab.loadclass;

import java.lang.reflect.Method;

import a00973641.lab.view.LabFrame;

/**
 * @author Mara
 *
 */
public class AClassLoader extends ClassLoader {

	/**
	 * Runs the main method of the given class bytes.
	 * 
	 * @param classBytes
	 */
	public void runClass(byte[] classBytes) {
		try {
			AClassLoader loader = new AClassLoader();
			Class<?> c = loader.findClass(classBytes, "MiniCalc");
			String[] args = new String[] {};

			Method m = c.getMethod("main", new Class[] { args.getClass() });
			m.invoke(null, new Object[] { args });
		} catch (Throwable e) {
			LabFrame.showError(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Load and define class with the given class bytes.
	 * 
	 * @param classBytes
	 * @param name
	 * @return defined class
	 * @throws ClassNotFoundException
	 */
	public Class<?> findClass(byte[] classBytes, String name) throws ClassNotFoundException {
		Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
		if (cl == null)
			throw new ClassNotFoundException(name);
		return cl;
	}
}
