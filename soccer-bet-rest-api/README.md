## Build

```
-Damazon.aws.accesskey=
-Damazon.aws.secretkey=
-Damazon.aws.region=
-Dserver.port=7777
```

`mvn spring-boot:run` with the above VM options. 

## Push to ECR

```
docker build -f Dockerfile -t soccer-bet-rest-api .
docker images
aws configure
aws ecr get-login-password --region ap-southeast-2 | docker login --username AWS --password-stdin 527875336349.dkr.ecr.ap-southeast-2.amazonaws.com
aws ecr create-repository --repository-name soccer-bet # Only if the repository has been created
docker tag soccer-bet-rest-api:latest 527875336349.dkr.ecr.ap-southeast-2.amazonaws.com/soccer-bet
docker push 527875336349.dkr.ecr.ap-southeast-2.amazonaws.com/soccer-bet
```

## Deploy to ECS

### Create stack

```
aws cloudformation create-stack --stack-name soccer-bet-stack --template-body file://./ecs.yml --capabilities CAPABILITY_NAMED_IAM --parameters ParameterKey=SubnetID,ParameterValue=subnet-97648ff1 ParameterKey=AccessKeySecretKey,ParameterValue=accessKeySecretKey
```

### Update stack

```
aws cloudformation update-stack --stack-name soccer-bet-stack --template-body file://./ecs.yml --capabilities CAPABILITY_NAMED_IAM --parameters 'ParameterKey=SubnetID,ParameterValue=subnet-97648ff1'
```

### Delete stack

```aws cloudformation delete-stack --stack-name soccer-bet-stack```
