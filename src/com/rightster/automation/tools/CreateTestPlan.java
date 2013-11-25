package com.test.automation.tools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * *****************************
 * Author : Rahul Kumar
 * ******************************
 */

public class CreateTestPlan {

    private static final String SRC_DIR = "src";
    private static final String OUTPUT_DIR_NAME = "output";
    private static final String TESTSUITES_DIR_NAME = "testsuites";
    private static final String TEST_PLAN_FILE = "testplan.txt";

    private static String BASE_TEST_DIR;
    private static String INCLUDE_MODULES;
    private static String TEST_PLAN;

    private String fileName;
    private String[] allFilesName;

    private File file;
    private BufferedWriter bufferedWriter;
    private static List<String> fileList = new ArrayList<String>();

    public static void main(String[] args) {
        CreateTestPlan createTestPlan = new CreateTestPlan();
        createTestPlan.createTestList();
    }

    public CreateTestPlan() {
        String getIncludeModules = "includemodules";
        String getBaseTestDir = "workspace.dir";

        Properties systemProperties = System.getProperties();

        INCLUDE_MODULES = systemProperties.getProperty(getIncludeModules);
        BASE_TEST_DIR = systemProperties.getProperty(getBaseTestDir) + File.separator + TESTSUITES_DIR_NAME;
        TEST_PLAN = systemProperties.getProperty(getBaseTestDir) + File.separator + OUTPUT_DIR_NAME + File.separator + TEST_PLAN_FILE;
    }

    public List<String> createTestList() {
        if (INCLUDE_MODULES != null) {
            if (INCLUDE_MODULES.equalsIgnoreCase("all")) {
                fileName = BASE_TEST_DIR;
                createTestRootDirectory(fileName);
            } else if (INCLUDE_MODULES.contains(",")) {
                allFilesName = INCLUDE_MODULES.split("\\,");
                for (String allFileName : allFilesName) {
                    fileName = BASE_TEST_DIR + File.separator + allFileName.trim();
                    createTestRootDirectory(fileName);
                }
            } else {
                fileName = BASE_TEST_DIR + File.separator + INCLUDE_MODULES;
                createTestRootDirectory(fileName);
            }
        }
        return fileList;
    }

    private void createTestPlanFile(List<String> fileList) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(TEST_PLAN));
            for (String s : fileList) {
                bufferedWriter.write(s + "\n");
            }
            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void createTestRootDirectory(String fileName) {
        file = new File(fileName);
        listTest(file);
    }

    private void listTest(File fileName) {
        if (fileName.exists() && fileName.isDirectory()) {
            for (File temp : fileName.listFiles()) {
                if (temp.toString().contains(SRC_DIR) && temp.isDirectory()) {
                    fileList.add(fileName.getAbsolutePath());
                    break;
                }
                listTest(temp);
            }
        }
    }
}