/*
 * The MIT License
 *
 * Copyright 2014 Lukas Plechinger.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package at.plechinger.spring.security.scribe;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import at.plechinger.spring.security.scribe.provider.ProviderConfiguration;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

/**
 *
 * @author Lukas Plechinger, www.plechinger.at
 */
public class ScribeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger LOG = Logger.getLogger(ScribeAuthenticationFilter.class.getName());
    //default values
    public static final String DEFAULT_FILTER_PROCESS_URL = "/j_spring_security_scribe/**";
    private static final String SESSION_TOKEN = "spring.security.scribe.token";
    /**
     * List of <code>SocialAuthenticationProvider</code>s
     */
    private List<ProviderConfiguration> providerConfigurations;
    private String configMatchParameter = "method";

    public ScribeAuthenticationFilter() {
        super(DEFAULT_FILTER_PROCESS_URL);
    }
    
    public ScribeAuthenticationFilter(String filterUrl) {
        super(filterUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        
        String callbackUrl = request.getRequestURL().append("?").append(request.getQueryString()).toString();

        LOG.log(Level.DEBUG, "callbackUrl " + callbackUrl);

        String configMatch = request.getParameter(configMatchParameter);

        LOG.log(Level.DEBUG, "configMatch: " + configMatch);

        ProviderConfiguration providerConfiguration = getMatchedProviderConfiguration(configMatch);

        HttpSession session = request.getSession(true);

        ServiceBuilder serviceBuilder = new ServiceBuilder()
                .provider(providerConfiguration.getApiClass())
                .apiKey(providerConfiguration.getApiKey())
                .apiSecret(providerConfiguration.getApiSecret())
                .callback(callbackUrl);

        //enable debug logging if enabled
        if (LOG.getLevel() == Level.DEBUG) {
             LOG.log(Level.DEBUG,"enable scribe debug mode");
            serviceBuilder.debug();
        }

        OAuthService oAuthService = serviceBuilder.build();

        if (!providerConfiguration.isAuthCodeProvided(request)) {
            Token requestToken = null;
            try {
                requestToken = oAuthService.getRequestToken();
            } catch (UnsupportedOperationException e) {
                //some networks dont support request tokens (eg. Facebook)
            }

            String authUrl = oAuthService.getAuthorizationUrl(requestToken);

            session.setAttribute(SESSION_TOKEN, requestToken);
            response.sendRedirect(authUrl);
            return null;
        } else {
            ScribeAuthentication scribeAuthentication = new ScribeAuthentication();
            String authCode = providerConfiguration.getAuthCode(request);
            Verifier verifier = new Verifier(authCode);
            Token requestToken = (Token) session.getAttribute(SESSION_TOKEN);

            Token accessToken = oAuthService.getAccessToken(requestToken, verifier);
            scribeAuthentication.setScribeToken(accessToken);
            scribeAuthentication.setRedirectUrl(callbackUrl);

            scribeAuthentication.setDetails(this.authenticationDetailsSource.buildDetails(request));

            scribeAuthentication.setProviderConfiguration(providerConfiguration);
            AuthenticationManager authenticationManager = this.getAuthenticationManager();
            Authentication success = authenticationManager.authenticate(scribeAuthentication);

            return success;
        }
    }

    private ProviderConfiguration getMatchedProviderConfiguration(String match) {
        if (match != null) {
            for (ProviderConfiguration providerConfiguration : providerConfigurations) {
                if (match.equals(providerConfiguration.getFilterIdentifier())) {
                    return providerConfiguration;
                }
            }
        }
        if (providerConfigurations != null && providerConfigurations.size() >= 1) {
            return providerConfigurations.get(0);
        }
        return null;
    }

    public List<ProviderConfiguration> getProviderConfigurations() {
        return providerConfigurations;
    }

    public void setProviderConfigurations(List<ProviderConfiguration> providerConfigurations) {
        this.providerConfigurations = providerConfigurations;
    }

    public String getConfigMatchParameter() {
        return configMatchParameter;
    }

    public void setConfigMatchParameter(String configMatchParameter) {
        this.configMatchParameter = configMatchParameter;
    }
}
