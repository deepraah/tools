package com.rightster.automation.tools;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * *****************************
 * Author : Rahul Kumar
 * ******************************
 */

public class CreateTestNGSuite {
    private static String CLASS_INCLUDE_METHODS = " first@firstfc/secondfc/ thirdfc,  second@firstsc/secondsc/ thirdsc, " +
            "third@firsttc/secondtc/thirdtc/fourthtc";
    private static String CLASS_EXCLUDE_METHODS = " first@firstexcludefc/secondexcludefc/ thirdexcludefc, " +
            " second@firstexcludesc/secondexcludesc/ thirdexcludesc, thirdnono@firstexcludetc/fifthextc/sixthextc";
    //    private static String CLASS_EXCLUDE_METHODS = "";

    private static List<TestNGSuiteBean.ClassesEntry> classesEntryList;
    private static List<TestNGSuiteBean.TestEntry> testEntryList;
    private static TestNGSuiteBean.ClassesEntry classesEntry;
    private static TestNGSuiteBean.TestEntry testEntry;
    private static TestNGSuiteBean.GroupsEntry groupsEntry;
    private static TestNGSuiteBean.RunMethodEntry methodEntry;

    private static List<TestNGSuiteBean.IncludeMethods> includeMethodsList;
    private static TestNGSuiteBean.IncludeMethods includeMethods;
    private static List<TestNGSuiteBean.ExcludeMethods> excludeMethodsList;
    private static TestNGSuiteBean.ExcludeMethods excludeMethods;

    private static List<List<String>> classIncludeMethodsList;
    private static List<List<String>> classExcludeMethodsList;

    static ClassMethods classMethods = new ClassMethods();

    public static void main(String[] args) throws JAXBException {

        TestNGSuiteBean testNGSuiteBean = new TestNGSuiteBean();
        CreateTestPlan createTestPlan = new CreateTestPlan();
        GenerateTestName generateTestName = new GenerateTestName();
        CreateClassList createClassList = new CreateClassList();
        testNGSuiteBean.setName("TestNGTestSuites");

        testEntryList = new ArrayList<TestNGSuiteBean.TestEntry>();
        groupsEntry = new TestNGSuiteBean.GroupsEntry();

        for (String string : createTestPlan.createTestList()) {
            testEntry = new TestNGSuiteBean.TestEntry();
            testEntry.setName(generateTestName.testName(string));
            List<String> newone = CreateClassList.generateOnlyClassesName(string);
            newone.contains("first");
            if (!CLASS_INCLUDE_METHODS.isEmpty() && CLASS_EXCLUDE_METHODS.isEmpty()) {
                classIncludeMethodsList = classMethods.createClassMethodList(CLASS_INCLUDE_METHODS);
                classesEntryList = new ArrayList<TestNGSuiteBean.ClassesEntry>();
                for (List<String> classMethodList : classIncludeMethodsList) {
                    if (classMethodList.size() > 1) {
                        classesEntryList.add(ClassEntryOneMethod(classMethodList, "include"));
                    }
                }
            } else if (!CLASS_EXCLUDE_METHODS.isEmpty() && CLASS_INCLUDE_METHODS.isEmpty()) {
                classExcludeMethodsList = classMethods.createClassMethodList(CLASS_EXCLUDE_METHODS);
                classesEntryList = new ArrayList<TestNGSuiteBean.ClassesEntry>();
                for (List<String> classMethodList : classExcludeMethodsList) {
                    if (classMethodList.size() > 1) {
                        classesEntryList.add(ClassEntryOneMethod(classMethodList, "exclude"));
                    }
                }

            } else if (!CLASS_EXCLUDE_METHODS.isEmpty() && !CLASS_INCLUDE_METHODS.isEmpty()) {
                classIncludeMethodsList = classMethods.createClassMethodList(CLASS_INCLUDE_METHODS);
                classExcludeMethodsList = classMethods.createClassMethodList(CLASS_EXCLUDE_METHODS);
                classesEntryList = ClassEntryBothMethods(classIncludeMethodsList, classExcludeMethodsList);
            }


//        groupsEntry.setRunMethodEntry(MethodEntry(includeMethodNames, excludeMethodNames));

            testEntry.setClassesEntry(classesEntryList);
            testEntry.setGroupsEntry(groupsEntry);
            testEntryList.add(testEntry);
            testNGSuiteBean.setTestEntry(testEntryList);
        }

        File file = new File("/localdata/rough/testframework/output/testngsuite.xml");

        JAXBContext jaxbContext = JAXBContext.newInstance(TestNGSuiteBean.class);
        Marshaller marshaller = jaxbContext.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(testNGSuiteBean, file);
        marshaller.marshal(testNGSuiteBean, System.out);
    }


    private static TestNGSuiteBean.ExcludeMethods ExcludeMethodEntry(String methodName) {
        excludeMethods = new TestNGSuiteBean.ExcludeMethods();
        excludeMethods.setName(methodName);
        return excludeMethods;
    }

    private static TestNGSuiteBean.IncludeMethods IncludeMethodEntry(String methodName) {
        includeMethods = new TestNGSuiteBean.IncludeMethods();
        includeMethods.setName(methodName);
        return includeMethods;
    }

    private static TestNGSuiteBean.ClassesEntry ClassEntryOneMethod(List<String> classMethods, String action) {
        classesEntry = new TestNGSuiteBean.ClassesEntry();
        classesEntry.setName(classMethods.get(0));
        classMethods.remove(0);

        classesEntry.setMethodEntry(MethodEntry(classMethods, action));
        return classesEntry;
    }

    private static List<TestNGSuiteBean.ClassesEntry> ClassEntryBothMethods(List<List<String>> includeClassMethods,
                                                                            List<List<String>> excludeClassMethods) {
        classesEntryList = new ArrayList<TestNGSuiteBean.ClassesEntry>();
        List<List<String>> includeMethodsToBeDeleted = new ArrayList<List<String>>();
        for (List<String> includeStringList : includeClassMethods) {
            for (List<String> excludeStringList : excludeClassMethods) {
                if (includeStringList.get(0).equals(excludeStringList.get(0))) {
                    includeMethodsToBeDeleted.add(includeStringList);
                    classesEntry = new TestNGSuiteBean.ClassesEntry();
                    classesEntry.setName(includeStringList.get(0));
                    includeStringList.remove(0);
                    excludeStringList.remove(0);
                    classesEntry.setMethodEntry(MethodEntry(includeStringList, excludeStringList));
                    classesEntryList.add(classesEntry);
                    excludeClassMethods.remove(excludeStringList);
                    break;
                }
            }
        }
        if (!includeMethodsToBeDeleted.isEmpty()) {
            for (List<String> includeMethodToBeDeleted : includeMethodsToBeDeleted) {
                includeClassMethods.remove(includeMethodToBeDeleted);
            }
        }

        for (List<String> includeStringList : includeClassMethods) {
            classesEntry = new TestNGSuiteBean.ClassesEntry();
            classesEntry.setName(includeStringList.get(0));
            includeStringList.remove(0);
            classesEntry.setMethodEntry(MethodEntry(includeStringList, "include"));
            classesEntryList.add(classesEntry);
        }
        for (List<String> excludeStringList : excludeClassMethods) {
            classesEntry = new TestNGSuiteBean.ClassesEntry();
            classesEntry.setName(excludeStringList.get(0));
            excludeStringList.remove(0);
            classesEntry.setMethodEntry(MethodEntry(excludeStringList, "exclude"));
            classesEntryList.add(classesEntry);
        }
        return classesEntryList;
    }

    private static TestNGSuiteBean.RunMethodEntry MethodEntry(List<String> methodNames, String action) {
        methodEntry = new TestNGSuiteBean.RunMethodEntry();
        if (methodNames != null) {
            includeMethodsList = new ArrayList<TestNGSuiteBean.IncludeMethods>(methodNames.size());
            excludeMethodsList = new ArrayList<TestNGSuiteBean.ExcludeMethods>(methodNames.size());
            for (String methodName : methodNames) {
                if (action == "include") {
                    includeMethodsList.add(IncludeMethodEntry(methodName));
                    methodEntry.setIncludeMethods(includeMethodsList);
                } else if (action == "exclude") {
                    excludeMethodsList.add(ExcludeMethodEntry(methodName));
                    methodEntry.setExcludeMethods(excludeMethodsList);
                }
            }
        }
        return methodEntry;
    }

    private static TestNGSuiteBean.RunMethodEntry MethodEntry(List<String> includeMethodNames, List<String> excludeMethodNames) {
        methodEntry = new TestNGSuiteBean.RunMethodEntry();
        if (includeMethodNames != null && excludeMethodNames != null) {
            includeMethodsList = new ArrayList<TestNGSuiteBean.IncludeMethods>(includeMethodNames.size());
            excludeMethodsList = new ArrayList<TestNGSuiteBean.ExcludeMethods>(excludeMethodNames.size());
            for (String includeMethodName : includeMethodNames) {
                includeMethodsList.add(IncludeMethodEntry(includeMethodName));
            }
            for (String excludeMethodName : excludeMethodNames) {
                excludeMethodsList.add(ExcludeMethodEntry(excludeMethodName));
            }
            methodEntry.setIncludeMethods(includeMethodsList);
            methodEntry.setExcludeMethods(excludeMethodsList);
        }
        return methodEntry;
    }
}