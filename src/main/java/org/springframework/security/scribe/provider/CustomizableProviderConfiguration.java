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

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Lukas
 */
public class CustomizableProviderConfiguration extends AbstractProviderConfiguration {

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
