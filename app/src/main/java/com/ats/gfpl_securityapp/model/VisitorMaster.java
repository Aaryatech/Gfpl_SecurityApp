package com.ats.gfpl_securityapp.model;

public class VisitorMaster {

    private int visitorId;
    private String personName;
    private String personCompany;
    private String mobileNo;
    private int noOfPerson;
    private String purposeRemark;
    private String visitPurposeText;
    private int purposeId;
    private String personToMeet;
    private int prsonId;
    private String idProof;
    private String idProof1;
    private String otherPhoto;
    private int gatePasstype;
    private String takeMobile;
    private int delStatus;
    private int isUsed;

    private int exInt1;
    private int exInt2;
    private int exInt3;
    private String exVar1;
    private String exVar2;
    private String exVar3;

    public VisitorMaster(int visitorId, String personName, String personCompany, String mobileNo, int noOfPerson, String purposeRemark, String visitPurposeText, int purposeId, String personToMeet, int prsonId, String idProof, String idProof1, String otherPhoto, int gatePasstype, String takeMobile, int delStatus, int isUsed, int exInt1, int exInt2, int exInt3, String exVar1, String exVar2, String exVar3) {
        this.visitorId = visitorId;
        this.personName = personName;
        this.personCompany = personCompany;
        this.mobileNo = mobileNo;
        this.noOfPerson = noOfPerson;
        this.purposeRemark = purposeRemark;
        this.visitPurposeText = visitPurposeText;
        this.purposeId = purposeId;
        this.personToMeet = personToMeet;
        this.prsonId = prsonId;
        this.idProof = idProof;
        this.idProof1 = idProof1;
        this.otherPhoto = otherPhoto;
        this.gatePasstype = gatePasstype;
        this.takeMobile = takeMobile;
        this.delStatus = delStatus;
        this.isUsed = isUsed;
        this.exInt1 = exInt1;
        this.exInt2 = exInt2;
        this.exInt3 = exInt3;
        this.exVar1 = exVar1;
        this.exVar2 = exVar2;
        this.exVar3 = exVar3;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonCompany() {
        return personCompany;
    }

    public void setPersonCompany(String personCompany) {
        this.personCompany = personCompany;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getNoOfPerson() {
        return noOfPerson;
    }

    public void setNoOfPerson(int noOfPerson) {
        this.noOfPerson = noOfPerson;
    }

    public String getPurposeRemark() {
        return purposeRemark;
    }

    public void setPurposeRemark(String purposeRemark) {
        this.purposeRemark = purposeRemark;
    }

    public String getVisitPurposeText() {
        return visitPurposeText;
    }

    public void setVisitPurposeText(String visitPurposeText) {
        this.visitPurposeText = visitPurposeText;
    }

    public int getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(int purposeId) {
        this.purposeId = purposeId;
    }

    public String getPersonToMeet() {
        return personToMeet;
    }

    public void setPersonToMeet(String personToMeet) {
        this.personToMeet = personToMeet;
    }

    public int getPrsonId() {
        return prsonId;
    }

    public void setPrsonId(int prsonId) {
        this.prsonId = prsonId;
    }

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public String getIdProof1() {
        return idProof1;
    }

    public void setIdProof1(String idProof1) {
        this.idProof1 = idProof1;
    }

    public String getOtherPhoto() {
        return otherPhoto;
    }

    public void setOtherPhoto(String otherPhoto) {
        this.otherPhoto = otherPhoto;
    }

    public int getGatePasstype() {
        return gatePasstype;
    }

    public void setGatePasstype(int gatePasstype) {
        this.gatePasstype = gatePasstype;
    }

    public String getTakeMobile() {
        return takeMobile;
    }

    public void setTakeMobile(String takeMobile) {
        this.takeMobile = takeMobile;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public int getExInt1() {
        return exInt1;
    }

    public void setExInt1(int exInt1) {
        this.exInt1 = exInt1;
    }

    public int getExInt2() {
        return exInt2;
    }

    public void setExInt2(int exInt2) {
        this.exInt2 = exInt2;
    }

    public int getExInt3() {
        return exInt3;
    }

    public void setExInt3(int exInt3) {
        this.exInt3 = exInt3;
    }

    public String getExVar1() {
        return exVar1;
    }

    public void setExVar1(String exVar1) {
        this.exVar1 = exVar1;
    }

    public String getExVar2() {
        return exVar2;
    }

    public void setExVar2(String exVar2) {
        this.exVar2 = exVar2;
    }

    public String getExVar3() {
        return exVar3;
    }

    public void setExVar3(String exVar3) {
        this.exVar3 = exVar3;
    }

    @Override
    public String toString() {
        return "VisitorMaster{" +
                "visitorId=" + visitorId +
                ", personName='" + personName + '\'' +
                ", personCompany='" + personCompany + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", noOfPerson=" + noOfPerson +
                ", purposeRemark='" + purposeRemark + '\'' +
                ", visitPurposeText='" + visitPurposeText + '\'' +
                ", purposeId=" + purposeId +
                ", personToMeet='" + personToMeet + '\'' +
                ", prsonId=" + prsonId +
                ", idProof='" + idProof + '\'' +
                ", idProof1='" + idProof1 + '\'' +
                ", otherPhoto='" + otherPhoto + '\'' +
                ", gatePasstype=" + gatePasstype +
                ", takeMobile='" + takeMobile + '\'' +
                ", delStatus=" + delStatus +
                ", isUsed=" + isUsed +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exInt3=" + exInt3 +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                ", exVar3='" + exVar3 + '\'' +
                '}';
    }
}
