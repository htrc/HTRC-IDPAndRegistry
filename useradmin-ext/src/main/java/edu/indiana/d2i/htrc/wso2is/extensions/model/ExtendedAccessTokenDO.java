package edu.indiana.d2i.htrc.wso2is.extensions.model;

import org.wso2.carbon.identity.oauth2.model.AccessTokenDO;

import java.sql.Timestamp;


public class ExtendedAccessTokenDO extends AccessTokenDO  {

    private String userFullName;

    private  String userEmail;

    public ExtendedAccessTokenDO(String consumerKey, String authzUser, String[] scope, Timestamp issuedTime, long validityPeriod, String tokenType) {
        super(consumerKey, authzUser, scope, issuedTime, validityPeriod, tokenType);
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
