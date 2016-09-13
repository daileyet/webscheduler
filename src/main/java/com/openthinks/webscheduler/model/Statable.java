/**   
 *  Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
* @Title: Statable.java 
* @Package com.openthinks.webscheduler.model 
* @author dailey.yet@outlook.com  
* @date Sep 13, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.model;

/**
 * Represent those include state Entity objects need sync between memory and disk.
 * @author dailey.yet@outlook.com
 *
 */
public interface Statable {

	/**
	 * get current {@link State} of this entity object
	 * @return State
	 */
	State getState();

	/**
	 * change current state to the given state
	 * @param nextState
	 */
	void moveTo(State nextState);

	/**
	 * check current state is in sync or out of sync
	 * @return boolean
	 */
	default boolean isInSync() {
		State state = getState();
		return state != null && state.isInSync();
	}

	/**
	 * Status of Object
	 * @author daile
	 *
	 */
	public enum State {
		/**
		 * only exist in memory
		 */
		NEW(false),
		/**
		 * same both in memory and disk
		 */
		SAVED(true),
		/**
		 * changed in memory, different with disk
		 */
		CHANGED(false),
		/**
		 * remove from memory, still exist in disk
		 */
		REMOVED(false);
		private boolean inSync;

		private State(boolean isInSync) {
			this.inSync = isInSync;
		}

		public boolean isInSync() {
			return this.inSync;
		}
	}

	public class DefaultStatable implements Statable {
		protected transient State currentState;

		public DefaultStatable() {
			currentState = State.NEW;
		}

		@Override
		public State getState() {
			return currentState;
		}

		@Override
		public void moveTo(State nextState) {
			this.currentState = nextState;
		}

		public void moveToSaved() {
			this.moveTo(State.SAVED);
		}

		public void moveToChanged() {
			this.moveTo(State.CHANGED);
		}

	}

}
