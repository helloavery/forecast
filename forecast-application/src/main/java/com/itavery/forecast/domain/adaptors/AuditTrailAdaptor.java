package com.itavery.forecast.domain.adaptors;

import com.itavery.forecast.functional.AuditTrail;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/29/19
 * https://github.com/helloavery
 */

public class AuditTrailAdaptor {

    public static DBObject toDBObject(AuditTrail auditTrail){
        return new BasicDBObject().append("productType", auditTrail.getProductType()).append("auditAction", auditTrail.getAuditAction())
                .append("actionedBy",  auditTrail.getActionedBy()).append("dateActioned", auditTrail.getDateActioned())
                .append("auditDescription", auditTrail.getAuditDescription());
    }
}
