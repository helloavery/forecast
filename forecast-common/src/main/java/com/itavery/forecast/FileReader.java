package com.itavery.forecast;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-01-10
 * https://github.com/helloavery
 */

public class FileReader {

    private static final Logger LOGGER = LogManager.getLogger(FileReader.class);

    public static String readDataFromFile(String filePath){
        try{
            return new String(Files.readAllBytes(Paths.get(filePath)));
        }
        catch(Exception e){
            LOGGER.error("Error reading data from file {}", filePath);
            throw new RuntimeException("Error reading data from input file", e);
        }
    }
}
