package uk.ac.uea.mathsthing.util;

import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Policy;

/**
 * Specialised {@link URLClassLoader} to differentiate classes that were 
 * loaded as plugins.
 * 
 * @author Jordan Woerner
 * @version 1.0
 */
public final class PluginLoader extends URLClassLoader {

	public PluginLoader(URL url) {
		super(new URL[] {url});
	}
	
	public PluginLoader(URL[] urls) {
		super(urls);
	}

	@Override
	protected void addURL(URL url) {
		super.addURL(url);
	}
	
	/**
	 * Gets the {@link PermissionCollection} that applies to plugin classes.
	 * 
	 * @param arg0 The source of the class as a {@link CodeSource}.
	 * @return Returns a {@link PermissionCollection} of permissions for the 
	 * specified {@link CodeSource}.
	 * @since 1.0
	 */
	@Override
	protected PermissionCollection getPermissions(CodeSource arg0) {
		Policy policy = new PluginSandbox();
		return policy.getPermissions(arg0);
	}
}
