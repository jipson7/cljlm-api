# cljlm-api

## Prerequisites

* Oracle JDK 7 or 8
* Leiningen 2.0 or higher
* Maven
* at least 11gb of RAM (to load the google corpus)

##Setup

```
mvn install:install-file -Dfile=./berkeleylm.jar -DgroupId=berkeley -DartifactId=nlp -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true -DlocalRepositoryPath=local-maven

```
To set up local maven repo with BerkleyLM JAR. Then run:

```
lein deps

```

to install all dependencies.

## Running

To start a web server for the application, run:

    lein ring server

