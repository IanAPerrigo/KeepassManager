# KeepassManager
Program to sync Keepass databases to AWS S3.

## Compilation
A gradle task exists for compiling a Fat JAR with Shadow:

```
gradlew shadowJar
```

## Configuration
An example configuration is provided below.

```
bucketName=aws_bucket_name
awsRegion=us-west-1
dbFilePath=/path/to/db.kdbx
keyFilePath=/path/to/db.keyx
accessKey=aws_access_key
secretKey=aws_secret_key
```

## Usage
```
Usage: <main class> [-h] [--[no-]key] [--config=<configLocation>]
      --config=<configLocation>
                   the config file location.
  -h, --help       display a help message
      --[no-]key   sync the key file
```

