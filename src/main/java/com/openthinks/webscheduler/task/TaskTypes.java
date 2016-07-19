package com.openthinks.webscheduler.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.process.filter.FileFilterVisitor;

public final class TaskTypes {

	private static final List<String> scanedPackages = new ArrayList<String>();
	private static final List<Class<? extends ITask>> supportTasks = new ArrayList<Class<? extends ITask>>();
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

	public static List<Class<? extends ITask>> getSupportTasks() {
		return supportTasks;
	}

	static void clear() {
		allTasks.clear();
		supportTasks.clear();
		customTasks.clear();
	}

	private static void visitPackage(String packName) {
		String packPath = WebUtils.getPackPath(packName);
		File dir = new File(packPath);
		dir.listFiles(new TaskFilterVistor(allTasks));
	}

	static class TaskFilterVistor extends FileFilterVisitor {

		public TaskFilterVistor(List<Class<?>> filterClasss) {
			super(filterClasss);
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
				if (isSupportTask(clazz)) {
					supportTasks.add((Class<? extends ITask>) clazz);
				}
			}
		}

		boolean isSupportTask(Class<?> clazz) {
			Class<?>[] clazzs = clazz.getInterfaces();
			for (Class<?> cl : clazzs) {
				if ("com.openthinks.webscheduler.task.support.SupportTask".equals(cl.getName())) {
					return true;
				}
			}
			return false;
		}
	}
}
