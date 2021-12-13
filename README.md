# aujar
## What is aujar ?
Aujar is a tool built using Java for Java projects. It helps you understand the inner working of Java projects that 
you are interested on by creating a simple, easy to understand tree graph which explains relationships between various 
classes in the projects. 

## Why aujar ?
Aujar means tool. So, it is a tool that helps you look deeper inside any java projects. 
* aujar gives you the only classes that you care about and does not include any other classes outside of boundary package path. 
* aujar gives you a ClassComponent Object which will be the root of your tree graph. Since you have access to whole graph tree, you can do whatever you like with it.
* aujar can also get your tree graph in JSON format.
* aujar is lightweight and has no external dependencies. 

## Example
You only need two classes to get the tree graph; Aujar and ClassComponent. 
For example, we are going to be running aujar with open source work scheduling library called quartz (version: 2.3.2). You can get the quartz library from
maven central [here](https://mvnrepository.com/artifact/org.quartz-scheduler/quartz).
Here we are going to be looking at class JobBuilder from org.quartz.JobBuilder path. We want also want to limit
our search to package path org.quartz.

```java
Aujar aujar = Aujar.build("org.quartz.JobBuilder", "org.quartz");
// In order to get root ClassComponent of tree graph
ClassComponent rootCassComponent = aujar.getTree();
// In order to get JSON representation of tree graph
String jsonTree = aujar.getJsonTree();
```
The Json that we get from above example looks like this. 

```json

{
  "classInherits":null, 
  "classImplements":[], 
  "classContains":
                  [
                    {
                      "classInherits":
                                      {
                                        "classInherits":null, 
                                        "classImplements":[], 
                                        "classContains":[], 
                                        "clazz":"class org.quartz.utils.Key"
                                      }, 
                      "classImplements":[], 
                      "classContains":[], 
                      "clazz":"class org.quartz.JobKey"
                    }, 
                    {
                      "classInherits":null, 
                      "classImplements":[], 
                      "classContains":[], 
                      "clazz":"interface org.quartz.Job"
                    }, 
                    {
                      "classInherits": 
                                      {
                                        "classInherits":
                                                        {
                                                          "classInherits":null, 
                                                          "classImplements":[], 
                                                          "classContains":[], 
                                                          "clazz":"class org.quartz.utils.DirtyFlagMap"
                                                        }, 
                                        "classImplements":[], 
                                        "classContains":[], 
                                        "clazz":"class org.quartz.utils.StringKeyDirtyFlagMap"
                                      }, 
                      "classImplements":[], 
                      "classContains":[], 
                      "clazz":"class org.quartz.JobDataMap"
                    }
                  ], 
  "clazz":"class org.quartz.JobBuilder"
}

```