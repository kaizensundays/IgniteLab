#!/bin/bash

export JAVA_HOME="$JAVA_21_HOME"

export MAVEN_OPTS="-Xmx1g"

mvn clean install -P test

echo Done

read
