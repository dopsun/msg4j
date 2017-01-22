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

package com.dopsun.msg4j.core.messages;

import javax.annotation.Nonnull;

/**
 * Field type is not expected.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
public class InvalidTypeException extends RuntimeException {
    private static final long serialVersionUID = -4497190672087372492L;

    /**
     * @param fieldName
     * @param expected
     * @param actual
     */
    public InvalidTypeException(@Nonnull String fieldName, FieldType expected, FieldType actual) {
        super("Field type mismatch: name=" + fieldName + ", expected=" + expected + ", actual="
                + actual);
    }
}
