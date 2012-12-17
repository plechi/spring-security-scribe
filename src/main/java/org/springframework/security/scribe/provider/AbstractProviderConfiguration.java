/*
 The MIT License

 Copyright (c) 2012 Lukas Plechinger

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
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
 * @author Lukas Plechinger, www.plechinger.at
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
