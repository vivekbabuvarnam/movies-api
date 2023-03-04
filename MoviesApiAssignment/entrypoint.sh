#!/bin/sh

APP_JAR=$(ls *.jar)

command="java ${JAVA_OPTS} -jar $APP_JAR"

echo $command

exec $command
