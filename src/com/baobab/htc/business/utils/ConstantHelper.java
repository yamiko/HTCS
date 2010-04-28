/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.baobab.htc.business.utils;

import com.baobab.htc.data.files.*;
import java.util.*;

public class ConstantHelper {

    public static final int COMES_WITH_PARTNER = 1;
    public static final int ALERT_TIMEOUT = 4000;

    public static final int SINGLE_FORM = 0;
    public static final int FIRST_FORM = 1;
    public static final int SECOND_FORM = 2;

    public static final String XML_EXTENSION = "xml";

    public static final int ERROR = -1;

    public static final int LOGIN_FORM = 0;
    public static final int TRANSMISSION_FORM = 1;
    public static final int SESSION_TYPE_FORM = 2;
    public static final int SESSION_FORM = 3;

    //public static final String ROOT = "file:///SDCard/MobileFiles/";
    private static ConstantHelper constantHelper = new ConstantHelper();
    public final String PROPERTIES_FILE = "file:///root2/BHTServices/HTC.properties";
    private Hashtable properties = new Hashtable(0);

    private ConstantHelper(){
        String propertiesString = FileReader.read(PROPERTIES_FILE);
        String[] propertiesArray = StringUtil.split(propertiesString, "~");
 
        for(int i = 0; i < propertiesArray.length; i++){
            String[] propertyArray = StringUtil.split(propertiesArray[i], "$");
            properties.put(propertyArray[0], propertyArray[1]);
        }

        this.root = (String) properties.get("Root");
        this.fileSeparator = (String) properties.get("FileSeparator");
        this.fileWildCardPrefix = (String) properties.get("FileWildCardPrefix");
        this.usersFileLocation = (String) properties.get("UsersFileLocation");
        this.sitesFileLocation = (String) properties.get("SitesFileLocation");
        this.htcServiceUrl = (String) properties.get("HtcServiceUrl");
    }

    public static ConstantHelper getInstance(){
        return constantHelper;
    }



    //public static final String ROOT = "file:///root2/MobileFiles/";
    //public static final String FILE_SEPARATOR = "/";
    //public static final String FILE_WILDCARD_PREFIX = "*.";

    private String root;
    private String fileSeparator;
    private String fileWildCardPrefix;
    private String usersFileLocation;
    private String sitesFileLocation;
    private String htcServiceUrl;

    public String getRoot() {
        return root;
    }

    public String getFileSeparator() {
        return fileSeparator;
    }

    public String getFileWildCardPrefix() {
        return fileWildCardPrefix;
    }

    public String getUsersFileLocation() {
        return usersFileLocation;
    }

    public String getSitesFileLocation() {
        return sitesFileLocation;
    }

    public String getHtcServiceUrl() {
        return htcServiceUrl;
    }
}
