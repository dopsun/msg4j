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

package com.dopsun.msg4j.core;

import java.util.Objects;

/**
 * Field with specified name not found.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
public class FieldNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -5065097779058583582L;

    private final String fieldName;

    /**
     * @param fieldName
     */
    public FieldNotFoundException(String fieldName) {
        super("Field not exist: " + Objects.requireNonNull(fieldName));

        this.fieldName = fieldName;
    }

    /**
     * @return
     */
    public String getFieldName() {
        return fieldName;
    }
}
