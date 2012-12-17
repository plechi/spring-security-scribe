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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.scribe.provider.ProviderConfiguration;

/**
 *
 * @author Lukas Plechinger, www.plechinger.at
 */
public class ScribeAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = Logger.getLogger(ScribeAuthenticationProvider.class);
    private UserDetailsService userDetailsService;
    private Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(1);

    public ScribeAuthenticationProvider() {
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER_SCRIBE"));
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ProviderConfiguration providerConfiguration = null;
        try {
            ScribeAuthentication scribeAuthentication = (ScribeAuthentication) authentication;


            providerConfiguration = scribeAuthentication.getProviderConfiguration();
            Token token = scribeAuthentication.getScribeToken();

            populateAuthorities(providerConfiguration);

            ServiceBuilder serviceBuilder = new ServiceBuilder()
                    .provider(providerConfiguration.getApiClass())
                    .apiKey(providerConfiguration.getApiKey())
                    .apiSecret(providerConfiguration.getApiSecret())
                    .callback(scribeAuthentication.getRedirectUrl());
            if (LOG.getLevel() == Level.DEBUG) {
                LOG.debug("enable scribe debug mode");
                serviceBuilder.debug();
            }
            OAuthService oAuthService = serviceBuilder.build();
            Map<String, Object> details = providerConfiguration.getUserDetails(oAuthService, token);
            LOG.debug("details: " + details);
            scribeAuthentication.setScribeDetails(details);
            String username = providerConfiguration.getUserId(details).toString();
            if (providerConfiguration.getUsernamePrefix() != null) {
                LOG.debug("use username prefix " + providerConfiguration.getUsernamePrefix());
                username = providerConfiguration.getUsernamePrefix() + username;
            }
            LOG.debug("username is: " + username);
            scribeAuthentication.setUserDetails(userDetailsService.loadUserByUsername(username));
            scribeAuthentication.setAuthenticated(true);
            return scribeAuthentication;
        } catch (JSONException ex) {
            throw new ScribeUserNotConnectedException(providerConfiguration, ex);
        }
    }

    public boolean supports(Class<?> authentication) {
        return ScribeAuthentication.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void setRoles(String[] roles) {
        grantedAuthorities.clear();
        for (String string : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(string));
        }
    }

    private void populateAuthorities(ProviderConfiguration providerConfiguration) {
        List<String> authorities = providerConfiguration.getAuthorities();
        if (authorities != null && !authorities.isEmpty()) {
            grantedAuthorities = new ArrayList<GrantedAuthority>(authorities.size());
            for (String authority : authorities) {
                LOG.debug("add authority " + authority);
                grantedAuthorities.add(new SimpleGrantedAuthority(authority));
            }
        }
    }
}
