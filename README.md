# hexagonal-spring-boot-kotlin [![Build Status](https://travis-ci.com/devs-from-matrix/hexagonal-spring-boot-kotlin.svg?branch=master)](https://travis-ci.com/devs-from-matrix/hexagonal-spring-boot-kotlin) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/5727f2a507544225bbfa751f8b7d27fd)](https://www.codacy.com/gh/devs-from-matrix/hexagonal-spring-boot-kotlin?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=devs-from-matrix/hexagonal-spring-boot-kotlin&amp;utm_campaign=Badge_Grade)

This project is a template reference for hexagonal spring boot. This repository is to be used by [app-generator](https://github.com/devs-from-matrix/app-generator) for scaffolding.

The keywords of the app-generators are the following

- `packagename` - to rename the package names
- `artifactName` - to rename the artifact id
- `Example` - to rename class, variables 

Use it with caution as these will be used by the app-generator to replace them with domain specific name in the scaffold code. 

## Pre-requisite 

- maven
- open jdk 21

## How to build ?

`mvn clean install`

### How to build a docker image ?

`cd bootstrap && mvn compile jib:dockerBuild`

[More information](https://cloud.google.com/java/getting-started/jib)

## How to start ?

`cd bootstrap && mvn spring-boot:run`

## Formatting

This project uses [git-code-format-maven-plugin](https://github.com/Cosium/git-code-format-maven-plugin) for formatting the code per [google style guide](https://google.github.io/styleguide/javaguide.html)

`mvn git-code-format:format-code`

## Contribution guidelines

We are really glad you're reading this, because we need volunteer developers to help this project come to fruition.

Request you to please read our [contribution guidelines](https://devs-from-matrix.github.io/basic-template-repository/#/README?id=contribution-guidelines)

## Author(s) of this template

- [Sundar](https://github.com/sundardamon)
- [Paul WILLIAMS](https://github.com/paul58914080)
- [Anup KUMAR](https://github.com/anupbaranwal)
