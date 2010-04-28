/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baobab.htc.data.files;

import javax.microedition.io.*;
import javax.microedition.io.file.*;
import java.io.*;

/**
 *
 * @author yamiko
 */
public class FileReader {

    public String reader(String url) {
        String fileContents = null;
        try {
            FileConnection fc = (FileConnection) Connector.open(url);
            if (!fc.exists()) {
                throw new IOException("File does not exist");
            }
            InputStream is = fc.openInputStream();
            byte b[] = new byte[1024];
            int length = is.read(b, 0, 1024);
            fileContents = new String(b, 0, length);
            is.close();
            fc.close();
            fc = null;
        } catch (IOException e) {
            // TODO: Log the error in system log
        }
        return fileContents;
    }
}
