# README #

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/de73c59384824981a8c901ec4f2e02d8)](https://app.codacy.com/manual/dave_33/statsres?utm_source=github.com&utm_medium=referral&utm_content=daveajlee/statsres&utm_campaign=Badge_Grade_Dashboard)

![example workflow](https://github.com/daveajlee/statsres/actions/workflows/maven.yml/badge.svg)

Statsres is a java program which calculates statistical measurements (including mean, median, standard deviation and others) for single or multiple dataset(s) during a single run. Statsres’s output is suitable for publications or further analysis. It is designed as a simple free solution for non-profit organisations needing a quick tool to perform statistical analysis. The ideas behind Statsres were conceived in 2008 and since then the program has been regularly updated for new Java versions up to and including Java 11. Statsres is an open source project but is no longer in active development.

This repository contains the current version of Statsres and the current source code in Java. In this document you can read the steps that are necessary to get Statsres up and running.

## How to run Statsres without checking out the code base ##

You need the Java 11 Runtime Enviroment (or later) installed on your computer. If you are not sure which version of Java you have, run “java -version” from the command prompt (Windows) or from a terminal (Linux/Mac). You can download the latest version of the Java Runtime Environment <a href="http://java.sun.com/">here</a>.

*   Windows/Linux/Mac: <a href="https://github-registry-files.githubusercontent.com/248600506/c32c8780-dc4d-11eb-9560-7477fd4c4af0?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIWNJYAX4CSVEH53A%2F20210704%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210704T145237Z&X-Amz-Expires=300&X-Amz-Signature=38ceea6a8d1cdfe22b14ac9021e9d9f8317778800029cc4d68842089d8d6600e&X-Amz-SignedHeaders=host&actor_id=0&key_id=0&repo_id=248600506&response-content-disposition=filename%3Dstatsres-2.2.1.jar&response-content-type=application%2Foctet-stream">JAR File</a> (Download the file to a directory of your choice). After installation, open a console or terminal and navigate to the chosen folder. Once in this directory, run java -jar “statsres-2.2.1.jar” Statsres contains an on-line help system to answer queries and provide further help during use of Statsres.

## How to checkout the code and begin to make changes ##

This repository contains the current version of Statsres and the current source code in Java. The current version of Statsres is 2.2.1. The next planned version is 2.3.0 - building the current source code builds version 2.3.0 beta:

*   Clone the git branch and import the project in your favourite IDE.
*   The current main class is UserInterface.
*   Dependencies are managed through the pom.xml file and Apache Maven.
*   No database configuration is currently used.
*   JUnit tests can be run either individually or collectively in the IDE or through maven.
*   Releases can be built through Maven - this should happen only after permission from the Repo Owner!

## Contribution guidelines ##

*   Before developing a feature please discuss your ideas with the Repo Owner.
*   Functional Tests should be written through JUnit and ensure a code coverage of at least 70%.
*   Code review should take place before a release is built and involve the Repo Owner and the Contributor.

## Contact Person ##

*   Dave Lee (Repo Owner)
