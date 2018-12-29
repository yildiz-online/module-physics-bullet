#!/bin/bash

echo "Building $BRANCH branch"

if [ "$BRANCH" = "develop" ]; then
  echo "Building development version"
  openssl version -a
  openssl aes-256-cbc -pass pass:$OPENSSL_PWD -in private-key.gpg.enc -out private-key.gpg -d && gpg --import --batch private-key.gpg && mvn -V -s settings.xml org.jacoco:jacoco-maven-plugin:prepare-agent clean deploy sonar:sonar -Dsonar.host.url=https://sonarcloud.io -Dsonar.organization=$SONAR_ORGANIZATION -Dsonar.login=$SONAR
elif [ "$BRANCH" = "master" ]; then
  echo "Building release"
  openssl aes-256-cbc -pass pass:$OPENSSL_PWD -in private-key.gpg.enc -out private-key.gpg -d && gpg --import --batch private-key.gpg && mvn -V -s settings.xml clean deploy
elif [ "$BRANCH" = "release" ]; then
  echo "Preparing a new release..."
  git checkout develop
  mvn versions:set -DremoveSnapshot=true
  git commit pom.xml -m "[YE-0] Release"
  git checkout master
  git merge -X theirs develop
  git push origin master
  git checkout develop
  mvn versions:set -DnextSnapshot=true
  git commit pom.xml -m "[YE-0] Prepare next development version."
  git push
else
  echo "Building unhandled branch"
  mvn -V -s settings.xml clean package
fi