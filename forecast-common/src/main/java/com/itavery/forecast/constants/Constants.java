package com.itavery.forecast.constants;

/**
 * @author Avery Grimes-Farrow
 * Created on; 2018-05-28
 * https;//github.com/helloavery
 */

public interface Constants {

    /***Prefixes****/
    String LOGIN_PREFIX = "launch/login/";
    String REGISTRATION_PREFIX = "launch/registration/";
    String HOME_PREFIX = "launch/";
    String USERID_PREFIX = "USERID; ";


    String FORECASTER_AUTO_ACC = "b1forecastauto";
    String HOST_URL_STRING = "http//forecast.itavery.com";

    /***Authy Constants****/
    String AUTHY_SEND_SMS_OTP = "https;//api.authy.com/protected/json/sms/%s";
    String AUTHY_VOICE_OTP="https;//api.authy.com/protected/json/call/%s";
    String AUTHY_VERIFY_OTP = "https;//api.authy.com/protected/json/verify/%s/%s";

    /***Mailgun and E-mail constants******/
    String MAILGUN_MAILBOX_VERIFICATION_URL = "https;//api.mailgun.net/v3/address/private/validate";
    String VERIFY_EMAIL_ADDRESS_API = "/api/verify_email/%s";
    String MAILGUN_DOMAIN_NAME = "forecast.itavery.com";

    /******Session Constants******/
    int MAX_INACTIVE_INTERVAL = 30 * 60;
    String AUTHENTICATED = "authenticated";
    String PARTIALLY_AUTHENTICATED = "partially-authenticated";
    String USER_ID = "user-id";
    String USER_NAME = "user-name";

    /***DAO Operation Errors***/
    String DAO_USER_NOT_FOUND = "DAO; User not found";
    String DAO_USER_NOT_UPDATED = "DAO; User could not be updated";
    String DAO_USER_NOT_DEACTIVATED = "DAO; User could not be deactivated";

    /****Service Operation Errors****/
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

    /**User Validation Errors***/
    String USER_INVALID_EMAIL = "Invalid email address";
    String USER_INVALID_PASSWORD = "Invalid password";
    String USER_INVALID_FIRST_NAME =  "Invalid first name";
    String USER_INVALUD_LAST_NAME = "Invalid last name";

    /***Product Demand Validation Errors****/
    String DEMAND_VALIDATOR_INVALID_PRODUCT_CODE = "Invalid Product Demand product code";
    String DEMAND_VALIDATOR_INVALID_WAREHOUSE = "Invalid Product Demand warehouse";
    String DEMAND_VALIDATOR_INVALID_PRODUCT_CATEGORY = "Invalid Product Demand product category";
    String DEMAND_VALIDATOR_INVALID_ORDER_DEMAND = "Invalid Product Demand order demand";

    /***Product Forecast Validation Errors***/
    String FORECAST_INVALID_SKU = "Invalid Forecast SKU";
    String FORECAST_INVALID_NATIONAL_INVENTORY = "Invalid Forecast national inventory";
    String FORECAST_INVALID_LEAD_TIME = "Invalid Forecast lead time";
    String FORECAST_INVALID_IN_TRANSIT_QTY = "Invalid Forecast in transit qty";
    String FORECAST_INVALID_FORECAST_THREE_MONTHS = "Invalid Forecast three months";
    String FORECAST_INVALID_FORECAST_SIX_MONTHS = "Invalid Forecast six months";
    String FORECAST_INVALID_FORECAST_NINE_MONTHS = "Invalid Forecast nine months";
    String FORECAST_INVALID_SALES_ONE_MONTH = "Invalid Forecast sales one month";
    String FORECAST_INVALID_SALES_THREE_MONTH = "Invalid Forecast sales three months";
    String FORECAST_INVALID_SALES_SIX_MONTH = "Invalid Forecast sales six months";
    String FORECAST_INVALID_SALES_NINE_MONTH = "Invalid Forecast sales nine months";
    String FORECAST_INVALID_MIN_BANK = "Invalid Forecast min bank";
    String FORECAST_INVALID_PIECES_PAST_DUE = "Invalid Forecast pieces past due";
    String FORECAST_INVALID_LOCAL_BO_QTY = "Invalid Forecast local bo qty";
    String FORECAST_INVALID_POTENTIAL_ISSUES = "Invalid Forecast potential issues";
    String FORECAST_INVALID_DECK_RISK = "Invalid Forecast deck risk";
    String FORECAST_INVALID_PPAP_RISK = "Invalid Forecast ppap risk";
    String FORECAST_INVALID_STOP_AUTO_BUY = "Invalid Forecast stop auto buy";
    String FORECAST_INVALID_REV_STOP = "Invalid Forecast rev stop";
    String FORECAST_INVALID_BACKORDER = "Invalid Forecast backorder";
    String FORECAST_INVALID_PERF_SIX_MONTH_AVG = "Invalid Forecast perf six month average";
    String FORECAST_INVALID_PERF_TWELVE_MONTH_AVG = "Invalid Forecast perf twelve month average";

    /***Spring Config Constants***/
    String API_ROOT = "/rest/*";

    /****Operation Results******/
    String DAO_TECHNICAL_ERROR = "There is a system issue, Please contact the Administrator";
    String GENERATOR_ASSIGN_EMAIL_ERROR = "There is an issue generating an e-mail, Please contact the Administrator";
    String SERVICE_INVALID_LOGIN = "Invalid login credentials";
    String SERVICE_ACCOUNT_UNVERIFIED = "Account is not Verified. Please check e-mail for Verification link";
    String SERVICE_ACCOUNT_DEACTIVATED = "Account has been deactivated. Please contact the Administrator if you would like your account to be reinstated";
    String USER_UPDATE_USER_SUCCESS = "User Successfully Updated";
    String USER_SUCCESSFUL_DEACTIVATION = "User Successfully Deactivated";
    String PRODUCT_DEMAND_ADD_ENTRY_SUCCESS = "ProductDemand has been added successfully";
    String PRODUCT_DEMAND_SUCCESSFUL_UPDATE = "Entry has been Updated Successfully";
    String PRODUCT_DEMAND_REMOVE_ENTRY_SUCCESSFUL = "Entry has been deleted successfully";
    String PRODUCT_FORECAST_ADD_ENTRY_SUCCESS = "ProductForecast has been added successfully";
    String PRODUCT_FORECAST_SUCCESSFUL_UPDATE = "Entry has been Updated Successfully";
    String PRODUCT_FORECAST_REMOVE_ENTRY_SUCCESSFUL = "Entry has been deleted successfully";
    String VERIFICATION_EMAIL_VERIFICATION_SUCCESSFUL = "Email has successfully been verified";
    String USERINTERFACE_ADD_USER_SUCCESS = "User successfully added";
    String USERINTERFACE_ADD_USERNAME_TO_LIST_SUCCESS = "Username successfully added to list";

}
