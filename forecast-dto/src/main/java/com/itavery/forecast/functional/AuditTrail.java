package com.itavery.forecast.functional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/29/19
 * https://github.com/helloavery
 */

public class AuditTrail implements Serializable {

    private static final long serialVersionUID = -2697813356950875547L;

    private String productType;
    private String auditAction;
    private String actionedBy;
    private Timestamp dateActioned;
    private String auditDescription;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getAuditAction() {
        return auditAction;
    }

    public void setAuditAction(String auditAction) {
        this.auditAction = auditAction;
    }

    public String getActionedBy() {
        return actionedBy;
    }

    public void setActionedBy(String actionedBy) {
        this.actionedBy = actionedBy;
    }

    public Timestamp getDateActioned() {
        return dateActioned;
    }

    public void setDateActioned(Timestamp dateActioned) {
        this.dateActioned = dateActioned;
    }

    public String getAuditDescription() {
        return auditDescription;
    }

    public void setAuditDescription(String auditDescription) {
        this.auditDescription = auditDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AuditTrail that = (AuditTrail) o;

        return new EqualsBuilder()
                .append(productType, that.productType)
                .append(auditAction, that.auditAction)
                .append(actionedBy, that.actionedBy)
                .append(dateActioned, that.dateActioned)
                .append(auditDescription, that.auditDescription)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(productType)
                .append(auditAction)
                .append(actionedBy)
                .append(dateActioned)
                .append(auditDescription)
                .toHashCode();
    }
}
