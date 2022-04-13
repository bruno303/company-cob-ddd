#!/bin/bash

set -e

jvm_args_separator="#"
jvm_rgs=""

function addJvmArg() {
  arg="$1"

  if [[ -z "${jvm_args}" ]]; then
    jvm_args+="$arg"
  else
    jvm_args+="$jvm_args_separator$arg"
  fi
}

addJvmArg "-Dspring.profiles.active=local"
addJvmArg "-Dspring.output.ansi.enabled=ALWAYS"

cmd=(./gradlew :web:bootRun -PjvmArgs="$jvm_args")

echo "running command: ${cmd[@]}"
"${cmd[@]}"
