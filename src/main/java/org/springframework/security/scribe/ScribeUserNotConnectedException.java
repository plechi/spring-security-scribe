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
