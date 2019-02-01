# Closest Rail Station

## Overview

This is a learning project in progress.

The app exposes an API to give the closest rail station (UK based).

The tasks can be found here: [trello board](https://trello.com/b/KLKXcGcC)

## Run locally

- app: Investigation in progress
- tests: `sbt test`

## Deploy from local machine

(To be deprecated once CI is enabled)

It's assuming you have correct creds on your local machine
- `./scripts/serverlessDeploy.sh`

## Notes

### Configure AWS via command line

- `aws configure`
- [help](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-configure.html)

### Integrate API Gateway with Lambda

- [AWS doc](https://docs.aws.amazon.com/lambda/latest/dg/with-on-demand-https-example.html)

### Deploying serverless software

- [With serverless](https://read.acloud.guru/continuous-deployment-with-serverless-and-circleci-772f990820ee)
- [With S3 bucket](https://vmokshagroup.com/blog/automating-deployment-of-aws-lambda/)

### Using `serverless` to generate boilerplate

- `serverless create --template aws-scala-sbt` from this [article](https://medium.com/@mkotsur/this-is-why-you-should-consider-using-aws-lambda-scala-6b3ea841f8b0)
- [Serverless Quick Start](https://serverless.com/framework/docs/providers/aws/guide/quick-start/)
- [Using serverless to create Basic Auth within API Gateway](https://medium.com/@Da_vidgf/http-basic-auth-with-api-gateway-and-serverless-5ae14ad0a270)

### `aws-lambda-scala` troubleshooting

- `import io.github.mkotsur.aws.handler.Lambda._` is mandatory in the tests to satisfy the compiler
