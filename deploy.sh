#!/usr/bin/env bash
mvn package
sam package --template-file sam.yaml --s3-bucket jonathanmv-smart-host-code-challenge --output-template-file target/deploy.yaml
sam deploy --template-file target/deploy.yaml --stack-name smart-host-code-challenge --capabilities CAPABILITY_IAM
