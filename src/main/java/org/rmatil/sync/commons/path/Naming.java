package org.rmatil.sync.commons.path;

public class Naming {


    /**
     * Returns the path to the file without the filename
     *
     * @param fileName The filename without the path
     * @param pathToFileWithFilename The path containing also the filename
     *
     * @return The path to the file without the filename and without an ending slash
     */
    public static String getPathWithoutFileName(String fileName, String pathToFileWithFilename) {
        // filename.txt
        // myPath/to/filename.txt
        int idx = pathToFileWithFilename.indexOf(fileName);
        // remove slash between path and filename
        return pathToFileWithFilename.substring(0, idx - 1);
    }

}
