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
* @Title: JavaCompileHelper.java 
* @Package com.openthinks.webscheduler.help 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 2, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.help;

import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.openthinks.webscheduler.model.task.def.TaskDefRuntimeData;

/**
 * @author dailey.yet@outlook.com
 *
 */
public final class JavaCompileHelper {

	private static JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
	private static JavaFileFitler javaFileFitler = new JavaFileFitler();

	public static class JCompiler {
		private TaskDefRuntimeData defRuntimeData;
		private DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		private StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(diagnostics, Locale.US,
				Charset.forName("UTF-8"));

		public JCompiler() {
			this.defRuntimeData = new TaskDefRuntimeData();
			this.defRuntimeData.makeDefault();
		}

		public JCompiler(TaskDefRuntimeData defRuntimeData) {
			this.defRuntimeData = defRuntimeData;
		}

		/**
		 * compile java source code at runtime
		 * @return boolean compile success with error or not
		 */
		public boolean exec() {
			Iterable<? extends JavaFileObject> compilationUnits = null;
			if (defRuntimeData.getSourceCode() != null && !defRuntimeData.isKeepSourceFile()) {
				JavaSourceFromString jsfs = new JavaSourceFromString(defRuntimeData.getFullName(),
						defRuntimeData.getSourceCode());
				compilationUnits = Arrays.asList(jsfs);
			} else if (defRuntimeData.isKeepSourceFile()) {
				File[] javaFiles = new File[0];
				if (defRuntimeData.getFileName() == null) {
					javaFiles = defRuntimeData.getSourceDirFile().listFiles(javaFileFitler);
				} else {
					javaFiles = new File[] { defRuntimeData.getSourceFile() };
				}
				compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(javaFiles));
			}
			List<String> options = new ArrayList<>();
			options.add("-d");
			options.add(defRuntimeData.getTargetDir());
			options.add("-classpath");
			URLClassLoader urlClassLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
			StringBuilder sb = new StringBuilder();
			for (URL url : urlClassLoader.getURLs()) {
				sb.append(url.getFile()).append(File.pathSeparator);
			}
			sb.append(defRuntimeData.getTargetDir());
			options.add(sb.toString());
			boolean isSuccess = javaCompiler.getTask(new PrintWriter(System.err), fileManager, diagnostics, options, null, compilationUnits)
					.call();
			return isSuccess;
		}

		public DiagnosticCollector<JavaFileObject> getDiagnostics() {
			return diagnostics;
		}

		class JavaSourceFromString extends SimpleJavaFileObject {
			final String code;

			public JavaSourceFromString(String name, String code) {
				super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
				this.code = code;
			}

			@Override
			public CharSequence getCharContent(boolean ignoreEncodingErrors) {
				return code;
			}
		}
	}

	static class JavaFileFitler implements FilenameFilter {

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(Kind.SOURCE.extension);
		}

	}

	public static JCompiler getCompiler() {
		return new JCompiler();
	}

	public static JCompiler getCompiler(TaskDefRuntimeData defRuntimeData) {
		return new JCompiler(defRuntimeData);
	}
}
