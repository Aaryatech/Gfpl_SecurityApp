package com.ats.gfpl_securityapp.model;

public class Purpose {

    private int purposeId;
    private String purposeHeading;
    private int purposeType;//2 Purpose Types
    private String description;
    private String remark;
    private String empId;
    private String notificationL4;
    private String passDuration;
    private int delStatus;
    private int isUsed;

    private int exInt1;
    private int exInt2;
    private int exInt3;
    private String exVar1;
    private String exVar2;
    private String exVar3;

    public Purpose(int purposeId, String purposeHeading, int purposeType, String description, String remark, String empId, String notificationL4, String passDuration, int delStatus, int isUsed, int exInt1, int exInt2, int exInt3, String exVar1, String exVar2, String exVar3) {
        this.purposeId = purposeId;
        this.purposeHeading = purposeHeading;
        this.purposeType = purposeType;
        this.description = description;
        this.remark = remark;
        this.empId = empId;
        this.notificationL4 = notificationL4;
        this.passDuration = passDuration;
        this.delStatus = delStatus;
        this.isUsed = isUsed;
        this.exInt1 = exInt1;
        this.exInt2 = exInt2;
        this.exInt3 = exInt3;
        this.exVar1 = exVar1;
        this.exVar2 = exVar2;
        this.exVar3 = exVar3;
    }

    public int getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(int purposeId) {
        this.purposeId = purposeId;
    }

    public String getPurposeHeading() {
        return purposeHeading;
    }

    public void setPurposeHeading(String purposeHeading) {
        this.purposeHeading = purposeHeading;
    }

    public int getPurposeType() {
        return purposeType;
    }

    public void setPurposeType(int purposeType) {
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
        return "Purpose{" +
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
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                ", exVar3='" + exVar3 + '\'' +
                '}';
    }
}
