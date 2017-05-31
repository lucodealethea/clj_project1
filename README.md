# Introduction to clj_project1

Scaffolding for a web project using Clojure language.

With two tools :

- [Leiningen](https://github.com/technomancy/leiningen)  -build and dependency management tools



- [Ring](https://github.com/ring-clojure/ring)  -standard Clojure interface to web application container (HTTP request handler); it will map HTTP requests to Clojure function called as Leiningen dependency

 

Note: it doesn 't use in this project Servlets as known in Java Web development, even if ring-servlet could be used and produce such artefacts.

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


###
TODO: write [great documentation](http://jacobian.org/writing/what-to-write/)
## Usage

FIXME

## License

Copyright © 2017 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
