package com.openthinks.webscheduler.task;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.process.filter.file.FileFilterVisitor;
import com.openthinks.libs.utilities.InstanceUtilities;
import com.openthinks.libs.utilities.InstanceUtilities.InstanceWrapper;
import com.openthinks.webscheduler.help.StaticUtils;

/**
 * all task definition keeper
 * @author dailey.yet@outlook.com
 *
 */
public final class TaskTypes {

	private static final Set<PackageUnit> scanedPackages = new HashSet<>();
	private static final List<Class<? extends ITaskDefinition>> supportTasks = new ArrayList<>();
	private static final List<Class<? extends CustomTaskDefinition>> customTaskDefinitions = new ArrayList<>();
	private static final List<Class<?>> allTasks = new ArrayList<>();
	private static final Map<Class<? extends ITaskDefinition>, ITaskDefinition> taskInstanceSingletonMap = new ConcurrentHashMap<>();

	static class PackageUnit {
		private String packageRootDir;
		private String packageName;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
			result = prime * result + ((packageRootDir == null) ? 0 : packageRootDir.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PackageUnit other = (PackageUnit) obj;
			if (packageName == null) {
				if (other.packageName != null)
					return false;
			} else if (!packageName.equals(other.packageName))
				return false;
			if (packageRootDir == null) {
				if (other.packageRootDir != null)
					return false;
			} else if (!packageRootDir.equals(other.packageRootDir))
				return false;
			return true;
		}

		static PackageUnit of(String packageName) {
			return of(WebUtils.getWebClassDir(), packageName);
		}

		static PackageUnit of(String rootDir, String packageName) {
			PackageUnit pu = new PackageUnit();
			pu.packageRootDir = rootDir;
			pu.packageName = packageName;
			return pu;
		}

		public String getPackageName() {
			return packageName;
		}

		public String getPackageRootDir() {
			if (this.packageRootDir == null) {
				this.packageRootDir = WebUtils.getWebClassDir();
			}
			return packageRootDir;
		}
	}

	static {
		scanedPackages.add(PackageUnit.of("com.openthinks.webscheduler.task.support"));
		scanedPackages.add(PackageUnit.of("com.openthinks.webscheduler.task.custom"));
		scanedPackages.add(
				PackageUnit.of(StaticUtils.getDefaultCustomTaskTargetDir(), "com.openthinks.webscheduler.task.custom"));
		scan();
	}

	public static void init() {

	}

	/**
	 * scan and automatically find task definition class 
	 */
	public static void scan() {
		clear();
		for (PackageUnit packName : scanedPackages) {
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

	public static Optional<TaskDefinitionMetaData> getTaskMetaData(Class<? extends ITaskDefinition> clazz) {
		ITaskDefinition taskDefinition = taskInstanceSingletonMap.get(clazz);
		if (taskDefinition == null) {
			return Optional.empty();
		}
		return Optional.of(new TaskDefinitionMetaData(taskDefinition));
	}

	static void clear() {
		allTasks.clear();
		supportTasks.clear();
		customTaskDefinitions.clear();
	}

	private static void visitPackage(PackageUnit pu) {
		String packPath = WebUtils.contactFilePath(pu.getPackageRootDir(),
				WebUtils.getPackagePath(pu.getPackageName()));
		File dir = new File(packPath);
		dir.listFiles(new TaskFilterVistor(pu, allTasks));
	}

	static class TaskFilterVistor extends FileFilterVisitor {
		private final PackageUnit pu;

		public TaskFilterVistor(final PackageUnit pu, List<Class<?>> filterClasss) {
			super(filterClasss);
			this.pu = pu;
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

		@Override
		protected String getFullClassName(File file) {
			return WebUtils.getClassName(file.getAbsolutePath(), pu.getPackageRootDir());
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
