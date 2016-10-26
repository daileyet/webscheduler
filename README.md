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
