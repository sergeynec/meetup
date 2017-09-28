#!/usr/bin/env bash

mvn clean package -Ddev -pl sample-project/web -am -Dspring.profiles.active=h2
