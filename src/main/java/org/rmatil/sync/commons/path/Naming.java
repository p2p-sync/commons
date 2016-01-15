package org.rmatil.sync.commons.path;

import java.nio.file.Paths;

public class Naming {

    /**
     * Returns the path to the file without the filename
     *
     * @param fileName               The filename without the path
     * @param pathToFileWithFilename The path containing also the filename
     *
     * @return The path to the file without the filename and without an ending slash
     */
    public static String getPathWithoutFileName(String fileName, String pathToFileWithFilename) {
        // filename.txt
        // myPath/to/filename.txt
        int idx = pathToFileWithFilename.lastIndexOf(fileName);
        // remove slash between path and filename
        return pathToFileWithFilename.substring(0, Math.max(0, idx - 1));
    }

    /**
     * Assembles the file name for conflict files
     *
     * @param relativeFilePath The relative path to the file (incl. the filename)
     * @param isFile           Whether the path represents a file (i.e. a dot is present in the filename)
     * @param fileExtension    The file extension of the file
     * @param clientDeviceId   The id of the client which is added to the filename
     *
     * @return The file name of the conflict file
     *
     * @throws IllegalArgumentException If the clientDeviceId contains illegal characters (dot)
     */
    public static String getConflictFileName(String relativeFilePath, boolean isFile, String fileExtension, String clientDeviceId)
            throws IllegalArgumentException {

        if (clientDeviceId.contains(".")) {
            throw new IllegalArgumentException("The device id must not have any dots in it");
        }

        String lastPathName = Paths.get(relativeFilePath).getFileName().toString();

        String newFileName = lastPathName;
        if (isFile && fileExtension.length() > 0) {
            int extStartIndex = lastPathName.lastIndexOf(fileExtension);

            // decrease the position of the dot
            extStartIndex = (extStartIndex > 0) ? extStartIndex - 1 : extStartIndex;

            newFileName = lastPathName.substring(0, extStartIndex);
        }

        // add client name
        newFileName = newFileName + "_" + clientDeviceId;
        // re-add file extension
        return newFileName + ((fileExtension.length() > 0) ? ("." + fileExtension) : "");
    }

}
