package com.itavery.forecast;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-05-28
 * https://github.com/helloavery
 */

public interface Constants {

    /***Prefixes****/
    String LOGIN_PREFIX = "launch/login/";
    String REGISTRATION_PREFIX = "launch/registration/";
    String HOME_PREFIX = "launch/";
    String USERID_PREFIX = "USERID: ";


    String FORECASTER_AUTO_ACC = "b1forecastauto";
    String HOST_URL_STRING = "http//forecast.itavery.com";

    /***Authy Constants****/
    String AUTHY_SEND_SMS_OTP = "https://api.authy.com/protected/json/sms/%s";
    String AUTHY_VOICE_OTP="https://api.authy.com/protected/json/call/%s";
    String AUTHY_VERIFY_OTP = "https://api.authy.com/protected/json/verify/%s/%s";

    /***Mailgun and E-mail constants******/
    String MAILGUN_MAILBOX_VERIFICATION_URL = "https://api.mailgun.net/v3/address/private/validate";
    String VERIFY_EMAIL_ADDRESS_API = "/api/verify_email/%s";
    String MAILGUN_DOMAIN_NAME = "forecast.itavery.com";

    /******Session Constants******/
    int MAX_INACTIVE_INTERVAL = 30 * 60;
    String AUTHENTICATED = "authenticated";
    String PARTIALLY_AUTHENTICATED = "partially-authenticated";
    String USER_ID = "user-id";
    String USER_NAME = "user-name";

    /****Service Operation Results****/
    String SERVICE_CANNOT_REGISTER_USER = "Cannot register user";
    String SERVICE_AUTHY_USER_NOT_CREATED = "Auhty user not created";
    String SERVICE_USER_NOT_CREATED =  "User not created";
    String SERVICE_CANNOT_VERIFY_AUTHY_USER = "Cannot verify Authy user";
    String SERVICE_CANNOT_LOGIN_USER = "Cannot login user";
    String SERVICE_CANNOT_VERIFY_OTP = "Cannot verify OTP";
    String SERVICE_USER_NOT_FOUND = "User not found";
    String SERVICE_ERROR_CREATING_AUDIT_EVENT = "Could not create audit event";
    String SERVICE_ERROR_UPDATING_USER = "Error updating user";
    String SERVICE_ERROR_DEACTIVATING_USER = "Error deactivating user";
    String SERVICE_ERROR_ADDING_ENTRY = "Error adding entry";
    String SERVICE_ERROR_UPDATING_ENTRY = "Failed to update entry";
    String SERVICE_ERROR_DELETING_ENTRY = "Failed to delete entry";
    String SERVICE_ENTRY_NOT_FOUND =  "Entry not found";
    String SERVICE_S3_GATEWAY_ERROR = "Error with communicating with S3 Gateway";
    String SERVICE_S3_GATEWAY_BUCKET_RETRIEVAL_ERROR = "Error fetching bucket";
    String SERVICE_S3_GATEWAY_BUCKET_SEND_ERROR = "Error sending bucket";

}
