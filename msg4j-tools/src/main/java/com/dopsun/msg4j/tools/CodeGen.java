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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.dopsun.msg4j.tools.model.ModelInfo;
import com.dopsun.msg4j.tools.model.yaml.YamlModelParser;
import com.dopsun.msg4j.tools.model.yaml.YamlModelSource;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public class CodeGen {
    /**
     * @param args
     * @throws IOException
     * @throws MalformedURLException
     * @throws ParseException
     */
    public static void main(String[] args)
            throws MalformedURLException, IOException, ParseException {
        Options options = new Options();
        options.addOption("o", "out", true, "Output file");
        options.addOption("s", "src", true, "Source file");
        options.addOption("l", "lang", true, "Language");

        CommandLineParser cmdParser = new DefaultParser();
        CommandLine cmd = cmdParser.parse(options, args);

        String lang = cmd.getOptionValue("lang", "Java");
        String srcFile = cmd.getOptionValue("src");
        String outFile = cmd.getOptionValue("out");

        YamlModelSource modelSource = YamlModelSource.load(new File(srcFile).toURI().toURL());
        YamlModelParser parser = YamlModelParser.create();
        ModelInfo modelInfo = parser.parse(modelSource);

        String codeText = null;
        if (lang.equals("Java")) {
            codeText = Generator.JAVA.generate(modelInfo);
        } else {
            throw new UnsupportedOperationException("Unrecognized lang: " + lang);
        }

        if (outFile != null) {
            File file = new File(outFile);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            Files.append(codeText, new File(outFile), Charsets.UTF_8);
        } else {
            System.out.println(codeText);
        }
    }
}
