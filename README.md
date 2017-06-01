# Introduction to clj_project1



Scaffolding for a web project using Clojure language.

With two tools :

- [Leiningen][1] -build and dependency management tools


- [Ring][2]  -standard Clojure interface to web application container (HTTP request handler); it will map HTTP requests to Clojure function called as Leiningen dependency


Note: it doesn 't use in this project Servlets as known in Java Web development, even if ring-servlet could be used and produce such artefacts.

# WHY CLOJURE ?

A very structured way to understand and nail down the steps required to build the web app container, it can now be compiled into ClosureScript that is a JS coding

Metabase is a cool BI access all_dbms written in clojure
[Metabase] [3]

# Install lein

Download its shell install script and makes it executable

```
sudo curl https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein > /usr/bin/lein
sudo chmod +x /usr/bin/lein
```

Install it now:

```
sudo /usr/bin/lein
```
# Create a new project

```
lein new clj_project1
cd clj_project1 ; ls
```
## Mark Project Dependencies in *project.clj* file by including the following:

```
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.6.1"]]
```

```
  :plugins [[lein-ring "0.12.0"]]
  :ring {:handler clj_project1.core/example-handler
         :init clj_project1.core/on-init
         :port 4001
         :destroy clj_project1.core/on-destroy})

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

## Simplify Common Web Development Tasks with lein-ring plugin

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
[3]:https://github.com/metabase/metabase
