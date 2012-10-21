/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.security.scribe.provider;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.springframework.security.scribe.ScribeUserNotConnectedException;

/**
 *
 * @author Lukas
 */
public abstract class AbstractProviderConfiguration implements ProviderConfiguration {

    protected abstract String getUserIdToken();

    public Map<String, Object> getUserDetails(OAuthService service, Token accessToken) throws JSONException, ScribeUserNotConnectedException {
        OAuthRequest request = new OAuthRequest(Verb.GET, getUserDetailsUrl());
        service.signRequest(accessToken, request);
        Response response = request.send();

        if (response.getCode() != 200) {
            throw new ScribeUserNotConnectedException("Could not get UserDetails; HTTP Status was " + response.getCode());
        }

        Map<String, Object> userDetailsMap = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(response.getBody());
        String[] jsonNames = JSONObject.getNames(jsonObject);

        for (String name : jsonNames) {
            userDetailsMap.put(name, jsonObject.get(name));
        }

        return userDetailsMap;
    }

    public boolean isAuthCodeProvided(HttpServletRequest request) {
        return (getAuthCode(request) != null);
    }

    public Object getUserId(Map<String, Object> userDetailsMap) {
        return userDetailsMap.get(getUserIdToken());
    }
    private String apiKey;
    private String apiSecret;
    private String usernamePrefix;

    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getUsernamePrefix() {
        return usernamePrefix;
    }

    public void setUsernamePrefix(String usernamePrefix) {
        this.usernamePrefix = usernamePrefix;
    }
}
