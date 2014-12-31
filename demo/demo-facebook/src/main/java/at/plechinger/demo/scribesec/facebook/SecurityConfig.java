/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;



/**
 *
 * @author lukas
 */
@Configuration
@EnableWebSecurity
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
