package com.openthinks.webscheduler.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.process.filter.FileFilterVisitor;
import com.openthinks.libs.utilities.InstanceUtilities;
import com.openthinks.libs.utilities.InstanceUtilities.InstanceWrapper;

/**
 * all task definition keeper
 * @author dailey.yet@outlook.com
 *
 */
public final class TaskTypes {

	private static final List<String> scanedPackages = new ArrayList<>();
	private static final List<Class<? extends ITaskDefinition>> supportTasks = new ArrayList<>();
	private static final List<Class<? extends CustomTaskDefinition>> customTaskDefinitions = new ArrayList<>();
	private static final List<Class<?>> allTasks = new ArrayList<>();
	private static final Map<Class<? extends ITaskDefinition>, ITaskDefinition> taskInstanceSingletonMap = new ConcurrentHashMap<>();
	static {
		scanedPackages.add("com.openthinks.webscheduler.task.support");
		scanedPackages.add("com.openthinks.webscheduler.task.custom");
		scan();
	}

	public static void init() {

	}

	/**
	 * scan and automatically find task definition class 
	 */
	public static void scan() {
		clear();
		for (String packName : scanedPackages) {
			visitPackage(packName);
		}
	}

	public static List<Class<?>> getAllTasks() {
		return allTasks;
	}

	public static List<Class<? extends CustomTaskDefinition>> getCustomTasks() {
		return customTaskDefinitions;
	}

	public static List<Class<? extends ITaskDefinition>> getSupportTasks() {
		return supportTasks;
	}

	public static List<TaskDefinitionMetaData> getSupportTaskMetaData() {
		List<TaskDefinitionMetaData> list = new ArrayList<>();
		supportTasks.stream().forEach(taskClazz -> {
			ITaskDefinition taskDefinition = taskInstanceSingletonMap.get(taskClazz);
			list.add(new TaskDefinitionMetaData(taskDefinition));
		});
		return list;
	}

	public static List<TaskDefinitionMetaData> getCustomTaskMetaData() {
		List<TaskDefinitionMetaData> list = new ArrayList<>();
		customTaskDefinitions.stream().forEach(taskClazz -> {
			ITaskDefinition taskDefinition = taskInstanceSingletonMap.get(taskClazz);
			list.add(new TaskDefinitionMetaData(taskDefinition));
		});
		return list;
	}

	static void clear() {
		allTasks.clear();
		supportTasks.clear();
		customTaskDefinitions.clear();
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
			if (ITaskDefinition.class.isAssignableFrom(clazz)) {
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
			if (CustomTaskDefinition.class.isAssignableFrom(clazz)) {
				Class<CustomTaskDefinition> customTaskClazz = (Class<CustomTaskDefinition>) clazz;
				customTaskDefinitions.add(customTaskClazz);
				if (!taskInstanceSingletonMap.containsKey(customTaskClazz)) {
					ITaskDefinition iTask = InstanceUtilities.create(ITaskDefinition.class,
							InstanceWrapper.build(customTaskClazz));
					taskInstanceSingletonMap.put(customTaskClazz, iTask);
				}
			} else {
				if (isSupportTask(clazz)) {
					Class<ITaskDefinition> supportTaskClazz = (Class<ITaskDefinition>) clazz;
					supportTasks.add(supportTaskClazz);
					if (!taskInstanceSingletonMap.containsKey(supportTaskClazz)) {
						ITaskDefinition iTask = InstanceUtilities.create(ITaskDefinition.class,
								InstanceWrapper.build(supportTaskClazz));
						taskInstanceSingletonMap.put(supportTaskClazz, iTask);
					}
				}
			}
		}

		boolean isSupportTask(Class<?> clazz) {
			Class<?>[] clazzs = clazz.getInterfaces();
			for (Class<?> cl : clazzs) {
				if ("com.openthinks.webscheduler.task.support.SupportTaskDefinition".equals(cl.getName())) {
					return true;
				}
			}
			return false;
		}
	}
}
