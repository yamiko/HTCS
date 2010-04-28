/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baobab.htc.business.utils;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 *
 * @author yamiko
 */
public class ErrorAlert {

    private static ErrorAlert errorAlert = new ErrorAlert();

    private ErrorAlert() {
        image = null;
        alert = new Alert("Error", "", image, AlertType.ERROR);
    }

    public static ErrorAlert getInstance() {
        return errorAlert;
    }
    private Image image;
    private Alert alert;
    /*
     * Method displays an error alert given a message for a given # of seconds
     */

    public void displayAlert(String errorMessage, Form nextForm, MIDlet midlet) {
        image = null;
        alert.setString(errorMessage);
        alert.setTimeout(ConstantHelper.ALERT_TIMEOUT);
        Display.getDisplay(midlet).setCurrent(alert, nextForm);
    }
}
