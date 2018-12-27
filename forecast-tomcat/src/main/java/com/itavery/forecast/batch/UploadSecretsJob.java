package com.itavery.forecast.batch;

import com.google.common.base.Stopwatch;
import com.itavery.forecast.bootconfig.ProgramArguments;
import com.itavery.forecast.external.S3GatewayService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * File created by Avery Grimes-Farrow
 * Created on: 2018-12-18
 * https://github.com/helloavery
 */

public class UploadSecretsJob implements Runnable{

    private static final Logger LOGGER = LogManager.getLogger(UploadSecretsJob.class);

    private final S3GatewayService s3GatewayService;
    private final ProgramArguments programArguments;

    public UploadSecretsJob(final S3GatewayService s3GatewayService, final ProgramArguments programArguments){
        this.s3GatewayService = s3GatewayService;
        this.programArguments = programArguments;
    }

    @Override
    public void run() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        String data = readDataFromFile(programArguments.getS3bucketObjectUploadFile());
        s3GatewayService.sendSecrets(programArguments.getS3bucket(), programArguments.getS3bucketObjectKeyring(),data);
        stopwatch.stop();
        LOGGER.info("Upload to S3 bucket complete, time took is {}", stopwatch.toString());
    }

    private String readDataFromFile(String filePath){
        try{
            Path path = Paths.get(getClass().getClassLoader()
                    .getResource(filePath).toURI());

            Stream<String> lines = Files.lines(path);
            String data = lines.collect(Collectors.joining("\n"));
            lines.close();
            return data;
        }
        catch(Exception e){
            LOGGER.error("Error reading data from file {}", filePath);
            throw new RuntimeException("Error reading data from input file", e);
        }
    }
}
