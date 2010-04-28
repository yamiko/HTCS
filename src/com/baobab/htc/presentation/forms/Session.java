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
public class Session extends Form implements CommandListener, ItemStateListener {
    /* Constants for the class */

    /* The Midlet activating this Form */
    private final HtcMidlet htcMidlet;

    Image[] choices = null; // Null image array for ChoiceGroup objects' constructors

    /* Command Buttons for the HTC Form */
    private Command saveCommand = new Command("Save", Command.OK, 3);
    private Command resetCommand = new Command("Reset", Command.CANCEL, 1);
    private Command cancelCommand = new Command("Exit", Command.EXIT, 1);

    /* Arrays for the choice group objects */
    String[] yesNo = {"Yes", "No"};
    String[] yesNoNotKnown = {"Yes", "No", "Not Known"};
    String[] yesNoNotKnownNA = {"Yes", "No", "Not Known", "N/A"};
    String[] sexTypes = {"Male", "Female - Non-pregnant", "Female - Pregnant"};
    String[] occupations = {"Not working", "Formerly Employed", "Self Employed", "Student", "Armed Forces/Police", "Other"};
    String[] highestEducations = {"None", "Some primary", "Completed primary", "Secondary", "Tertiary"};
    String[] maritalStatusTypes = {"Never Married", "Married - Monogomous", "Married - Polygamous", "Separated or Divorced", "Widowed"};
    String[] knewAboutServiceTypes = {"Radio", "Outreach", "Signposts", "Clients", "Newsprint",
        "Clinic/Hosp", "Television", "Reffered", "Other"};
    String[] referredByTypes = {"Not Referred", "Relative", "Friend", "Other Client", "Religious Institution",
        "CBOs", "NGOs", "Antenatal Clinic", "TB Clinic", "STI Clinic", "Other Clinic/Hospital", "Other"};
    String[] mostImportantReasonsOptions = {"Engaged in Risky Behaviour Recently", "Worried About Oen Health",
        "Suspect or Know Partner in Risky Behaviour", "Partner's Health is Concerning/Partner is HIV+",
        "Before Initiating a Sexual Relationship", "Pre-marital Testing(if not yet sexually active)",
        "Pregnant", "2nd Test(confirmation after window period)", "Don't Believe Previous HIV+ Result",
        "Referral from Health Worker", "Referral from Family or Friend", "Other"};
    String[] everHivTestedBeforeOptions = {"No", "Yes - HIV+", "Yes - HIV-", "Yes - Did Not Get Final Results"};
    String[] oftenGetTestedOptions = {"Every 3 Months", "Every 6 Months", "Every 12 Months", "Every Other Year", "Other"};
    String[] oftenDrinksAlcoholOptions = {"Dont Drink", "A Few Nights Each Week", "Once a Week", "2-3 Times a Month", "Hardly Ever"};
    String[] sexualPartnersOptions = {"0", "1-2", "3-6", "6+"};
    String[] sexualRelationshipsOptions = {"Haven't Had Sex in Last 3 Months", "Spouse or Live-in Partner", "Long-term/Serious Partner", "Casual Partner", "Same Sex Partner"};
    String[] statusOfPartnersOptions = {"No", "Yes - All Negative", "Yes - At Least One Positive"};
    String[] frequentlyUseCondomOptions = {"Never", "Sometimes", "Always", "Can't Remember"};
    String[] familyPlanningMethodsOptions = {"No - Trying to get Pregnant",
        "No - Not Trying to get Pregnant and not Using Family Planning", "Yes"};
    String[] testResultTypes = {"Non Reactive", "Reactive", "Inconclusive"};
    String[] testResultTypesNA = {"Non Reactive", "Reactive", "Incoclusive", "N/A"};
    String[] finalResultTypes = {"Non Reactive", "Reactive", "Inconclusive", "Exposed Infant"};
    String[] symptoms = {"Cough for Three or More Weeks", "Night Sweats", "Unintentional Weight Loss",
        "Previous TB", "Abnormal Genital Discharge", "Sores or Ulcers in Genital Area",
        "Pain on Passing Urine"};
    String[] riskReductionPlans = {"None", "Abstinence", "Client Will Have One Partner", "Client Will Ask Partner to be Monogamous",
        "Client will increase use of Condoms", "Client will reduce number of Partners", "Eliminate high risk partners",
        "Reduce Alcohol/Drug Use", "Retest for HIV", "Test Partner for HIV", "Attend PTC"};

    /* Site and session Information */
    private TextField traditionalAuthority = new TextField("T/A", "", 15, TextField.ANY);
    private ChoiceGroup returnVisit = new ChoiceGroup("Return Visit", ChoiceGroup.EXCLUSIVE, yesNo, choices);

    /* Demographic Information */
    private ChoiceGroup sex = new ChoiceGroup("Sex", ChoiceGroup.EXCLUSIVE, sexTypes, choices);
    private DateField dateOfBirth = new DateField("Date of Birth", DateField.DATE); // Not a text field but a data field
    private ChoiceGroup occupation = new ChoiceGroup("Occupation", ChoiceGroup.EXCLUSIVE, occupations, choices);
    private ChoiceGroup highestEducation = new ChoiceGroup("Highest Education", ChoiceGroup.EXCLUSIVE, highestEducations, choices);
    private ChoiceGroup maritalStatus = new ChoiceGroup("Marital Status", ChoiceGroup.EXCLUSIVE, maritalStatusTypes, choices);
    private ChoiceGroup mostImportantReasons = new ChoiceGroup("Reasons One Came for Testing Today", ChoiceGroup.MULTIPLE, mostImportantReasonsOptions, choices);

    /* History of Testing and HIV Risk */
    private ChoiceGroup everHivTestedBefore = new ChoiceGroup("Ever HIV Tested Before?", ChoiceGroup.EXCLUSIVE, everHivTestedBeforeOptions, choices);
    private ChoiceGroup oftenGetTested = new ChoiceGroup("How Often Do You Get Tested?", ChoiceGroup.EXCLUSIVE, oftenGetTestedOptions, choices);
    private ChoiceGroup oftenDrinksAlcohol = new ChoiceGroup("How Often Do You Drink Alcohol?", ChoiceGroup.EXCLUSIVE, oftenDrinksAlcoholOptions, choices);
    private ChoiceGroup sexualPartners = new ChoiceGroup("How Many Sexual Partners Have You Had in the Last 3 Months?", ChoiceGroup.EXCLUSIVE, sexualPartnersOptions, choices);
    private ChoiceGroup sexualRelationships = new ChoiceGroup("Which Type of Sexual Relationship Have You Had in the Last 3 Months?", ChoiceGroup.EXCLUSIVE, sexualRelationshipsOptions, choices);
    private ChoiceGroup statusOfPartners = new ChoiceGroup("Know Status of ALL Your Partners in the Last 3 Months?", ChoiceGroup.EXCLUSIVE, statusOfPartnersOptions, choices);
    private ChoiceGroup frequentlyUseCondom = new ChoiceGroup("In the Last 3 Months, How Frequently Did One Use a Condom with the Sexual Partners You Were Not Married To?", ChoiceGroup.EXCLUSIVE, frequentlyUseCondomOptions, choices);

    /* Pregnancy and Family Planning */
    private ChoiceGroup usingFpMethod = new ChoiceGroup("Currently using FP method?", ChoiceGroup.EXCLUSIVE, yesNoNotKnown, choices);

    /* HIV Testing */
    private ChoiceGroup testResult1 = new ChoiceGroup("Test 1", ChoiceGroup.EXCLUSIVE, testResultTypes, choices);
    private ChoiceGroup testResult2 = new ChoiceGroup("Test 2", ChoiceGroup.EXCLUSIVE, testResultTypesNA, choices);
    private ChoiceGroup testResult3 = new ChoiceGroup("Test 3", ChoiceGroup.EXCLUSIVE, testResultTypesNA, choices);
    private ChoiceGroup finalResult = new ChoiceGroup("Final Result", ChoiceGroup.EXCLUSIVE, finalResultTypes, choices);

    /* Referrals and Risk Reduction */
    private ChoiceGroup hivSymptoms = new ChoiceGroup("Does the client have any of the following symptoms?", ChoiceGroup.EXCLUSIVE, symptoms, choices);
    private ChoiceGroup riskReductionPlan = new ChoiceGroup("Risk Reduction Plan", ChoiceGroup.MULTIPLE, riskReductionPlans, choices);
    private TextField condomsGiven = new TextField("Condoms given by councellor", "", 2, TextField.NUMERIC);

    public Session(HtcMidlet htcMidlet) {
        super("HTC Session");
        this.htcMidlet = htcMidlet;

        /* Site and session information */
        append(traditionalAuthority);
        append(returnVisit);

        /* Demographic information */
        append(sex);
        append(dateOfBirth);
        append(occupation);
        append(highestEducation);
        append(maritalStatus);
        append(mostImportantReasons);

        /* History of HIV Testing and HIV-Risk */
        append(everHivTestedBefore);
        append(oftenGetTested);
        append(oftenDrinksAlcohol);
        append(sexualPartners);
        append(sexualRelationships);
        append(statusOfPartners);
        append(frequentlyUseCondom);

        /* Pregnancy and Family Planning */
        append(usingFpMethod);

        /* HIV Testing */
        append(testResult1);
        append(testResult2);
        append(testResult3);
        append(finalResult);

        /* Referrals and Risk Reduction */
        append(hivSymptoms);
        append(riskReductionPlan);
        append(condomsGiven);

        /* Commands for the form */
        addCommand(saveCommand);
        addCommand(resetCommand);
        addCommand(cancelCommand);

        setCommandListener(this);
        setItemStateListener(this);
}

    /*
     * Event handler for command buttons
     */
    public void commandAction(Command command, Displayable s) {
        if (command == cancelCommand) {
            htcMidlet.destroyApp(false);
            htcMidlet.notifyDestroyed();
        } else if (command == saveCommand) {
            if (htcMidlet.getSessionStatus() == ConstantHelper.FIRST_FORM) {
                // Save/send HTC form and capture second form
                htcMidlet.saveData();
                htcMidlet.setSessionStatus(ConstantHelper.SECOND_FORM);
                htcMidlet.setForm(ConstantHelper.SESSION_FORM);
            } else {
                // Save/send HTC form and display the session type form
                boolean errorFree = htcMidlet.saveData();
                if(errorFree){
                    htcMidlet.setForm(ConstantHelper.SESSION_TYPE_FORM);
                }
            }
        }
    }

    /*
     * Method will listen to state changes for the various form items and navigate to next item if
     * applicable
     */
    public void itemStateChanged(Item item) {
        if (item == returnVisit) {
            sex.setLayout(ChoiceGroup.LAYOUT_VCENTER);
            Display.getDisplay(htcMidlet).setCurrentItem(sex);
        } else if (item == sex) {
            dateOfBirth.setLayout(ChoiceGroup.LAYOUT_VCENTER);
            Display.getDisplay(htcMidlet).setCurrentItem(dateOfBirth);
        } else if (item == occupation) {
            highestEducation.setLayout(ChoiceGroup.LAYOUT_VCENTER);
            Display.getDisplay(htcMidlet).setCurrentItem(highestEducation);
        } else if (item == highestEducation) {
            maritalStatus.setLayout(ChoiceGroup.LAYOUT_VCENTER);
            Display.getDisplay(htcMidlet).setCurrentItem(maritalStatus);
        } else if (item == maritalStatus) {
            Display.getDisplay(htcMidlet).setCurrentItem(mostImportantReasons);
        } else if (item == everHivTestedBefore) {
            Display.getDisplay(htcMidlet).setCurrentItem(oftenGetTested);
        } else if (item == oftenGetTested) {
            Display.getDisplay(htcMidlet).setCurrentItem(oftenDrinksAlcohol);
        } else if (item == oftenDrinksAlcohol) {
            Display.getDisplay(htcMidlet).setCurrentItem(sexualPartners);
        } else if (item == sexualPartners) {
            Display.getDisplay(htcMidlet).setCurrentItem(sexualRelationships);
        } else if (item == sexualRelationships) {
            Display.getDisplay(htcMidlet).setCurrentItem(statusOfPartners);
        } else if (item == statusOfPartners) {
            Display.getDisplay(htcMidlet).setCurrentItem(frequentlyUseCondom);
        } else if (item == frequentlyUseCondom) {
            Display.getDisplay(htcMidlet).setCurrentItem(usingFpMethod);
        } else if (item == usingFpMethod) {
            Display.getDisplay(htcMidlet).setCurrentItem(testResult1);
        } else if (item == testResult1) {
            int selectedIndex = testResult1.getSelectedIndex();
            if (selectedIndex == 0) {
                Display.getDisplay(htcMidlet).setCurrentItem(finalResult);
            } else {
                Display.getDisplay(htcMidlet).setCurrentItem(testResult2);
            }
        } else if (item == testResult2) {
            int selectedIndex = testResult2.getSelectedIndex();
            if (selectedIndex == 0) {
                Display.getDisplay(htcMidlet).setCurrentItem(finalResult);
            } else {
                Display.getDisplay(htcMidlet).setCurrentItem(testResult3);
            }
        } else if (item == testResult3) {
            Display.getDisplay(htcMidlet).setCurrentItem(finalResult);
        } else if (item == finalResult) {
            Display.getDisplay(htcMidlet).setCurrentItem(hivSymptoms);
        }
    }

    public TextField getTraditionalAuthority() {
        return traditionalAuthority;
    }

    public ChoiceGroup getReturnVisit() {
        return returnVisit;
    }

    public ChoiceGroup getSex() {
        return sex;
    }

    public DateField getDateOfBirth() {
        return dateOfBirth;
    }

    public ChoiceGroup getOccupation() {
        return occupation;
    }

    public ChoiceGroup getHighestEducation() {
        return highestEducation;
    }

    public ChoiceGroup getMaritalStatus() {
        return maritalStatus;
    }

    public ChoiceGroup getMostImportantReasons() {
        return mostImportantReasons;
    }

    public ChoiceGroup getEverHivTestedBefore() {
        return everHivTestedBefore;
    }

    public ChoiceGroup getOftenGetTested() {
        return oftenGetTested;
    }

    public ChoiceGroup getOftenDrinksAlcohol() {
        return oftenDrinksAlcohol;
    }

    public ChoiceGroup getSexualPartners() {
        return sexualPartners;
    }

    public ChoiceGroup getSexualRelationships() {
        return sexualRelationships;
    }

    public ChoiceGroup getStatusOfPartners() {
        return statusOfPartners;
    }

    public ChoiceGroup getFrequentlyUseCondom() {
        return frequentlyUseCondom;
    }

    public ChoiceGroup getUsingFpMethod() {
        return usingFpMethod;
    }

    public ChoiceGroup getTestResult1() {
        return testResult1;
    }

    public ChoiceGroup getTestResult2() {
        return testResult2;
    }

    public ChoiceGroup getTestResult3() {
        return testResult3;
    }

    public ChoiceGroup getFinalResult() {
        return finalResult;
    }

    public ChoiceGroup getHivSymptoms() {
        return hivSymptoms;
    }

    public ChoiceGroup getRiskReductionPlan() {
        return riskReductionPlan;
    }

    public TextField getCondomsGiven() {
        return condomsGiven;
    }
}
