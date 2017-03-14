/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dopsun.msg4j.o2m;

import javax.annotation.Nullable;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public class O2mServerRequestErrorException extends RuntimeException {
    private static final long serialVersionUID = -2012803288095058732L;

    private int errorCode;

    @Nullable
    private String errorText;

    /**
     * Constructs a new exception with {@code null} as its detail message. The cause is not
     * initialized, and may subsequently be initialized by a call to {@link #initCause}.
     * 
     * @param errorCode
     * @param errorText
     */
    public O2mServerRequestErrorException(int errorCode, @Nullable String errorText) {
        super("Request error: code: " + errorCode + " text: " + errorText);

        this.errorCode = errorCode;
        this.errorText = errorText;
    }

    /**
     * @return the errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * @return the errorText
     */
    public String getErrorText() {
        return errorText;
    }
}
