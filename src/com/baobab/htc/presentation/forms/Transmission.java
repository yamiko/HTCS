/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baobab.htc.presentation.forms;

import com.baobab.htc.presentation.midlets.*;
import com.baobab.htc.business.utils.*;
import com.baobab.htc.business.services.clients.*;
import com.baobab.htc.data.files.*;
import javax.microedition.lcdui.*;
import java.util.*;
import java.rmi.*;
import javax.xml.rpc.*;

/**
 *
 * @author yamiko
 */
public class Transmission extends Form implements CommandListener {
    /* The Midlet activating this Form */

    private final HtcMidlet htcMidlet;

    /* For the transmission form */
    private Command sendCommand = new Command("Send", Command.OK, 1);
    private Command continueCommand = new Command("Continue", Command.EXIT, 3);

    public Transmission(HtcMidlet htcMidlet) {
        super("Transmission Form");
        this.htcMidlet = htcMidlet;

        /* Setup transmission form */
        setTransmissionForm();
        setCommandListener(this);
    }

    /*
     * Event handler for command buttons
     */
    public void commandAction(Command command, Displayable s) {
        if (command == sendCommand) {
            sendXmlDocuments();
        }
        htcMidlet.setForm(ConstantHelper.SESSION_TYPE_FORM);
    }

    /*
     * Method to clear and setup transmission form
     */
    private void setTransmissionForm() {
        deleteAll();
        append(getNumberOfXmlDocuments());

        addCommand(sendCommand);
        addCommand(continueCommand);
    }

    /*
     * Method to count the number of xml documents in the outbox
     */
    private String getNumberOfXmlDocuments() {
        StringBuffer label = new StringBuffer();
        System.out.println("Getting number of XML Docs...");
        System.out.println(ConstantHelper.getInstance().getRoot());
        int numberOfDocuments = FileReader.getNumberOfDocuments(ConstantHelper.getInstance().getRoot(), ConstantHelper.XML_EXTENSION);

        label.append("(");
        label.append(String.valueOf(numberOfDocuments));
        label.append(") Outbox");

        return label.toString();
    }

    /*
     * Method to clear and setup transmission form
     */
    private void sendXmlDocuments() {
        Vector listOfFiles = FileReader.getFileNames(ConstantHelper.getInstance().getRoot(), ConstantHelper.XML_EXTENSION);
        Object[] listOfFilesArray = new Object[listOfFiles.size()];
        listOfFiles.copyInto(listOfFilesArray);
        HtcService htcService = new HtcService_Stub(ConstantHelper.getInstance().getHtcServiceUrl());
        for (int i = 0; i < listOfFilesArray.length; i++) {
            int sentStatus = -1;
            String currentFile = (String) listOfFilesArray[i];
            StringBuffer fileName = new StringBuffer();
            fileName.append(ConstantHelper.getInstance().getRoot());
            fileName.append(currentFile);
            String contents = FileReader.read(fileName.toString());
            System.out.println("Contents :");
            System.out.println(contents);
            try {
                sentStatus = htcService.submitXmlDocument(contents).intValue();
                if (sentStatus != ConstantHelper.ERROR) {
                    boolean deleted = FileWriter.deleteFile(fileName.toString());
                } else {
                    System.out.println("Received negative response from the server... skipping delete...");
                }
            } catch (RemoteException re) {
                // TODO: Log the error in system log
                System.out.println(re.toString());
            } catch (JAXRPCException jre) {
                // TODO: Log the error in system log
                System.out.println(jre.toString());
            }
            System.out.println("sent status::");
            System.out.println(String.valueOf(sentStatus));
        }
    }
}
