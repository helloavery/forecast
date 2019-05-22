package com.itavery.forecast.service.audit;

import com.itavery.forecast.audit.AuditType;
import com.itavery.forecast.product.ProductType;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-06-20
 * https://github.com/helloavery
 */

public interface AuditService {

    void createAudit(String username, AuditType auditCode, ProductType productType) throws Exception;
}
