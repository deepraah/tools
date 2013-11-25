package com.rightster.automation.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * *****************************
 * Author : Rahul Kumar
 * ******************************
 */

public class CreateClassList {

    private static final String JAVA_PACKAGE_SEPARATOR = ".";
    private static final String JAVA_FILE_EXTENSION = ".java";

    private static File file;
    private static File newfile;
    private static List<String> classesList;
    private static List<String> classesNameList;
    private static List<String> allClassesList;

    private static String finalClass="";

    public static void main(String[] args) {
        CreateTestPlan createTestPlan = new CreateTestPlan();
        for (String s : createTestPlan.createTestList()) {
            System.out.println("Module is : " + s);
        }
        for (String string : generateAllCompleteClassesNames("/localdata/rough/testframework/testsuites/apolloapis/src")) {
            System.out.println("Each class is : " + string);
        }
        for(String string1 : generateOnlyClassesName("/localdata/rough/testframework/testsuites/apolloapis/src")){
            System.out.println("Name is : " + string1);
        }
    }

    private static List<String> createClassesList(List<String> fileList) {
/*        allClassesList = new ArrayList<String>();
        for (String string : fileList) {
            file = new File(string + File.separator + "src");
            finalClass = "";
            for (String s : generateAllCompleteClassesNames(file)) {
                allClassesList.add(s);
            }
        }*/
        return allClassesList;
    }

    public static List<String> generateAllCompleteClassesNames(String fileString) {
        classesList = new ArrayList<String>();
        file = new File(fileString + File.separator);
        String[] files = file.list();
        for (String string : files) {
            newfile = new File(fileString + File.separator + string);
            if (!newfile.toString().contains("common") && !string.contains("resources")) {
                if (!string.contains(JAVA_FILE_EXTENSION) && newfile.isDirectory()) {
                    finalClass += string + JAVA_PACKAGE_SEPARATOR;
                    generateAllCompleteClassesNames(newfile.toString());
                } else if (string.contains(JAVA_FILE_EXTENSION)) {
                    finalClass = (finalClass + string).substring(0, (finalClass + string).lastIndexOf("."));
                    classesList.add(finalClass);
                }
            }
        }
        return classesList;
    }

    public static List<String> generateOnlyClassesName(String fileString) {
        classesNameList = new ArrayList<String>();
        file = new File(fileString);
        String[] files = file.list();
        for (String string : files) {
            newfile = new File(file + File.separator + string);
            if (!newfile.toString().contains("common") && !string.contains("resources")) {
                if (!string.contains(JAVA_FILE_EXTENSION) && newfile.isDirectory()) {
                    generateOnlyClassesName(newfile.toString());
                } else if (string.contains(JAVA_FILE_EXTENSION)) {
                    classesNameList.add(string.substring(0, string.lastIndexOf(".")));
                }
            }
        }
        return classesNameList;
    }
}