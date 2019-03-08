#!/usr/bin/env bash

docker build -t dynamodbtest .
docker run -p 8000:8000 -d dynamodbtest
