#!/usr/bin/env bash

set -e

VERSION="$1"

if [ -z "$VERSION" ]
then
  echo "Please say which version you want to release"
  echo "Example:"
  echo "    $0 1.0.0"
  exit 1
fi

# release version passed as argument
./set-version.sh $VERSION
gradle clean build publishToMavenLocal -x check -x test

