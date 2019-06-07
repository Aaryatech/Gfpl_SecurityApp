package com.ats.gfpl_securityapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Party {

    @SerializedName("vendorId")
    @Expose
    private Integer vendorId;
    @SerializedName("vendorCode")
    @Expose
    private String vendorCode;
    @SerializedName("vendorName")
    @Expose
    private String vendorName;
    @SerializedName("vendorAdd1")
    @Expose
    private String vendorAdd1;
    @SerializedName("vendorAdd2")
    @Expose
    private String vendorAdd2;
    @SerializedName("vendorAdd3")
    @Expose
    private String vendorAdd3;
    @SerializedName("vendorAdd4")
    @Expose
    private String vendorAdd4;
    @SerializedName("vendorStateId")
    @Expose
    private Integer vendorStateId;
    @SerializedName("vendorState")
    @Expose
    private String vendorState;
    @SerializedName("vendorCity")
    @Expose
    private String vendorCity;
    @SerializedName("vendorContactPerson")
    @Expose
    private String vendorContactPerson;
    @SerializedName("vendorMobile")
    @Expose
    private String vendorMobile;
    @SerializedName("vendorPhone")
    @Expose
    private String vendorPhone;
    @SerializedName("vendorEmail")
    @Expose
    private String vendorEmail;
    @SerializedName("vendorGstNo")
    @Expose
    private String vendorGstNo;
    @SerializedName("vendorApprvBy")
    @Expose
    private String vendorApprvBy;
    @SerializedName("vendorItem")
    @Expose
    private String vendorItem;
    @SerializedName("vendorDate")
    @Expose
    private String vendorDate;
    @SerializedName("vendorType")
    @Expose
    private Integer vendorType;
    @SerializedName("isUsed")
    @Expose
    private Integer isUsed;
    @SerializedName("createdIn")
    @Expose
    private Integer createdIn;
    @SerializedName("deletedIn")
    @Expose
    private Integer deletedIn;

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorAdd1() {
        return vendorAdd1;
    }

    public void setVendorAdd1(String vendorAdd1) {
        this.vendorAdd1 = vendorAdd1;
    }

    public String getVendorAdd2() {
        return vendorAdd2;
    }

    public void setVendorAdd2(String vendorAdd2) {
        this.vendorAdd2 = vendorAdd2;
    }

    public String getVendorAdd3() {
        return vendorAdd3;
    }

    public void setVendorAdd3(String vendorAdd3) {
        this.vendorAdd3 = vendorAdd3;
    }

    public String getVendorAdd4() {
        return vendorAdd4;
    }

    public void setVendorAdd4(String vendorAdd4) {
        this.vendorAdd4 = vendorAdd4;
    }

    public Integer getVendorStateId() {
        return vendorStateId;
    }

    public void setVendorStateId(Integer vendorStateId) {
        this.vendorStateId = vendorStateId;
    }

    public String getVendorState() {
        return vendorState;
    }

    public void setVendorState(String vendorState) {
        this.vendorState = vendorState;
    }

    public String getVendorCity() {
        return vendorCity;
    }

    public void setVendorCity(String vendorCity) {
        this.vendorCity = vendorCity;
    }

    public String getVendorContactPerson() {
        return vendorContactPerson;
    }

    public void setVendorContactPerson(String vendorContactPerson) {
        this.vendorContactPerson = vendorContactPerson;
    }

    public String getVendorMobile() {
        return vendorMobile;
    }

    public void setVendorMobile(String vendorMobile) {
        this.vendorMobile = vendorMobile;
    }

    public String getVendorPhone() {
        return vendorPhone;
    }

    public void setVendorPhone(String vendorPhone) {
        this.vendorPhone = vendorPhone;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public String getVendorGstNo() {
        return vendorGstNo;
    }

    public void setVendorGstNo(String vendorGstNo) {
        this.vendorGstNo = vendorGstNo;
    }

    public String getVendorApprvBy() {
        return vendorApprvBy;
    }

    public void setVendorApprvBy(String vendorApprvBy) {
        this.vendorApprvBy = vendorApprvBy;
    }

    public String getVendorItem() {
        return vendorItem;
    }

    public void setVendorItem(String vendorItem) {
        this.vendorItem = vendorItem;
    }

    public String getVendorDate() {
        return vendorDate;
    }

    public void setVendorDate(String vendorDate) {
        this.vendorDate = vendorDate;
    }

    public Integer getVendorType() {
        return vendorType;
    }

    public void setVendorType(Integer vendorType) {
        this.vendorType = vendorType;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public Integer getCreatedIn() {
        return createdIn;
    }

    public void setCreatedIn(Integer createdIn) {
        this.createdIn = createdIn;
    }

    public Integer getDeletedIn() {
        return deletedIn;
    }

    public void setDeletedIn(Integer deletedIn) {
        this.deletedIn = deletedIn;
    }

    @Override
    public String toString() {
        return "Party{" +
                "vendorId=" + vendorId +
                ", vendorCode='" + vendorCode + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", vendorAdd1='" + vendorAdd1 + '\'' +
                ", vendorAdd2='" + vendorAdd2 + '\'' +
                ", vendorAdd3='" + vendorAdd3 + '\'' +
                ", vendorAdd4='" + vendorAdd4 + '\'' +
                ", vendorStateId=" + vendorStateId +
                ", vendorState='" + vendorState + '\'' +
                ", vendorCity='" + vendorCity + '\'' +
                ", vendorContactPerson='" + vendorContactPerson + '\'' +
                ", vendorMobile='" + vendorMobile + '\'' +
                ", vendorPhone='" + vendorPhone + '\'' +
                ", vendorEmail='" + vendorEmail + '\'' +
                ", vendorGstNo='" + vendorGstNo + '\'' +
                ", vendorApprvBy='" + vendorApprvBy + '\'' +
                ", vendorItem='" + vendorItem + '\'' +
                ", vendorDate='" + vendorDate + '\'' +
                ", vendorType=" + vendorType +
                ", isUsed=" + isUsed +
                ", createdIn=" + createdIn +
                ", deletedIn=" + deletedIn +
                '}';
    }
}
