package com.itavery.forecast.service.audit;

import com.itavery.forecast.audit.AuditType;
import com.itavery.forecast.dao.audit.AuditDAO;
import com.itavery.forecast.product.ProductType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-06-20
 * https://github.com/helloavery
 */

@Service
public class AuditServiceImpl implements AuditService {

    private final static Logger LOGGER = LogManager.getLogger(AuditServiceImpl.class);

    private final static String USER_ACCOUNT_ACTIVITY = "ACCOUNT";
    private final static String EMAIL_ACCOUNT_ACTIVITY = "EMAIL";
    private final static String ENTRY_ACCOUNT_ACTIVITY = "ENTRY";
    @Inject
    private AuditDAO auditDAO;

    @Override
    public void createAudit(String username, AuditType auditCode, ProductType productType) throws Exception {
        try {
            LOGGER.info("Determining auditing type for {}", auditCode);
            if (auditCode.getAuditCode().contains(USER_ACCOUNT_ACTIVITY) || auditCode.getAuditCode().contains(EMAIL_ACCOUNT_ACTIVITY)) {
                LOGGER.info("Event is user account related, creating user audit event");
                auditDAO.createUserAuditEvent(username, auditCode);
            } else if (auditCode.getAuditCode().contains(ENTRY_ACCOUNT_ACTIVITY)) {
                LOGGER.info("Event is entry related, creating entry audit event");
                auditDAO.createEntryAuditEvent(username, auditCode, productType);
            }
        } catch (Exception e) {
            LOGGER.error("AuditServiceImpl Error: Could not create audit event for username {} and code {}", username, auditCode);
            LOGGER.error(e.getMessage(), e);
            throw new Exception("AuditServiceImpl Error: Could not create audit event");
        }
    }
}
