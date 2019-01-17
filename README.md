# aws-dynamodb
Java lib to interact with AWS DynamoDB

# Getting started with Dynamodb for Java local development
- Install the AWS Command Line Interface from [here](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html).
- Setup local DynamoDB by following the instruction [here](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html).
    - Docker fans! Simply run `docker run -p 8000:8000 amazon/dynamodb-local` to execute DynamoDB locally.
- Configure local DynamoDB using `aws configure`
    ```
    AWS Access Key ID : None
    AWS Secret Access Key : None
    Default region name : local
    Default output format : json
    ```
    All set. Start developing with AWS Dynamodb.