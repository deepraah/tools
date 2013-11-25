package com.test.automation.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * *****************************
 * Author : Rahul Kumar
 * ******************************
 */

public class Test {
    private static String string = " firstclass@firstfc/secondfc/ thirdfc,  secondclass@firstsc/secondsc";
    private static String[] allClassMethods;
    private static String[] allClassMethod;
    private static String[] individualClassMethod;

    private static List<String> finalClassMethod;
    private static List<List<String>> finalClassMethods;

    public static void main(String[] args) {
        for (List<String> list : testArray(string)) {
            System.out.println("size : " + list.size());
        }
        System.out.println(testArray(string));
    }

    private static List<List<String>> testArray(String string) {
        finalClassMethods = new ArrayList<List<String>>();
        if (!string.trim().isEmpty()) {
            if (string.contains(",")) {
                allClassMethods = string.split(",");
            } else {
                allClassMethods = new String[1];
                allClassMethods[0] = string;
            }
            for (String string1 : allClassMethods) {
                finalClassMethod = new ArrayList<String>();
                if (!string.trim().isEmpty()) {
                    allClassMethod = string1.trim().split("@");
                    finalClassMethod.add(allClassMethod[0]);
                    individualClassMethod = allClassMethod[1].toString().split("/");
                    for (String string2 : individualClassMethod) {
                        finalClassMethod.add(string2);
                    }
                    finalClassMethods.add(finalClassMethod);
                }
            }
        }
        return finalClassMethods;
    }
}