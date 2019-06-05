package com.itavery.forecast.service.entitlement;

import com.itavery.forecast.constants.RoleValues;
import com.itavery.forecast.exceptions.InvalidEntitlementException;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-05-25
 * https://github.com/helloavery
 */

public interface EntitlementService {

    void matchEntitlementAgainstUser(int userId, RoleValues role) throws InvalidEntitlementException;
}
