# sqs-message-driver

Code examples for sqs message driver using AWS SDK for Java 2.x

## Prerequisites

- Docker
- Docker Compose
- AWS CLI (Optional)

## Setup

```bash
$ docker-compose up -d
```

## Usage

create queue

```bash
$ aws sqs create-queue --queue-name test --endpoint-url http://localhost:4566
{
    "QueueUrl": "http://localhost:4566/000000000000/test"
}
```

list queues

```bash
$ aws sqs list-queues --endpoint-url http://localhost:4566
{
    "QueueUrls": [
        "http://localhost:4566/000000000000/test"
    ]
}
```

## Test

```bash
$ ./gradlew test
```
