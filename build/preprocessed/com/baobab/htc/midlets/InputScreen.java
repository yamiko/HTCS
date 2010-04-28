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
// This class displays an input field on the screen
// and returns the value entered to the MIDlet
class InputScreen  {
    /*
        extends Form
        implements CommandListener {

    private final ImageViewerMidlet midlet;
    private final TextField inputField =
            new TextField("Input", "", 32, TextField.ANY);
    private final Command okCommand =
            new Command("OK", Command.OK, 1);
    private final Command cancelCommand =
            new Command("Cancel", Command.OK, 1);

    InputScreen(ImageViewerMidlet midlet) {
        super("Input");
        this.midlet = midlet;
        append(inputField);
        addCommand(okCommand);
        addCommand(cancelCommand);
        setCommandListener(this);
    }

    public void setQuestion(String question, String text) {
        inputField.setLabel(question);
        inputField.setString(text);
    }

    public String getInputText() {
        return inputField.getString();
    }

    public void commandAction(Command command, Displayable d) {
        if (command == okCommand) {
            midlet.input(inputField.getString());
        } else if (command == cancelCommand) {
            midlet.cancelInput();
        }
    } */
}
