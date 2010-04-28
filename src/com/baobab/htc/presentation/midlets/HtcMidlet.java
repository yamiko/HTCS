/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baobab.htc.presentation.midlets;

import com.baobab.htc.presentation.forms.*;
import com.baobab.htc.business.beans.*;
import com.baobab.htc.business.utils.*;
import com.baobab.htc.data.factories.*;
import com.baobab.htc.data.files.*;
import java.util.*;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * @author yamiko
 */
public class HtcMidlet extends MIDlet {
    /* Memory variables */

    StringBuffer couplesReference = new StringBuffer();

    /* Forms/screens for the App */
    private Login loginForm;
    private Transmission transmissionForm;
    private SessionType sessionTypeForm;
    private Session sessionForm;

    /*
     * Control structure flags
     */
    private int sessionStatus = -1;

    public HtcMidlet() {
        /* Initialise Midlet and create Login and HTC Forms */
        loginForm = new Login(this);
        transmissionForm = new Transmission(this);
        sessionTypeForm = new SessionType(this);
        sessionForm = new Session(this);
    }

    public void startApp() {
        Display.getDisplay(this).setCurrent(loginForm);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    /*
     * Method to set a particular form on display given a parameter
     */
    public void setForm(int form) {
        if (form == ConstantHelper.LOGIN_FORM) {
            Display.getDisplay(this).setCurrent(loginForm);
        } else if (form == ConstantHelper.TRANSMISSION_FORM) {
            Display.getDisplay(this).setCurrent(transmissionForm);
        } else if (form == ConstantHelper.SESSION_TYPE_FORM) {
            Display.getDisplay(this).setCurrent(sessionTypeForm);
        } else if (form == ConstantHelper.SESSION_FORM) {
            Display.getDisplay(this).setCurrent(sessionForm);
        }
    }

    /*
     * Method to save form data to HTC Form Bean
     */
    public boolean saveData() {
        boolean isValid;
        HtcBean htcBean = new HtcBean();

        /* Site and Session Attributes */
        htcBean.setSessionType(sessionTypeForm.getSessionType().getSelectedIndex());
        htcBean.setSiteCode(loginForm.getSiteCode().getString());
        htcBean.setCounsellorCode(loginForm.getUserName().getString());
        htcBean.setTraditionalAuthority(sessionForm.getTraditionalAuthority().getString());
        htcBean.setSessionDate(new Date());
        htcBean.setReturnVisit(sessionForm.getReturnVisit().getSelectedIndex());

        /* Generating couplesReference*/
        if (sessionStatus == ConstantHelper.FIRST_FORM) {
            couplesReference = new StringBuffer();
            couplesReference.append(generateCouplesReference());
        } else if (sessionStatus == ConstantHelper.SINGLE_FORM) {
            couplesReference = new StringBuffer();
        }

        htcBean.setCouplesReference(couplesReference.toString());

        /* Demographic Information */
        htcBean.setSex(sessionForm.getSex().getSelectedIndex());
        htcBean.setDateOfBirth(sessionForm.getDateOfBirth().getDate());
        htcBean.setCurrentOccupation(sessionForm.getOccupation().getSelectedIndex());
        htcBean.setHighestEducation(sessionForm.getHighestEducation().getSelectedIndex());
        htcBean.setMaritalStatus(sessionForm.getMaritalStatus().getSelectedIndex());

        /* Referrals and risk Reduction */
        boolean[] mostImportantReasonsFlags = new boolean[sessionForm.getMostImportantReasons().size()];
        int[] mostImportantReasonsIntFlags;
        sessionForm.getMostImportantReasons().getSelectedFlags(mostImportantReasonsFlags);
        mostImportantReasonsIntFlags = convertToIntArray(mostImportantReasonsFlags);
        htcBean.setMostImportantReasons(mostImportantReasonsIntFlags);

        /* History of HIV Testing and Risk */
        htcBean.setEverHivTestedBefore(sessionForm.getEverHivTestedBefore().getSelectedIndex());
        if (htcBean.getEverHivTestedBefore() == 0 && sessionForm.getOftenGetTested().getSelectedIndex() == 0) {
            htcBean.setOftenGetTested(-1); // Set option to not applicable if one has never been tested before
        } else {
            htcBean.setOftenGetTested(sessionForm.getOftenGetTested().getSelectedIndex());
        }

        htcBean.setOftenDrinksAlcohol(sessionForm.getOftenDrinksAlcohol().getSelectedIndex());
        htcBean.setSexualPartners(sessionForm.getSexualPartners().getSelectedIndex());

        if (htcBean.getSexualPartners() == 0) {
            if (sessionForm.getSexualRelationships().getSelectedIndex() == 0) {
                htcBean.setSexualRelationships(-1); // Set sexual relationships option to N/A if no sexual partners option was selected
            } else {
                htcBean.setSexualRelationships(sessionForm.getSexualRelationships().getSelectedIndex());
            }

            if (sessionForm.getStatusOfPartners().getSelectedIndex() == 0) {
                htcBean.setStatusOfPartners(-1); //Set status of partners option to N/A if no sexual partners option was selected
            } else {
                htcBean.setStatusOfPartners(sessionForm.getStatusOfPartners().getSelectedIndex());
            }

            if (sessionForm.getFrequentlyUseCondom().getSelectedIndex() == 0) {
                htcBean.setFrequentlyUseCondom(-1); //Set condom usage frequency option to N/A if no sexual partners option was selected
            } else {
                htcBean.setFrequentlyUseCondom(sessionForm.getFrequentlyUseCondom().getSelectedIndex());
            }
        }


        /* Pregnancy and Family Planning */
        htcBean.setUsingFpMethod(sessionForm.getUsingFpMethod().getSelectedIndex());

        /* HIV Testing */
        htcBean.setTestResult1(sessionForm.getTestResult1().getSelectedIndex());
        htcBean.setTestResult2(sessionForm.getTestResult2().getSelectedIndex());
        htcBean.setTestResult3(sessionForm.getTestResult3().getSelectedIndex());
        htcBean.setFinalResult(sessionForm.getFinalResult().getSelectedIndex());

        /* Referrals and risk Reduction */
        boolean[] hivFlags = new boolean[sessionForm.getHivSymptoms().size()],
                riskReductionPlanFlags = new boolean[sessionForm.getRiskReductionPlan().size()];

        int[] hivIntFlags,
                riskReductionPlanIntFlags;

        sessionForm.getHivSymptoms().getSelectedFlags(hivFlags);
        sessionForm.getRiskReductionPlan().getSelectedFlags(riskReductionPlanFlags);

        hivIntFlags = convertToIntArray(hivFlags);
        riskReductionPlanIntFlags = convertToIntArray(riskReductionPlanFlags);

        htcBean.setHivSymptoms(hivIntFlags);
        htcBean.setRiskReductionPlans(riskReductionPlanIntFlags);

        try {
            htcBean.setCondomsGiven(Integer.parseInt(sessionForm.getCondomsGiven().getString()));
        } catch (Exception ex) {
            // TODO: Log Error in system log
            htcBean.setCondomsGiven(0);
        }

        isValid = validateBean(htcBean);
        if (isValid) {
            /* Write To File */
            StringBuffer fileName = new StringBuffer();
            String fileReference = generateFileName(ConstantHelper.XML_EXTENSION);

            fileName.append(ConstantHelper.getInstance().getRoot());
            fileName.append(fileReference);
            htcBean.setReference(fileReference);

            XmlFactory xmlFactory = new XmlFactory();
            boolean fileCreated = FileWriter.createFile(fileName.toString());
            System.out.println(fileCreated);
            boolean isWritten = FileWriter.writeToFile(fileName.toString(), xmlFactory.htcBeanToXml(htcBean));
            System.out.println(isWritten);
        }
        return isValid;
    }

    /*
     * Method validates the HtcBean
     */
    private boolean validateBean(HtcBean htcBean) {
        boolean isValid = true;

        boolean mostImportantReasonsSelected = false,
                riskReductionPlansSelected = false;

        for(int i = 0; i < htcBean.getMostImportantReasons().length; i++){
            if(htcBean.getMostImportantReasons()[i] > 0){
                mostImportantReasonsSelected = true;
            }
        }

        for(int i = 0; i < htcBean.getRiskReductionPlans().length; i++){
            if(htcBean.getRiskReductionPlans()[i] > 0){
                riskReductionPlansSelected = true;
            }
        }

        if (htcBean.getTraditionalAuthority().trim().equals("")) {
            ErrorAlert.getInstance().displayAlert("T/A is Blank!", sessionForm, this);
            isValid = false;
        } else if (!riskReductionPlansSelected) {
            ErrorAlert.getInstance().displayAlert("Please select at least one risk reduction plan!", sessionForm, this);
            isValid = false;
        } else if (!mostImportantReasonsSelected) {
            ErrorAlert.getInstance().displayAlert("Please select at least one reason for undertakaking an HTC session today!", sessionForm, this);
            isValid = false;
        } else if (htcBean.getDateOfBirth() == null) {
            ErrorAlert.getInstance().displayAlert("Invalid Date of Birth!", sessionForm, this);
            isValid = false;
        } else if (htcBean.getDateOfBirth().getTime() > (new Date()).getTime()) {
            ErrorAlert.getInstance().displayAlert("Invalid Date of Birth!", sessionForm, this);
            isValid = false;
        } else if (htcBean.getSex() == 0 && htcBean.getMostImportantReasons()[6] == 1) {
            ErrorAlert.getInstance().displayAlert("Invalid Reason for Testing - Male Can not be pregnant!", sessionForm, this);
            isValid = false;
        } else if (htcBean.getEverHivTestedBefore() == 0 && htcBean.getOftenGetTested() > 0) {
            ErrorAlert.getInstance().displayAlert("Inconsistent Testing History!", sessionForm, this);
            isValid = false;
        } else if (htcBean.getSexualPartners() == 0 && htcBean.getSexualRelationships() > 0) {
            ErrorAlert.getInstance().displayAlert("Inconsistent Relationship Type to # of Partners Selected!", sessionForm, this);
            isValid = false;
        } else if (htcBean.getSexualPartners() == 0 && htcBean.getFrequentlyUseCondom() > 0) {
            ErrorAlert.getInstance().displayAlert("Inconsistent Condom Frequency Usage to # of Partners Selected!", sessionForm, this);
            isValid = false;
        } else if (htcBean.getSexualPartners() == 0 && htcBean.getStatusOfPartners() > 0) {
            ErrorAlert.getInstance().displayAlert("Inconsistent HIV Status of Partners to # of Partners Selected!", sessionForm, this);
            isValid = false;
        }
        return isValid;
    }

    /*
     * Method generates a file name with the given extension
     */
    private String generateFileName(String extension) {
        StringBuffer couplesReference = new StringBuffer();
        Date today = new Date();
        couplesReference.append(loginForm.getUserName().getString().trim());
        couplesReference.append(loginForm.getSiteCode().getString().trim());
        couplesReference.append(today.getTime());
        couplesReference.append(".");
        couplesReference.append(extension.trim());
        return couplesReference.toString();
    }

    /*
     * Method generates a couples reference from a userName, siteCode and current time in milliseconds
     */
    private String generateCouplesReference() {
        StringBuffer couplesReference = new StringBuffer();
        Date today = new Date();
        couplesReference.append(loginForm.getUserName().getString().trim());
        couplesReference.append(loginForm.getSiteCode().getString().trim());
        couplesReference.append(today.getTime());
        return couplesReference.toString();
    }

    /*
     * Method converting a boolean array to an integer array
     */
    private int[] convertToIntArray(boolean[] booleanArray) {
        int[] intArray = new int[booleanArray.length];
        for (int i = 0; i < booleanArray.length; i++) {
            if (booleanArray[i]) {
                intArray[i] = 1;
            } else {
                intArray[i] = 0;
            }
        }
        return intArray;
    }

    public int getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(int sessionStatus) {
        this.sessionStatus = sessionStatus;
    }
}
