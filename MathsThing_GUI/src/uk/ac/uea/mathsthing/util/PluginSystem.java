package uk.ac.uea.mathsthing.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.Policy;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import uk.ac.uea.mathsthing.IPlugin;

/**
 * Reads JAR files and allows access to the {@link Class} objects stored 
 * within them.
 * 
 * @author Jordan Woerner
 * @version 1.0
 */
public final class PluginSystem {

	/** Singleton instance of the {@link PluginSystem}. */
	private static PluginSystem instance = null;
	/** A list of found plugins. */
	private ArrayList<Class<?>> plugins;
	/** The {@link PluginLoader} to use when loading plugin classes. */
	private PluginLoader classLoader;
	
	static {
		Policy.setPolicy(new PluginSandbox());
		System.setSecurityManager(new SecurityManager());
	}
	
	/**
	 * Loads all the plugins stored in a specified plugins folder.
	 * 
	 * @throws NoSuchMethodException Thrown if the found {@link ClassLoader} 
	 * does not have an 'addURL' method.
	 * @throws InvocationTargetException Thrown if the 'addURL' method cannot 
	 * be run.
	 * @throws IllegalAccessException Thrown if the JVM security prevents 
	 * 'addURL' from running.
	 * @throws IOException Thrown if the JAR file cannot be opened.
	 * @throws ClassNotFoundException Thrown when a class cannot be loaded.
	 * @since 1.0
	 */
	private PluginSystem() 
			throws NoSuchMethodException, InvocationTargetException, 
			IllegalAccessException, IOException, ClassNotFoundException
	{
		this.plugins = new ArrayList<>();
		
		String workingDir = null;
		try {
			workingDir = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		} catch (URISyntaxException e) {
			throw new IOException("Plugin system could not find current directory.", e);
		}
		workingDir = workingDir.substring(0, workingDir.lastIndexOf('/')+1);
		
		// Find the plugins folder.
		String path = String.format("%splugins", workingDir);
		File pluginFolder = new File(path);
		File[] files = pluginFolder.listFiles();
		
		// If the folder cannot be found or there are no files, give up.
		if (files == null || files.length == 0)
			return;
		
		for (File jarFile : files) {
			if (jarFile.exists()) {
				loadJar(jarFile); // Add the Jar to the classpath.
				plugins.addAll(getClasses(jarFile)); // Load the classes.
			} else {
				System.err.println("JAR" + jarFile + " not found!");
			}
		}
	}
	
	/**
	 * Gets all the plugins found in the plugins folder and returns a list of 
	 * them.
	 * 
	 * @return An {@link ArrayList} of {@link Class} objects found in the 
	 * plugin JAR files.
	 * @throws NoSuchMethodException Thrown if the found {@link ClassLoader} 
	 * does not have an 'addURL' method.
	 * @throws InvocationTargetException Thrown if the 'addURL' method cannot 
	 * be run.
	 * @throws IllegalAccessException Thrown if the JVM security prevents 
	 * 'addURL' from running.
	 * @throws ClassNotFoundException Thrown when a class cannot be loaded.
	 * @throws IOException Thrown if the JAR file cannot be opened.
	 * @since 1.0
	 */
	public static final ArrayList<Class<?>> getPlugins() 
			throws NoSuchMethodException, InvocationTargetException, 
			IllegalAccessException, ClassNotFoundException, IOException
	{
		if (instance == null) {
			instance = new PluginSystem();
		}
		
		return instance.plugins;
	}
	
	/**
	 * Loads a specified JAR file into the classpath, allowing the contents to 
	 * be read.
	 * 
	 * @param file The {@link File} object for the JAR file.
	 * @throws NoSuchMethodException Thrown if the found {@link ClassLoader} 
	 * does not have an 'addURL' method.
	 * @throws InvocationTargetException Thrown if the 'addURL' method cannot 
	 * be run.
	 * @throws IllegalAccessException Thrown if the JVM security prevents 
	 * 'addURL' from running.
	 * @throws IOException Thrown if the JAR file cannot be opened.
	 */
	private final void loadJar(File file) 
			throws NoSuchMethodException, InvocationTargetException, 
			IllegalAccessException, IOException
	{		
		URL url = null;
		Class<PluginLoader> foundClass = null;
		
		try {
			url = new URL("jar", "", "file:" + file.getCanonicalPath() + "!/");	
			/* Get the current ClassPath. */
			classLoader = new PluginLoader(url);
			foundClass = PluginLoader.class;
			/* Invoke "addURL" like this thanks to encapsulation. */
			Method addMethod = foundClass.getDeclaredMethod("addURL", URL.class);
			addMethod.setAccessible(true);
			addMethod.invoke(classLoader, new Object[]{url});
		} catch (MalformedURLException urlEx) {
			/* Thrown if the URL created is invalid in any way. */
			urlEx.printStackTrace();
			throw new MalformedURLException("Invalid URI - " + url);
		} catch (NoSuchMethodException noEx) {
			/* Thrown when the 'addURL' method can't be found. */
			noEx.printStackTrace();
			throw new NoSuchMethodException("Method 'addURL' not found in "
					+ foundClass.getName());
		} catch (InvocationTargetException invEx) {
			/* Thrown if 'addURL' cannot be invoked. */
			invEx.printStackTrace();
			throw new InvocationTargetException(invEx,
					"Could not invoke 'addURL' on " + foundClass.getName());
		} catch (IllegalAccessException accessEx) {
			/* Thrown when the application does not have permission to invoke 
				'addURL' on the class. */
			accessEx.printStackTrace();
			throw new IllegalAccessException("Not allowed to access - "
					+ file.getName());
		} catch (IOException ioEx) {
			/* Thrown when the canonical path of the file fails to return. */
			throw new IOException("Failed to get path of - " 
					+ file.getName(), ioEx);
		}
	}
	
	/**
	 * Gets a list of the classes contained inside the specified JAR file.
	 * 
	 * @param file The JAR file to read.
	 * @return An {@link ArrayList} of Classes typed as wild card (bound to 
	 * {@link IPlugin} subclasses) containing all the class names.
	 * @throws IOException Thrown if there are any issues reading the JAR file.
	 * @throws ClassNotFoundException Thrown when a class cannot be loaded.
	 * @since 1.0
	 */
	private final ArrayList<Class<?>> getClasses(File file) 
			throws IOException, ClassNotFoundException
	{	
		ArrayList<Class<?>> classes = new ArrayList<>();
		JarInputStream jarStream = null;
		String className = null;
		
		try {
			/* Open up the JAR. */
			jarStream = new JarInputStream(new FileInputStream(file));
			JarEntry jarEntry = null;
			
			/* Look at all the files in the JAR file. Keep checking until 
				nothing is left in the JarInputStream. */
			while ((jarEntry = jarStream.getNextJarEntry()) != null) {
				/* Add any classes found to the ArrayList. */
				if (jarEntry.getName().endsWith(".class")) {
					className = jarEntry.getName();
					/* Strip the '.class' from the class name to load it. */
					String strippedName = className.replace("/", ".").
							substring(0, className.length() - 6);
					/* Try and load the class. */
					Class<?> clazz = Class.forName(strippedName, true, classLoader);
					
					// Check that the class derives from one of the plugins.
					if (IPlugin.class.isAssignableFrom(clazz)) {
						classes.add(clazz); // Add it to the ArrayList.
					}
				}					
			}
		} catch(IOException ioEx) {
			/* Thrown if the JarInputStream fails. */
			throw new IOException("Could not read JAR file - " + file.getName(), ioEx);
		} catch (ClassNotFoundException e) {
			/* Thrown if a class cannot be loaded. */
			throw new ClassNotFoundException("Could not find class - " + className, e);
		} finally {
			/* Attempt to close the JarInputStream when we're done. */
			try {
				jarStream.close();
			} catch (IOException e) {
				throw new IOException("Could not close jarStream.", e);
			} finally {
				jarStream = null;
			}
		}
		
		return classes;
	}
}
