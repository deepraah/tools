package com.rightster.automation.tools;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * *****************************
 * Author : Rahul Kumar
 * ******************************
 */

@XmlRootElement(name = "suite")
public class TestNGSuiteBean {
    private String name;

    @XmlAttribute(name = "name", required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List<TestEntry> testEntry;

    @XmlElement(name = "test", type = TestEntry.class)
    public void setTestEntry(List<TestEntry> testEntry) {
        this.testEntry = testEntry;
    }

    public List<TestEntry> getTestEntry() {
        return testEntry;
    }


    @XmlRootElement(name = "test")
    public static class TestEntry {
        private List<ClassesEntry> classesEntry = new ArrayList<ClassesEntry>();
        private GroupsEntry groupsEntry;
        private int verbose;
        private String name;

        @XmlAttribute
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlElement(name = "class")
        @XmlElementWrapper(name = "classes")
        public List<ClassesEntry> getClassesEntry() {
            return classesEntry;
        }

        public void setClassesEntry(List<ClassesEntry> classesEntry) {
            this.classesEntry = classesEntry;
        }

        @XmlElement(name =  "groups")
        public GroupsEntry getGroupsEntry() {
            return groupsEntry;
        }

        public void setGroupsEntry(GroupsEntry groupsEntry) {
            this.groupsEntry = groupsEntry;
        }
    }

    @XmlRootElement(name = "class")
    public static class ClassesEntry {
        private String name;
        private RunMethodEntry methodEntry;


        @XmlAttribute(required = true)
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlElement(name = "methods")
        public RunMethodEntry getMethodEntry() {
            return methodEntry;
        }

        public void setMethodEntry(RunMethodEntry methodEntry) {
            this.methodEntry = methodEntry;
        }
    }

    public static class GroupsEntry {
        private RunMethodEntry runMethodEntry;

        @XmlElement(name = "run")
        public RunMethodEntry getRunMethodEntry() {
            return runMethodEntry;
        }

        public void setRunMethodEntry(RunMethodEntry runMethodEntry) {
            this.runMethodEntry = runMethodEntry;
        }
    }

    public static class RunMethodEntry {
        private List<IncludeMethods> includeMethods = new ArrayList<IncludeMethods>();
        private List<ExcludeMethods> excludeMethods = new ArrayList<ExcludeMethods>();

        @XmlElement(name = "include")
        public List<IncludeMethods> getIncludeMethods() {
            return includeMethods;
        }

        public void setIncludeMethods(List<IncludeMethods> includeMethods) {
            this.includeMethods = includeMethods;
        }

        @XmlElement(name = "exclude")
        public List<ExcludeMethods> getExcludeMethods() {
            return excludeMethods;
        }

        public void setExcludeMethods(List<ExcludeMethods> excludeMethods) {
            this.excludeMethods = excludeMethods;
        }
    }

    public static class IncludeMethods {
        private String name;

        @XmlAttribute
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ExcludeMethods {
        private String name;

        @XmlAttribute
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}