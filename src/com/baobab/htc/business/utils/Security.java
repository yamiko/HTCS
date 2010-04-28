/*
 *  Program Name: Security
 *        Author: Yamiko J. Msosa
 *  Date Written: 15th April 2010
 *       Purpose: This class provides methods for dealing with security/encryption issues
 */
package com.baobab.htc.business.utils;

import com.baobab.htc.business.utils.security.*;
import com.baobab.htc.data.files.*;
import java.util.*;

public class Security {

    public static void main(String args[]){
        Security security = new Security();
        String myString = "3333";
        System.out.println(Security.toMD5(myString));
    }

    public static String toMD5(String string) {
        //convert plaintext into bytes
        byte plain[] = string.trim().getBytes();

        // create MD5 object
        MD5 md5 = new MD5(plain);

        //get the resulting hashed byte
        byte[] result = md5.doFinal();

        //convert the hashed byte into hexadecimal character for display
        String hashResult = md5.toHex(result);

        return hashResult;
    }

    /*
     * Method returns a hexadecimal representation of a given string
     */
    public static String toHexString(String string) {
        char[] chars = string.trim().toCharArray();
        StringBuffer output = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            output.append(Integer.toHexString((int) chars[i]));
        }
        return output.toString();
    }

    public static boolean logUser(String userId, String password){
        boolean validUser = false;
        Hashtable users = new Hashtable(0);
        String usersString = FileReader.read(ConstantHelper.getInstance().getUsersFileLocation());
        String[] usersArray = StringUtil.split(usersString, "~");

        for(int i = 0; i < usersArray.length; i++){
            String[] userArray = StringUtil.split(usersArray[i], "$");
            users.put(userArray[0], userArray[1]);
        }

        String storedPassword = (String) users.get(userId);

        if(storedPassword != null){
            if(storedPassword.equals(toMD5(password))){
                validUser = true;
            }
        }

        return validUser;
    }

    public static boolean validateSite(String siteCode){
        boolean validSite = false;
        Hashtable sites = new Hashtable(0);
        String sitesString = FileReader.read(ConstantHelper.getInstance().getSitesFileLocation());
        String[] sitesArray = StringUtil.split(sitesString, "~");

        for(int i = 0; i < sitesArray.length; i++){
            sites.put(sitesArray[i], sitesArray[i]);
        }

        String storedSite = (String) sites.get(siteCode);

        if(storedSite != null){
           validSite = true;
        }

        return validSite;
    }
}
