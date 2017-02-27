/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * @Title: GroupTask.java
 * @Package com.openthinks.webscheduler.task.support
 * @Description: TODO
 * @author dailey.yet@outlook.com
 * @date Aug 29, 2016
 * @version V1.0
 */
package com.openthinks.webscheduler.task.support;

import java.util.Optional;

import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.libs.utilities.CommonUtilities;
import com.openthinks.libs.utilities.InstanceUtilities;
import com.openthinks.libs.utilities.InstanceUtilities.InstanceWrapper;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.help.StaticUtils;
import com.openthinks.webscheduler.model.TaskRunTimeData;
import com.openthinks.webscheduler.model.task.DefaultTaskRef;
import com.openthinks.webscheduler.model.task.ITaskRef;
import com.openthinks.webscheduler.service.TaskService;
import com.openthinks.webscheduler.task.ITaskDefinition;
import com.openthinks.webscheduler.task.TaskContext;
import com.openthinks.webscheduler.task.TaskDefinitionDescriber;
import com.openthinks.webscheduler.task.TaskInterruptException;
import com.openthinks.webscheduler.task.TaskRefDefinitionDescriber;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class GroupTask implements SupportTaskDefinition {
  TaskService taskService = WebContexts.get().lookup(TaskService.class);

  @Override
  public void execute(TaskContext context) {
    TaskRunTimeData taskRunTimeData = getTaskRunTimeData(context).get();
    ITaskRef taskRef = getTaskRefDescriber().createTaskRef();
    try {
      taskRunTimeData.preparedTaskRef(taskRef);
      Optional<String> ops = taskRef.getProp("task-ids");
      if (ops.isPresent()) {
        String taskIds = ops.get();
        String[] ids = taskIds.split(";");
        for (String id : ids) {// execute each task
          ProcessLogger.debug("Processing subtask[" + id + "]");
          TaskContext subtaskCtx = context.clone();
          String subResult = executeSubTask(subtaskCtx, id);
          taskRunTimeData.getLastTaskResult().track(subResult);
          context.syncTaskRuntimeData();
        }
      }
    } catch (Exception e) {
      ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod(), e);
      throw new TaskInterruptException(e);
    }
  }

  /**
   * execute task by task id
   * 
   * @param subContext TaskContext
   * @param id String
   */
  protected String executeSubTask(TaskContext subTaskCtx, String id) {
    String _id = id.trim();
    TaskRunTimeData trtd = null;
    StringBuffer sb = new StringBuffer();
    try {
      trtd = taskService.getTask(_id);
      ITaskDefinition taskInstance = InstanceUtilities.create(ITaskDefinition.class,
          InstanceWrapper.build(trtd.getTaskClass()));
      subTaskCtx.setTaskRuntimeData(trtd);
      taskInstance.execute(subTaskCtx);
      sb.append(StaticUtils.formatNow() + ": Success to execute task[id='" + _id + "',type='"
          + trtd.getTaskType() + "'].");
    } catch (Exception e) {
      ProcessLogger.warn(CommonUtilities.getCurrentInvokerMethod(), e);
      if (trtd != null) {
        sb.append(StaticUtils.formatNow() + ": Failed to execute task[id='" + _id + "',type='"
            + trtd.getTaskType() + "']; ");
      } else {
        sb.append(StaticUtils.formatNow() + ": Failed to execute task[id='" + _id + "']; ");
      }
      sb.append("error message: " + e.getMessage());
    }
    return sb.toString();
  }

  @Override
  public TaskDefinitionDescriber getTaskDescriber() {
    return TaskDefinitionDescriber.build(GroupTask.class)
        .push("Group other tasks as one task, execute them one by one.");
  }

  @Override
  public TaskRefDefinitionDescriber getTaskRefDescriber() {
    TaskRefDefinitionDescriber describer = DefaultTaskRef.getTaskRefDescriber();
    describer.setRequired(true);
    preparedRefDescription(describer);
    return describer;
  }

  private void preparedRefDescription(TaskRefDefinitionDescriber describer) {
    describer.push("#[required]task ids, split by semicolon(;)");
    describer.push(
        "task-ids=0762a2b4-7ae7-4a90-9428-83076bfaa417;03d2a2b4-7ae7-4a90-9428-83076bfaaer58");
  }

}
