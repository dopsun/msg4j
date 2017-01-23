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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.dopsun.msg4j.tools.model.FieldInfo;
import com.dopsun.msg4j.tools.model.MessageInfo;
import com.google.common.collect.ImmutableList;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public class YamlMessageInfo implements MessageInfo {
    private final String id;
    private final String name;
    private final boolean isAbstract;

    private final List<FieldInfo> requiredFields;
    private final List<FieldInfo> optionalFields;

    private final List<MessageInfo> includedMessages;

    YamlMessageInfo(YamlModelInfo modelInfo, Map<String, Object> input) {
        Objects.requireNonNull(modelInfo);
        Objects.requireNonNull(input);

        name = Objects.requireNonNull((String) input.get("name"));

        if (input.containsKey("id")) {
            id = Objects.requireNonNull((String) input.get("id"));
        } else {
            id = name;
        }

        if (input.containsKey("isAbstract")) {
            isAbstract = (Boolean) input.get("isAbstract");
        } else {
            isAbstract = false;
        }

        ImmutableList.Builder<FieldInfo> requiredFieldsBuilder = ImmutableList.builder();
        ImmutableList.Builder<FieldInfo> optionalFieldsBuilder = ImmutableList.builder();
        ImmutableList.Builder<MessageInfo> includedMessagesBuilder = ImmutableList.builder();

        Map<String, FieldInfo> fieldsMap = new HashMap<>();

        if (input.containsKey("fields")) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> inputFields = (List<Map<String, Object>>) input.get("fields");
            for (Map<String, Object> inputField : inputFields) {
                boolean isOptional = inputField.containsKey("optional")
                        && ((Boolean) inputField.get("optional"));

                String fieldName = (String) inputField.get("name");
                if (fieldsMap.containsKey(fieldName)) {
                    continue;
                }

                FieldInfo fieldInfo = modelInfo.getFieldByName(fieldName);
                fieldsMap.put(fieldName, fieldInfo);

                if (isOptional) {
                    optionalFieldsBuilder.add(fieldInfo);
                } else {
                    requiredFieldsBuilder.add(fieldInfo);
                }
            }
        }

        if (input.containsKey("includeMessages")) {
            @SuppressWarnings("unchecked")
            List<Map<String, String>> inputMessages = (List<Map<String, String>>) input
                    .get("includeMessages");
            for (Map<String, String> inputMessage : inputMessages) {
                String fieldName = inputMessage.get("name");
                MessageInfo messageInfo = modelInfo.getMessageByName(fieldName);
                includedMessagesBuilder.add(messageInfo);

                for (FieldInfo fieldInfo : messageInfo.getRequiredFields()) {
                    if (fieldsMap.containsKey(fieldInfo.getName())) {
                        continue;
                    }

                    fieldsMap.put(fieldInfo.getName(), fieldInfo);
                    requiredFieldsBuilder.add(fieldInfo);
                }

                for (FieldInfo fieldInfo : messageInfo.getOptionalFields()) {
                    if (fieldsMap.containsKey(fieldInfo.getName())) {
                        continue;
                    }

                    fieldsMap.put(fieldInfo.getName(), fieldInfo);
                    optionalFieldsBuilder.add(fieldInfo);
                }
            }
        }

        requiredFields = requiredFieldsBuilder.build();
        optionalFields = optionalFieldsBuilder.build();

        includedMessages = includedMessagesBuilder.build();
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
    public boolean isAbstract() {
        return isAbstract;
    }

    @Override
    public List<FieldInfo> getRequiredFields() {
        return requiredFields;
    }

    @Override
    public List<FieldInfo> getOptionalFields() {
        return optionalFields;
    }

    @Override
    public List<MessageInfo> getIncludedMessages() {
        return includedMessages;
    }

}
