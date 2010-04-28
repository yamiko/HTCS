/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baobab.htc.midlets;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

/**
 * @author yamiko
 */
// Main class which inits the MIDlet and creates the screens
public class ImageViewerMidlet {
    /*
        extends MIDlet {

    private final Image logo;
    private final ImageCanvas imageCanvas;
    private FileSelector fileSelector;
    private final InputScreen inputScreen;
    private int operationCode = -1;
    private static FileSelector staticSelector;

    public ImageViewerMidlet() {
// init basic parameters
        logo = makeImage("/logo.png");
        ErrorScreen.init(logo, Display.getDisplay(this));
        imageCanvas = new ImageCanvas(this);
        fileSelector = new FileSelector(this);
        inputScreen = new InputScreen(this);
    }

    public void startApp() {
        Displayable current = Display.getDisplay(this).getCurrent();
        if (current == null) {
// Checks whether the API is available
            boolean isAPIAvailable = System.getProperty(
                    "microedition.io.file.FileConnection.version") != null;
// shows splash screen
            String text = getAppProperty("MIDlet-Name")
                    + "\n"
                    + getAppProperty("MIDlet-Vendor");
            if (!isAPIAvailable) {
                text += "\nFile Connection API is not available";
            }
            Alert splashScreen = new Alert(null,
                    text,
                    logo,
                    AlertType.INFO);
            if (isAPIAvailable) {
                splashScreen.setTimeout(30);
                Display.getDisplay(this).setCurrent(splashScreen,
                        fileSelector);
            } else {
                Display.getDisplay(this).setCurrent(splashScreen);
            }
        } else {
            Display.getDisplay(this).setCurrent(current);
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
// stop the commands queue thread
        fileSelector.stop();
        notifyDestroyed();
    }

    void fileSelectorExit() {
        destroyApp(false);
    }

    void requestInput(String text, String label, int operationCode) {
        inputScreen.setQuestion(text, label);
        this.operationCode = operationCode;
        Display.getDisplay(this).setCurrent(inputScreen);
    }

    void cancelInput() {
        Display.getDisplay(this).setCurrent(fileSelector);
    }

    void input(String input) {
        fileSelector.inputReceived(input, operationCode);
        Display.getDisplay(this).setCurrent(fileSelector);
    }

    void displayImage(String imageName) {
        imageCanvas.displayImage(imageName);
        Display.getDisplay(this).setCurrent(imageCanvas);
    }

    void displayFileBrowser() {
        Display.getDisplay(this).setCurrent(fileSelector);
    }

    void showError(Exception e) {
        ErrorScreen.showError(e.getMessage(), fileSelector);
    }

    void showMsg(String text) {
        Alert infoScreen = new Alert("Image Viewer",
                text,
                logo,
                AlertType.INFO);
        infoScreen.setTimeout(3000);
        Display.getDisplay(this).setCurrent(infoScreen, fileSelector);
    }
// loads a given image by name
    static Image makeImage(String filename) {
        Image image = null;
        try {
            image = Image.createImage(filename);
        } catch (Exception e) {
            //
        }
        return image;
    }*/
}
