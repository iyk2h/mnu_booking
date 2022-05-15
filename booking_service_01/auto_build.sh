#!/bin/sh

app_name="board_web_00"
./mvnw clean install
java -jar ./target/${app_name}-0.0.1-SNAPSHOT.jar 