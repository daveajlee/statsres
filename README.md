# README #

Thank you for your interest in Statsres - Statistical Analysis Software. This repository contains the current version of Statsres and the current source code in Java. In this document you can read the steps that are necessary to get Statsres up and running.

### What is this repository for? ###

* This repository contains the current version of Statsres and the current source code in Java. The current version of Statsres is 2.1.0. The next planned version is 2.2.0 - building the current source code builds version 2.2.0 beta.

### How do I get set up? ###

* First of all download the current version from Downloads. There are versions for Linux/Mac (zip) and Windows (exe or setup exe). Have a look around and get to know Statsres!
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