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
package org.springframework.security.scribe;

import java.util.Collection;
import java.util.Map;
import org.scribe.model.Token;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.scribe.provider.ProviderConfiguration;

/**
 *
 * @author Lukas
 */
public class ScribeAuthentication extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;
    private UserDetails userDetails;
    private Token scribeToken;
    private Map<String, Object> scribeDetails;
    private String redirectUrl;
    private ProviderConfiguration providerConfiguration;

    public ScribeAuthentication(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public ScribeAuthentication(Token scribeToken, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.scribeToken = scribeToken;
        super.setAuthenticated(true);
    }

    public ScribeAuthentication() {
        super(null);
    }

    public Object getCredentials() {
        return scribeToken;
    }

    public Object getPrincipal() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public Token getScribeToken() {
        return scribeToken;
    }

    public void setScribeToken(Token scribeToken) {
        this.scribeToken = scribeToken;
    }

    public Map<String, Object> getScribeDetails() {
        return scribeDetails;
    }

    public void setScribeDetails(Map<String, Object> scribeDetails) {
        this.scribeDetails = scribeDetails;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public ProviderConfiguration getProviderConfiguration() {
        return providerConfiguration;
    }

    public void setProviderConfiguration(ProviderConfiguration providerConfiguration) {
        this.providerConfiguration = providerConfiguration;
    }
}
