package com.itavery.forecast.dao.entitlement;

import com.itavery.forecast.enums.RoleValues;
import com.itavery.forecast.exceptions.InvalidEntitlementException;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-05-25
 * https://github.com/helloavery
 */

public interface EntitlementDAO {

    boolean verifyEntitlement(int userId, RoleValues role) throws InvalidEntitlementException;
}
