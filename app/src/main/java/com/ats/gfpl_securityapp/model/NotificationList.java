package com.ats.gfpl_securityapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationList {

    @SerializedName("notificationId")
    @Expose
    private Integer notificationId;
    @SerializedName("gatepassVisitorId")
    @Expose
    private Integer gatepassVisitorId;
    @SerializedName("empId")
    @Expose
    private Integer empId;
    @SerializedName("empName")
    @Expose
    private String empName;
    @SerializedName("gatepassType")
    @Expose
    private Integer gatepassType;
    @SerializedName("purposeType")
    @Expose
    private Integer purposeType;
    @SerializedName("purposeId")
    @Expose
    private Integer purposeId;
    @SerializedName("requestAcceptedTime")
    @Expose
    private String requestAcceptedTime;
    @SerializedName("timeDifferenceRequest")
    @Expose
    private Integer timeDifferenceRequest;
    @SerializedName("status")
    @Expose
    private Integer status;
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

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getGatepassVisitorId() {
        return gatepassVisitorId;
    }

    public void setGatepassVisitorId(Integer gatepassVisitorId) {
        this.gatepassVisitorId = gatepassVisitorId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getGatepassType() {
        return gatepassType;
    }

    public void setGatepassType(Integer gatepassType) {
        this.gatepassType = gatepassType;
    }

    public Integer getPurposeType() {
        return purposeType;
    }

    public void setPurposeType(Integer purposeType) {
        this.purposeType = purposeType;
    }

    public Integer getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(Integer purposeId) {
        this.purposeId = purposeId;
    }

    public String getRequestAcceptedTime() {
        return requestAcceptedTime;
    }

    public void setRequestAcceptedTime(String requestAcceptedTime) {
        this.requestAcceptedTime = requestAcceptedTime;
    }

    public Integer getTimeDifferenceRequest() {
        return timeDifferenceRequest;
    }

    public void setTimeDifferenceRequest(Integer timeDifferenceRequest) {
        this.timeDifferenceRequest = timeDifferenceRequest;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "NotificationList{" +
                "notificationId=" + notificationId +
                ", gatepassVisitorId=" + gatepassVisitorId +
                ", empId=" + empId +
                ", empName='" + empName + '\'' +
                ", gatepassType=" + gatepassType +
                ", purposeType=" + purposeType +
                ", purposeId=" + purposeId +
                ", requestAcceptedTime='" + requestAcceptedTime + '\'' +
                ", timeDifferenceRequest=" + timeDifferenceRequest +
                ", status=" + status +
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
