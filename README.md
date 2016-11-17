# Webscheduler: coolest task scheduler system  :camel: :cool:

## Introduction
Webscheduler is a simple web task scheduler system, you can set up it quickly and less configuration. Current, this system include following features:

* Support build-in task and persistence on disk
* Basic chart for task status and types
* Unified task input and output,simple and easy to implement it.
* Support customized task definition online
* Simple but flexible security configure and controller
  * include user and roles management
  * provider web page, page element and action access management
  * protection setting for each task definition by override task input risk entry
* Use [Quartz](http://www.quartz-scheduler.org/) as final job scheduling
* Support cron expression as job trigger
* Reload system configuration online

## Getting start

Download or clone project source
```
git clone https://github.com/daileyet/webscheduler.git
```

Go to project source directoty(/pathtodownload/webscheduler/src/main/resources) and edit file **ws-conf.properties** as following:

```
############################################################
# WebScheduler application configure global properties
############################################################
#environment:DEV,TEST,PROD
namespace=PROD
#----DEFAULT----
mapdb.file=file:/path_to_put_stored_data_on_default/webscheduler.odb
security.file=classpath:/conf/security.xml
refs.unchange.path=classpath:/conf/unchange-refs/
logger.level=INFO
quartz.file=classpath:/conf/quartz.properties
#----PROD----
#default setting WEB-INF/classes
#PROD.easyweb.class.dir=file:WEB-INF/classes
PROD.mapdb.file=file:/path_to_put_stored_data_on_production/webscheduler.odb
PROD.refs.unchange.path=classpath:/conf/unchange-refs/PROD/
PROD.logger.level=INFO
#----DEV----
DEV.easyweb.class.dir=file:/path_to_app_class_folder_on_develop/webscheduler/target/classes
DEV.mapdb.file=file:/path_to_put_stored_data_on_develop/webscheduler.odb
DEV.refs.unchange.path=classpath:/conf/unchange-refs/DEV/
DEV.logger.level=DEBUG
```

Go to run/deploy by maven
```
mvn tomcat7:run
```

Access system by URL http://localhost:8080/webscheduler/ and login by admin/123456

## Video & Screenshots
  [Video on YouTube](https://youtu.be/nusM7J4If8Q)
  
**Screenshots**:

![Home page](https://fgkwjw.bn1303.livefilestore.com/y3mHWASdtBxK5q7ZDN23ftMlGBll9gQgK0HJgnsN2v4zZ0HGhvcivR2eJFawkyUGQnyXj7M_IK7CxKQEh8-Pjr3EPJP2GsT8G5wew_9T88lmxL8FMYPP5da0W8zoPzGU3NJGk1sGwHDy0unMuU3V-eEdmhwmY44qMsJCWv0zE736xY?width=1319&height=838&cropmode=none)
 ![Task page](https://ggkwjw.bn1303.livefilestore.com/y3miOVDLzcZySfoSGCZ2nAnLOOzGA__wEx1PTfpg5nnTnxVUzkOdmZT5rZXJAVcviYu8kMIKEjUB04doOlH7JW-GVj4zBpMuF3_6WIy2T-djW1GNY9mYKE6VtI7Yl7U7DllqHj0ZLeWVVdS8Ahl6Zjg-LIYwRZKkpf8FCn9HF_sJBc?width=1319&height=838&cropmode=none)
 ![Setting page](https://h2kwjw.bn1303.livefilestore.com/y3mJ48lnEXPaECSISMzZPP53KtoH5Ejj2oesvhDR8JGc2QI10yE8716H_BqFD3Z6bE-qkXK6SyNmaNhh01rHt0uFXW_26wGO04RivZSsbC_mPU0uF0TcHzIYb8t5du28fU2wixrvzyyFXLqYWj7cvrly3SaIDCPzcQpQMSuIqeKBlM?width=1319&height=838&cropmode=none)
