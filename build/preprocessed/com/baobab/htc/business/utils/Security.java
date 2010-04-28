/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baobab.htc.business.utils;

/**
 *
 * @author yamiko
 */
public class Security {

    /*
     * Method returns a hexadecimal representation of a given string
     */
    public String toHexString(String string) {
        char[] chars = string.trim().toCharArray();
        StringBuffer output = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            output.append(Integer.toHexString((int) chars[i]));
        }
        return output.toString();
    }
}
