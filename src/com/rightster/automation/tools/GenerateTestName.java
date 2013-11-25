package com.rightster.automation.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * *****************************
 * Author : Rahul Kumar
 * ******************************
 */

public class GenerateTestName {
    static String getBaseTestDir = "workspace.dir";

    static Properties systemProperties = System.getProperties();

    private static final String TESTSUITES_DIR_NAME = "testsuites";
    private static final String BASE_DIR = systemProperties.getProperty(getBaseTestDir) + File.separator +
            TESTSUITES_DIR_NAME + File.separator;

    public String testName(String string) {
        String[] x = string.trim().split(BASE_DIR);
        return (x[1].trim().replaceAll("/", "."));
    }
}
