package com.itavery.forecast.domain.util;

import com.itavery.forecast.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-03-10
 * https://github.com/helloavery
 */

public class SerializeUser {

    private static final Logger LOGGER = LogManager.getLogger(SerializeUser.class);

    public void serialize(Integer userId, String firstName, String lastName, String username, String email,
                          String countryCode, String phoneNumber, String region) throws IOException {

        try (FileOutputStream fileOut =
                     new FileOutputStream("/tmp/user.ser"); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            User user = new User();
            user.setUserId(userId);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUsername(username);
            user.setEmail(email);
            user.setCountryCode(countryCode);
            user.setPhoneNumber(phoneNumber);
            user.setRegion(region);
            out.writeObject(user);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/user.ser");
        } catch (IOException e) {
            LOGGER.info("Could not serialize user {}", userId);
            LOGGER.error(e.getMessage(), e);
            throw new IOException("ERROR_SERIALIZING_USER");
        }
    }

}
