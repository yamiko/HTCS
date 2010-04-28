/*
 *  Program Name: XmlFactory
 *        Author: Yamiko J. Msosa
 *  Date Written: 15th April 2010
 *       Purpose: This is the main XML factory for the necessary business beans
 */
package com.baobab.htc.data.factories;

import com.baobab.htc.business.beans.*;
import com.baobab.htc.business.utils.*;
import org.kxml2.kdom.*;

import java.util.*;

public class XmlFactory {

    /* Constants for the class */
    final int ERROR = -1;
    public static void main(String args[]) {
        XmlFactory xmlFactory = new XmlFactory();
        HtcBean htcBean = new HtcBean();
        String tempString = xmlFactory.htcBeanToXml(htcBean);
        System.out.println(tempString);
    }

    /*
     * Method generates an XML string from an HtcBean
     */
    public String htcBeanToXml(HtcBean htcBean) {
        Document document = new Document();
        Vector elements = new Vector(0);
        Element root = new Element();
        root.setName("HtcForm");

        /* Site and session details */
        createElement("FileReference", htcBean.getReference(), elements);
        createElement("SessionType", String.valueOf(htcBean.getSessionType()), elements);
        createElement("TraditionalAuthority", htcBean.getTraditionalAuthority(), elements);
        createElement("CounsellorCode", htcBean.getCounsellorCode(), elements);
        createElement("SiteCode", htcBean.getSiteCode(), elements);
        
        if(htcBean.getSessionDate() != null){
            createElement("SessionDate", String.valueOf(htcBean.getSessionDate().getTime()), elements);
        } else {
            createElement("SessionDate", String.valueOf(ERROR), elements);
        }
        createElement("ReturnVisit", String.valueOf(htcBean.getReturnVisit()), elements);
        createElement("CouplesReference", htcBean.getCouplesReference(), elements);

        /* Demographics */
        createElement("Sex", String.valueOf(htcBean.getSex()), elements);
        if(htcBean.getDateOfBirth() != null){
            createElement("DateOfBirth", String.valueOf(htcBean.getDateOfBirth().getTime()), elements);
        }else{
            createElement("DateOfBirth", String.valueOf(ERROR), elements);
        }
        createElement("CurrentOccupation", String.valueOf(htcBean.getCurrentOccupation()), elements);
        createElement("HighestEducation", String.valueOf(htcBean.getHighestEducation()), elements);
        createElement("MaritalStatus", String.valueOf(htcBean.getMaritalStatus()), elements);
        createElement("MostImportantReasons", htcBean.getMostImportantReasons(), elements);

        /* History of HIV Testing and Risk */
        createElement("EverHivTestedBefore", String.valueOf(htcBean.getEverHivTestedBefore()), elements);
        createElement("OftenGetTested", String.valueOf(htcBean.getOftenGetTested()), elements);
        createElement("OftenDrinksAlcohol", String.valueOf(htcBean.getOftenDrinksAlcohol()), elements);
        createElement("SexualPartners", String.valueOf(htcBean.getSexualPartners()), elements);
        createElement("SexualRelationships", String.valueOf(htcBean.getSexualRelationships()), elements);
        createElement("StatusOfPartners", String.valueOf(htcBean.getStatusOfPartners()), elements);
        createElement("FrequentlyUseCondom", String.valueOf(htcBean.getFrequentlyUseCondom()), elements);

        /* Pregnancy and Planning */
        createElement("UsingFpMethod", String.valueOf(htcBean.getUsingFpMethod()), elements);

        /* HIV Testing */
        createElement("TestResult1", String.valueOf(htcBean.getTestResult1()), elements);
        createElement("TestResult2", String.valueOf(htcBean.getTestResult2()), elements);
        createElement("TestResult3", String.valueOf(htcBean.getTestResult3()), elements);
        createElement("FinalResult", String.valueOf(htcBean.getFinalResult()), elements);

        /* Referrals and Risk Reduction */
        createElement("HivSymptoms", htcBean.getHivSymptoms(), elements);
        createElement("RiskReductionPlans", htcBean.getRiskReductionPlans(), elements);
        
        createElement("CondomsGiven", String.valueOf(htcBean.getCondomsGiven()), elements);

        Object[] elementsArray = new Object[elements.size()];
        elements.copyInto(elementsArray);
        addElements(elementsArray, root);

        document.addChild(Element.ELEMENT, root);

        String xmlFile = XmlUtil.writeToString(document);

        return xmlFile;
    }

    /*
     * Method creates a full element given a Name and a String Value and a Collection of Elements to be added to
     */
    private void createElement(String elementName, String stringValue, Vector elements) {
        if (stringValue == null) {
            stringValue = "";
        }
        Element element = new Element();
        element.setName(elementName);
        element.addChild(Element.TEXT, stringValue);
        elements.addElement(element);
    }


    /*
     * Method adds elements to root element from an array of elements
     */
    private Element addElements(Object[] elements, Element rootElement) {
        for (int i = 0; i < elements.length; i++) {
            StringBuffer stringBuffer = new StringBuffer("Adding element: ");
            stringBuffer.append(i);
            if(elements[i] == null){
                stringBuffer.append(" is null");
            }
        }

        for (int i = 0; i < elements.length; i++) {
            Object element = elements[i];
            rootElement.addChild(Element.ELEMENT, element);
        }
        return rootElement;
    }

    /*
     * Method creates a nested element for an integer array
     */
    private void createElement(String elementName, int[] flags, Vector elements) {
        Vector newElements = new Vector(0);
        Element element = new Element();
        element.setName(elementName);
        int counter = 0;
        for (int i = 0; i < flags.length; i++) {
            int flag = flags[i];
            counter++;
            Element childElement = new Element();
            StringBuffer flagName = new StringBuffer("Flag");
            flagName.append(String.valueOf(counter).trim());

            childElement.setName(flagName.toString());

            childElement.addChild(Element.TEXT, String.valueOf(flag));
            newElements.addElement(childElement);
        }

        Object[] newElementsArray = new Object[newElements.size()];
        newElements.copyInto(newElementsArray);

        addElements(newElementsArray, element);
        elements.addElement(element);
    }
}
