package com.itavery.forecast.service.audit;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  6/20/18
 |
 *===========================================================================*/

import com.itavery.forecast.audit.AuditType;
import com.itavery.forecast.product.ProductType;

public interface AuditService {

    void createAudit(String username, AuditType auditCode, ProductType productType) throws Exception;
}
