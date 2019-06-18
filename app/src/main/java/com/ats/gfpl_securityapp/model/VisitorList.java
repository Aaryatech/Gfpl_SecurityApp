package com.ats.gfpl_securityapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VisitorList {

    @SerializedName("gatepassVisitorId")
    @Expose
    private Integer gatepassVisitorId;
    @SerializedName("visitDateIn")
    @Expose
    private String visitDateIn;
    @SerializedName("securityIdIn")
    @Expose
    private Integer securityIdIn;
    @SerializedName("personName")
    @Expose
    private String personName;
    @SerializedName("personCompany")
    @Expose
    private String personCompany;
    @SerializedName("personPhoto")
    @Expose
    private String personPhoto;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("idProof")
    @Expose
    private String idProof;
    @SerializedName("idProof1")
    @Expose
    private String idProof1;
    @SerializedName("otherPhoto")
    @Expose
    private String otherPhoto;
    @SerializedName("purposeId")
    @Expose
    private Integer purposeId;
    @SerializedName("visitPurposeText")
    @Expose
    private String visitPurposeText;
    @SerializedName("purposeRemark")
    @Expose
    private String purposeRemark;
    @SerializedName("empIds")
    @Expose
    private String empIds;
    @SerializedName("empName")
    @Expose
    private String empName;
    @SerializedName("gateId")
    @Expose
    private Integer gateId;
    @SerializedName("gatePasstype")
    @Expose
    private Integer gatePasstype;
    @SerializedName("visitStatus")
    @Expose
    private Integer visitStatus;
    @SerializedName("visitType")
    @Expose
    private Integer visitType;
    @SerializedName("inTime")
    @Expose
    private String inTime;
    @SerializedName("visitCardId")
    @Expose
    private Integer visitCardId;
    @SerializedName("visitCardNo")
    @Expose
    private String visitCardNo;
    @SerializedName("takeMobile")
    @Expose
    private Integer takeMobile;
    @SerializedName("meetingDiscussion")
    @Expose
    private String meetingDiscussion;
    @SerializedName("uploadPhoto")
    @Expose
    private String uploadPhoto;
    @SerializedName("visitOutTime")
    @Expose
    private String visitOutTime;
    @SerializedName("totalTimeDifference")
    @Expose
    private float totalTimeDifference;
    @SerializedName("securityIdOut")
    @Expose
    private Integer securityIdOut;
    @SerializedName("visitDateOut")
    @Expose
    private String visitDateOut;
    @SerializedName("userSignImage")
    @Expose
    private String userSignImage;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
    @SerializedName("isUsed")
    @Expose
    private Integer isUsed;
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
    private String exVar1;
    @SerializedName("exVar2")
    @Expose
    private String exVar2;
    @SerializedName("exVar3")
    @Expose
    private String exVar3;
    @SerializedName("securityInName")
    @Expose
    private Object securityInName;
    @SerializedName("securityOutName")
    @Expose
    private Object securityOutName;
    @SerializedName("purposeHeading")
    @Expose
    private String purposeHeading;
    @SerializedName("gateName")
    @Expose
    private String gateName;
    @SerializedName("assignEmpName")
    @Expose
    private String assignEmpName;

    public VisitorList(Integer gatepassVisitorId, String visitDateIn, Integer securityIdIn, String personName, String personCompany, String personPhoto, String mobileNo, String idProof, String idProof1, String otherPhoto, Integer purposeId, String visitPurposeText, String purposeRemark, String empIds, String empName, Integer gateId, Integer gatePasstype, Integer visitStatus, Integer visitType, String inTime, Integer visitCardId, String visitCardNo, Integer takeMobile, String meetingDiscussion, String uploadPhoto, String visitOutTime, Integer totalTimeDifference, Integer securityIdOut, String visitDateOut, String userSignImage, Integer delStatus, Integer isUsed, Integer exInt1, Integer exInt2, Integer exInt3, String exVar1, String exVar2, String exVar3, Object securityInName, Object securityOutName, String purposeHeading, String gateName, String assignEmpName) {
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
        this.securityInName = securityInName;
        this.securityOutName = securityOutName;
        this.purposeHeading = purposeHeading;
        this.gateName = gateName;
        this.assignEmpName = assignEmpName;
    }

    public Integer getGatepassVisitorId() {
        return gatepassVisitorId;
    }

    public void setGatepassVisitorId(Integer gatepassVisitorId) {
        this.gatepassVisitorId = gatepassVisitorId;
    }

    public String getVisitDateIn() {
        return visitDateIn;
    }

    public void setVisitDateIn(String visitDateIn) {
        this.visitDateIn = visitDateIn;
    }

    public Integer getSecurityIdIn() {
        return securityIdIn;
    }

    public void setSecurityIdIn(Integer securityIdIn) {
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

    public Integer getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(Integer purposeId) {
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

    public Integer getGateId() {
        return gateId;
    }

    public void setGateId(Integer gateId) {
        this.gateId = gateId;
    }

    public Integer getGatePasstype() {
        return gatePasstype;
    }

    public void setGatePasstype(Integer gatePasstype) {
        this.gatePasstype = gatePasstype;
    }

    public Integer getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(Integer visitStatus) {
        this.visitStatus = visitStatus;
    }

    public Integer getVisitType() {
        return visitType;
    }

    public void setVisitType(Integer visitType) {
        this.visitType = visitType;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public Integer getVisitCardId() {
        return visitCardId;
    }

    public void setVisitCardId(Integer visitCardId) {
        this.visitCardId = visitCardId;
    }

    public String getVisitCardNo() {
        return visitCardNo;
    }

    public void setVisitCardNo(String visitCardNo) {
        this.visitCardNo = visitCardNo;
    }

    public Integer getTakeMobile() {
        return takeMobile;
    }

    public void setTakeMobile(Integer takeMobile) {
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

    public Integer getSecurityIdOut() {
        return securityIdOut;
    }

    public void setSecurityIdOut(Integer securityIdOut) {
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

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
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

    public Object getSecurityInName() {
        return securityInName;
    }

    public void setSecurityInName(Object securityInName) {
        this.securityInName = securityInName;
    }

    public Object getSecurityOutName() {
        return securityOutName;
    }

    public void setSecurityOutName(Object securityOutName) {
        this.securityOutName = securityOutName;
    }

    public String getPurposeHeading() {
        return purposeHeading;
    }

    public void setPurposeHeading(String purposeHeading) {
        this.purposeHeading = purposeHeading;
    }

    public String getGateName() {
        return gateName;
    }

    public void setGateName(String gateName) {
        this.gateName = gateName;
    }

    public String getAssignEmpName() {
        return assignEmpName;
    }

    public void setAssignEmpName(String assignEmpName) {
        this.assignEmpName = assignEmpName;
    }

    @Override
    public String toString() {
        return "VisitorList{" +
                "gatepassVisitorId=" + gatepassVisitorId +
                ", visitDateIn='" + visitDateIn + '\'' +
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
                ", visitDateOut='" + visitDateOut + '\'' +
                ", userSignImage='" + userSignImage + '\'' +
                ", delStatus=" + delStatus +
                ", isUsed=" + isUsed +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exInt3=" + exInt3 +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                ", exVar3='" + exVar3 + '\'' +
                ", securityInName=" + securityInName +
                ", securityOutName=" + securityOutName +
                ", purposeHeading='" + purposeHeading + '\'' +
                ", gateName='" + gateName + '\'' +
                ", assignEmpName='" + assignEmpName + '\'' +
                '}';
    }
}
