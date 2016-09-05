package com.openthinks.webscheduler.help.confs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConfigObjects implements Iterable<ConfigObject> {
	private final List<ConfigObject> configObjects;

	public ConfigObjects() {
		this.configObjects = new ArrayList<>();
	}

	public List<ConfigObject> getConfigObjects() {
		return configObjects;
	}

	public boolean add(ConfigObject configObject) {
		return this.configObjects.add(configObject);
	}

	public boolean isEmpty() {
		return this.configObjects.isEmpty();
	}

	@Override
	public Iterator<ConfigObject> iterator() {
		return configObjects.iterator();
	}

	public void clear() {
		configObjects.clear();

	}

}
