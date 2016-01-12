package org.rmatil.sync.commons.test.hashing;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.rmatil.sync.commons.hashing.Hash;
import org.rmatil.sync.commons.hashing.HashingAlgorithm;
import org.rmatil.sync.commons.test.config.Config;
import org.rmatil.sync.commons.test.util.APathTest;
import org.rmatil.sync.commons.test.util.FileUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class HashTest {

    protected static Path testFile;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        APathTest.setUp();
    }

    @AfterClass
    public static void tearDown() {
        APathTest.tearDown();
    }

    @Before
    public void before() {
        testFile = FileUtil.createTestFile(Config.DEFAULT.getRootTestDir());
    }

    @After
    public void after() {
        testFile = FileUtil.deleteTestFile(Config.DEFAULT.getRootTestDir());

    }

    @Test
    public void testSha1()
            throws IOException {
        String hash = Hash.hash(HashingAlgorithm.SHA_1, testFile.toFile());

        assertEquals("SHA1 hash is not equal", "da39a3ee5e6b4b0d3255bfef95601890afd80709", hash);

        testFile = FileUtil.modifyTestFile(Config.DEFAULT.getRootTestDir());

        hash = Hash.hash(HashingAlgorithm.SHA_1, testFile.toFile());

        assertEquals("SHA1 hash is not equal after modification", "f9e7d601b1f22e2ead34c1afd7dfee195f31a711", hash);

        testFile = FileUtil.deleteTestFile(Config.DEFAULT.getRootTestDir());
    }

    @Test
    public void testSha256()
            throws IOException {

        String hash = Hash.hash(HashingAlgorithm.SHA_256, testFile.toFile());

        assertEquals("SHA256 hash is not equal", "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", hash);

        testFile = FileUtil.modifyTestFile(Config.DEFAULT.getRootTestDir());

        hash = Hash.hash(HashingAlgorithm.SHA_256, testFile.toFile());

        assertEquals("SHA256 hash is not equal after modification", "2dddc2bb86f352ea34213f067371c3eea9edab97001bee9aa5047df583b739ba", hash);
    }

    @Test
    public void testSha512()
            throws IOException {

        String hash = Hash.hash(HashingAlgorithm.SHA_512, testFile.toFile());


        assertEquals("SHA512 hash is not equal", "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e", hash);

        testFile = FileUtil.modifyTestFile(Config.DEFAULT.getRootTestDir());


        hash = Hash.hash(HashingAlgorithm.SHA_512, testFile.toFile());

        assertEquals("SHA512 hash is not equal after modification", "e88c176ea596c01dc2ee01390e24b707792acbad34457ccb17f400a180c307b1b88ef60ee541d1a6f0f8ad5bb5702a03ebc59b4c4fbb5cb1f7c96aa9c39a31cc", hash);
    }

    @Test
    public void testFileNotExisting()
            throws IOException {
        thrown.expect(FileNotFoundException.class);

        FileUtil.deleteTestFile(Config.DEFAULT.getRootTestDir());

        String hash = Hash.hash(HashingAlgorithm.SHA_1, testFile.toFile());
    }

    @Test
    public void testFolderSha1()
            throws IOException {
        // add a second file
        Path filePath = Files.createFile(Config.DEFAULT.getRootTestDir().resolve(Paths.get("mySecondFile.txt")));
        Path filePath2 = Files.createFile(Config.DEFAULT.getRootTestDir().resolve(Paths.get("mySecondFila.txt"))); // same length file name -> force lexicographical order
        // add a directory
        Path dirPath = Files.createDirectory(Config.DEFAULT.getRootTestDir().resolve(Paths.get("myDir")));
        Files.createDirectories(dirPath.resolve(Paths.get("innerDir"))); // the inner directory should not affect the hash

        // hash the directory recursively
        String expectedHashSha1 = "da39a3ee5e6b4b0d3255bfef95601890afd80709";
        String expectedHashSha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        String expectedHashSha512 = "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e";

        String hash1 = Hash.hash(HashingAlgorithm.SHA_1, Config.DEFAULT.getRootTestDir().toFile());
        assertEquals("Sha1 hash is not equal", expectedHashSha1, hash1);

        String hash2 = Hash.hash(HashingAlgorithm.SHA_256, Config.DEFAULT.getRootTestDir().toFile());
        assertEquals("Sha256 is not equal", expectedHashSha256, hash2);

        String hash3 = Hash.hash(HashingAlgorithm.SHA_512, Config.DEFAULT.getRootTestDir().toFile());
        assertEquals("Sha512 is not equal", expectedHashSha512, hash3);
    }

    @Test
    public void testStringToSha1() {
        String str = "Hello World! ";
        String expected = "f9e7d601b1f22e2ead34c1afd7dfee195f31a711";

        String hash = Hash.hash(HashingAlgorithm.SHA_1, str);

        assertEquals("Sha1 strings are not equal", expected, hash);
    }

    @Test
    public void testStringToSha256() {
        String str = "Hello World! ";
        String expected = "2dddc2bb86f352ea34213f067371c3eea9edab97001bee9aa5047df583b739ba";

        String hash = Hash.hash(HashingAlgorithm.SHA_256, str);

        assertEquals("Sha256 strings are not equal", expected, hash);
    }

    @Test
    public void testStringToSha512() {
        String str = "Hello World! ";
        String expected = "e88c176ea596c01dc2ee01390e24b707792acbad34457ccb17f400a180c307b1b88ef60ee541d1a6f0f8ad5bb5702a03ebc59b4c4fbb5cb1f7c96aa9c39a31cc";

        String hash = Hash.hash(HashingAlgorithm.SHA_512, str);

        assertEquals("Sha512 strings are not equal", expected, hash);

    }

    @Test
    public void testByteToSha1() {
        String str = "Hello World! ";
        String expected = "f9e7d601b1f22e2ead34c1afd7dfee195f31a711";

        String hash = Hash.hash(HashingAlgorithm.SHA_1, str.getBytes());

        assertEquals("Sha1 strings are not equal", expected, hash);
    }


    @Test
    public void testByteToSha256() {
        String str = "Hello World! ";
        String expected = "2dddc2bb86f352ea34213f067371c3eea9edab97001bee9aa5047df583b739ba";

        String hash = Hash.hash(HashingAlgorithm.SHA_256, str.getBytes());

        assertEquals("Sha256 strings are not equal", expected, hash);
    }

    @Test
    public void testByteToSha512() {
        String str = "Hello World! ";
        String expected = "e88c176ea596c01dc2ee01390e24b707792acbad34457ccb17f400a180c307b1b88ef60ee541d1a6f0f8ad5bb5702a03ebc59b4c4fbb5cb1f7c96aa9c39a31cc";

        String hash = Hash.hash(HashingAlgorithm.SHA_512, str.getBytes());

        assertEquals("Sha512 strings are not equal", expected, hash);
    }
}
