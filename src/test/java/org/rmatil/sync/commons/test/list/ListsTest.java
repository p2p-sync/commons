package org.rmatil.sync.commons.test.list;

import org.junit.Test;
import org.rmatil.sync.commons.list.Lists;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;

public class ListsTest {

    @Test
    public void testGetInstance() {
        List<ITestClass> testClasses = new ArrayList<>();
        testClasses.add(new TestClass1());
        testClasses.add(new TestClass2());

        List<ITestClass> testclass1 = Lists.getInstances(testClasses, TestClass1.class);
        assertEquals("Instances are not of size 1", 1, testclass1.size());
        assertThat("testClass1 is not instance of TestClass1.class", testclass1.get(0), instanceOf(TestClass1.class));

        List<ITestClass> testclass2 = Lists.getInstances(testClasses, TestClass2.class);
        assertEquals("Instances are not of size 1", 1, testclass2.size());
        assertThat("testClass2 is not instance of TestClass2.class", testclass2.get(0), instanceOf(TestClass2.class));

    }

    public interface ITestClass {}

    public class TestClass1 implements ITestClass {}

    public class TestClass2 implements ITestClass {}

}
