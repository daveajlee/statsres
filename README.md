<p align="center">
<img src="https://www.davelee.de/common/assets/img/portfolio/statsres.webp" alt="Statsres" width="300" height="300">
</p>

<p align=center><a href="https://app.codacy.com/manual/dave_33/statsres?utm_source=github.com&utm_medium=referral&utm_content=daveajlee/statsres&utm_campaign=Badge_Grade_Dashboard"><img src="https://api.codacy.com/project/badge/Grade/de73c59384824981a8c901ec4f2e02d8" alt="Codacy Badge"> </a>
</p>

Statsres is a simple open source free solution for non-profit organisations needing a quick tool to calculate statistical measurements  such as mean, median, standard deviation and others. The output is suitable for publications or further analysis. 

## Current version of Statsres

Statsres is a java program which calculates statistical measurements (including mean, median, standard deviation and others) for single or multiple dataset(s) during a single run. Statsres’s output is suitable for publications or further analysis. It is designed as a simple free solution for non-profit organisations needing a quick tool to perform statistical analysis. The ideas behind Statsres were conceived in 2008 and since then the program has been regularly updated for new Java versions up to and including Java 11. Statsres is an open source project but is no longer in active development.

###  How to run Statsres without checking out the code base

You need the Java 11 Runtime Enviroment (or later) installed on your computer. If you are not sure which version of Java you have, run “java -version” from the command prompt (Windows) or from a terminal (Linux/Mac). You can download the latest version of the Java Runtime Environment <a href="http://java.sun.com/">here</a>.

*   Windows/Linux/Mac: <a href="https://github.com/daveajlee/statsres/packages/881594">JAR File</a> (Download the file to a directory of your choice). After installation, open a console or terminal and navigate to the chosen folder. Once in this directory, run java -jar “statsres-2.2.1.jar” Statsres contains an on-line help system to answer queries and provide further help during use of Statsres.

### How to checkout the code and begin to make changes

This repository contains the current version of Statsres and the current source code in Java. The current version of Statsres is 2.3.0. The next planned version is 2.4.0 - building the current source code builds version 2.4.0 beta:

*   Clone the git branch and import the project in your favourite IDE.
*   The current main class is UserInterface.
*   Dependencies are managed through the pom.xml file and Apache Maven.
*   No database configuration is currently used.
*   JUnit tests can be run either individually or collectively in the IDE or through maven.
*   Releases can be built through Maven - this should happen only after permission from the Repo Owner!
