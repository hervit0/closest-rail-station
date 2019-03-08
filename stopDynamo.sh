#!/usr/bin/env bash

docker stop $(docker ps -q --filter ancestor=dynamodbtest) > /dev/null

