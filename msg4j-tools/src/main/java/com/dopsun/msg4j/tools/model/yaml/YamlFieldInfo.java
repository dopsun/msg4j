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

import com.dopsun.msg4j.tools.model.FieldInfo;
import com.dopsun.msg4j.tools.model.FieldType;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public class YamlFieldInfo implements FieldInfo {
    private final String id;
    private final String name;
    private final FieldType type;

    YamlFieldInfo(Map<String, String> input) {
        Objects.requireNonNull(input);

        id = Objects.requireNonNull(input.get("id"));
        name = Objects.requireNonNull(input.get("name"));
        type = Enum.valueOf(FieldType.class, input.get("type"));
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public FieldType getType() {
        return type;
    }

}
