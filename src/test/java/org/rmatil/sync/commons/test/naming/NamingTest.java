package org.rmatil.sync.commons.test.naming;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.rmatil.sync.commons.path.Naming;

import static org.junit.Assert.*;

public class NamingTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    @Test
    public void testGetConflictFileName() {
        String deviceId = "client1";
        String illegalDeviceId = "client.1";

        String fn1 = "myPath/to/myFile.txt";
        String fn2 = "myPath/to/myFile.tar.gz";
        String fn3 = "myPath/to/myFileWithoutExtension";
        String fn4 = "myPath/to/myDir";
        String fn5 = "myPath/to/myDir.With.Dots";

        String ret1 = Naming.getConflictFileName(fn1, true, "txt", deviceId);
        assertEquals("Filename for fn1 is not equal", "myFile_client1.txt", ret1);

        String ret2 = Naming.getConflictFileName(fn2, true, "tar.gz", deviceId);
        assertEquals("Filename for fn2 is not equal", "myFile_client1.tar.gz", ret2);

        String ret3 = Naming.getConflictFileName(fn3, true, "", deviceId);
        assertEquals("Filename for fn3 is not equal", "myFileWithoutExtension_client1", ret3);

        String ret4 = Naming.getConflictFileName(fn4, false, "", deviceId);
        assertEquals("Filename for fn4 is not equal", "myDir_client1", ret4);

        String ret5 = Naming.getConflictFileName(fn5, false, "", deviceId);
        assertEquals("Filename for fn4 is not equal", "myDir.With.Dots_client1", ret5);

        thrown.expect(IllegalArgumentException.class);
        Naming.getConflictFileName(fn1, true, "txt", illegalDeviceId);
    }
}
