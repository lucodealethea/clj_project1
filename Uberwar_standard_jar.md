# [lein-ring][1] To Package our Web App with two techniques:


1. By building a standalone uberjar file


1. By building a standard jar web file

### Executable jar files
Lein-Ring can generate executable jar files for deployment purposes:


```
cd /clj_project1
lein ring uberjar
```

This generates a jar file with all dependencies.

*/home/username/clj_project1/target/clj_project1-0.1.0-SNAPSHOT-standalone.jar*

You can then copy the file to your web server and execute inside our Jetty container it with:

```
java -jar <project>-<version>-standalone.jar
java -jar target/clj_project1-0.1.0-SNAPSHOT-standalone.jar


```

## War files for Java Web application container

### Compiling

Lein-Ring can generate war files that can be loaded onto legacy Java
web services such as Apache Tomcat / WebSphere:

    lein ring war

A servlet class and web.xml file will be generated automatically, and
your application packaged up in a war file.

Like the `lein jar` command, you can specify the filename being
generated as an additional option:

    lein ring war my-app.war

Also provided is a `lein ring uberwar` command, which packages up **all
the dependencies into the war:**

    lein ring uberwar

The following war-specific options are supported:

* `:war-exclusions` -
  A list of regular expressions for excluding files from the target
  war. Defaults to excluding hidden files.

* `:servlet-class` -
  The servlet class name.

* `:servlet-name` -
  The name of the servlet (in web.xml). Defaults to the handler name.

* `:url-pattern` -
  The url pattern of the servlet mapping (in web.xml). Defaults to "/*".

* `:servlet-path-info?` -
  If true, a `:path-info` key is added to the request map. Defaults to true.

* `:listener-class` -
  Class used for servlet init/destroy functions. Called listener
  because underneath it uses a ServletContextListener.

* `:web-xml` -
  web.xml file to use in place of auto-generated version (relative to project root).

* `:servlet-version` -
  The version of the servlet spec that we claim to conform
  to. Attributes corresponding to this version will be added to the
  web-app element of the web.xml. If not specified, defaults to 2.5.

* `:uberwar-name` -
  The name of the file generated by lein ring uberwar.

These keys should be placed under the `:ring` key in `project.clj`,
and are optional values. If not supplied, default values will be used instead.

### Resources

A war file can also include additional resource files, such as images or
stylesheets. These should be placed in the directory specified by the
Leiningen `:resources-path` key, which defaults to "resources". These
resources will be placed on the classpath. To include multiple directories,
use the Leiningen `:resource-paths` key, which should be a vector. The
values in `:resources-path` and `:resource-paths` will be concatenated.

However, there is another sort of resource, one accessed through the
`ServletContext` object. These resources are usually not on the classpath,
and are instead placed in the root of the war file. If you happen to need this
functionality, you can place your files in the directory specified by the
`:war-resources-path` key (within the project map, rather than the map
specified by `:ring`), which defaults to "war-resources". (As with
normal resources, here you can use `:war-resource-paths` to include multiple
directories.) It's recommended that you only use WAR resources for
compatibility with legacy Java interfaces; under most circumstances, you
should use the normal `:resources-path` instead.

### Next: [Ring Handlers][4]
### Previous: [Create a simple project and run it][2]

[1]: https://github.com/weavejester/lein-ring
[2]: https://github.com/lucodealethea/clj_project1/blob/master/Create_clj_project1_and_run.md
[3]: https://github.com/clojure/tools.nrepl
[4]: https://github.com/lucodealethea/clj_project1/blob/master/next