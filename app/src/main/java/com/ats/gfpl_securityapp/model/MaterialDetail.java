package com.ats.gfpl_securityapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MaterialDetail {
    @SerializedName("gatepassInwardId")
    @Expose
    private Integer gatepassInwardId;
    @SerializedName("inwardDate")
    @Expose
    private String inwardDate;
    @SerializedName("gatePassType")
    @Expose
    private Integer gatePassType;
    @SerializedName("gatePassSubType")
    @Expose
    private Integer gatePassSubType;
    @SerializedName("invoiceNumber")
    @Expose
    private String invoiceNumber;
    @SerializedName("partyName")
    @Expose
    private String partyName;
    @SerializedName("partyId")
    @Expose
    private Integer partyId;
    @SerializedName("securityId")
    @Expose
    private Integer securityId;
    @SerializedName("securityName")
    @Expose
    private String securityName;
    @SerializedName("personPhoto")
    @Expose
    private String personPhoto;
    @SerializedName("inwardPhoto")
    @Expose
    private String inwardPhoto;
    @SerializedName("inTime")
    @Expose
    private String inTime;
    @SerializedName("noOfNugs")
    @Expose
    private Integer noOfNugs;
    @SerializedName("itemType")
    @Expose
    private Integer itemType;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("toEmpId")
    @Expose
    private Integer toEmpId;
    @SerializedName("toDeptId")
    @Expose
    private Integer toDeptId;
    @SerializedName("toStatus")
    @Expose
    private Integer toStatus;
    @SerializedName("toEmpName")
    @Expose
    private String toEmpName;
    @SerializedName("toDeptName")
    @Expose
    private String toDeptName;
    @SerializedName("exInt1")
    @Expose
    private Integer exInt1;
    @SerializedName("exInt2")
    @Expose
    private Integer exInt2;
    @SerializedName("exInt3")
    @Expose
    private Integer exInt3;
    @SerializedName("exVar1")
    @Expose
    private Object exVar1;
    @SerializedName("exVar2")
    @Expose
    private Object exVar2;
    @SerializedName("exVar3")
    @Expose
    private Object exVar3;
    @SerializedName("docHandoverDetail")
    @Expose
    private List<DocHandoverDetail> docHandoverDetail = null;

    private Boolean isChecked;

    public Integer getGatepassInwardId() {
        return gatepassInwardId;
    }

    public void setGatepassInwardId(Integer gatepassInwardId) {
        this.gatepassInwardId = gatepassInwardId;
    }

    public String getInwardDate() {
        return inwardDate;
    }

    public void setInwardDate(String inwardDate) {
        this.inwardDate = inwardDate;
    }

    public Integer getGatePassType() {
        return gatePassType;
    }

    public void setGatePassType(Integer gatePassType) {
        this.gatePassType = gatePassType;
    }

    public Integer getGatePassSubType() {
        return gatePassSubType;
    }

    public void setGatePassSubType(Integer gatePassSubType) {
        this.gatePassSubType = gatePassSubType;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getSecurityId() {
        return securityId;
    }

    public void setSecurityId(Integer securityId) {
        this.securityId = securityId;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getPersonPhoto() {
        return personPhoto;
    }

    public void setPersonPhoto(String personPhoto) {
        this.personPhoto = personPhoto;
    }

    public String getInwardPhoto() {
        return inwardPhoto;
    }

    public void setInwardPhoto(String inwardPhoto) {
        this.inwardPhoto = inwardPhoto;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public Integer getNoOfNugs() {
        return noOfNugs;
    }

    public void setNoOfNugs(Integer noOfNugs) {
        this.noOfNugs = noOfNugs;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getToEmpId() {
        return toEmpId;
    }

    public void setToEmpId(Integer toEmpId) {
        this.toEmpId = toEmpId;
    }

    public Integer getToDeptId() {
        return toDeptId;
    }

    public void setToDeptId(Integer toDeptId) {
        this.toDeptId = toDeptId;
    }

    public Integer getToStatus() {
        return toStatus;
    }

    public void setToStatus(Integer toStatus) {
        this.toStatus = toStatus;
    }

    public String getToEmpName() {
        return toEmpName;
    }

    public void setToEmpName(String toEmpName) {
        this.toEmpName = toEmpName;
    }

    public String getToDeptName() {
        return toDeptName;
    }

    public void setToDeptName(String toDeptName) {
        this.toDeptName = toDeptName;
    }

    public Integer getExInt1() {
        return exInt1;
    }

    public void setExInt1(Integer exInt1) {
        this.exInt1 = exInt1;
    }

    public Integer getExInt2() {
        return exInt2;
    }

    public void setExInt2(Integer exInt2) {
        this.exInt2 = exInt2;
    }

    public Integer getExInt3() {
        return exInt3;
    }

    public void setExInt3(Integer exInt3) {
        this.exInt3 = exInt3;
    }

    public Object getExVar1() {
        return exVar1;
    }

    public void setExVar1(Object exVar1) {
        this.exVar1 = exVar1;
    }

    public Object getExVar2() {
        return exVar2;
    }

    public void setExVar2(Object exVar2) {
        this.exVar2 = exVar2;
    }

    public Object getExVar3() {
        return exVar3;
    }

    public void setExVar3(Object exVar3) {
        this.exVar3 = exVar3;
    }

    public List<DocHandoverDetail> getDocHandoverDetail() {
        return docHandoverDetail;
    }

    public void setDocHandoverDetail(List<DocHandoverDetail> docHandoverDetail) {
        this.docHandoverDetail = docHandoverDetail;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "MaterialDetail{" +
                "gatepassInwardId=" + gatepassInwardId +
                ", inwardDate='" + inwardDate + '\'' +
                ", gatePassType=" + gatePassType +
                ", gatePassSubType=" + gatePassSubType +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", partyName='" + partyName + '\'' +
                ", partyId=" + partyId +
                ", securityId=" + securityId +
                ", securityName='" + securityName + '\'' +
                ", personPhoto='" + personPhoto + '\'' +
                ", inwardPhoto='" + inwardPhoto + '\'' +
                ", inTime='" + inTime + '\'' +
                ", noOfNugs=" + noOfNugs +
                ", itemType=" + itemType +
                ", delStatus=" + delStatus +
                ", status=" + status +
                ", toEmpId=" + toEmpId +
                ", toDeptId=" + toDeptId +
                ", toStatus=" + toStatus +
                ", toEmpName='" + toEmpName + '\'' +
                ", toDeptName='" + toDeptName + '\'' +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exInt3=" + exInt3 +
                ", exVar1=" + exVar1 +
                ", exVar2=" + exVar2 +
                ", exVar3=" + exVar3 +
                ", docHandoverDetail=" + docHandoverDetail +
                ", isChecked=" + isChecked +
                '}';
    }
}
