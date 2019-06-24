#!/bin/sh

set -e

mvn -s .travis.settings.xml -Prelease --batch-mode release:prepare release:perform
git push --tags
git push --quiet origin master

