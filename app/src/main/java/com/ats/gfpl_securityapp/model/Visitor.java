package com.ats.gfpl_securityapp.model;

public class Visitor {
    private int gatepassVisitorId;
    private String visitDateIn;
    private int securityIdIn;
    private String personName;
    private String personCompany;
    private String personPhoto;
    private String mobileNo;
    private String idProof;
    private String idProof1;
    private String otherPhoto;
    private int purposeId;
    private String visitPurposeText;
    private String purposeRemark;
    private String empIds;
    private String empName;
    private int gateId;
    private int gatePasstype;
    private int visitStatus ;
    private int visitType;
    private String inTime;
    private int visitCardId;
    private String visitCardNo;
    private int takeMobile;
    private String meetingDiscussion;
    private String uploadPhoto;
    private String visitOutTime;
    private float totalTimeDifference;
    private int securityIdOut;
    private String visitDateOut;
    private String userSignImage;
    private int delStatus;
    private int isUsed;

    private int exInt1;
    private int exInt2;
    private int exInt3;
    private String exVar1;
    private String exVar2;
    private String exVar3;

    public Visitor(int gatepassVisitorId, String visitDateIn, int securityIdIn, String personName, String personCompany, String personPhoto, String mobileNo, String idProof, String idProof1, String otherPhoto, int purposeId, String visitPurposeText, String purposeRemark, String empIds, String empName, int gateId, int gatePasstype, int visitStatus, int visitType, String inTime, int visitCardId, String visitCardNo, int takeMobile, String meetingDiscussion, String uploadPhoto, String visitOutTime, float totalTimeDifference, int securityIdOut, String visitDateOut, String userSignImage, int delStatus, int isUsed, int exInt1, int exInt2, int exInt3, String exVar1, String exVar2, String exVar3) {
        this.gatepassVisitorId = gatepassVisitorId;
        this.visitDateIn = visitDateIn;
        this.securityIdIn = securityIdIn;
        this.personName = personName;
        this.personCompany = personCompany;
        this.personPhoto = personPhoto;
        this.mobileNo = mobileNo;
        this.idProof = idProof;
        this.idProof1 = idProof1;
        this.otherPhoto = otherPhoto;
        this.purposeId = purposeId;
        this.visitPurposeText = visitPurposeText;
        this.purposeRemark = purposeRemark;
        this.empIds = empIds;
        this.empName = empName;
        this.gateId = gateId;
        this.gatePasstype = gatePasstype;
        this.visitStatus = visitStatus;
        this.visitType = visitType;
        this.inTime = inTime;
        this.visitCardId = visitCardId;
        this.visitCardNo = visitCardNo;
        this.takeMobile = takeMobile;
        this.meetingDiscussion = meetingDiscussion;
        this.uploadPhoto = uploadPhoto;
        this.visitOutTime = visitOutTime;
        this.totalTimeDifference = totalTimeDifference;
        this.securityIdOut = securityIdOut;
        this.visitDateOut = visitDateOut;
        this.userSignImage = userSignImage;
        this.delStatus = delStatus;
        this.isUsed = isUsed;
        this.exInt1 = exInt1;
        this.exInt2 = exInt2;
        this.exInt3 = exInt3;
        this.exVar1 = exVar1;
        this.exVar2 = exVar2;
        this.exVar3 = exVar3;
    }

    public int getGatepassVisitorId() {
        return gatepassVisitorId;
    }

    public void setGatepassVisitorId(int gatepassVisitorId) {
        this.gatepassVisitorId = gatepassVisitorId;
    }

    public String getVisitDateIn() {
        return visitDateIn;
    }

    public void setVisitDateIn(String visitDateIn) {
        this.visitDateIn = visitDateIn;
    }

    public int getSecurityIdIn() {
        return securityIdIn;
    }

    public void setSecurityIdIn(int securityIdIn) {
        this.securityIdIn = securityIdIn;
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

    public String getPersonPhoto() {
        return personPhoto;
    }

    public void setPersonPhoto(String personPhoto) {
        this.personPhoto = personPhoto;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    public int getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(int purposeId) {
        this.purposeId = purposeId;
    }

    public String getVisitPurposeText() {
        return visitPurposeText;
    }

    public void setVisitPurposeText(String visitPurposeText) {
        this.visitPurposeText = visitPurposeText;
    }

    public String getPurposeRemark() {
        return purposeRemark;
    }

    public void setPurposeRemark(String purposeRemark) {
        this.purposeRemark = purposeRemark;
    }

    public String getEmpIds() {
        return empIds;
    }

    public void setEmpIds(String empIds) {
        this.empIds = empIds;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public int getGateId() {
        return gateId;
    }

    public void setGateId(int gateId) {
        this.gateId = gateId;
    }

    public int getGatePasstype() {
        return gatePasstype;
    }

    public void setGatePasstype(int gatePasstype) {
        this.gatePasstype = gatePasstype;
    }

    public int getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(int visitStatus) {
        this.visitStatus = visitStatus;
    }

    public int getVisitType() {
        return visitType;
    }

    public void setVisitType(int visitType) {
        this.visitType = visitType;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public int getVisitCardId() {
        return visitCardId;
    }

    public void setVisitCardId(int visitCardId) {
        this.visitCardId = visitCardId;
    }

    public String getVisitCardNo() {
        return visitCardNo;
    }

    public void setVisitCardNo(String visitCardNo) {
        this.visitCardNo = visitCardNo;
    }

    public int getTakeMobile() {
        return takeMobile;
    }

    public void setTakeMobile(int takeMobile) {
        this.takeMobile = takeMobile;
    }

    public String getMeetingDiscussion() {
        return meetingDiscussion;
    }

    public void setMeetingDiscussion(String meetingDiscussion) {
        this.meetingDiscussion = meetingDiscussion;
    }

    public String getUploadPhoto() {
        return uploadPhoto;
    }

    public void setUploadPhoto(String uploadPhoto) {
        this.uploadPhoto = uploadPhoto;
    }

    public String getVisitOutTime() {
        return visitOutTime;
    }

    public void setVisitOutTime(String visitOutTime) {
        this.visitOutTime = visitOutTime;
    }

    public float getTotalTimeDifference() {
        return totalTimeDifference;
    }

    public void setTotalTimeDifference(float totalTimeDifference) {
        this.totalTimeDifference = totalTimeDifference;
    }

    public int getSecurityIdOut() {
        return securityIdOut;
    }

    public void setSecurityIdOut(int securityIdOut) {
        this.securityIdOut = securityIdOut;
    }

    public String getVisitDateOut() {
        return visitDateOut;
    }

    public void setVisitDateOut(String visitDateOut) {
        this.visitDateOut = visitDateOut;
    }

    public String getUserSignImage() {
        return userSignImage;
    }

    public void setUserSignImage(String userSignImage) {
        this.userSignImage = userSignImage;
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
        return "Visitor{" +
                "gatepassVisitorId=" + gatepassVisitorId +
                ", visitDateIn=" + visitDateIn +
                ", securityIdIn=" + securityIdIn +
                ", personName='" + personName + '\'' +
                ", personCompany='" + personCompany + '\'' +
                ", personPhoto='" + personPhoto + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", idProof='" + idProof + '\'' +
                ", idProof1='" + idProof1 + '\'' +
                ", otherPhoto='" + otherPhoto + '\'' +
                ", purposeId=" + purposeId +
                ", visitPurposeText='" + visitPurposeText + '\'' +
                ", purposeRemark='" + purposeRemark + '\'' +
                ", empIds='" + empIds + '\'' +
                ", empName='" + empName + '\'' +
                ", gateId=" + gateId +
                ", gatePasstype=" + gatePasstype +
                ", visitStatus=" + visitStatus +
                ", visitType=" + visitType +
                ", inTime='" + inTime + '\'' +
                ", visitCardId=" + visitCardId +
                ", visitCardNo='" + visitCardNo + '\'' +
                ", takeMobile=" + takeMobile +
                ", meetingDiscussion='" + meetingDiscussion + '\'' +
                ", uploadPhoto='" + uploadPhoto + '\'' +
                ", visitOutTime='" + visitOutTime + '\'' +
                ", totalTimeDifference=" + totalTimeDifference +
                ", securityIdOut=" + securityIdOut +
                ", visitDateOut=" + visitDateOut +
                ", userSignImage='" + userSignImage + '\'' +
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
