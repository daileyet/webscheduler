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
 * @Title: SecurityConfig.java
 * @Package com.openthinks.webscheduler.help.confs
 * @Description: TODO
 * @author dailey.yet@outlook.com
 * @date Aug 23, 2016
 * @version V1.0
 */
package com.openthinks.webscheduler.help.confs;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.libs.utilities.CommonUtilities;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.exception.FailedConfigPath;
import com.openthinks.webscheduler.exception.UnSupportConfigPath;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.model.security.WebSecurity;
import com.openthinks.webscheduler.service.WebSecurityService;

/**
 * @author dailey.yet@outlook.com
 *
 */
public final class SecurityConfig extends AbstractConfigObject {
  private WebSecurity webSecurity;

  SecurityConfig(String configPath, ConfigObject parent) {
    super(configPath, parent);
  }

  SecurityConfig(String configPath) {
    super(configPath);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.openthinks.webscheduler.help.confs.ConfigObject#config()
   */
  @Override
  public void config() {
    ProcessLogger.debug(getClass() + " start config...");
    try {
      InputStream in = new FileInputStream(getConfigFile());
      unmarshal(in);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  public File getConfigFile() {
    File configFile = null;
    if (configPath.startsWith(StaticDict.CLASS_PATH_PREFIX)) {// classpath:/conf/security.xml
      String classpath = configPath.substring(StaticDict.CLASS_PATH_PREFIX.length());
      configFile = new File(WebUtils.getWebClassDir(), classpath);
      ProcessLogger.debug(CommonUtilities.getCurrentInvokerMethod(), configFile.getAbsolutePath());
    } else if (configPath.startsWith(StaticDict.FILE_PREFIX)) {// file:R:/MyGit/webscheduler/target/classes/conf/security.xml
      String filePath = configPath.substring(StaticDict.FILE_PREFIX.length());
      File file = new File(filePath), absoulteFile = file, relativeFile = null;
      if (!absoulteFile.exists()) {
        relativeFile = new File(WebUtils.getWebClassDir(), filePath);
        file = relativeFile;
      }
      if (file == null || !file.exists()) {
        throw new FailedConfigPath(configPath);
      }
      configFile = file;
    } else {
      throw new UnSupportConfigPath(configPath);
    }
    return configFile;
  }

  public WebSecurity getWebSecurity() {
    return webSecurity;
  }

  private void unmarshal(InputStream in) throws JAXBException {
    JAXBContext jaxbContext = JAXBContext.newInstance(WebSecurity.class);
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    this.webSecurity = (WebSecurity) unmarshaller.unmarshal(in);
    WebContexts.get().lookup(WebSecurityService.class).init(this);
  }

}
