/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.baobab.htc.data.factories;

import com.baobab.htc.business.beans.*;
import org.kxml2.io.*;
import org.kxml2.kdom.*;
import org.xmlpull.v1.*;


/**
 *
 * @author yamiko
 */
public class XmlFactory {

    public static void main(String args[]){
        XmlFactory xmlFactory = new XmlFactory();
        HtcForm htcForm = new HtcForm();
        String tempString = xmlFactory.htcBeanToXml(htcForm);
    }

    public String htcBeanToXml(HtcForm htcForm){
        Document document = new Document();
        Element root = new Element();
        root.setName("HtcForm");
        
        Element sessionType = new Element();
        sessionType.setName("SessionType");
        
        root.addChild(Element.ELEMENT, sessionType);

        document.addChild(Element.ELEMENT, root);

        KXmlSerializer writer = new KXmlSerializer();
        document.write(writer);
        System.out.println(document.toString());
        return root.toString();
    }

}
