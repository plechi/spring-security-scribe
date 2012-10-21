/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.security.scribe;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.scribe.provider.ProviderConfiguration;

/**
 *
 * @author Lukas
 */
public class ScribeUserNotConnectedException extends AuthenticationException {

    /**
     * Creates a new instance of
     * <code>NotAutorizedException</code> without detail message.
     */
    public ScribeUserNotConnectedException() {
        super("Unknown Error");
    }

    /**
     * Constructs an instance of
     * <code>NotAutorizedException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ScribeUserNotConnectedException(String msg) {
        super(msg);
    }

    public ScribeUserNotConnectedException(ProviderConfiguration config) {
        super("Scribe User not Connected. Api: " + config.getApiClass().getName());
    }

    public ScribeUserNotConnectedException(ProviderConfiguration config, Throwable cause) {
        super("Scribe User not Connected. Api: " + config.getApiClass().getName(), cause);
    }
}
