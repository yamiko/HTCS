/*
 *  Program Name: FileWriter
 *        Author: Yamiko J. Msosa
 *  Date Written: 15th April 2010
 *       Purpose: This class encapsulates helper methods for file writing
 */
package com.baobab.htc.data.files;

import javax.microedition.io.*;
import javax.microedition.io.file.*;
import java.io.*;

public class FileWriter {

    /*
     * Method to write a specified string to file
     */
    public static boolean writeToFile(String url, String contents){
        boolean isWritten = false;
        try {
            FileConnection filecon = (FileConnection) Connector.open(url);
            // Always check whether the file or directory exists.
            // Create the file if it doesn't exist.
            if (filecon.exists()) {
                OutputStream dataOutputStream = filecon.openOutputStream();
                dataOutputStream.write(contents.getBytes());
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
     * Method to write a specified string to file
     */
    public static boolean deleteFile(String url){
        boolean isDeleted = false;
        try {
            FileConnection filecon = (FileConnection) Connector.open(url);
            // Always check whether the file or directory exists.
            // Create the file if it doesn't exist.
            if (filecon.exists()) {
                filecon.delete();
                isDeleted = true;
            }
            filecon.close();
            filecon = null;
        } catch (IOException ioe) {
            // TODO: Log the error in system log
        }
        return isDeleted;
    }

    /*
     * Method to create a specified file on the given resource
     */
    public static boolean createFile(String url) {
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
            System.out.println("Creating file error: ");
            System.out.println(ioe.getMessage());
            System.out.println(ioe.toString());
        }
        return isCreated;
    }
}
