 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baobab.htc.data.files;

import javax.microedition.io.*;
import javax.microedition.io.file.*;
import java.io.*;
import org.kxml2.io.*;
import org.kxml2.kdom.*;

/**
 *
 * @author yamiko
 */
public class FileWriter {

    /*
     * Method to write a specified string to file
     */
    public boolean writeToFile(String url, String contents){
        boolean isWritten = false;
        try {
            FileConnection filecon = (FileConnection) Connector.open(url);
            // Always check whether the file or directory exists.
            // Create the file if it doesn't exist.
            if (!filecon.exists()) {
                DataOutputStream dataOutputStream = filecon.openDataOutputStream();
                dataOutputStream.writeChars(contents);
                dataOutputStream.close();
                isWritten = true;
            }
            filecon.close();
            filecon = null;
        } catch (IOException ioe) {
            // TODO: Log the error in system log
        }
        return isWritten;
    }

    public boolean writeToFile(String url, Document document){
        boolean isWritten = false;
        try {
            KXmlSerializer writer = new KXmlSerializer();
            FileConnection filecon = (FileConnection) Connector.open(url);
            // Always check whether the file or directory exists.
            // Create the file if it doesn't exist.
            if (!filecon.exists()) {
                DataOutputStream dataOutputStream = filecon.openDataOutputStream();
                writer.setOutput(dataOutputStream, "UTF-8");
                document.write(writer);
                //dataOutputStream.writeChars(contents);
                writer.flush();
                dataOutputStream.close();
                isWritten = true;
            }
            filecon.close();
            filecon = null;
        } catch (IOException ioe) {
            // TODO: Log the error in system log
        }
        return isWritten;
    }

    /*
     * Method to create a specified file
     */
    public boolean createFile(String url) {
        boolean isCreated = false;
        try {
            FileConnection filecon = (FileConnection) Connector.open(url);
            // Always check whether the file or directory exists.
            // Create the file if it doesn't exist.
            if (!filecon.exists()) {
                filecon.create();
                isCreated = true;
            }
            filecon.close();
        } catch (IOException ioe) {
            // TODO: Log the error in system log
        }
        return isCreated;
    }
}
