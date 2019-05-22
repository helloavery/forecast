package com.itavery.forecast.dao.audit;

import com.itavery.forecast.audit.AuditType;
import com.itavery.forecast.mithra.annotation.Transactional;
import com.itavery.forecast.mithra.product.AuditTrailProductDB;
import com.itavery.forecast.mithra.user.AuditTrailDB;
import com.itavery.forecast.product.ProductType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

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

    @Override
    @Transactional
    public void createUserAuditEvent(String username, AuditType auditCode) throws Exception {
        try {
            AuditTrailDB auditTrail = new AuditTrailDB();
            auditTrail.setAuditAction(auditCode.getAuditCode());
            auditTrail.setAuditDescription(auditCode.getMessage() + username);
            auditTrail.setActionedBy(username);
            auditTrail.setDateActioned(new Timestamp(new Date().getTime()));
            auditTrail.insert();
        } catch (Exception e) {
            LOGGER.error("Could not create audit event for audit code {} and user {}", auditCode, username);
            LOGGER.error(e.getMessage(), e);
            throw new Exception("Audit DAO: Error creating user audit events");
        }
    }

    @Override
    @Transactional
    public void createEntryAuditEvent(String username, AuditType auditCode, ProductType productType) throws Exception {
        try {
            AuditTrailProductDB auditTrailProductDB = new AuditTrailProductDB();
            auditTrailProductDB.setProductType(productType.getValue());
            auditTrailProductDB.setAuditAction(auditCode.getAuditCode());
            auditTrailProductDB.setActionedBy(username);
            auditTrailProductDB.setDateActioned(new Timestamp(new Date().getTime()));
            auditTrailProductDB.setAuditDescription(auditCode.getMessage() + username);
            auditTrailProductDB.insert();
        } catch (Exception e) {
            LOGGER.error("Could not create audit event for audit code {} and user {}", auditCode, username);
            LOGGER.error(e.getMessage(), e);
            throw new Exception("Audit DAO: Error creating user audit events");
        }
    }
}
