# aujar
## What is aujar 
Aujar is a tool built using Java for Java projects. The purpose of aujar is to help you understand the inner working of 
Java projects that you are interested on by creating a simple, concise and easy to understand tree graph which explains 
relationships between various classes in the projects. Basically, it is a tool which helps you look deeper inside any 
java projects. It is specially helpful when you have to understand the relationship of certain class to all other classes
in a big project. For example if you have to change how configuration are read in a project that you have never worked on
before and you want to make sure the changes you applied to configuration class is not going to break any other part of
program or you simply want to understand how configuration class is linked to other classes in the project, aujar can be
helpful. Following are the features of aujar
* aujar gives you the only classes that you care about and does not include any other classes outside of boundary package path that you have provided. 
* aujar gives you a tree (```ClassComponent```) which will be the root of your tree graph. Since you have access to the whole tree graph, you can do whatever you like with it.
* You can also get your tree in JSON format with aujar.
* You can also get regular graph (```Map<List<Class<?>, List<AjEdge>>```).
* You can also download the svg file with diagram with aujar.
* aujar is lightweight and has no external dependencies at all. 

## Example
You only need to work with ```Aujar``` class to get tree graph, regular graph or to download svg file. For example, 
we are going to be running aujar with open source work scheduling library called quartz (version: 2.3.2). 
You can get the quartz library from maven central [here](https://mvnrepository.com/artifact/org.quartz-scheduler/quartz).
Or if you are using maven you can simply add following in your pom file.
```
<dependency>
    <groupId>org.quartz-scheduler</groupId>
    <artifactId>quartz</artifactId>
    <version>2.3.2</version>
</dependency>
```
Here we are going to be looking at class JobBuilder from org.quartz.JobBuilder path. We also want to limit
our search to package path org.quartz.

```java
Aujar aujar = Aujar.build("org.quartz.JobBuilder", "org.quartz");

// To get graph tree
ClassComponent rootCassComponent = aujar.getTree();

// To get graph tree in JSON 
String jsonTree = aujar.getJsonTree();

// To get normal graph 
Map<Class<?>, List<AjEdge>> edges = aujar.getGraph();

/**
 * To download svg file with graph diagram 
 * download path could be a complete path to a file or just 
 * a folder. In case of folder, a saved svg file will be named 
 * graph.svg
 */
aujar.save("folder/where/you/want/to/download/file");
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
The saved svg diagram from above example looks like this. 

![Alt Image text](./test-data/jobbuilder.svg?raw=true "JobBuilder.svg")