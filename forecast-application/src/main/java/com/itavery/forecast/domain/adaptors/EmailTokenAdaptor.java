package com.itavery.forecast.domain.adaptors;

import com.itavery.forecast.user.EmailToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/28/19
 * https://github.com/helloavery
 */

public class EmailTokenAdaptor {

    public static DBObject toDBObject(EmailToken emailToken){
        return new BasicDBObject("_id", emailToken.getEmail()).append("email",emailToken.getEmail()).append("token", emailToken.getToken());
    }

}
