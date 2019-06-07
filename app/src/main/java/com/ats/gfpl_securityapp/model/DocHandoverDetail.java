package com.ats.gfpl_securityapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocHandoverDetail {

    @SerializedName("documentHandoverId")
    @Expose
    private Integer documentHandoverId;
    @SerializedName("gatepassInwardId")
    @Expose
    private Integer gatepassInwardId;
    @SerializedName("handOverDate")
    @Expose
    private String handOverDate;
    @SerializedName("fromUserId")
    @Expose
    private Integer fromUserId;
    @SerializedName("toUserId")
    @Expose
    private Integer toUserId;
    @SerializedName("fromUserName")
    @Expose
    private String fromUserName;
    @SerializedName("toUserName")
    @Expose
    private String toUserName;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
    @SerializedName("fromDepatrmentId")
    @Expose
    private Integer fromDepatrmentId;
    @SerializedName("fromDepatrmentName")
    @Expose
    private String fromDepatrmentName;
    @SerializedName("toDeptId")
    @Expose
    private Integer toDeptId;
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
    private String exVar1;
    @SerializedName("exVar2")
    @Expose
    private String exVar2;
    @SerializedName("exVar3")
    @Expose
    private String exVar3;

    public Integer getDocumentHandoverId() {
        return documentHandoverId;
    }

    public void setDocumentHandoverId(Integer documentHandoverId) {
        this.documentHandoverId = documentHandoverId;
    }

    public Integer getGatepassInwardId() {
        return gatepassInwardId;
    }

    public void setGatepassInwardId(Integer gatepassInwardId) {
        this.gatepassInwardId = gatepassInwardId;
    }

    public String getHandOverDate() {
        return handOverDate;
    }

    public void setHandOverDate(String handOverDate) {
        this.handOverDate = handOverDate;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
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

    public Integer getFromDepatrmentId() {
        return fromDepatrmentId;
    }

    public void setFromDepatrmentId(Integer fromDepatrmentId) {
        this.fromDepatrmentId = fromDepatrmentId;
    }

    public String getFromDepatrmentName() {
        return fromDepatrmentName;
    }

    public void setFromDepatrmentName(String fromDepatrmentName) {
        this.fromDepatrmentName = fromDepatrmentName;
    }

    public Integer getToDeptId() {
        return toDeptId;
    }

    public void setToDeptId(Integer toDeptId) {
        this.toDeptId = toDeptId;
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
        return "DocHandoverDetail{" +
                "documentHandoverId=" + documentHandoverId +
                ", gatepassInwardId=" + gatepassInwardId +
                ", handOverDate='" + handOverDate + '\'' +
                ", fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                ", fromUserName='" + fromUserName + '\'' +
                ", toUserName='" + toUserName + '\'' +
                ", status=" + status +
                ", delStatus=" + delStatus +
                ", fromDepatrmentId=" + fromDepatrmentId +
                ", fromDepatrmentName='" + fromDepatrmentName + '\'' +
                ", toDeptId=" + toDeptId +
                ", toDeptName='" + toDeptName + '\'' +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exInt3=" + exInt3 +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                ", exVar3='" + exVar3 + '\'' +
                '}';
    }
}
