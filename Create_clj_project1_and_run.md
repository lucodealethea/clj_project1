# 1.Create a new project

```
lein new clj_project1
cd clj_project1 ; ls
```
## Mark Project Dependencies in *project.clj* file by including the following:

```
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.6.1"]]
```

Note: to include one of the above libraries, for example `ring-core`, add
the following to your `:dependencies`:

    [ring/ring-core "1.6.1"]

To include all of them:

    [ring "1.6.1"]

ie.
* ring-core - essential functions for handling parameters, cookies and more
* ring-devel - functions for developing and debugging Ring applications
* ring-servlet - construct Java servlets from Ring handlers
* ring-jetty-adapter - a Ring adapter that uses the Jetty webserver


## Simplify Common Web Development Tasks with [lein-ring][4] plugin:

- start and reload, configuring standalone development web server (Ring Jetty adapter to start ...web app container server)
- point to Ring web server handler function
- defining functions to be called upon creation/destruction of the web instance
- (build a war or uberjar) package

add it as a plugin to your `project.clj` file or
your global profile:
```
   :plugins [[lein-ring "0.12.0"]]
```

Then add a new `:ring` key to your `project.clj` file that contains a
map of configuration options. At **minimum** there must be a `:handler`
key that references your Ring handler:

```
    :ring {:handler clj_project1.core/handler}
```
When this is set, you can use Lein-Ring's commands.

##### General lein-ring options

As well as the handler, you can specify several additional options via
your `project.clj` file:

* `:init` -
  A function to be called once before your handler starts. It should
  take no arguments. If you've compiled your Ring application into a
  war-file, this function will be called when your handler servlet is
  first initialized.

* `:destroy` -
  A function called before your handler exits or is unloaded. It
  should take no arguments. If your Ring application has been compiled
  into a war-file, then this will be called when your hander servlet
  is destroyed.

* `:adapter` -
  A map of options to be passed to the Ring adapter. This has no
  effect if you're deploying your application as a war-file.

* `:async?` -
  If true, treat handler as an async handler. Default false.


##### Environment variables

Lein-Ring pays attention to several environment variables, including:

* `PORT`    - the port the web server uses for HTTP
* `SSLPORT` - the port the web server uses for HTTPS

These will override any options specified in the `project.clj` file,
but won't override any options specified at the command line.


#### Handler, init and destroy functions

##### Ring Handler for...
- mapping between HTTP requests and Clojure code
- access to request data
- setting of response data (body, headers and sessions etc..)

> like specify keyword body in the response map:
```
(ns clj_project1.core)
(defn example-handler [request]
  {:body "Hello Clojure Delicate World !"}))
```
> or any other keyword, if we do not specify, the servlet container will have the default value **for example**, for the HTTP Status Code set to 200 </br>
**Note**: Have a look at the 4xx and 5xx codes. They are clearly intended for technical issues, and "understand and satisfy the request" should be taken in this sense. Server cannot understand the request if it has an unsupported media type (415) or the method is not allowed (405). Server cannot satisfy a request if bandwidth limit is exceeded (509) or there was a network read timeout (598). But booking a seat on a full plane has nothing to do with these issues and here the request was technically understood and satisfied even though the plane is full, ergo: HTTP 200.

##### Init or Destroy for one time operations
- configuration initialization
ex. database connections pool and tear down

##### Our project.clj file now somehow looks like:

```
  (defproject project1 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.6.1"]]
  :plugins [[lein-ring "0.12.0"]]
  :ring {:handler clj_project1.core/example-handler
         :init clj_project1.core/on-init
         :port 4001
         :destroy clj_project1.core/on-destroy})

```

##### Our core.clj file looks like
```
(ns clj_project1.core)

(defn example-handler [request]
  {:body "Hello Clojure Delicate World !"})
(defn on-init []
  (println "Initializing this sample webapp !"))
(defn on-destroy []
  (println "Destroying sample webapp!"))
```
### Start a Development Web Server and Live-Edit Our Code

- with Lein and lein-ring plugin running our Ring Handlers

```
# without launching a web browser
lein ring server-headless
```

- will start the server listening on port as per configured our project.clj
(default is port 3000)
- we could also specify the port in the lein-ring instruction
```
lein ring server-headless 4004

### Web server options

The following options affect the behavior of the web server started by
`lein ring server`:

* `:port` - The server port or port range
* `:stacktraces?` -
* `:stacktrace-middleware` -
* `:auto-reload?` -
* `:reload-paths` -
* `:auto-refresh?` -
* `:nrepl` -
  A map of `:start?` and (optionally) `:port` and `:host` keys. If
  `:start?` is true, open up an nREPL server on the given
  port. `:start?` defaults to false, `:port` defaults to an arbitrary
  free port, and `:host` defaults to `"localhost"`.  __NOTE: This
  option is only for development with the `lein ring server` task.
  Setting this option will not cause a generated uberjar/uberwar to
  run an nREPL server.  If you would like to run an nREPL server in
  your production app, then see the clojure.tools.nrepl.server
  project.__

### Executable jar files
Lein-Ring can generate executable jar files for deployment purposes:

    lein ring uberjar

This generates a jar file with all dependencies. You can then copy the
file to your web server and execute it with:

    java -jar <project>-<version>-standalone.jar


## War files

### Compiling

Lein-Ring can generate war files that can be loaded onto legacy Java
web services such as Apache Tomcat:

    lein ring war

A servlet class and web.xml file will be generated automatically, and
your application packaged up in a war file.

Like the `lein jar` command, you can specify the filename being
generated as an additional option:

    lein ring war my-app.war

Also provided is a `lein ring uberwar` command, which packages up all
the dependencies into the war:

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

###
TODO: write [great documentation](http://jacobian.org/writing/what-to-write/)
## Usage

FIXME

## License

Copyright © 2017 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.


[1]: https://github.com/technomancy/leiningen
[2]: https://github.com/ring-clojure/ring
[4]: https://github.com/weavejester/lein-ring
[3]: https://github.com/metabase/metabase
[5]: https://github.com/lucodealethea/clj_project1/blob/master/Create_clj_project1_and_run.md