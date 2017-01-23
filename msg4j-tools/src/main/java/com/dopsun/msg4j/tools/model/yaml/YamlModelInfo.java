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

import com.dopsun.msg4j.tools.model.ConstInfo;
import com.dopsun.msg4j.tools.model.FieldInfo;
import com.dopsun.msg4j.tools.model.MessageInfo;
import com.dopsun.msg4j.tools.model.ModelInfo;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public class YamlModelInfo implements ModelInfo {

    private final Map<String, String> options;

    private final List<ConstInfo> consts;
    private final List<FieldInfo> fields;
    private final List<MessageInfo> messages;
    private final List<MessageInfo> finalMessages;

    private final Map<String, FieldInfo> fieldByName = new HashMap<>();
    private final Map<String, MessageInfo> messageByName = new HashMap<>();

    private final FieldInfo messageTypeField;

    YamlModelInfo(Map<String, Object> input) {
        Objects.requireNonNull(input);

        ImmutableMap.Builder<String, String> optionsBuilder = ImmutableMap.builder();
        ImmutableList.Builder<ConstInfo> constsBuilder = ImmutableList.builder();
        ImmutableList.Builder<FieldInfo> fieldsBuilder = ImmutableList.builder();
        ImmutableList.Builder<MessageInfo> messagesBuilder = ImmutableList.builder();
        ImmutableList.Builder<MessageInfo> finalMessagesBuilder = ImmutableList.builder();

        if (input.containsKey("options")) {
            @SuppressWarnings("unchecked")
            Map<String, String> inputOptions = (Map<String, String>) input.get("options");
            optionsBuilder.putAll(inputOptions);
        }

        if (input.containsKey("consts")) {
            @SuppressWarnings("unchecked")
            List<Map<String, String>> inputConsts = (List<Map<String, String>>) input.get("consts");
            for (Map<String, String> inputConst : inputConsts) {
                YamlConstInfo yamlConstInfo = new YamlConstInfo(inputConst);
                constsBuilder.add(yamlConstInfo);
            }
        }

        if (input.containsKey("fields")) {
            @SuppressWarnings("unchecked")
            List<Map<String, String>> inputFields = (List<Map<String, String>>) input.get("fields");
            for (Map<String, String> inputField : inputFields) {
                YamlFieldInfo yamlFieldInfo = new YamlFieldInfo(inputField);
                if (fieldByName.containsKey(yamlFieldInfo.getName())) {
                    throw new RuntimeException(
                            "Duplciated field name detected: " + yamlFieldInfo.getName());
                }
                fieldByName.put(yamlFieldInfo.getName(), yamlFieldInfo);
                fieldsBuilder.add(yamlFieldInfo);
            }
        }

        if (input.containsKey("messages")) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> inputMessages = (List<Map<String, Object>>) input
                    .get("messages");
            for (Map<String, Object> inputMessage : inputMessages) {
                YamlMessageInfo yamlMessageInfo = new YamlMessageInfo(this, inputMessage);
                if (messageByName.containsKey(yamlMessageInfo.getName())) {
                    throw new RuntimeException(
                            "Duplciated message name detected: " + yamlMessageInfo.getName());
                }
                messageByName.put(yamlMessageInfo.getName(), yamlMessageInfo);

                messagesBuilder.add(yamlMessageInfo);
                if (!yamlMessageInfo.isAbstract()) {
                    finalMessagesBuilder.add(yamlMessageInfo);
                }
            }
        }

        options = optionsBuilder.build();
        consts = constsBuilder.build();
        fields = fieldsBuilder.build();
        messages = messagesBuilder.build();
        finalMessages = finalMessagesBuilder.build();

        String optMessageTypeFieldName = options.get("messageTypeField");
        if (optMessageTypeFieldName == null) {
            optMessageTypeFieldName = "MessageType";
        }

        if (!fieldByName.containsKey(optMessageTypeFieldName)) {
            throw new RuntimeException("Option 'messageTypeField' not found.");
        }

        this.messageTypeField = fieldByName.get(optMessageTypeFieldName);
    }

    final FieldInfo getFieldByName(String name) {
        return fieldByName.get(name);
    }

    final MessageInfo getMessageByName(String name) {
        return messageByName.get(name);
    }

    @Override
    public FieldInfo getMessageTypeField() {
        return messageTypeField;
    }

    @Override
    public Map<String, String> getOptions() {
        return options;
    }

    @Override
    public List<ConstInfo> getConsts() {
        return consts;
    }

    @Override
    public List<FieldInfo> getFields() {
        return fields;
    }

    @Override
    public List<MessageInfo> getMessages() {
        return messages;
    }

    @Override
    public List<MessageInfo> getFinalMessages() {
        return finalMessages;
    }

}
