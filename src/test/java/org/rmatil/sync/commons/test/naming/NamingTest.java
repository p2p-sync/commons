package org.rmatil.sync.commons.test.naming;

import org.junit.Test;
import org.rmatil.sync.commons.path.Naming;

import static org.junit.Assert.*;

public class NamingTest {

    @Test
    public void testGetPathWithoutFileName() {
        String fn1 = "File.txt";
        String f1 = "my/Path/To/File.txt";

        assertEquals("Path is not correct", "my/Path/To", Naming.getPathWithoutFileName(fn1, f1));

        String f2 = "./my/Path/To/File.txt";
        assertEquals("Path is not correct", "./my/Path/To", Naming.getPathWithoutFileName(fn1, f2));

        String f3 = "/my/Path/To/File.txt";
        assertEquals("Path is not correct", "/my/Path/To", Naming.getPathWithoutFileName(fn1, f3));
    }
}
