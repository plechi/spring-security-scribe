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
package at.plechinger.spring.security.scribe.provider;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;
import at.plechinger.spring.security.scribe.ScribeUserNotConnectedException;

/**
 *
 * @author Lukas Plechinger, www.plechinger.at
 */
public interface ProviderConfiguration extends Serializable {

    String getAuthCode(HttpServletRequest request);

    boolean isAuthCodeProvided(HttpServletRequest request);

    Class getApiClass();

    Map<String, Object> getUserDetails(OAuthService service, Token accessToken) throws JSONException, ScribeUserNotConnectedException;

    String getUserDetailsUrl();

    Object getUserId(Map<String, Object> userDetailsMap);

    String getUsernamePrefix();

    String getApiKey();

    String getApiSecret();

    String getFilterIdentifier();

    public List<String> getAuthorities();
}
