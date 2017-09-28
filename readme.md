# SBERTECH Dev Meetup

## Requirements
* Java 8+
* Maven 3.3.+

## Getting started
1. Install BOM
2. Run appropriate snippet

## Run app-server in dev mode:
1. ```mvn clean package -Ddev -Dspring.profiles.active=h2```
2. `curl -i -X GET 'http://localhost:8008/myApp/phrase/lalala`

## Run app-cli in dev mode:
1. ```mvn clean package -Dspring.profiles.active=h2```
2. ```java -jar sample-project/cli/target/cli.jar ${parameterName}```, _parameterName_ - parameter's  name (lalala for Dev mode)

## Update bom-project
1. Fix dependencies list into `./bom/pom.xml`
2. ```mvn versions:set -DnewVersion=${version} -f bom/pom.xml``` - set new BOM version
3. ```mvn clean install -f bom/pom.xml``` - install into local Maven repo
4. Edit `./pom.xml` to set new BOM version 

## IDEA run configuration
If you use [IDEA](https://www.jetbrains.com/idea/) as your favorite IDE, you can use _Run Configuration_-snippets to run Cli/Jetty/Tests

## Project structure
```
- bom - BOM-file project
- client - Java API to interact with configuration service 
- common - useful utilities
- sample-project - project example
	|___da - data access module
	|___domain - business logic module
	|___web - REST layer module
	|___war - WAR-package module
	|___cli - executable JAR module	
```
