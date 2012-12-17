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

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.scribe.builder.api.FacebookApi;

/**
 *
 * @author Lukas Plechinger, www.plechinger.at
 */
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

    public List<String> getAuthorities() {
        return null;
    }
}
