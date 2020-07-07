# README #

Statsres is a java program which calculates statistical measurements (including mean, median, standard deviation and others) for single or multiple dataset(s) during a single run. Statsres’s output is suitable for publications or further analysis. It is designed as a simple free solution for non-profit organisations needing a quick tool to perform statistical analysis. The ideas behind Statsres were conceived in 2008 and since then the program has been regularly updated for new Java versions up to and including Java 8. Statsres is an open source project but is no longer in active development.

This repository contains the current version of Statsres and the current source code in Java. In this document you can read the steps that are necessary to get Statsres up and running.

### How to run Statsres without checking out the code base ###

You need the Java 8 Runtime Enviroment (or later) installed on your computer. If you are not sure which version of Java you have, run “java -version” from the command prompt (Windows) or from a terminal (Linux/Mac). You can download the latest version of the Java Runtime Environment <a href="http://java.sun.com/">here</a>.

Windows: <a href="https://github.com/daveajlee/statsres/blob/master/downloads/statsres-2.1.0.exe">Setup File</a> (Download and run this file and follow the onscreen instructions). After installation, you can run Statsres from the start menu or the desktop.

Linux/Mac:</span> <a href="https://github.com/daveajlee/statsres/blob/master/downloads/statsres-2.1.0.zip">ZIP File</a> (Download the file to a directory of your choice). Extract the files from this zip into a directory of your choice. After extraction, open a console or terminal and navigate to the folder where you extracted the files to. Once in this directory, run java -jar “Statsres.jar”.

### How can I checkout the code and begin to make changes? ###

This repository contains the current version of Statsres and the current source code in Java. The current version of Statsres is 2.1.0. The next planned version is 2.2.0 - building the current source code builds version 2.2.0 beta:

* Clone the git branch and import the project in your favourite IDE.
* The current main class is UserInterface.
* Dependencies are managed through the pom.xml file and Apache Maven.
* No database configuration is currently used.
* JUnit tests can be run either individually or collectively in the IDE or through maven.
* Releases can be built through Maven - this should happen only after permission from the Repo Owner!

### Contribution guidelines ###

* Before developing a feature please discuss your ideas with the Repo Owner.
* Functional Tests should be written through JUnit and ensure a code coverage of at least 70% - use SonarQube to check code coverage.
* Code review should take place before a release is built and involve the Repo Owner and the Contributor.

### Who do I talk to? ###

* Dave Lee (Repo Owner)
