/**
 * 
 */
package com.openthinks.webscheduler.model;

/**
 * @author dailey.yet@outlook.com
 *
 */
public interface Statable {
	
	State getState();
	
	/**
	 * Status of Object
	 * @author daile
	 *
	 */
	public enum State {
		IN_SYNC,OUT_SYNC;
	}
	
	void moveTo(State nextState);
}

