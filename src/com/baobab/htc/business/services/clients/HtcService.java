package com.baobab.htc.business.services.clients;
import javax.xml.namespace.QName;

public interface HtcService extends java.rmi.Remote {

    /**
     *
     */
    public Integer submitXmlDocument(String XmlDocument) throws java.rmi.RemoteException;

}
