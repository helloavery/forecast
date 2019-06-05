package com.itavery.forecast.dao.entitlement;

import com.itavery.forecast.constants.RoleValues;
import com.itavery.forecast.exceptions.InvalidEntitlementException;
import com.itavery.forecast.mithra.user.UserRolesDB;
import com.itavery.forecast.mithra.user.UserRolesDBFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-05-25
 * https://github.com/helloavery
 */

@Repository
public class EntitlementDAOImpl implements EntitlementDAO{

    private static final Logger LOGGER = LogManager.getLogger(EntitlementDAOImpl.class);

    @Override
    public boolean verifyEntitlement(int userId, RoleValues role) throws InvalidEntitlementException {
        try{
            UserRolesDB userRolesDB = UserRolesDBFinder.findOne(UserRolesDBFinder.userId().eq(userId)
                    .and(UserRolesDBFinder.roleId().eq(role.getUserRoleValue())));
            return userRolesDB != null;
        }
        catch(Exception e){
            LOGGER.error("Error checking user entitlement for user {} and role {}", userId, role.getName());
            throw new RuntimeException("Error checking user entitlement for user");
        }
    }
}
