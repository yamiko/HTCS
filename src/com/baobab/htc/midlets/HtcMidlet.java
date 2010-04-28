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
public class HtcMidlet extends MIDlet implements CommandListener, ItemStateListener {
    /*
     * Constants for the class
     */

    final int COMES_WITH_PARTNER = 1;
    final int SINGLE_FORM = 0;
    final int FIRST_FORM = 1;
    final int SECOND_FORM = 2;

    /*
     * Buttons/Commands for the Application
     */
    /* For the login form */
    private Command exitCommand = new Command("Exit", Command.EXIT, 1);
    private Command saveCommand = new Command("Save", Command.OK, 3);

    /* For the session type form */
    private Command selectCommand = new Command("Select", Command.OK, 1);
    private Command quitCommand = new Command("Exit", Command.EXIT, 3);

    /* For the HTC Form*/
    private Command loginCommand = new Command("login", Command.OK, 4);
    private Command resetCommand = new Command("Reset", Command.CANCEL, 1);
    private Command cancelCommand = new Command("Exit", Command.EXIT, 1);

    /* Forms/screens for the App */
    private Form loginForm;
    private Form sessionTypeForm;
    private Form mainForm;

    /* TextFields for the login form */
    private TextField userName = new TextField("User Name", "", 15, TextField.ANY);
    private TextField password = new TextField("Password", "", 15, TextField.PASSWORD);
    private TextField siteCode = new TextField("SiteCode", "", 15, TextField.ANY);

    Image[] choices = null; // Null image array for ChoiceGroup objects' constructors

    /* Arrays for the choice group objects */
    String[] yesNo = {"Yes", "No"};
    String[] yesNoNotKnown = {"Yes", "No", "Not Known"};
    String[] yesNoNotKnownNA = {"Yes", "No", "Not Known", "N/A"};
    String[] sessionTypes = {"Individual", "Comes with partner", "Child with guardian", "Other"};
    String[] residenceTypes = {"Urban", "Rural", "Other"};
    String[] occupations = {"None", "Teacher", "HCW", "Business", "Military/Police", "Student", "Farmer",
        "Housewife", "Driver", "Other"};
    String[] highestEducations = {"None", "Primary", "Secondary", "Tertiary"};
    String[] marriageTypes = {"Monogomous", "Polygamous", "N/A"};
    String[] maritalHistoryTypes = {"Previously Separated/Divorced", "Previously Widowed", "N/A"};
    String[] knewAboutServiceTypes = {"Radio", "Outreach", "Signposts", "Clients", "Newsprint",
        "Clinic/Hosp", "Television", "Reffered", "Other"};
    String[] referredByTypes = {"Not Referred", "Relative", "Friend", "Other Client", "Religious Institution",
        "CBOs", "NGOs", "Antenatal Clinic", "TB Clinic", "STI Clinic", "Other Clinic/Hospital", "Other"};
    String[] mostImportantReasons = {"Current HIV Risk Behaviour", "Past HIV Risk Behaviour",
        "Feel Ill/Other Symptoms", "Partner Behaviour/Not Trusting Partner", "Partner Ill/Other Symptoms",
        "Partner Died", "Rape", "Reffered", "Occupational Exposure to HIV", "Planning for Marriage",
        "Reunion in Marriage/Relationship", "Pregnancy/PMTCT", "2nd Test/Window Period",
        "Confirm Previous HIV Status", "Need Counselling", "Other"};
    String[] everHivTestedBeforeOptions = {"No", "Yes, HIV+", "Yes, HIV-", "Inconclusive", "Did Not Get Results"};
    String[] partnerTypes = {"N/A", "Spouse", "Steady", "Casual", "Other"};
    String[] partnerHivStatusTypes = {"HIV+", "HIV-", "Status Unknown"};
    String[] familyPlanningMethods = {"Pill", "Injectable", "Sterilization", "Condom", "UID", "Natural FP",
        "Norplant", "N/A"};
    String[] testingProtocols = {"Serial", "Parallel"};
    String[] testResultTypes = {"Non Reactive", "Reactive"};
    String[] testResultTypesNA = {"Non Reactive", "Reactive", "N/A"};
    String[] finalResultTypes = {"Non Reactive", "Reactive", "Inconclusive", "Exposed Infant"};
    String[] symptoms = {"TB Treatment Now or in the Past 12 Months", "Reactive", "Admitted to hospital in the Past 12 Months",
        "Unexplained Weight Loss", "Chronic Fever", "Chronic Diarrhea", "Oral Thrush", "Karposi's Sarcoma"};
    String[] referrals = {"Not Referred", "ARV Clinic", "STI Clinic", "FP Clinic", "TB Clinic",
        "PMTCT Services", "PLWA Assosiation", "Post Test Club", "HBC", "Other"};
    String[] sexTypes = {"Male", "Female"};
    String[] riskReductionPlans = {"None", "Abstinence", "Client Will Have One Partner", "Client Will Ask Partner to be Monogamous",
        "Client will increase use of Condoms", "Client will reduce number of Partners", "Eliminate high risk partners",
        "Change Alcohol/Drug Use", "Retest for HIV", "Test Partner for HIV", "Attend PTC", "Disclose HIV status", "Other"};
    String[] maritalStatusTypes = {"Never Married", "Married", "Separated", "Divorced", "Widowed"};
    //String[] hivStatusTypes = {"Negative", "Positive"};

    /*
     * Site and session Information
     */
    private TextField traditionalAuthority = new TextField("T/A", "", 15, TextField.ANY);
    private ChoiceGroup returnVisit = new ChoiceGroup("Return Visit", ChoiceGroup.EXCLUSIVE, yesNo, choices);
    private ChoiceGroup sessionType = new ChoiceGroup("Session Type", ChoiceGroup.EXCLUSIVE, sessionTypes, choices);

    /*
     * Demographic Information
     */
    private ChoiceGroup sex = new ChoiceGroup("Sex", ChoiceGroup.EXCLUSIVE, sexTypes, choices);
    private DateField dateOfBirth = new DateField("Date of Birth", DateField.DATE); // Not a text field but a data field
    private ChoiceGroup currentResidence = new ChoiceGroup("Residence", ChoiceGroup.EXCLUSIVE, residenceTypes, choices);
    private ChoiceGroup occupation = new ChoiceGroup("Occupation", ChoiceGroup.EXCLUSIVE, occupations, choices);
    private ChoiceGroup highestEducation = new ChoiceGroup("Highest Education", ChoiceGroup.EXCLUSIVE, highestEducations, choices);
    private ChoiceGroup maritalStatus = new ChoiceGroup("Marital Status", ChoiceGroup.EXCLUSIVE, maritalStatusTypes, choices);
    private ChoiceGroup marriageType = new ChoiceGroup("Marriage Types", ChoiceGroup.EXCLUSIVE, marriageTypes, choices);
    private ChoiceGroup maritalHistory = new ChoiceGroup("Marital History", ChoiceGroup.EXCLUSIVE, maritalHistoryTypes, choices);
    private ChoiceGroup knewAboutService = new ChoiceGroup("How Knew About Service", ChoiceGroup.EXCLUSIVE, knewAboutServiceTypes, choices);
    private ChoiceGroup referredBy = new ChoiceGroup("Referred By", ChoiceGroup.EXCLUSIVE, referredByTypes, choices);
    private ChoiceGroup mostImportantReason = new ChoiceGroup("Most Important Reason Today", ChoiceGroup.EXCLUSIVE, mostImportantReasons, choices);

    /*
     * History of Testing and HIV Risk
     */
    private ChoiceGroup everHivTestedBefore = new ChoiceGroup("Ever HIV Tested Before?", ChoiceGroup.EXCLUSIVE, everHivTestedBeforeOptions, choices);
    private DateField lastHivTest = new DateField("When Last HIV Test", DateField.DATE); // Not a text field but a data field
    private ChoiceGroup everHadSex = new ChoiceGroup("Client Ever Had Sex?", ChoiceGroup.EXCLUSIVE, yesNo, choices);
    private TextField ageAtFirstSex = new TextField("Age at First Sex", "", 2, TextField.NUMERIC);
    private DateField lastTimeClientHadSex = new DateField("Last Time Client Had Sex", DateField.DATE); // Not a text field but a data field
    private ChoiceGroup coupleEverHadSex = new ChoiceGroup("Couple Ever Had Sex?", ChoiceGroup.EXCLUSIVE, yesNo, choices);
    private ChoiceGroup everReceivedBloodTransfusion = new ChoiceGroup("Ever Received Blood Transfusion?", ChoiceGroup.EXCLUSIVE, yesNoNotKnownNA, choices);

    /*
     * Partner History
     */
    private ChoiceGroup partner1Type = new ChoiceGroup("Partner 1 - Type", ChoiceGroup.EXCLUSIVE, partnerTypes, choices);
    private ChoiceGroup partner1Status = new ChoiceGroup("Partner 1 - New Partner?", ChoiceGroup.EXCLUSIVE, yesNo, choices);
    private ChoiceGroup partner1HivStatus = new ChoiceGroup("Partner 1 - HIV Status", ChoiceGroup.EXCLUSIVE, partnerHivStatusTypes, choices);
    private ChoiceGroup partner1HivTestInLast3Months = new ChoiceGroup("Partner 1 - Is tested in last 3 months?", ChoiceGroup.EXCLUSIVE, yesNo, choices);
    private ChoiceGroup partner1CondomUsedLastSex = new ChoiceGroup("Partner 1 - Condom used when last having sex?", ChoiceGroup.EXCLUSIVE, yesNoNotKnownNA, choices);
    private ChoiceGroup partner2Type = new ChoiceGroup("Partner 2 - Type", ChoiceGroup.EXCLUSIVE, partnerTypes, choices);
    private ChoiceGroup partner2Status = new ChoiceGroup("Partner 2 - New Partner?", ChoiceGroup.EXCLUSIVE, yesNo, choices);
    private ChoiceGroup partner2HivStatus = new ChoiceGroup("Partner 2 - HIV Status", ChoiceGroup.EXCLUSIVE, partnerHivStatusTypes, choices);
    private ChoiceGroup partner2HivTestInLast3Months = new ChoiceGroup("Partner 2 - Is tested in last 3 months?", ChoiceGroup.EXCLUSIVE, yesNo, choices);
    private ChoiceGroup partner2CondomUsedLastSex = new ChoiceGroup("Partner 2 - Condom used when last having sex?", ChoiceGroup.EXCLUSIVE, yesNoNotKnownNA, choices);
    private ChoiceGroup partner3Type = new ChoiceGroup("Partner 3 - Type", ChoiceGroup.EXCLUSIVE, partnerTypes, choices);
    private ChoiceGroup partner3Status = new ChoiceGroup("Partner 3 - New Partner?", ChoiceGroup.EXCLUSIVE, yesNo, choices);
    private ChoiceGroup partner3HivStatus = new ChoiceGroup("Partner 3 - HIV Status", ChoiceGroup.EXCLUSIVE, partnerHivStatusTypes, choices);
    private ChoiceGroup partner3HivTestInLast3Months = new ChoiceGroup("Partner 3 - Is tested in last 3 months?", ChoiceGroup.EXCLUSIVE, yesNo, choices);
    private ChoiceGroup partner3CondomUsedLastSex = new ChoiceGroup("Partner 3 - Condom used when last having sex?", ChoiceGroup.EXCLUSIVE, yesNoNotKnownNA, choices);
    private ChoiceGroup partner4Type = new ChoiceGroup("Partner 4 - Type", ChoiceGroup.EXCLUSIVE, partnerTypes, choices);
    private ChoiceGroup partner4Status = new ChoiceGroup("Partner 4 - New Partner?", ChoiceGroup.EXCLUSIVE, yesNo, choices);
    private ChoiceGroup partner4HivStatus = new ChoiceGroup("Partner 4 - HIV Status", ChoiceGroup.EXCLUSIVE, partnerHivStatusTypes, choices);
    private ChoiceGroup partner4HivTestInLast3Months = new ChoiceGroup("Partner 4 - Is tested in last 3 months?", ChoiceGroup.EXCLUSIVE, yesNo, choices);
    private ChoiceGroup partner4CondomUsedLastSex = new ChoiceGroup("Partner 4 - Condom used when last having sex?", ChoiceGroup.EXCLUSIVE, yesNoNotKnownNA, choices);

    /*
     * Pregnancy and Family Planning
     */
    private ChoiceGroup clientPregnant = new ChoiceGroup("Is client pregnant?", ChoiceGroup.EXCLUSIVE, yesNoNotKnownNA, choices);
    private ChoiceGroup desiresPregnancy = new ChoiceGroup("Client desires pregnancy soon(next 2 yrs)?", ChoiceGroup.EXCLUSIVE, yesNoNotKnown, choices);
    private ChoiceGroup usingFpMethod = new ChoiceGroup("Currently using FP method?", ChoiceGroup.EXCLUSIVE, yesNoNotKnown, choices);
    private ChoiceGroup whichFpMethod = new ChoiceGroup("If using FP, which method?", ChoiceGroup.EXCLUSIVE, familyPlanningMethods, choices);

    /*
     * Screening for TB and STI
     */
    /* TB */
    private ChoiceGroup cough = new ChoiceGroup("Cough for 3 or more weeks?", ChoiceGroup.EXCLUSIVE, yesNoNotKnown, choices);
    private ChoiceGroup nightSweats = new ChoiceGroup("Night sweats?", ChoiceGroup.EXCLUSIVE, yesNoNotKnown, choices);
    private ChoiceGroup weightLoss = new ChoiceGroup("Unintentional weight loss?", ChoiceGroup.EXCLUSIVE, yesNoNotKnown, choices);
    private ChoiceGroup hadTb = new ChoiceGroup("Previously had TB?", ChoiceGroup.EXCLUSIVE, yesNoNotKnown, choices);
    /* STI*/
    private ChoiceGroup abnormalDischarge = new ChoiceGroup("Abnormal discharge?", ChoiceGroup.EXCLUSIVE, yesNoNotKnown, choices);
    private ChoiceGroup sores = new ChoiceGroup("Sores or ulcers in genital areas?", ChoiceGroup.EXCLUSIVE, yesNoNotKnown, choices);
    private ChoiceGroup pain = new ChoiceGroup("Pain on passing urine?", ChoiceGroup.EXCLUSIVE, yesNoNotKnown, choices);

    /*
     * HIV Testing
     */
    private ChoiceGroup testingProtocol = new ChoiceGroup("Testing Protocol", ChoiceGroup.EXCLUSIVE, testingProtocols, choices);
    private ChoiceGroup testResult1 = new ChoiceGroup("Test 1", ChoiceGroup.EXCLUSIVE, testResultTypes, choices);
    private ChoiceGroup testResult2 = new ChoiceGroup("Test 2", ChoiceGroup.EXCLUSIVE, testResultTypesNA, choices);
    private ChoiceGroup testResult3 = new ChoiceGroup("Test 3", ChoiceGroup.EXCLUSIVE, testResultTypesNA, choices);
    private ChoiceGroup finalResult = new ChoiceGroup("Final Result", ChoiceGroup.EXCLUSIVE, finalResultTypes, choices);

    /*
     * Referrals and Risk Reduction
     */
    private ChoiceGroup hivSymptoms = new ChoiceGroup("Does the client have any of the following symptoms?", ChoiceGroup.EXCLUSIVE, symptoms, choices);
    private ChoiceGroup referredTo = new ChoiceGroup("Referred To", ChoiceGroup.EXCLUSIVE, referrals, choices);
    private ChoiceGroup riskReductionPlan = new ChoiceGroup("Risk Reduction Plan", ChoiceGroup.MULTIPLE, riskReductionPlans, choices);
    private TextField condomsGiven = new TextField("Condoms given by councellor", "", 2, TextField.NUMERIC);

    /*
     * Control structure flags
     */
    private boolean firstTime;
    private int sessionStatus = -1;

    public HtcMidlet() {
        //Initialise Midlet and create Login and HTC Forms
        firstTime = true;
        loginForm = new Form("Login");
        sessionTypeForm = new Form("Session Type");
        mainForm = new Form("HTC Session");
    }

    public void startApp() {
        if (firstTime) {
            /* Setup the login screen */
            loginForm.append(userName);
            loginForm.append(password);
            loginForm.append(siteCode);

            loginForm.addCommand(exitCommand);
            loginForm.addCommand(loginCommand);

            /*
             * Setup the session type form
             */
            sessionTypeForm.append(sessionType);
            sessionTypeForm.addCommand(selectCommand);
            sessionTypeForm.addCommand(quitCommand);

            /* Set up the main form */

            /*
             * Site and session information
             */
            mainForm.append(traditionalAuthority);
            mainForm.append(returnVisit);

            /*
             * Demographic information
             */
            mainForm.append(sex);
            mainForm.append(dateOfBirth);
            mainForm.append(currentResidence);
            mainForm.append(occupation);
            mainForm.append(highestEducation);
            mainForm.append(maritalStatus);
            mainForm.append(marriageType);
            mainForm.append(maritalHistory);
            mainForm.append(knewAboutService);
            mainForm.append(referredBy);
            mainForm.append(mostImportantReason);

            /*
             * History of HIV Testing and HIV-Risk
             */
            mainForm.append(everHivTestedBefore);
            mainForm.append(lastHivTest);
            mainForm.append(everHadSex);
            mainForm.append(ageAtFirstSex);
            mainForm.append(lastTimeClientHadSex);
            mainForm.append(coupleEverHadSex);
            mainForm.append(everReceivedBloodTransfusion);

            /*
             * Partner History (Individual CT only)
             */
            mainForm.append(partner1Type);
            mainForm.append(partner1Status);
            mainForm.append(partner1HivStatus);
            mainForm.append(partner1HivTestInLast3Months);
            mainForm.append(partner1CondomUsedLastSex);

            mainForm.append(partner2Type);
            mainForm.append(partner2Status);
            mainForm.append(partner2HivStatus);
            mainForm.append(partner2HivTestInLast3Months);
            mainForm.append(partner2CondomUsedLastSex);

            mainForm.append(partner3Type);
            mainForm.append(partner3Status);
            mainForm.append(partner3HivStatus);
            mainForm.append(partner3HivTestInLast3Months);
            mainForm.append(partner3CondomUsedLastSex);

            mainForm.append(partner4Type);
            mainForm.append(partner4Status);
            mainForm.append(partner4HivStatus);
            mainForm.append(partner4HivTestInLast3Months);
            mainForm.append(partner4CondomUsedLastSex);

            /*
             * Pregnancy and Family Planning
             */
            mainForm.append(clientPregnant);
            mainForm.append(desiresPregnancy);
            mainForm.append(usingFpMethod);
            mainForm.append(whichFpMethod);

            /*
             * Screening of TB and STI
             */
            mainForm.append(cough);
            mainForm.append(nightSweats);
            mainForm.append(weightLoss);
            mainForm.append(hadTb);

            mainForm.append(abnormalDischarge);
            mainForm.append(sores);
            mainForm.append(pain);

            /*
             * HIV Testing
             */
            mainForm.append(testingProtocol);
            mainForm.append(testResult1);
            mainForm.append(testResult2);
            mainForm.append(testResult3);
            mainForm.append(finalResult);

            /*
             * Referrals and Risk Reduction
             */
            mainForm.append(hivSymptoms);
            mainForm.append(referredTo);
            mainForm.append(riskReductionPlan);
            mainForm.append(condomsGiven);

            /*
             * Commands for the form
             */
            mainForm.addCommand(saveCommand);
            mainForm.addCommand(resetCommand);
            mainForm.addCommand(cancelCommand);

            /*
             * Event listeners
             */
            loginForm.setCommandListener(this);
            sessionTypeForm.setCommandListener(this);
            sessionTypeForm.setItemStateListener(this);
            mainForm.setCommandListener(this);
            mainForm.setItemStateListener(this);

            firstTime = false;// Unsetting the first time flag
        }

        Display.getDisplay(this).setCurrent(loginForm);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    /*
     * Event handler for command buttons
     */
    public void commandAction(Command command, Displayable s) {
        if (command == exitCommand) {
            destroyApp(false);
            notifyDestroyed();
        } else if (command == loginCommand) {
            boolean validUserAndSite = validateUser();
            // Load the HTC Form if a valid user has logged at a valid site
            if (validUserAndSite) {
                Display.getDisplay(this).setCurrent(sessionTypeForm);
            }
        } else if (command == selectCommand) {
            processSessionTypeForm();
        } else if (command == resetCommand) {
            destroyApp(false);
            notifyDestroyed();
        } else if (command == cancelCommand) {
            destroyApp(false);
            notifyDestroyed();
        } else if (command == saveCommand) {
            //          lastName.setString("");
            //          Display.getDisplay(this).setCurrentItem(lastName);
            if (sessionStatus == FIRST_FORM) {
                // Save/send HTC form and capture second form
                sessionStatus = SECOND_FORM;
                Display.getDisplay(this).setCurrent(mainForm);
            } else {
                // Save/send HTC form and display the session type form
                Display.getDisplay(this).setCurrent(sessionTypeForm);
            }
        }
    }

    /*
     * Method will listen to state changes for the various form items and navigate to next item if
     * applicable
     */
    public void itemStateChanged(Item item) {
        if (item == sessionType) {
            processSessionTypeForm();
        } else if (item == returnVisit) {
            sex.setLayout(ChoiceGroup.LAYOUT_VCENTER);
            Display.getDisplay(this).setCurrentItem(sex);
        } else if (item == sex) {
            dateOfBirth.setLayout(ChoiceGroup.LAYOUT_VCENTER);
            Display.getDisplay(this).setCurrentItem(dateOfBirth);
        } else if (item == currentResidence) {
            occupation.setLayout(ChoiceGroup.LAYOUT_VCENTER);
            Display.getDisplay(this).setCurrentItem(occupation);
        } else if (item == occupation) {
            highestEducation.setLayout(ChoiceGroup.LAYOUT_VCENTER);
            Display.getDisplay(this).setCurrentItem(highestEducation);
        } else if (item == highestEducation) {
            maritalStatus.setLayout(ChoiceGroup.LAYOUT_VCENTER);
            Display.getDisplay(this).setCurrentItem(maritalStatus);
        } else if (item == maritalStatus) {
            marriageType.setLayout(ChoiceGroup.LAYOUT_VCENTER);
            Display.getDisplay(this).setCurrentItem(marriageType);
        } else if (item == marriageType) {
            Display.getDisplay(this).setCurrentItem(maritalHistory);
        } else if (item == maritalHistory) {
            Display.getDisplay(this).setCurrentItem(knewAboutService);
        } else if (item == knewAboutService) {
            Display.getDisplay(this).setCurrentItem(referredBy);
        } else if (item == referredBy) {
            Display.getDisplay(this).setCurrentItem(mostImportantReason);
        } else if (item == mostImportantReason) {
            Display.getDisplay(this).setCurrentItem(everHivTestedBefore);
        } else if (item == everHivTestedBefore) {
            Display.getDisplay(this).setCurrentItem(lastHivTest);
        } else if (item == everHadSex) {
            Display.getDisplay(this).setCurrentItem(ageAtFirstSex);
        } else if (item == coupleEverHadSex) {
            Display.getDisplay(this).setCurrentItem(everReceivedBloodTransfusion);
        } else if (item == everReceivedBloodTransfusion) {
            partner1Type.setLayout(ChoiceGroup.LAYOUT_VCENTER);
            Display.getDisplay(this).setCurrentItem(partner1Type);
        } else if (item == partner1Type) {
            /*boolean selectedValue[] = new boolean[partner1Type.size()];
            partner1Type.getSelectedFlags(selectedValue);
            int selectedIndex = -1;
            for(int i=0; i<selectedValue.length; i++){
            if(selectedValue[i]){
            selectedIndex = i;
            }
            }*/
            int selectedIndex = partner1Type.getSelectedIndex();
            if (selectedIndex == 0) {
                Display.getDisplay(this).setCurrentItem(clientPregnant);
            } else {
                Display.getDisplay(this).setCurrentItem(partner1Status);
            }
        } else if (item == partner1Status) {
            Display.getDisplay(this).setCurrentItem(partner1HivStatus);
        } else if (item == partner1HivStatus) {
            int selectedIndex = partner1HivStatus.getSelectedIndex();
            if (selectedIndex == 1) {
                Display.getDisplay(this).setCurrentItem(partner1HivTestInLast3Months);
            } else {
                Display.getDisplay(this).setCurrentItem(partner1CondomUsedLastSex);
            }
        } else if (item == partner1HivTestInLast3Months) {
            Display.getDisplay(this).setCurrentItem(partner1CondomUsedLastSex);
        } else if (item == partner1CondomUsedLastSex) {
            Display.getDisplay(this).setCurrentItem(partner2Type);
        } else if (item == partner2Type) {
            int selectedIndex = partner2Type.getSelectedIndex();
            if (selectedIndex == 0) {
                Display.getDisplay(this).setCurrentItem(clientPregnant);
            } else {
                Display.getDisplay(this).setCurrentItem(partner2Status);
            }
        } else if (item == partner2Status) {
            Display.getDisplay(this).setCurrentItem(partner2HivStatus);
        } else if (item == partner2HivStatus) {
            int selectedIndex = partner2HivStatus.getSelectedIndex();
            if (selectedIndex == 1) {
                Display.getDisplay(this).setCurrentItem(partner2HivTestInLast3Months);
            } else {
                Display.getDisplay(this).setCurrentItem(partner2CondomUsedLastSex);
            }
        } else if (item == partner2HivTestInLast3Months) {
            Display.getDisplay(this).setCurrentItem(partner2CondomUsedLastSex);
        } else if (item == partner2CondomUsedLastSex) {
            Display.getDisplay(this).setCurrentItem(partner3Type);
        } else if (item == partner3Type) {
            int selectedIndex = partner3Type.getSelectedIndex();
            if (selectedIndex == 0) {
                Display.getDisplay(this).setCurrentItem(clientPregnant);
            } else {
                Display.getDisplay(this).setCurrentItem(partner3Status);
            }
        } else if (item == partner3Status) {
            Display.getDisplay(this).setCurrentItem(partner3HivStatus);
        } else if (item == partner3HivStatus) {
            int selectedIndex = partner3HivStatus.getSelectedIndex();
            if (selectedIndex == 1) {
                Display.getDisplay(this).setCurrentItem(partner3HivTestInLast3Months);
            } else {
                Display.getDisplay(this).setCurrentItem(partner3CondomUsedLastSex);
            }
        } else if (item == partner3HivTestInLast3Months) {
            Display.getDisplay(this).setCurrentItem(partner3CondomUsedLastSex);
        } else if (item == partner3CondomUsedLastSex) {
            Display.getDisplay(this).setCurrentItem(partner4Type);
        } else if (item == partner4Type) {
            int selectedIndex = partner4Type.getSelectedIndex();
            if (selectedIndex == 0) {
                Display.getDisplay(this).setCurrentItem(clientPregnant);
            } else {
                Display.getDisplay(this).setCurrentItem(partner4Status);
            }
        } else if (item == partner4Status) {
            Display.getDisplay(this).setCurrentItem(partner4HivStatus);
        } else if (item == partner4HivStatus) {
            int selectedIndex = partner4HivStatus.getSelectedIndex();
            if (selectedIndex == 1) {
                Display.getDisplay(this).setCurrentItem(partner4HivTestInLast3Months);
            } else {
                Display.getDisplay(this).setCurrentItem(partner4CondomUsedLastSex);
            }
        } else if (item == partner4HivTestInLast3Months) {
            Display.getDisplay(this).setCurrentItem(partner4CondomUsedLastSex);
        } else if (item == partner4CondomUsedLastSex) {
            Display.getDisplay(this).setCurrentItem(clientPregnant);
        } else if (item == clientPregnant) {
            Display.getDisplay(this).setCurrentItem(desiresPregnancy);
        } else if (item == usingFpMethod) {
            Display.getDisplay(this).setCurrentItem(whichFpMethod);
        } else if (item == whichFpMethod) {
            Display.getDisplay(this).setCurrentItem(cough);
        } else if (item == cough) {
            Display.getDisplay(this).setCurrentItem(nightSweats);
        } else if (item == nightSweats) {
            Display.getDisplay(this).setCurrentItem(weightLoss);
        } else if (item == weightLoss) {
            Display.getDisplay(this).setCurrentItem(hadTb);
        } else if (item == hadTb) {
            Display.getDisplay(this).setCurrentItem(abnormalDischarge);
        } else if (item == abnormalDischarge) {
            Display.getDisplay(this).setCurrentItem(sores);
        } else if (item == sores) {
            Display.getDisplay(this).setCurrentItem(pain);
        } else if (item == pain) {
            Display.getDisplay(this).setCurrentItem(testingProtocol);
        } else if (item == testingProtocol) {
            Display.getDisplay(this).setCurrentItem(testResult1);
        } else if (item == testResult1) {
            int selectedIndex = testResult1.getSelectedIndex();
            if (selectedIndex == 0) {
                Display.getDisplay(this).setCurrentItem(finalResult);
            } else {
                Display.getDisplay(this).setCurrentItem(testResult2);
            }
        } else if (item == testResult2) {
            int selectedIndex = testResult2.getSelectedIndex();
            if (selectedIndex == 0) {
                Display.getDisplay(this).setCurrentItem(finalResult);
            } else {
                Display.getDisplay(this).setCurrentItem(testResult3);
            }
        } else if (item == testResult3) {
            Display.getDisplay(this).setCurrentItem(finalResult);
        } else if (item == finalResult) {
            int selectedIndex = finalResult.getSelectedIndex();
            if (selectedIndex == 1) {
                Display.getDisplay(this).setCurrentItem(hivSymptoms);
            } else {
                Display.getDisplay(this).setCurrentItem(referredTo);
            }
        }
    }

    /*
     * Method to validate user credentials
     */
    private boolean validateUser() {
        boolean valid = true;
        return valid;
    }

    /*
     * Method to process session type form accordingly
     */
    private void processSessionTypeForm() {
        if (sessionType.getSelectedIndex() == COMES_WITH_PARTNER) {
            sessionStatus = FIRST_FORM;
        } else {
            sessionStatus = SINGLE_FORM;
        }
        Display.getDisplay(this).setCurrent(mainForm);
    }
}
