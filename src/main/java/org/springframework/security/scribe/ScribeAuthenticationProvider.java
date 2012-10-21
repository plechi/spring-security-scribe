/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.security.scribe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
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
 * @author Lukas
 */
public class ScribeAuthenticationProvider implements AuthenticationProvider {

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
            OAuthService oAuthService = new ServiceBuilder()
                    .provider(providerConfiguration.getApiClass())
                    .apiKey(providerConfiguration.getApiKey())
                    .apiSecret(providerConfiguration.getApiSecret())
                    .callback(scribeAuthentication.getRedirectUrl())
                    .debug()
                    .build();

            Map<String, Object> details = providerConfiguration.getUserDetails(oAuthService, token);
            
            System.out.println("details: "+details);

            scribeAuthentication.setScribeDetails(details);

            String username = providerConfiguration.getUserId(details).toString();
            if(providerConfiguration.getUsernamePrefix()!=null){
                username=providerConfiguration.getUsernamePrefix()+username;
            }
            
            System.out.println("username is: "+username);
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
}