package com.itavery.forecast.domain.adaptors;

import com.itavery.forecast.functional.AccountStatusType;
import com.itavery.forecast.user.AccountStatus;
import com.itavery.forecast.user.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.lang3.BooleanUtils;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/29/19
 * https://github.com/helloavery
 */

public class AccountStatusAdaptor {

    public static AccountStatus createNewAccountStatusObject(User user){
        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setUserId(user.getUserId());
        accountStatus.setStatus(AccountStatusType.ACTIVE.getCode());
        accountStatus.setEmailVerified(BooleanUtils.toInteger(false));
        accountStatus.setActiveAndVerified(BooleanUtils.toInteger(false));
        return accountStatus;
    }

    public static AccountStatus createDeactivationStatusObject(int userId){
        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setUserId(userId);
        accountStatus.setStatus(AccountStatusType.DEACTIVATED.getCode());
        accountStatus.setActiveAndVerified(BooleanUtils.toInteger(false));
        return accountStatus;
    }

    public static DBObject toDBObject(AccountStatus accountStatus){
        return new BasicDBObject("_id", accountStatus.getUserId()).
                append("userId", accountStatus.getUserId()).append("status", accountStatus.getStatus())
                .append("emailVerified", accountStatus.getEmailVerified()).append("activeAndVerified", accountStatus.getActiveAndVerified());
    }
}
