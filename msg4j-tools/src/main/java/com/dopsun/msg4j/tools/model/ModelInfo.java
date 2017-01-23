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

package com.dopsun.msg4j.tools.model;

import java.util.List;
import java.util.Map;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public interface ModelInfo {
    /**
     * @return field for message type.
     */
    FieldInfo getMessageTypeField();

    /**
     * @param fieldInfo
     * @return <code>true</code> if <code>fieldInfo</code> is message type field.
     */
    default boolean isMessageTypeField(FieldInfo fieldInfo) {
        return fieldInfo.getName().equals(getMessageTypeField().getName());
    }

    /**
     * @return
     */
    public Map<String, String> getOptions();

    /**
     * @return
     */
    public List<ConstInfo> getConsts();

    /**
     * @return
     */
    public List<FieldInfo> getFields();

    /**
     * @return
     */
    public List<MessageInfo> getMessages();

    /**
     * @return
     */
    public List<MessageInfo> getFinalMessages();
}
