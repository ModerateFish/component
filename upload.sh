#!/bin/sh

./gradlew clean && \
./gradlew :common:publish && \
./gradlew :extension:publish && \
./gradlew :framework:publish && \
./gradlew :widget:publish