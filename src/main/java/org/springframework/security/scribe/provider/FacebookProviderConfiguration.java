/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.security.scribe.provider;

import javax.servlet.http.HttpServletRequest;
import org.scribe.builder.api.FacebookApi;

public class FacebookProviderConfiguration extends AbstractProviderConfiguration {

    private static final String PARAMETER_CODE = "code";
    private static final String DETAILS_URL = "https://graph.facebook.com/me";
    private static final String USER_ID_TOKEN = "id";
    private static final Class API_CLASS = FacebookApi.class;
    private static final String FILTER_IDENTIFER = "facebook";

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
