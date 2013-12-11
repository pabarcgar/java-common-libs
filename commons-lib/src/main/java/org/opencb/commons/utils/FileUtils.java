package org.opencb.commons.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: imedina
 * Date: 9/25/13
 * Time: 1:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileUtils {


    public static void checkPath(Path path) throws IOException {
        checkPath(path, false);
    }

    public static void checkPath(Path path, boolean writable) throws IOException {
        if(path == null) {
            throw new IOException("Path is null");
        }

        if(!Files.exists(path)) {
            throw new IOException("Path '" + path.toAbsolutePath() + "' does not exist");
        }

        if(!Files.isReadable(path)) {
            throw new IOException("Path '" + path.toAbsolutePath() + "' cannot be read");
        }

        if (writable && !Files.isWritable(path)) {
            throw new IOException("Path '" + path.toAbsolutePath() + "' cannot be written");
        }
    }

    public static void checkFile(Path path) throws IOException {
        checkFile(path, false);
    }
    
    public static void checkFile(Path path, boolean writable) throws IOException {
        checkPath(path, writable);
        
        if(Files.isDirectory(path)) {
            throw new IOException("Path '" + path.toAbsolutePath() + "' must be a file");
        }
    }
    

    /**
     * This method is able to determine whether a file is GZipped and return a BufferedReader in any case
     * @param path to be read
     * @return BufferedReader object
     * @throws java.io.IOException
     */
    public static BufferedReader newBufferedReader(Path path) throws IOException {
        BufferedReader br = null;
        if(path.toFile().getName().endsWith(".gz")) {
            br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(path.toFile()))));
        }else {
            br = Files.newBufferedReader(path, Charset.defaultCharset());
        }
        return br;
    }

    /**
     * This method is able to determine whether a file is GZipped and return a BufferedReader in any case
     * @param path to be read
     * @return BufferedReader object
     * @throws java.io.IOException
     */
    public static BufferedReader newBufferedReader(Path path, Charset charset) throws IOException {
        BufferedReader br = null;
        if(path.toFile().getName().endsWith(".gz")) {
            br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(path.toFile()))));
        }else {
            br = Files.newBufferedReader(path, charset);
        }
        return br;
    }

}
