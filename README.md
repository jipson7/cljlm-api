# cljlm-api

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

##Setup

First Run

```
mvn install:install-file -Dfile=./berkeleylm.jar -DgroupId=berkeley -DartifactId=nlp -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true -DlocalRepositoryPath=local-maven

```
To set up local maven repo with BerkleyLM JAR. Then run:

```
lein deps

```

To install all dependencies

## Running

To start a web server for the application, run:

    lein ring server

