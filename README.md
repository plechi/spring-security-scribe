OAuth Login for Spring Security
===============================

This project provides support for creating a "Login with &lt;insert name of social network here&gt;" function in your Springframework-Application.
It uses [Scribe](https://github.com/fernandezpablo85/scribe-java) from [fernandezpablo85](http://fernandezpablo85.github.com/) as OAuth client library.

Basic Usage (Example: "Login with Facebook")
-----------

Download sources and then run (use `bootstrap` only the first time):

	$ mvn install -P bootstrap

Then include the dependency in your `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.security.scribe</groupId>
    <artifactId>spring-security-scribe</artifactId>
    <version>0.1-SNAPSHOT</version>
</dependency>
```

The library is not available in any public maven repository yet. 

Spring application context configuration:
```xml
<http access-denied-page="error.jsp" use-expressions="true" entry-point-ref="authenticaionEntryPoint" 
        xmlns="http://www.springframework.org/schema/security">
    <intercept-url pattern="protected.jsp" access="isAuthenticated()" />
    <intercept-url pattern="/**" access="permitAll" />     
    <logout logout-url="/logout" logout-success-url="index.jsp"/>             
    <custom-filter before="FORM_LOGIN_FILTER" ref="scribeAuthenticationFilter"/>
</http>
    
<authentication-manager alias="authenticationManager" 
        xmlns="http://www.springframework.org/schema/security">
    <authentication-provider ref="scribeAuthenticationProvider"/>
</authentication-manager>
    
<bean id="authenticaionEntryPoint" 
        class="org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint">
    <property name="loginFormUrl" value="index.jsp" />
</bean>
    
<bean id="scribeAuthenticationProvider" 
        class="org.springframework.security.scribe.ScribeAuthenticationProvider"> 
    <property name="userDetailsService" ref="userService"/>
</bean>
    
<bean id="scribeAuthenticationFilter" 
        class="org.springframework.security.scribe.ScribeAuthenticationFilter">
    <property name="authenticationManager" ref="authenticationManager" />
    <property name="authenticationSuccessHandler">
        <bean class="org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler">
            <property name="defaultTargetUrl" value="protected.jsp" />
            <property name="alwaysUseDefaultTargetUrl" value="true" />
        </bean>
    </property>
    <property name="authenticationFailureHandler">
        <bean class="org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler">
            <property name="defaultFailureUrl" value="error.jsp" />
        </bean>
    </property>
    <property name="filterProcessesUrl" value="/login"/>
    <property name="providerConfigurations">
        <list>
            <bean class="org.springframework.security.scribe.provider.FacebookProviderConfiguration">
                <property name="apiKey" value="1234567890"/>
                <property name="apiSecret" value="ABCDEFGHIJKLMNOPQRSTUVWXYZ" />
            </bean>
        </list>
    </property>
</bean>
```

Now you can login with Facebook by calling `/login` in your browser.

That's it. If the user successfully authenticated with Facebook, you can get his credentials by retrieving the `Authentication` object from the `SecurityContext`.

```java
Authentication auth = SecurityContextHolder.getContext().getAuthentication();
```

The Authentication-Object is from type `ScribeAuthentication`. 
The username you retrieve would be `oauth_facebook_<facebook uid>`.

Using other OAuth Providers
---------------------------

###Facebook and Twitter###
This library comes with two built-in Configurations for Facebook and Twitter.
You can use them just by setting the OAuth-API Key and the Application Secret you get when you register your applications:

Facebook:

```xml
<bean class="org.springframework.security.scribe.provider.FacebookProviderConfiguration">
    <property name="apiKey" value="1234567890"/>
    <property name="apiSecret" value="ABCDEFGHIJKLMNOPQRSTUVWXYZ" />
</bean>
```

Twitter:

```xml
<bean class="org.springframework.security.scribe.provider.TwitterProviderConfiguration">
    <property name="apiKey" value="1234567890"/>
    <property name="apiSecret" value="ABCDEFGHIJKLMNOPQRSTUVWXYZ" />
</bean>
```

You can easy use every OAuth-Provider scribe supports ([full list here](https://github.com/fernandezpablo85/scribe-java/tree/master/src/test/java/org/scribe/examples)) by configuring following (same as Twitter example above)

```xml
<bean class="org.springframework.security.scribe.provider.CustomizableProviderConfiguration">
    <property name="apiKey" value="1234567890"/>
    <property name="apiSecret" value="ABCDEFGHIJKLMNOPQRSTUVWXYZ" />
    <!--username prefix-->
    <property name="usernamePrefix" value="oauth_twitter_"/>
    <!--Scribe API Class-->
    <property name="apiClass" value="org.scribe.builder.api.TwitterApi" /> 
    <!--parameter to get the verify code from the callback url-->  
    <property name="verifyParameter" value="oauth_verifier"/>
    <!--following lines are used to determine the User's uid at Twitter:-->
    <!--REST-API-URL to retrieve user details-->
    <property name="userDetailsUrl" value="https://api.twitter.com/1.1/account/verify_credentials.json" />
    <!--JSON-Key of the uid in the result of the REST-Call above-->
    <property name="userIdToken" value="id"/>
</bean>
```

TODO list
---------

There are still many things to do

 1. better documentation (especially javadoc)
 2. more implementations of configurations

I actually have very small resources left to maintain the project, but i'm very omtimistic to work on it from time to time.
There is also a productive enviroment which uses this library, so critical issues will be fixed very fast.

About Me
--------

Email me: lukasplechinger at gmail.com
Follow me at Twitter: [@plechinger](https://www.twitter.com/plechinger)






