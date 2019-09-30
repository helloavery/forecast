package com.itavery.forecast.domain.adaptors;

import com.averygrimes.core.pojo.RoleValues;
import com.itavery.forecast.functional.Regions;
import com.itavery.forecast.request.RegistrationRequest;
import com.itavery.forecast.user.AccountStatus;
import com.itavery.forecast.user.User;
import com.itavery.forecast.user.UserCreds;
import com.itavery.forecast.user.UserRole;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.lang3.math.NumberUtils;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-10-09
 * https://github.com/helloavery
 */

public class UserAdaptor {

    public static User toUserObject(RegistrationRequest request, int authyId, Regions region){
        User user = new User();
        user.setAuthyId(authyId);
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setCountryCode(request.getCountryCode());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRegion(region.getCode());
        return user;
    }

    public static UserCreds toNewUserCredsObject(RegistrationRequest request){
        UserCreds userCreds = new UserCreds();
        ObjectId objectId = new ObjectId();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userCreds.setUserId(NumberUtils.toInt(objectId.toString()));
        userCreds.setPassword(passwordEncoder.encode(request.getPassword()));
        userCreds.setCreatedDate(new Timestamp(new Date().getTime()));
        return userCreds;
    }

    public static UserCreds toUserCredsObject(User request, RegistrationRequest registrationRequest){
        UserCreds userCreds = new UserCreds();
        userCreds.setUserId(request.getUserId());
        userCreds.setPassword(registrationRequest.getPassword());
        return userCreds;
    }

    public static UserRole toNewUserRoleObject(User user, RoleValues roleValue){
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(roleValue.getUserRoleValue());
        return userRole;
    }

    public static DBObject toDBObject(User user){
        return new BasicDBObject("_id", user.getUserId()).append("authyId", user.getAuthyId()).append("firstName", user.getFirstName())
                .append("lastName", user.getLastName()).append("username", user.getUsername()).append("email", user.getEmail())
                .append("countryCode", user.getCountryCode()).append("phoneNumber", user.getPhoneNumber()).append("region", user.getRegion());
    }

    public static DBObject toDBObject(AccountStatus accountStatus){
        return new BasicDBObject("_id", accountStatus.getUserId()).append("status", accountStatus.getStatus())
                .append("emailVerified", accountStatus.getEmailVerified()).append("activeAndVerified", accountStatus.getActiveAndVerified());
    }

    public static DBObject toDBObject(UserCreds userCreds){
        return new BasicDBObject("_id", userCreds.getUserId()).append("password", userCreds.getPassword()).append("createdDate", userCreds.getCreatedDate());
    }

    public static DBObject toDBObject(UserRole userRole){
        return new BasicDBObject("_id", userRole.getUserId()).append("roleId", userRole.getRoleId());
    }
}
