package com.itavery.forecast.external;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  10/30/18            
 |            
 *===========================================================================*/

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailValidationResponse {

    private String address;
    @JsonProperty(value = "is_disposable_address")
    private boolean isDisposableAddress;
    @JsonProperty(value = "is_role_address")
    private boolean isRoleAddress;
    @JsonProperty(value = "is_valid")
    private boolean isValid;
    @JsonProperty(value = "mailbox_verification")
    private boolean mailboxVerification;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDisposableAddress() {
        return isDisposableAddress;
    }

    public void setDisposableAddress(boolean disposableAddress) {
        isDisposableAddress = disposableAddress;
    }

    public boolean isRoleAddress() {
        return isRoleAddress;
    }

    public void setRoleAddress(boolean roleAddress) {
        isRoleAddress = roleAddress;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isMailboxVerification() {
        return mailboxVerification;
    }

    public void setMailboxVerification(boolean mailboxVerification) {
        this.mailboxVerification = mailboxVerification;
    }
}
