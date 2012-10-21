/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.security.scribe.provider;

import javax.servlet.http.HttpServletRequest;
import org.scribe.builder.api.FacebookApi;
import org.scribe.builder.api.TwitterApi;

public class TwitterProviderConfiguration extends AbstractProviderConfiguration {

    private static final String PARAMETER_CODE = "oauth_verifier";
    private static final String DETAILS_URL = "https://api.twitter.com/1.1/account/verify_credentials.json";
    private static final String USER_ID_TOKEN = "id";
    private static final Class API_CLASS = TwitterApi.class;
    private static final String FILTER_IDENTIFER = "twitter";

    public String getAuthCode(HttpServletRequest request) {
        return request.getParameter(PARAMETER_CODE);
    }

    public Class getApiClass() {
        return API_CLASS;
    }

    public String getUserDetailsUrl() {
        return DETAILS_URL;
    }

    @Override
    protected String getUserIdToken() {
        return USER_ID_TOKEN;
    }

    public String getFilterIdentifer() {
        return FILTER_IDENTIFER;
    }
}
