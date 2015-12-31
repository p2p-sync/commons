package org.rmatil.sync.commons.hashing;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
            throw new FileNotFoundException(file.getAbsolutePath() + " (Is a directory)");
        }

        HashCode hc = null;

        switch (hashingAlgorithm) {
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
