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
public class Login extends Form implements CommandListener {
    /* The Midlet activating this Form */
    private final HtcMidlet htcMidlet;

    Image[] choices = null; // Null image array for ChoiceGroup objects' constructors

     String[] testingTypeTypes = {"Static", "Mobile Van", "Outreach - Car", "Outreach - Motorbike", "Door-to-door"};

     /* login form */
    private TextField userName = new TextField("User Name", "", 15, TextField.ANY);
    private TextField password = new TextField("Password", "", 15, TextField.PASSWORD);
    private TextField siteCode = new TextField("SiteCode", "", 15, TextField.ANY);
    private ChoiceGroup testingType = new ChoiceGroup("Testing Type", ChoiceGroup.EXCLUSIVE, testingTypeTypes, choices);

     /* Commands the login form */
    private Command exitCommand = new Command("Exit", Command.EXIT, 1);
    private Command loginCommand = new Command("login", Command.OK, 4);

    public Login(HtcMidlet htcMidlet){
        super("Login");
        this.htcMidlet = htcMidlet;
            /* Setup the login screen */
            append(userName);
            append(password);
            append(siteCode);
            append(testingType);

            addCommand(exitCommand);
            addCommand(loginCommand);


            /*
             * Event listeners
             */
            setCommandListener(this);
    }

    /*
     * Event handler for command buttons
     */
    public void commandAction(Command command, Displayable s) {
        if (command == exitCommand) {
            htcMidlet.destroyApp(false);
            htcMidlet.notifyDestroyed();
        } else if (command == loginCommand) {
            boolean validUserAndSite = validateUser();
            // Load the HTC Form if a valid user has logged at a valid site
            if (validUserAndSite) {
                htcMidlet.setForm(ConstantHelper.TRANSMISSION_FORM);
            }else {
                ErrorAlert.getInstance().displayAlert("Invalid Login Credentials!", this, htcMidlet);
            }
        }
    }

    /*
     * Method to validate user credentials
     */
    private boolean validateUser() {
        boolean valid = true;
        String userNameString = userName.getString().trim();
        String passwordString = password.getString().trim();

        valid = Security.logUser(userNameString, passwordString);

        if(!Security.validateSite(siteCode.getString().trim())){
            valid = false;
        }
        return valid;
    }

    public TextField getUserName() {
        return userName;
    }

    public TextField getSiteCode() {
        return siteCode;
    }

    public ChoiceGroup getTestingType() {
        return testingType;
    }
}
