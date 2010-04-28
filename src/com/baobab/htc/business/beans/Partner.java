package com.baobab.htc.business.beans;

/**
 * Created by IntelliJ IDEA.
 * User: yamiko
 * Date: 08-Apr-2010
 * Time: 15:04:11
 * To change this template use File | Settings | File Templates.
 */
public class Partner {
    /*
     * Getters and Setters for the class
     */

    public int getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(int partnerType) {
        this.partnerType = partnerType;
    }

    public int getPartnerStatus() {
        return partnerStatus;
    }

    public void setPartnerStatus(int partnerStatus) {
        this.partnerStatus = partnerStatus;
    }

    public int getPartnerHivStatus() {
        return partnerHivStatus;
    }

    public void setPartnerHivStatus(int partnerHivStatus) {
        this.partnerHivStatus = partnerHivStatus;
    }

    public int getHivTestInLast3Months() {
        return hivTestInLast3Months;
    }

    public void setHivTestInLast3Months(int hivTestInLast3Months) {
        this.hivTestInLast3Months = hivTestInLast3Months;
    }

    public int getCondomUsedLastSex() {
        return condomUsedLastSex;
    }

    public void setCondomUsedLastSex(int condomUsedLastSex) {
        this.condomUsedLastSex = condomUsedLastSex;
    }

    /*
    * Properties for the class
    */
    private int partnerType = -1;
    private int partnerStatus = -1;
    private int partnerHivStatus = -1;
    private int hivTestInLast3Months = -1;
    private int condomUsedLastSex = -1;
}
