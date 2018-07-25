#!/bin/bash

mvn release:rollback
mvn release:clean
mvn clean
git tag -d $1
git push --delete origin $1
