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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

import org.yaml.snakeyaml.Yaml;

import com.dopsun.msg4j.tools.model.ModelSource;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public class YamlModelSource implements ModelSource {
    /**
     * @param url
     * @return
     * @throws IOException
     */
    public static YamlModelSource load(URL url) throws IOException {
        Objects.requireNonNull(url);

        Yaml yaml = new Yaml();
        try (InputStream inputStream = url.openStream()) {
            @SuppressWarnings("unchecked")
            Map<String, Object> input = (Map<String, Object>) yaml.load(url.openStream());
            return new YamlModelSource(input);
        }
    }

    private final Map<String, Object> input;

    YamlModelSource(Map<String, Object> input) {
        Objects.requireNonNull(input);

        this.input = input;
    }

    /**
     * @return the input
     */
    public Map<String, Object> getInput() {
        return input;
    }
}
