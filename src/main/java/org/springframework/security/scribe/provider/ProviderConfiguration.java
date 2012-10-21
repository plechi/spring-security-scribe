/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.security.scribe.provider;

import java.io.Serializable;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;
import org.springframework.security.scribe.ScribeUserNotConnectedException;

/**
 *
 * @author Lukas
 */
public interface ProviderConfiguration extends Serializable{

    String getAuthCode(HttpServletRequest request);

    boolean isAuthCodeProvided(HttpServletRequest request);

    Class getApiClass();

    Map<String, Object> getUserDetails(OAuthService service, Token accessToken) throws JSONException, ScribeUserNotConnectedException;

    String getUserDetailsUrl();

    Object getUserId(Map<String, Object> userDetailsMap);

    String getUsernamePrefix();

    String getApiKey();

    String getApiSecret();
    
    String getFilterIdentifer();
}
