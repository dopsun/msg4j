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

import com.dopsun.msg4j.tools.model.ModelInfo;
import com.dopsun.msg4j.tools.model.ModelParser;
import com.dopsun.msg4j.tools.model.ModelSource;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public class YamlModelParser implements ModelParser {
    /**
     * @return
     */
    public static YamlModelParser create() {
        return new YamlModelParser();
    }

    YamlModelParser() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public ModelInfo parse(ModelSource source) {
        Objects.requireNonNull(source);
        if (!(source instanceof YamlModelSource)) {
            throw new IllegalArgumentException();
        }

        YamlModelSource yamlSource = (YamlModelSource) source;

        return doParse(yamlSource);
    }

    private YamlModelInfo doParse(YamlModelSource source) {
        Map<String, Object> input = source.getInput();
        return new YamlModelInfo(input);
    }

}
