/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baobab.htc.presentation.forms;

import com.baobab.htc.presentation.midlets.*;
import com.baobab.htc.business.utils.*;
import javax.microedition.lcdui.*;

/**
 *
 * @author yamiko
 */
public class SessionType extends Form implements CommandListener, ItemStateListener {

    /* The Midlet activating this Form */
    private final HtcMidlet htcMidlet;

    Image[] choices = null; // Null image array for ChoiceGroup objects' constructors

    /* Command Buttons for the session type form */
    private Command selectCommand = new Command("Select", Command.OK, 1);
    private Command quitCommand = new Command("Exit", Command.EXIT, 3);

    /* Arrays for the choice group objects */
    String[] sessionTypes = {"Individual", "Comes with partner"};

    /* Site and session Information */
    private ChoiceGroup sessionType = new ChoiceGroup("Session Type", ChoiceGroup.EXCLUSIVE, sessionTypes, choices);

    public SessionType(HtcMidlet htcMidlet){
        super("Session Type");
        this.htcMidlet = htcMidlet;

            /* Setup the session type form */
            append(sessionType);
            addCommand(selectCommand);
            addCommand(quitCommand);

            setCommandListener(this);
            setItemStateListener(this);
    }

    /*
     * Event handler for command buttons
     */
    public void commandAction(Command command, Displayable s) {
        if (command == selectCommand) {
            processSessionTypeForm();
        } else if (command == quitCommand) {
            htcMidlet.destroyApp(false);
            htcMidlet.notifyDestroyed();
        }
    }

    /*
     * Method will listen to state changes for the various form items and navigate to next item if
     * applicable
     */
    public void itemStateChanged(Item item) {
        if (item == sessionType) {
            processSessionTypeForm();
        }
    }

    /*
     * Method to process session type form accordingly
     */
    private void processSessionTypeForm() {
        if (sessionType.getSelectedIndex() == ConstantHelper.COMES_WITH_PARTNER) {
            htcMidlet.setSessionStatus(ConstantHelper.FIRST_FORM);
        } else {
            htcMidlet.setSessionStatus(ConstantHelper.SINGLE_FORM);
        }
        htcMidlet.setForm(ConstantHelper.SESSION_FORM);
    }

    public ChoiceGroup getSessionType() {
        return sessionType;
    }
}
