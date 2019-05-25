package com.itavery.forecast.service.entitlement;

import com.itavery.forecast.dao.entitlement.EntitlementDAO;
import com.itavery.forecast.enums.RoleValues;
import com.itavery.forecast.exceptions.InvalidEntitlementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-05-25
 * https://github.com/helloavery
 */

@Service
public class EntitlementServiceImpl implements EntitlementService {

    private static final Logger LOGGER = LogManager.getLogger(EntitlementServiceImpl.class);
    @Inject
    EntitlementDAO entitlementDAO;

    @Override
    public void matchEntitlementAgainstUser(int userId, RoleValues role) throws InvalidEntitlementException {
        try{
            boolean isRequestedEntitlementPresent = entitlementDAO.verifyEntitlement(userId, role);
            if(!isRequestedEntitlementPresent){
                LOGGER.error("User does not have the requested entitlements");
                throw new InvalidEntitlementException("Requested user does not have the requested entitlements");
            }
            LOGGER.info("User {} has requested entitlement {}", userId, role.getName());
        }
        catch(Exception e){
            LOGGER.error("Error checking user entitlements for userId {} for entitlement {}", userId, role.getName());
            throw new RuntimeException("Error checking user entitlements");
        }
    }
}
