/*
 *  Program Name: XmlUtil
 *        Author: Yamiko J. Msosa
 *  Date Written: 15th April 2010
 *       Purpose: This class encapsulates helper methods for manipulating XML documents
 */
package com.baobab.htc.business.utils;

import java.io.*;
import org.kxml2.io.*;
import org.kxml2.kdom.*;
import javax.microedition.io.*;
import javax.microedition.io.file.*;

public class XmlUtil {

    /*
     * Constructor for the class
     */
    public XmlUtil(){

    }

    /*
     * Method returns a string representation of an XML Document
     */
    public static String writeToString(Document document){
        String returnString = null;
        try {
            KXmlSerializer writer = new KXmlSerializer();
                OutputStream output = new OutputStream() {
                    private StringBuffer string = new StringBuffer();

                    //@Override
                    public void write(int b) throws IOException {
                        this.string.append((char) b);
                    }

                    //
                    public String toString(){
                        return this.string.toString();
                    }
                };
                DataOutputStream dataOutputStream = new DataOutputStream(output);
                writer.setOutput(dataOutputStream, "UTF-8");
                document.write(writer);
                returnString = output.toString();
                writer.flush();
                dataOutputStream.close();
        } catch (IOException ioe) {
            // TODO: Log the error in system log
        }
        return returnString;
    }

    public static boolean writeToFile(String url, Document document){
        boolean isWritten = false;
        try {
            KXmlSerializer writer = new KXmlSerializer();
            FileConnection filecon = (FileConnection) Connector.open(url);
            if (!filecon.exists()) {
                OutputStream dataOutputStream = filecon.openOutputStream();
                writer.setOutput(dataOutputStream, "UTF-8");
                document.write(writer);
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
}
