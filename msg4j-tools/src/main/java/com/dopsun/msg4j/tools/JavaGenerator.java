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

package com.dopsun.msg4j.tools;

import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.dopsun.msg4j.tools.model.FieldInfo;
import com.dopsun.msg4j.tools.model.ModelInfo;
import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public class JavaGenerator implements Generator {

    @Override
    public String generate(ModelInfo modelInfo) {
        String javaClassName = modelInfo.getOptions().get("javaClassName");
        List<String> parts = Lists.newArrayList(Splitter.on(".").split(javaClassName));
        String containerClassName = parts.get(parts.size() - 1);

        parts.remove(parts.size() - 1);
        String packageStr = Joiner.on(".").join(parts);

        initializeVelocityTemplate();

        VelocityContext context = new VelocityContext();
        Template javaProxyTemplate = getVelocityTemplate();

        context.put("generator", this);

        context.put("package", packageStr);
        context.put("runTime", new Date());
        context.put("containerClassName", containerClassName);
        context.put("model", modelInfo);

        StringBuffer sb = mergeTemplate(context, javaProxyTemplate);
        return sb.toString();
    }

    /**
     * AbcXyz to ABC_XYZ
     * 
     * @param name
     * @return
     */
    public String nameToConst(String name) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, name);
    }

    /**
     * @param fieldInfo
     * @return
     */
    public String getFieldInfoString(FieldInfo fieldInfo) {
        return fieldInfo.getType().name() + "FieldInfo";
    }

    /**
     * @param fieldInfo
     * @return
     */
    public boolean canFieldValueOptional(FieldInfo fieldInfo) {
        switch (fieldInfo.getType()) {
        case Int:
        case Long:
        case Double:
        case String:
        case Message:
        case MessageList:
            return true;
        default:
            return false;
        }
    }

    /**
     * @param fieldInfo
     * @param optional
     * @return
     */
    public String getFieldValueTypeString(FieldInfo fieldInfo, boolean optional) {
        switch (fieldInfo.getType()) {
        case Byte:
            if (optional) {
                throw new IllegalArgumentException();
            }

            return "byte";
        case Char:
            if (optional) {
                throw new IllegalArgumentException();
            }

            return "char";
        case Short:
            if (optional) {
                throw new IllegalArgumentException();
            }

            return "short";
        case Int:
            return optional ? "OptionalInt" : "int";
        case Long:
            return optional ? "OptionalLong" : "long";
        case Float:
            if (optional) {
                throw new IllegalArgumentException();
            }

            return "float";
        case Double:
            return optional ? "OptionalDouble" : "double";
        case String:
            return optional ? "Optional<String>" : "String";
        case Message:
            return optional ? "Optional<ImmutableMessage>" : "ImmutableMessage";
        case MessageList:
            return optional ? "Optional<List<ImmutableMessage>>" : "List<ImmutableMessage>";
        default:
            throw new RuntimeException("Unrecognized type: " + fieldInfo.getType());
        }
    }

    private StringBuffer mergeTemplate(VelocityContext context, Template javaProxyTemplate) {
        StringWriter sw = new StringWriter();

        try {
            javaProxyTemplate.merge(context, sw);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return new StringBuffer(sw.toString());
    }

    private Template getVelocityTemplate() {
        try {
            return Velocity.getTemplate("JavaTemplate.vm");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void initializeVelocityTemplate() {
        Properties p = new Properties();
        p.put("resource.loader", "classpath");
        p.put("classpath.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        try {
            Velocity.init(p);
        } catch (Exception e) {
            throw new RuntimeException("Can't initialise velocity");
        }
    }

}
