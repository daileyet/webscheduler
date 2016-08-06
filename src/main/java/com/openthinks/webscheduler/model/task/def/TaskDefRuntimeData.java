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
* @Title: TaskDefRuntimeData.java 
* @Package com.openthinks.webscheduler.model.task.def 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 2, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.model.task.def;

import java.io.File;
import java.io.Serializable;

import com.openthinks.webscheduler.help.StaticUtils;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class TaskDefRuntimeData implements Serializable,Comparable<TaskDefRuntimeData>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1649976392291537981L;
	// xxxx.java
	private String fileName;
	private String fullName;
	private String sourceCode;
	private String sourceDir;
	private String targetDir;
	private CompileResult lastCompileResult;
	private boolean keepSourceFile;

	public void makeDefault() {
		if (sourceDir == null)
			sourceDir = StaticUtils.getDefaultCustomTaskSourceDir();
		if (targetDir == null)
			targetDir = StaticUtils.getDefaultCustomTaskTargetDir();
		if (fullName == null) {
			lastCompileResult = new CompileResult();
		} else {
			lastCompileResult = new CompileResult(getFullName());
		}
	}

	public boolean isKeepSourceFile() {
		return keepSourceFile;
	}

	public void setKeepSourceFile(boolean keepSourceFile) {
		this.keepSourceFile = keepSourceFile;
	}

	public CompileResult getLastCompileResult() {
		return lastCompileResult;
	}

	public void setLastCompileResult(CompileResult lastCompileResult) {
		this.lastCompileResult = lastCompileResult;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getSourceDir() {
		return sourceDir == null ? StaticUtils.getDefaultCustomTaskSourceDir() : sourceDir;
	}

	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}

	public String getTargetDir() {
		return targetDir == null ? StaticUtils.getDefaultCustomTaskTargetDir() : targetDir;
	}

	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}

	public File getSourceFile() {
		return new File(this.getSourceDir(), getFileName());
	}

	public File getSourceDirFile() {
		return new File(this.getSourceDir());
	}

	public File getTargetDirFile() {
		return new File(this.getTargetDir());
	}

	@Override
	public String toString() {
		return "TaskDefRuntimeData [fileName=" + fileName + ", fullName=" + fullName + ", sourceCode=" + sourceCode
				+ ", lastCompileResult=" + lastCompileResult + ", keepSourceFile=" + keepSourceFile
				+ ", sourceDir=" + this.sourceDir + ", targetDir=" + this.targetDir + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
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
		TaskDefRuntimeData other = (TaskDefRuntimeData) obj;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		return true;
	}

	@Override
	public int compareTo(TaskDefRuntimeData o) {
		return getFullName().compareTo(o.getFullName());
	}

}
