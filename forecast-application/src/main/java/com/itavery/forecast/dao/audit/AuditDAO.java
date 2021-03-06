package com.itavery.forecast.dao.audit;

import com.itavery.forecast.functional.AuditType;
import com.itavery.forecast.functional.ProductType;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-06-18
 * https://github.com/helloavery
 */

public interface AuditDAO {

    void createUserAuditEvent(String username, AuditType auditCode) throws Exception;

    void createEntryAuditEvent(String username, AuditType auditCode, ProductType productType) throws Exception;
}
