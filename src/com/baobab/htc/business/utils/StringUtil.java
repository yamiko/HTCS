/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baobab.htc.business.utils;

import java.util.*;

public class StringUtil {

    public static String intToString(int intValue) {
        String returnString = String.valueOf(intValue);
        return returnString;
    }

    public static String[] split(String str, String c) {
        String r[] = null;
        if (str != null) {
            int lenght = str.length();
            int first = 0;
            Vector lista = new Vector();
            if (str.indexOf(c) != -1) {
                for (int i = 0; i < lenght; i++) {
                    if (i + c.length() <= lenght) {
                        if (str.substring(i, i + c.length()).equals(c)) {
                            lista.addElement(str.substring(first, i));
                            first = i + c.length();
                        }
                    }
                }
                if (!str.endsWith(c)) {
                    lista.addElement(str.substring(first, lenght));
                }
            } else {
                lista.addElement(str);
            }
            r = new String[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                r[i] = lista.elementAt(i).toString();
            }
        }
        return r;
    }
}
