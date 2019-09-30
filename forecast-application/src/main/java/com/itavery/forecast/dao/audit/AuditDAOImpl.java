package com.itavery.forecast.dao.audit;

import com.itavery.forecast.Constants;
import com.itavery.forecast.domain.adaptors.AuditTrailAdaptor;
import com.itavery.forecast.domain.mongodb.MongoDBBase;
import com.itavery.forecast.functional.AuditTrail;
import com.itavery.forecast.functional.AuditType;
import com.itavery.forecast.functional.ProductType;
import com.itavery.forecast.utils.exceptions.DAOException;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-06-18
 * https://github.com/helloavery
 */

@Repository
public class AuditDAOImpl implements AuditDAO {

    private static final Logger LOGGER = LogManager.getLogger(AuditDAOImpl.class);

    private MongoDBBase mongoDBBase;

    @Inject
    public void setMongoDBBase(MongoDBBase mongoDBBase) {
        this.mongoDBBase = mongoDBBase;
    }

    @Override
    public void createUserAuditEvent(String username, AuditType auditCode) throws Exception {
        try {
            AuditTrail auditTrail = new AuditTrail();
            auditTrail.setAuditAction(auditCode.getAuditCode());
            auditTrail.setAuditDescription(auditCode.getMessage() + username);
            auditTrail.setActionedBy(username);
            auditTrail.setDateActioned(new Timestamp(new Date().getTime()));
            mongoDBBase.insertDocument(Constants.AUDIT_TRAIL_MONGO_COLLECTION, AuditTrailAdaptor.toDBObject(auditTrail));
        } catch (Exception e) {
            LOGGER.error("Could not create audit event for audit code {} and user {}", auditCode, username);
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Audit DAO: Error creating user audit events");
        }
    }

    @Override
    public void createEntryAuditEvent(String username, AuditType auditCode, ProductType productType) throws Exception {
        try {
            AuditTrail auditTrail = new AuditTrail();
            auditTrail.setProductType(productType.getValue());
            auditTrail.setAuditAction(auditCode.getAuditCode());
            auditTrail.setActionedBy(username);
            auditTrail.setDateActioned(new Timestamp(new Date().getTime()));
            auditTrail.setAuditDescription(auditCode.getMessage() + username);
            mongoDBBase.insertDocument(Constants.AUDIT_TRAIL_MONGO_COLLECTION, AuditTrailAdaptor.toDBObject(auditTrail));
        } catch (Exception e) {
            LOGGER.error("Could not create audit event for audit code {} and user {}", auditCode, username);
            LOGGER.error(e.getMessage(), e);
            throw DAOException.buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Audit DAO: Error creating user audit events");
        }
    }
}
