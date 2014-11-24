package uk.ac.uea.mathsthing.util;

import java.awt.AWTPermission;
import java.io.FilePermission;
import java.security.AllPermission;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.ProtectionDomain;
import java.util.PropertyPermission;

/**
 * Custom security {@link Policy} to prevent plugin classes from harming the 
 * users computer or the application.
 * 
 * @author Jordan Woerner
 * @version 1.0
 */
public final class PluginSandbox extends Policy {

	/**
	 * Gets the appropriate set of {@link Permission} for the specified 
	 * {@link ProtectionDomain}. If the class under question was loaded by 
	 * {@link PluginLoader}, this will be a very restricted set. Otherwise it 
	 * will be {@link AllPermission}.
	 * 
	 * @param domain The {@link ProtectionDomain} to check {@link Permissions} 
	 * for.
	 * @return Returns a {@link PermissionCollection} of all the valid 
	 * {@link Permission} provided for a class.
	 * @since 1.0
	 * @see Policy#getPermissions(ProtectionDomain)
	 */
	@Override
	public final PermissionCollection getPermissions(ProtectionDomain domain) 
	{
		if (isPlugin(domain))
			return getPluginPermissions();
		
		return getDefaultPermissions();
	}
	
	/**
	 * Checks if the specified {@link ProtectionDomain} is from a plugin class 
	 * or not.
	 * 
	 * @param domain The {@link ProtectionDomain} requesting a 
	 * {@link Permission}.
	 * @return Returns true if the specified {@link ProtectionDomain} is a 
	 * plugin, false otherwise.
	 */
	private final boolean isPlugin(ProtectionDomain domain)
	{
		return (domain.getClassLoader() instanceof PluginLoader);
	}
	
	/**
	 * Gets a set of {@link Permissions} that allows a class to perform all 
	 * JVM functions.
	 * 
	 * @return A {@link Permissions} object containing {@link AllPermission}.
	 * @since 1.0
	 */
	private final Permissions getDefaultPermissions()
	{
		Permissions perms = new Permissions();
		perms.add(new AllPermission());
		return perms;
	}
	
	/**
	 * Gets an empty set of {@link Permissions} to stop plugin classes from 
	 * doing anything harmful.
	 * 
	 * @return A {@link Permissions} object containing no {@link Permission} 
	 * objects.
	 * @since 1.0
	 */
	private final Permissions getPluginPermissions()
	{
		Permissions perms = new Permissions();
		perms.add(new AWTPermission("showWindowWithoutWarningBanner"));
		perms.add(new AWTPermission("setWindowsAlwaysOnTop"));
		perms.add(new PropertyPermission("*", "read"));
		perms.add(new FilePermission("<<ALL FILES>>", "read"));
		return perms;
	}
}
