#!/bin/bash

mvn verify -Pintegration-test
mvn package
