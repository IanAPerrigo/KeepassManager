import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import picocli.CommandLine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class KeepassSync {
    public static void main(String[] args) {
        // Process CLI args.
        KeepassCommand keepassCommand = new KeepassCommand();
        new CommandLine(keepassCommand).parseArgs(args);
        Properties conf = new Properties();


        // Read config file.
        try {
            FileInputStream is = new FileInputStream(keepassCommand.configLocation);
            conf.load(is);
        } catch(IOException ioException) {
            System.err.printf("Config at path \"%s\" is not valid.%n", keepassCommand.configLocation);
            System.exit(-1);
        }

        final String BUCKET_NAME = conf.getProperty("bucketName");
        final String AWS_REGION = conf.getProperty("awsRegion");
        final String DB_FILE_PATH = conf.getProperty("dbFilePath");
        final String KEY_FILE_PATH = conf.getProperty("keyFilePath");
        final String ACCESS_KEY = conf.getProperty("accessKey");
        final String SECRET_KEY = conf.getProperty("secretKey");

        // Establish connection to AWS S3 with provided credentials.
        AWSCredentials creds = new BasicAWSCredentials(
                ACCESS_KEY,
                SECRET_KEY
        );

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .withRegion(Regions.fromName(AWS_REGION))
                .build();

        // Upload the DB file to the S3 bucket.
        File dbFile = new File(DB_FILE_PATH);

        s3client.putObject(
                BUCKET_NAME,
                "Database/%s".formatted(dbFile.getName()),
                dbFile
        );

        // Conditionally upload the key file to the S3 bucket.
        if(keepassCommand.keySync) {
            File keyFile = new File(KEY_FILE_PATH);

            s3client.putObject(
                    BUCKET_NAME,
                    "Database/%s".formatted(keyFile.getName()),
                    keyFile
            );
        }
    }
}
