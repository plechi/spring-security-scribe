/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.security.scribe.provider;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Lukas
 */
public class CustomizeableProviderConfiguration extends AbstractProviderConfiguration {

    private String verifyParameter;
    private Class apiClass;
    private String userDetailsUrl;
    private String userIdToken;
    private String filterIdentifer;

    public String getAuthCode(HttpServletRequest request) {
        return request.getParameter(verifyParameter);
    }

    public Class getApiClass() {
        return apiClass;
    }

    public String getUserDetailsUrl() {
        return userDetailsUrl;
    }

    @Override
    protected String getUserIdToken() {
        return userIdToken;
    }

    public String getVerifyParameter() {
        return verifyParameter;
    }

    public void setVerifyParameter(String verifyParameter) {
        this.verifyParameter = verifyParameter;
    }

    public void setApiClass(Class apiClass) {
        this.apiClass = apiClass;
    }

    public void setUserDetailsUrl(String userDetailsUrl) {
        this.userDetailsUrl = userDetailsUrl;
    }

    public void setUserIdToken(String userIdToken) {
        this.userIdToken = userIdToken;
    }

    public String getFilterIdentifer() {
        return filterIdentifer;
    }

    public void setFilterIdentifer(String filterIdentifer) {
        this.filterIdentifer = filterIdentifer;
    }
}
