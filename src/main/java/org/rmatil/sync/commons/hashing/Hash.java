package org.rmatil.sync.commons.hashing;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Hashing utilities for files
 */
public class Hash {

    /**
     * Hashes the given file with the given hashing algorithm
     *
     * @param hashingAlgorithm The hashing algorithm to use
     * @param file             The file to hash
     *
     * @return The hash
     *
     * @throws IOException           If the hashing fails
     * @throws FileNotFoundException If the given file does not exist
     */
    public static String hash(HashingAlgorithm hashingAlgorithm, File file)
            throws IOException {

        if (! file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath() + " (No such file or directory)");
        }

        if (file.isDirectory()) {
            return Hash.hashDirectory(hashingAlgorithm, file.toPath());
        }


        HashCode hc = null;

        switch (hashingAlgorithm) {
            case MD5:
                hc = Files.hash(file, Hashing.md5());
                break;
            case SHA_1:
                hc = Files.hash(file, Hashing.sha1());
                break;
            case SHA_256:
                hc = Files.hash(file, Hashing.sha256());
                break;
            case SHA_512:
                hc = Files.hash(file, Hashing.sha512());
                break;
        }

        return hc.toString();
    }

    /**
     * Creates a hash over the content of the given directory.
     *
     * @param hashingAlgorithm The hashing algorithm to use
     * @param path             The directory to hash
     *
     * @return The calculated hash
     *
     * @throws IOException If reading the directory failed
     */
    protected static String hashDirectory(HashingAlgorithm hashingAlgorithm, Path path)
            throws IOException {
        String hash = path.getFileName().toString();

        if (path.toFile().exists() && path.toFile().isFile()) {
            return Hash.hash(hashingAlgorithm, hash.concat(Hash.hash(hashingAlgorithm, path.toFile())));
        }

        List<Path> childPaths = new ArrayList<>();
        try (DirectoryStream<Path> stream = java.nio.file.Files.newDirectoryStream(path)) {
            for (Path childFile : stream) {
                childPaths.add(childFile);
            }
        }

        // order child paths by length of their name and then lexicographically
        Collections.sort(childPaths, (o1, o2) -> {
            if (o1.getFileName().toString().length() < o2.getFileName().toString().length()) {
                return - 1;
            }

            if (o1.getFileName().toString().length() > o2.getFileName().toString().length()) {
                return 1;
            }

            // if they have the same length, compare them lexicographically
            return o1.compareTo(o2);
        });

        for (Path childPath : childPaths) {
            hash = hash.concat(hashDirectory(hashingAlgorithm, childPath));
        }

        return Hash.hash(hashingAlgorithm, hash);
    }

    /**
     * Hashes the given string with the given hashing algorithm
     *
     * @param hashingAlgorithm The hashing algorithm to use
     * @param strToHash        The string to hash
     *
     * @return The hashed string
     */
    public static String hash(HashingAlgorithm hashingAlgorithm, String strToHash) {
        HashFunction hf = null;

        switch (hashingAlgorithm) {
            case MD5:
                hf = Hashing.md5();
                break;
            case SHA_1:
                hf = Hashing.sha1();
                break;
            case SHA_256:
                hf = Hashing.sha256();
                break;
            case SHA_512:
                hf = Hashing.sha512();
                break;
        }

        return hf.hashString(strToHash, StandardCharsets.UTF_8).toString();
    }

    /**
     * Hashes the content of the given byte array with the given hash function
     *
     * @param hashingAlgorithm The hashing algorithm to use
     * @param data             The data to hash
     *
     * @return The hash string
     */
    public static String hash(HashingAlgorithm hashingAlgorithm, byte[] data) {
        HashFunction hf = null;

        switch (hashingAlgorithm) {
            case MD5:
                hf = Hashing.md5();
                break;
            case SHA_1:
                hf = Hashing.sha1();
                break;
            case SHA_256:
                hf = Hashing.sha256();
                break;
            case SHA_512:
                hf = Hashing.sha512();
                break;
        }

        return hf.hashBytes(data).toString();
    }
}
