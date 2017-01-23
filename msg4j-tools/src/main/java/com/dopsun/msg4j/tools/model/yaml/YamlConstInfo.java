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

package com.dopsun.msg4j.tools.model.yaml;

import java.util.Map;
import java.util.Objects;

import com.dopsun.msg4j.tools.model.ConstInfo;
import com.dopsun.msg4j.tools.model.ConstType;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public class YamlConstInfo implements ConstInfo {
    private final String name;
    private final ConstType type;
    private final String value;

    YamlConstInfo(Map<String, String> input) {
        Objects.requireNonNull(input);

        name = Objects.requireNonNull(input.get("name"));
        type = Enum.valueOf(ConstType.class, input.get("type"));
        value = Objects.requireNonNull(input.get("value"));
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public ConstType type() {
        return type;
    }

    @Override
    public String value() {
        return value;
    }

}
