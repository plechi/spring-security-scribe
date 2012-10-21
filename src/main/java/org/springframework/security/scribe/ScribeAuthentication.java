/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
