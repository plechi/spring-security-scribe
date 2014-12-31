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

package at.plechinger.demo.scribesec.facebook;

import at.plechinger.spring.security.scribe.ScribeAuthenticationFilter;
import at.plechinger.spring.security.scribe.ScribeAuthenticationProvider;
import at.plechinger.spring.security.scribe.provider.FacebookProviderConfiguration;
import at.plechinger.spring.security.scribe.provider.ProviderConfiguration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;



/**
 *
 * @author lukas
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private AuthenticationProvider authenticationProvider;
    
    @Autowired
    private ScribeAuthenticationFilter authFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider)
                .addFilterAfter(authFilter,LogoutFilter.class);
    }
    
    @Bean(name = "authenticationProvider")
    public AuthenticationProvider authenticationProvider(){
        ScribeAuthenticationProvider provider=new ScribeAuthenticationProvider();
        return provider;
    }
    
    @Bean
    @Autowired
    public ScribeAuthenticationFilter authFilterBean(AuthenticationManager authManager){
        ScribeAuthenticationFilter filter=new ScribeAuthenticationFilter("/oauth-login");
        //filter.setConfigMatchParameter("atype");
        
        
        filter.setAuthenticationManager(authManager);
        
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login-error"));
        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/login-success"));
        
        FacebookProviderConfiguration facebookProvider=new FacebookProviderConfiguration();
        facebookProvider.setApiKey("12345678");
        facebookProvider.setApiSecret("xxxxxxxxxxxxxxxxxxxxxxxx");
        
        
        List<ProviderConfiguration> config=new ArrayList<>(1);
        config.add(facebookProvider);
        filter.setProviderConfigurations(config);

        
        return filter;
    }
    
}
