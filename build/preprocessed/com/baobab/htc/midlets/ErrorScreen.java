/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baobab.htc.midlets;

/**
 *
 * @author yamiko
 */
import javax.microedition.lcdui.*;

class ErrorScreen
        extends Alert {

    private static Image image;
    private static Display display;
    private static ErrorScreen instance = null;

    private ErrorScreen() {
        super("Error");
        setType(AlertType.ERROR);
        setTimeout(5000);
        setImage(image);
    }

    static void init(Image img, Display disp) {
        image = img;
        display = disp;
    }

    static void showError(String message, Displayable next) {
        if (instance == null) {
            instance = new ErrorScreen();
        }
        instance.setTitle("Error");
        instance.setString(message);
        display.setCurrent(instance, next);
    }
}
