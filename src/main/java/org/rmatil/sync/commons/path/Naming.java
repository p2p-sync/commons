package org.rmatil.sync.commons.path;

import java.nio.file.Path;
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

        // return just the leading slash if the file only has the root element and the filename
        // e.g. /myFile.txt
        if (1 == idx && "/".equals(pathToFileWithFilename.substring(0, 1))) {
            return "/";
        }

        // remove slash between path and filename
        return pathToFileWithFilename.substring(0, Math.max(0, idx - 1));
    }

    /**
     * Returns the parent of the given path.
     * If the specified path is only one element long (e.g. /myFile or myFile),
     * then the root (i.e. /) is returned in the first case, null in the second.
     *
     * @param path The path of which to get its parent path
     *
     * @return The parent path or null, if no parent can be found
     */
    public static String getParentPath(String path) {
        Path child = Paths.get(path);

        if (child.getNameCount() > 1) {
            Path parent = child.subpath(0, child.getNameCount() - 1);

            // path.subpath() returns always a relative path
            if (child.isAbsolute()) {
                // we may not use toAbsolutePath since it resolves
                // the path to a implementation based default directory (e.g. the current dir)
                return "/".concat(parent.toString());
            }

            return parent.toString();
        }

        // if we have an element like /myFile.txt
        // return root as parent
        if (path.startsWith("/")) {
            return "/";
        }

        return null;
    }

    /**
     * Assembles the file name for conflict files
     *
     * @param relativeFilePath The relative path to the file (incl. the filename)
     * @param isFile           Whether the path represents a file (i.e. a dot is present in the filename)
     * @param fileExtension    The file extension of the file
     * @param clientDeviceId   The id of the client which is added to the filename
     *
     * @return The path to the file having the new conflict file name
     *
     * @throws IllegalArgumentException If the clientDeviceId contains illegal characters (dot)
     */
    public static String getConflictFileName(String relativeFilePath, boolean isFile, String fileExtension, String clientDeviceId)
            throws IllegalArgumentException {

        if (clientDeviceId.contains(".")) {
            throw new IllegalArgumentException("The device id must not have any dots in it");
        }

        String lastPathName = Paths.get(relativeFilePath).getFileName().toString();
        String pathWithoutFileName = Naming.getPathWithoutFileName(lastPathName, relativeFilePath);

        // append a slash to the end only if the path is not empty
        if (! pathWithoutFileName.equals("") && ! pathWithoutFileName.equals("/")) {
            pathWithoutFileName = pathWithoutFileName.concat("/");
        }

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
        return pathWithoutFileName + newFileName + ((fileExtension.length() > 0) ? ("." + fileExtension) : "");
    }

}
