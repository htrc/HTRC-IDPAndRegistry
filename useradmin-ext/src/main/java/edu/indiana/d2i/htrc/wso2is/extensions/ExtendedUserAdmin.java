package edu.indiana.d2i.htrc.wso2is.extensions;


import edu.indiana.d2i.htrc.wso2is.extensions.bean.UserInfoRequest;
import edu.indiana.d2i.htrc.wso2is.extensions.bean.UserInfoResponse;
import edu.indiana.d2i.htrc.wso2is.extensions.model.ExtendedAccessTokenDO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.CarbonConstants;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.identity.oauth.common.OAuth2ErrorCodes;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.dao.TokenMgtDAO;
import org.wso2.carbon.identity.oauth2.dto.OAuth2TokenValidationRequestDTO;
import org.wso2.carbon.identity.oauth2.dto.OAuth2TokenValidationResponseDTO;
import org.wso2.carbon.identity.oauth2.validators.TokenValidationHandler;
import org.wso2.carbon.user.core.UserRealm;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.carbon.user.core.UserStoreManager;
import org.wso2.carbon.user.mgt.UserAdmin;
import org.wso2.carbon.user.mgt.common.ClaimValue;
import org.wso2.carbon.user.mgt.common.FlaggedName;
import org.wso2.carbon.user.mgt.common.UserAdminException;

import java.util.ArrayList;
import java.util.List;


public class ExtendedUserAdmin {

    private static Log log = LogFactory.getLog(ExtendedUserAdmin.class);

    private static Log audit = CarbonConstants.AUDIT_LOG;

    private static String AUDIT_MESSAGE = "Initiator : %s | Action : %s | Target : %s | Data : { %s } | Result : %s ";


    private UserRealm getUserRealm() {
        return (UserRealm) CarbonContext.getThreadLocalCarbonContext().getUserRealm();
    }

    public UserInfoResponse getUserInformation(UserInfoRequest userInfoRequest) {

        TokenMgtDAO tokenMgtDAO = new TokenMgtDAO();
        UserInfoResponse userInfoResp = new UserInfoResponse();

        String accessToken = userInfoRequest.getAccessToken();
        String clientId = userInfoRequest.getClientId();
        String clientSecret = userInfoRequest.getClientSecret();

        // incomplete user information request
        if (accessToken == null || clientId == null) {
            log.warn("Client Id or Access Token is not present");
            userInfoResp.setError(true);
            userInfoResp.setErrorCode(OAuth2ErrorCodes.INVALID_REQUEST);
            userInfoResp.setErrorMsg("Client Id or Access Token is not present " +
                    "in the user information request.");
            return userInfoResp;
        }

        OAuth2TokenValidationRequestDTO tokenValidationRequestDTO = new OAuth2TokenValidationRequestDTO();
        OAuth2TokenValidationRequestDTO.OAuth2AccessToken oAuth2AccessToken = tokenValidationRequestDTO.new OAuth2AccessToken();
        oAuth2AccessToken.setIdentifier(accessToken);
        oAuth2AccessToken.setTokenType("bearer");
        tokenValidationRequestDTO.setAccessToken(oAuth2AccessToken);

        TokenValidationHandler validationHandler = TokenValidationHandler.getInstance();
        OAuth2TokenValidationResponseDTO tokenValidationResponseDTO = null;
        try {
            tokenValidationResponseDTO = validationHandler.validate(tokenValidationRequestDTO);


            ExtendedAccessTokenDO extendedAccessTokenDO = null;

            if (!tokenValidationResponseDTO.isValid()) {
                userInfoResp.setError(true);
                userInfoResp.setErrorCode(OAuth2ErrorCodes.INVALID_REQUEST);
                userInfoResp.setErrorMsg(tokenValidationResponseDTO.getErrorMsg());
                return userInfoResp;
            }


            extendedAccessTokenDO = (ExtendedAccessTokenDO) tokenMgtDAO.retrieveAccessToken(accessToken);
            if (extendedAccessTokenDO != null && (extendedAccessTokenDO.getUserFullName() == null || extendedAccessTokenDO.getUserEmail() == null)) {
                UserRealm ur = getUserRealm();
                UserStoreManager um = ur.getUserStoreManager();
                extendedAccessTokenDO.setUserFullName(um.getUserClaimValue(extendedAccessTokenDO.getAuthzUser(), "http://wso2.org/claims/givenname", "default")
                        + " " + um.getUserClaimValue(extendedAccessTokenDO.getAuthzUser(), "http://wso2.org/claims/lastname", "default"));
                extendedAccessTokenDO.setUserEmail(um.getUserClaimValue(extendedAccessTokenDO.getAuthzUser(), "http://wso2.org/claims/emailaddress", "default"));
            }
            userInfoResp.setAuthorizedUser(extendedAccessTokenDO.getAuthzUser());
            userInfoResp.setUserFullName(extendedAccessTokenDO.getUserFullName());
            userInfoResp.setUserEmail(extendedAccessTokenDO.getUserEmail());

            return userInfoResp;
        } catch (IdentityOAuth2Exception e) {
            log.error("Error when reading the Request Information.", e);
            userInfoResp.setError(true);
            userInfoResp.setErrorMsg("Error when processing the user information request.");
            return userInfoResp;
        } catch (UserStoreException e) {
            log.error("Error when getting user information", e);
            userInfoResp.setError(true);
            userInfoResp.setErrorMsg("Error ahen getting user information");
            return userInfoResp;
        }
    }

    public String getUserEmailFromUserId(String userId){
        UserRealm ur = getUserRealm();
        UserStoreManager um = null;

        try {
            um = ur.getUserStoreManager();
            return um.getUserClaimValue(userId, "http://wso2.org/claims/emailaddress", "default");
        } catch (UserStoreException e) {
            log.error("Error when getting user information", e);
            return null;
        }
    }

    public String[] getUserIdsFromEmail(String userEmail){
        UserRealm ur = getUserRealm();
        UserStoreManager um = null;

        try {
            um = ur.getUserStoreManager();
            return um.getUserList("http://wso2.org/claims/emailaddress",userEmail,"default");
        } catch (UserStoreException e) {
            log.error("Error when getting user Ids from user email", e);
            return null;
        }

//        ClaimValue claimValue = new ClaimValue();
//        claimValue.setClaimURI("http://wso2.org/claims/emailaddress");
//        claimValue.setValue(userEmail);
//        UserAdmin userAdmin = new UserAdmin();
//
//        List<String> userIds = new ArrayList<String>();
//        try {
//            FlaggedName[] flaggedNames = userAdmin.listUserByClaim(claimValue, "*", 10);
//            for(FlaggedName fn : flaggedNames){
//                userIds.add(fn.getItemName());
//            }
//            return userIds;
//
//        } catch (UserAdminException e) {
//            log.error("Error when getting user Ids", e);
//            return null;
//        }
    }


}

