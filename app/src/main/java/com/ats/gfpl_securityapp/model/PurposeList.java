package com.ats.gfpl_securityapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurposeList {

    @SerializedName("purposeId")
    @Expose
    private Integer purposeId;
    @SerializedName("purposeHeading")
    @Expose
    private String purposeHeading;
    @SerializedName("purposeType")
    @Expose
    private Integer purposeType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("empId")
    @Expose
    private String empId;
    @SerializedName("notificationL4")
    @Expose
    private String notificationL4;
    @SerializedName("passDuration")
    @Expose
    private String passDuration;
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
    private Object exVar1;
    @SerializedName("exVar2")
    @Expose
    private Object exVar2;
    @SerializedName("exVar3")
    @Expose
    private Object exVar3;
    @SerializedName("assignEmpName")
    @Expose
    private String assignEmpName;

    public Integer getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(Integer purposeId) {
        this.purposeId = purposeId;
    }

    public String getPurposeHeading() {
        return purposeHeading;
    }

    public void setPurposeHeading(String purposeHeading) {
        this.purposeHeading = purposeHeading;
    }

    public Integer getPurposeType() {
        return purposeType;
    }

    public void setPurposeType(Integer purposeType) {
        this.purposeType = purposeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getNotificationL4() {
        return notificationL4;
    }

    public void setNotificationL4(String notificationL4) {
        this.notificationL4 = notificationL4;
    }

    public String getPassDuration() {
        return passDuration;
    }

    public void setPassDuration(String passDuration) {
        this.passDuration = passDuration;
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

    public String getAssignEmpName() {
        return assignEmpName;
    }

    public void setAssignEmpName(String assignEmpName) {
        this.assignEmpName = assignEmpName;
    }

    @Override
    public String toString() {
        return "PurposeList{" +
                "purposeId=" + purposeId +
                ", purposeHeading='" + purposeHeading + '\'' +
                ", purposeType=" + purposeType +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                ", empId='" + empId + '\'' +
                ", notificationL4='" + notificationL4 + '\'' +
                ", passDuration='" + passDuration + '\'' +
                ", delStatus=" + delStatus +
                ", isUsed=" + isUsed +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exInt3=" + exInt3 +
                ", exVar1=" + exVar1 +
                ", exVar2=" + exVar2 +
                ", exVar3=" + exVar3 +
                ", assignEmpName='" + assignEmpName + '\'' +
                '}';
    }
}
