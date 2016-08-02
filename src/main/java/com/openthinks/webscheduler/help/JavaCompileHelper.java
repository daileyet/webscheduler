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
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * @author dailey.yet@outlook.com
 *
 */
public final class JavaCompileHelper {

	private static JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
	private static JavaFileFitler javaFileFitler = new JavaFileFitler();

	static class JCompiler {
		private File sourceDir;
		private File sourceFile;
		private File targetDir;
		private DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		private StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(diagnostics, Locale.US,
				Charset.forName("UTF-8"));

		public JCompiler() {
			init();
		}

		/**
		 * 
		 * @param sourceFile File source java file 
		 */
		public JCompiler(File sourceFile) {
			super();
			this.sourceFile = sourceFile;
			init();
		}

		/**
		 * @param sourceDir File source java file directory
		 * @param sourceFile File source java file 
		 * @param targetDir File target java compile directory
		 */
		public JCompiler(File sourceDir, File sourceFile, File targetDir) {
			super();
			this.sourceDir = sourceDir;
			this.sourceFile = sourceFile;
			this.targetDir = targetDir;
			init();
		}

		public void setSourceFile(File sourceFile) {
			this.sourceFile = sourceFile;
		}

		public void setSourceDir(File sourceDir) {
			this.sourceDir = sourceDir;
		}

		public void setTargetDir(File targetDir) {
			this.targetDir = targetDir;
		}

		protected void init() {
			if (this.sourceDir == null) {
				this.sourceDir = new File(StaticUtils.getDefaultCustomTaskSourceDir());
			}
			if (this.targetDir == null) {
				this.targetDir = new File(StaticUtils.getDefaultCustomTaskTargetDir());
			}
		}

		/**
		 * compile java source code at runtime
		 * @return boolean compile success with error or not
		 */
		public boolean exec() {
			File[] javaFiles = new File[0];
			if (this.sourceFile == null) {
				javaFiles = sourceDir.listFiles(javaFileFitler);
			} else {
				javaFiles = new File[] { this.sourceFile };
			}
			Iterable<? extends JavaFileObject> compilationUnits = fileManager
					.getJavaFileObjectsFromFiles(Arrays.asList(javaFiles));
			boolean isSuccess = javaCompiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits)
					.call();
			return isSuccess;
		}

		public DiagnosticCollector<JavaFileObject> getDiagnostics() {
			return diagnostics;
		}
	}

	static class JavaFileFitler implements FilenameFilter {

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".java");
		}

	}
}
