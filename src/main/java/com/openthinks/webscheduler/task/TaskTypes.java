package com.openthinks.webscheduler.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.openthinks.easyweb.annotation.process.WebProcesser;
import com.openthinks.easyweb.annotation.process.WebProcesser.FileFilterVistor;
import com.openthinks.easyweb.context.WebContexts;

public final class TaskTypes {

	private static final List<String> scanedPackages = new ArrayList<String>();
	private static final List<Class<? extends SupportTask>> supportTasks = new ArrayList<Class<? extends SupportTask>>();
	private static final List<Class<? extends CustomTask>> customTasks = new ArrayList<Class<? extends CustomTask>>();
	private static final List<Class<?>> allTasks = new ArrayList<Class<?>>();
	static {
		scanedPackages.add("com.openthinks.webscheduler.task.support");
		scanedPackages.add("com.openthinks.webscheduler.task.custom");
		scan();
	}

	public static void init() {

	}

	public static void scan() {
		clear();
		for (String packName : scanedPackages) {
			visitPackage(packName);
		}
	}

	public static List<Class<?>> getAllTasks() {
		return allTasks;
	}

	public static List<Class<? extends CustomTask>> getCustomTasks() {
		return customTasks;
	}

	public static List<Class<? extends SupportTask>> getSupportTasks() {
		return supportTasks;
	}

	static void clear() {
		allTasks.clear();
		supportTasks.clear();
		customTasks.clear();
	}

	private static void visitPackage(String packName) {
		final WebProcesser webProcesser = WebContexts.get().getWebProcesser();
		String packPath = webProcesser.getPackPath(packName);
		File dir = new File(packPath);
		dir.listFiles(new TaskFilterVistor(webProcesser, allTasks));
	}

	static class TaskFilterVistor extends FileFilterVistor {

		public TaskFilterVistor(WebProcesser webProcesser, List<Class<?>> filterClasss) {
			webProcesser.super(filterClasss);
		}

		@Override
		public boolean acceptClassType(Class<?> clazz) {
			if (ITask.class.isAssignableFrom(clazz)) {
				return true;
			}
			return false;
		}

		@Override
		public boolean acceptClassName(File file) {
			return true;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void doAddition(Class<?> clazz) {
			if (CustomTask.class.isAssignableFrom(clazz)) {
				customTasks.add((Class<? extends CustomTask>) clazz);
			} else {
				supportTasks.add((Class<? extends SupportTask>) clazz);
			}
		}

	}
}
