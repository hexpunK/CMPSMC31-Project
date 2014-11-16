package uk.ac.uea.mathsthing.util;

/**
 * Observer pattern server interface. {@link IObserver#update()} will be 
 * called by observed objects , the implementation of update should be able to 
 * access any updated data in the object.
 * 
 * @author Jordan Woerner
 * @version 1.0
 */
public interface IObserver {

	/**
	 * Called by observed objects to notify this {@link IObserver} of changes 
	 * that need attention in the objects.
	 * 
	 * @param data Any extra data that needs passing to the clients.
	 * @since 1.0
	 */
	public void update(Object data);
}
