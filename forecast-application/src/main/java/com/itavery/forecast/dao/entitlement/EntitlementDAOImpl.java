package com.itavery.forecast.dao.entitlement;

import com.itavery.forecast.constants.RoleValues;
import com.itavery.forecast.domain.mithra.user.UserRolesDB;
import com.itavery.forecast.domain.mithra.user.UserRolesDBFinder;
import com.itavery.forecast.exceptions.DAOException;
import com.itavery.forecast.exceptions.InvalidEntitlementException;
import org.apache.http.HttpStatus;
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
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Error checking user entitlement for user");
        }
    }
}
