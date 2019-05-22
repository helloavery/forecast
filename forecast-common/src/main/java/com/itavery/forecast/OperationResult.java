package com.itavery.forecast;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-10-30
 * https://github.com/helloavery
 */

public enum OperationResult {

    DAO_TECHNICAL_ERROR("There is a system issue, Please contact the Administrator"),
    GENERATOR_ASSIGN_EMAIL_ERROR("There is an issue generating an e-mail, Please contact the Administrator"),
    SERVICE_INVALID_LOGIN("Invalid login credentials"),
    SERVICE_ACCOUNT_UNVERIFIED("Account is not Verified. Please check e-mail for Verification link"),
    SERVICE_ACCOUNT_DEACTIVATED("Account has been deactivated. Please contact the Administrator if you would like your account to be reinstated"),
    USER_UPDATE_USER_SUCCESS("User Successfully Updated"),
    USER_SUCCESSFUL_DEACTIVATION("User Successfully Deactivated"),
    PRODUCT_DEMAND_ADD_ENTRY_SUCCESS("ProductDemand has been added successfully"),
    PRODUCT_DEMAND_SUCCESSFUL_UPDATE("Entry has been Updated Successfully"),
    PRODUCT_DEMAND_REMOVE_ENTRY_SUCCESSFUL("Entry has been deleted successfully"),
    PRODUCT_FORECAST_ADD_ENTRY_SUCCESS("ProductForecast has been added successfully"),
    PRODUCT_FORECAST_SUCCESSFUL_UPDATE("Entry has been Updated Successfully"),
    PRODUCT_FORECAST_REMOVE_ENTRY_SUCCESSFUL("Entry has been deleted successfully"),
    VERIFICATION_EMAIL_VERIFICATION_SUCCESSFUL("Email has successfully been verified"),
    USERINTERFACE_ADD_USER_SUCCESS("User successfully added"),
    USERINTERFACE_ADD_USERNAME_TO_LIST_SUCCESS("Username successfully added to list");

    private String message;

    OperationResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
