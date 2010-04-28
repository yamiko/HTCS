/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.baobab.htc.midlets;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * @author yamiko
 */
public class LoginForm extends MIDlet implements CommandListener{

    /* First time flag */
    private boolean firstTime;

    /* Buttons for the Application */
    private Command exitCommand = new Command("Exit", Command.EXIT, 1);
    private Command saveCommand = new Command("Save", Command.OK, 3);
    private Command loginCommand = new Command("login", Command.OK, 4);
    private Command resetCommand = new Command("Reset", Command.CANCEL, 1);
    private Command cancelCommand = new Command("Cancel", Command.CANCEL, 1);

    /* Forms/screens for the App */
    private Form loginForm;
    private Form mainForm;

    /* TextFields for the login form */
    private TextField userName = new TextField("User Name", "", 15, TextField.ANY);
    private TextField password = new TextField("Password", "", 15, TextField.PASSWORD);
    private TextField siteCode = new TextField("SiteCode", "", 15, TextField.ANY);

    /* TextFields for the main form */
    private TextField districtCode = new TextField("District Code", "", 15, TextField.ANY);
    private TextField traditionalAuthority = new TextField("T/A", "", 15, TextField.ANY);
    private TextField clientCode = new TextField("Client Code", "", 15, TextField.ANY);
    private TextField couplesReference = new TextField("Couples Reference", "", 15, TextField.ANY);

    private TextField lastName = new TextField("Last Name", "", 15, TextField.ANY);

    private DateField dateOfBirth = new DateField("Date of Birth", DateField.DATE); // Not a text field but a data field

    Image[] choices = null; // Null image array for ChoiceGroup objects' constructors

    /* Arrays for the choice group objects */
    String[] yesNo = {"Yes", "No"};
    String[] yesNoNotKnown = {"Yes", "No", "Not Known"};
    String[] sessionTypes = {"Individual", "Comes with partner", "Child with guardian", "Other"};
    String[] residenceTypes = {"Urban", "Rural", "Other"};
    String[] occupations = {"None", "Teacher", "HCW", "Business", "Military/Police", "Student", "Farmer", "Housewife", "Driver", "Other"};
    String[] highestEducations = {"None", "Primary", "Secondary", "Tertiary"};
    String[] marriageTypes = {"Monogomous", "Polygamous", "N/A"};
    String[] maritalHistory = {"Previously Separated/Divorced", "Previously Widowed", "N/A"};

    String[] sexGroup = {"Male", "Female"};
    String[] maritalStatusGroup = {"Never Married", "Married", "Separated", "Divorced" ,"Widowed"};
    String[] hivStatusGroup = {"Negative", "Positive"};

    private ChoiceGroup returnVisit = new ChoiceGroup("Return Visit", ChoiceGroup.EXCLUSIVE, yesNo, choices);
    private ChoiceGroup newClientCode = new ChoiceGroup("New Client Code", ChoiceGroup.EXCLUSIVE, yesNo, choices);
    private ChoiceGroup sessionType = new ChoiceGroup("New Client Code", ChoiceGroup.EXCLUSIVE, sessionTypes, choices);

    private ChoiceGroup sex = new ChoiceGroup("Sex", ChoiceGroup.EXCLUSIVE, sexGroup, choices);

    private ChoiceGroup maritalStatus = new ChoiceGroup("Marital Status", ChoiceGroup.EXCLUSIVE, maritalStatusGroup, choices);
    private ChoiceGroup hivStatus = new ChoiceGroup("HIV Status", ChoiceGroup.EXCLUSIVE, hivStatusGroup, choices);


    public LoginForm(){
        firstTime = true;
        loginForm = new Form("Login");
        mainForm = new Form("Registration");
    }

    public void startApp() {
       if (firstTime) {
            /* Setup the login screen */
            loginForm.append(userName);
            loginForm.append(password);
            loginForm.append(siteCode);

            loginForm.addCommand(exitCommand);
            loginForm.addCommand(loginCommand);
            loginForm.setCommandListener(this);

            /* Set up the main form */
            mainForm.append(lastName);
            mainForm.append(dateOfBirth);
            mainForm.append(sex);
            mainForm.append(maritalStatus);
            mainForm.append(hivStatus);

            mainForm.addCommand(saveCommand);
            mainForm.addCommand(resetCommand);
            mainForm.addCommand(cancelCommand);
            firstTime = false;
       }

        Display.getDisplay(this).setCurrent(loginForm);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command command, Displayable s) {
        if (command == exitCommand) {
            destroyApp(false);
            notifyDestroyed();
        } else if(command == loginCommand){
            mainForm.setCommandListener(this);
            Display.getDisplay(this).setCurrent(mainForm);
        } else if(command == resetCommand){
            lastName.setString("");
            Display.getDisplay(this).setCurrentItem(lastName);
        } else if (command == cancelCommand) {
            destroyApp(false);
            notifyDestroyed();
        } else if (command == saveCommand) {
            lastName.setString("");
            Display.getDisplay(this).setCurrentItem(lastName);
        }
    }

}
