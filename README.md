# kube-aws-iam-controller AWS Java SDK Example

This is an example demo/verify that the [AWS Java
SDK](https://github.com/aws/aws-sdk-java) works with the
[kube-aws-iam-controller](https://github.com/mikkeloscar/kube-aws-iam-controller).

It works because the Java SDK refreshes credential files at an interval of [5
min.](https://github.com/aws/aws-sdk-java/blob/9cbfa17dd0dd58efe5f3f6db46a95863820522fd/aws-java-sdk-core/src/main/java/com/amazonaws/auth/profile/ProfileCredentialsProvider.java#L37)
ensuring that there are new credentials available when needed.

This assumes you have the [kube-aws-iam-controller running in your
cluster](https://github.com/mikkeloscar/kube-aws-iam-controller#setup).

The example just goes to EC2 every 5 min. and lists the current instances:

```
Aug 25, 2018 10:31:38 AM net.moscar.kubeawsiamexample.App describeEC2
INFO: Getting instances:
Found instance with id i-abcd1234, AMI ami-abcd1234, type t2.medium, state running and monitoring state enabled
Found instance with id i-abcd1234, AMI ami-abcd1234, type t2.medium, state running and monitoring state enabled
Found instance with id i-abcd1234, AMI ami-abcd1234, type t2.medium, state running and monitoring state enabled
```

## Build

```bash
$ mvn package
$ docker build --rm -t mikkeloscar/kube-aws-iam-controller-java-example:latest .
$ docker push mikkeloscar/kube-aws-iam-controller-java-example:latest
```

## Create IAM Role

```bash
# $ASSUME_ROLE_ARN is the arn of the role used by the kube-aws-iam-controller deployment
$ aws cloudformation create-stack --stack-name aws-iam-example \
  --parameters "ParameterKey=AssumeRoleARN,ParameterValue=$ASSUME_ROLE_ARN" \
  --template-body=file://iam-role.yaml --capabilities CAPABILITY_NAMED_IAM
```

## Deploy exmaple

```bash
$ kubectl apply -f deployment.yaml
```
