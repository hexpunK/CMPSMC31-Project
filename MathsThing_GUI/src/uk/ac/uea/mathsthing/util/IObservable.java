package uk.ac.uea.mathsthing.util;

/**
 * Observer pattern client interface. Multiple {@link IObserver} instances 
 * can be attached and will all be notified of any changes to the object when 
 * {@link IObservable#update()} is called.
 * 
 * @author Jordan Woerner
 * @version 1.0
 */
public interface IObservable {

	/**
	 * Attaches a new {@link IObserver} to this subject. 
	 * 
	 * @param observable {@link IObserver} object to send updates to.
	 * @since 1.0
	 */
	public void attach(IObserver observable);
	
	/**
	 * Removes an attached {@link IObserver} from this subject.
	 * 
	 * @param observable {@link IObserver} to remove.
	 * @since 1.0
	 */
	public void detach(IObserver observable);
	
	/**
	 * Sends updates to the attached {@link IObserver} instances.
	 * 
	 * @since 1.0
	 */
	public void update();
}
