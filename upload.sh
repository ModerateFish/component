#!/bin/sh

./gradlew clean && \
./gradlew :common:uploadArchives && \
./gradlew :extension:uploadArchives && \
./gradlew :framework:uploadArchives && \
./gradlew :widget:uploadArchives