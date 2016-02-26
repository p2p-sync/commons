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

        String f4 = "/myDir/myPath/myDir/myPath";
        assertEquals("Path is not correct", "/myDir/myPath/myDir", Naming.getPathWithoutFileName("myPath", f4));

        String f5 = "myDir";
        assertEquals("Path is not correct", "", Naming.getPathWithoutFileName("myDir", f5));

        String f6 = "/myFile.txt";
        assertEquals("Path is not correct", "/", Naming.getPathWithoutFileName("myFile.txt", f6));
    }

    @Test
    public void testGetParentPath() {
        assertEquals("Parent path is not equal", "my/path/to", Naming.getParentPath("my/path/to/myFile.txt"));
        assertEquals("Parent path is not equal", "my/path/to", Naming.getParentPath("my/path/to/myFile"));
        assertEquals("Parent path is not equal", "/my/path/to", Naming.getParentPath("/my/path/to/myFile.txt"));
        assertEquals("Parent path is not equal", "/my/path/to", Naming.getParentPath("/my/path/to/myFile"));
        assertEquals("Parent path is not equal", "/", Naming.getParentPath("/myFile.txt"));
        assertNull("Parent path should be null", Naming.getParentPath("myFile.txt"));
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
        String fn6 = "myFile.txt";
        String fn7 = "/myFile.txt";

        String ret1 = Naming.getConflictFileName(fn1, true, "txt", deviceId);
        assertEquals("Filename for fn1 is not equal", "myPath/to/myFile_client1.txt", ret1);

        String ret2 = Naming.getConflictFileName(fn2, true, "tar.gz", deviceId);
        assertEquals("Filename for fn2 is not equal", "myPath/to/myFile_client1.tar.gz", ret2);

        String ret3 = Naming.getConflictFileName(fn3, true, "", deviceId);
        assertEquals("Filename for fn3 is not equal", "myPath/to/myFileWithoutExtension_client1", ret3);

        String ret4 = Naming.getConflictFileName(fn4, false, "", deviceId);
        assertEquals("Filename for fn4 is not equal", "myPath/to/myDir_client1", ret4);

        String ret5 = Naming.getConflictFileName(fn5, false, "", deviceId);
        assertEquals("Filename for fn4 is not equal", "myPath/to/myDir.With.Dots_client1", ret5);

        String ret6 = Naming.getConflictFileName(fn6, true, "txt", deviceId);
        assertEquals("Filename for fn6 is not equal", "myFile_client1.txt", ret6);

        String ret7 = Naming.getConflictFileName(fn7, true, "txt", deviceId);
        assertEquals("Filename for fn7 is not equal", "/myFile_client1.txt", ret7);

        thrown.expect(IllegalArgumentException.class);
        Naming.getConflictFileName(fn1, true, "txt", illegalDeviceId);
    }
}
