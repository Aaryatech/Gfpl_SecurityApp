package com.ats.gfpl_securityapp.model;

public class EmpGatePass {

    private int gatepassEmpId;
    private String empDateOut;
    private String empDateIn;
    private int userId;
    private String userName;
    private int empId;
    private String empName;
    private int gatePassType;
    private int gatePassSubType;
    private int purposeId;
    private String purposeText;
    private String purposeRemark;
    private int securityIdOut;
    private int securityIdIn;
    private float noOfHr;
    private String outTime;
    private String inTime;
    private String newOutTime;
    private String newInTime;
    private String actualTimeDifference;
    private int gatePassStatus;
    private int delStatus;
    private int isUsed;

    private Integer exInt1;
    private Integer exInt2;
    private Integer exInt3;
    private String exVar1;
    private String exVar2;
    private String exVar3;

    public EmpGatePass(int gatepassEmpId, String empDateOut, String empDateIn, int userId, String userName, int empId, String empName, int gatePassType, int gatePassSubType, int purposeId, String purposeText, String purposeRemark, int securityIdOut, int securityIdIn, float noOfHr, String outTime, String inTime, String newOutTime, String newInTime, String actualTimeDifference, int gatePassStatus, int delStatus, int isUsed, Integer exInt1, Integer exInt2, Integer exInt3, String exVar1, String exVar2, String exVar3) {
        this.gatepassEmpId = gatepassEmpId;
        this.empDateOut = empDateOut;
        this.empDateIn = empDateIn;
        this.userId = userId;
        this.userName = userName;
        this.empId = empId;
        this.empName = empName;
        this.gatePassType = gatePassType;
        this.gatePassSubType = gatePassSubType;
        this.purposeId = purposeId;
        this.purposeText = purposeText;
        this.purposeRemark = purposeRemark;
        this.securityIdOut = securityIdOut;
        this.securityIdIn = securityIdIn;
        this.noOfHr = noOfHr;
        this.outTime = outTime;
        this.inTime = inTime;
        this.newOutTime = newOutTime;
        this.newInTime = newInTime;
        this.actualTimeDifference = actualTimeDifference;
        this.gatePassStatus = gatePassStatus;
        this.delStatus = delStatus;
        this.isUsed = isUsed;
        this.exInt1 = exInt1;
        this.exInt2 = exInt2;
        this.exInt3 = exInt3;
        this.exVar1 = exVar1;
        this.exVar2 = exVar2;
        this.exVar3 = exVar3;
    }

    public int getGatepassEmpId() {
        return gatepassEmpId;
    }

    public void setGatepassEmpId(int gatepassEmpId) {
        this.gatepassEmpId = gatepassEmpId;
    }

    public String getEmpDateOut() {
        return empDateOut;
    }

    public void setEmpDateOut(String empDateOut) {
        this.empDateOut = empDateOut;
    }

    public String getEmpDateIn() {
        return empDateIn;
    }

    public void setEmpDateIn(String empDateIn) {
        this.empDateIn = empDateIn;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public int getGatePassType() {
        return gatePassType;
    }

    public void setGatePassType(int gatePassType) {
        this.gatePassType = gatePassType;
    }

    public int getGatePassSubType() {
        return gatePassSubType;
    }

    public void setGatePassSubType(int gatePassSubType) {
        this.gatePassSubType = gatePassSubType;
    }

    public int getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(int purposeId) {
        this.purposeId = purposeId;
    }

    public String getPurposeText() {
        return purposeText;
    }

    public void setPurposeText(String purposeText) {
        this.purposeText = purposeText;
    }

    public String getPurposeRemark() {
        return purposeRemark;
    }

    public void setPurposeRemark(String purposeRemark) {
        this.purposeRemark = purposeRemark;
    }

    public int getSecurityIdOut() {
        return securityIdOut;
    }

    public void setSecurityIdOut(int securityIdOut) {
        this.securityIdOut = securityIdOut;
    }

    public int getSecurityIdIn() {
        return securityIdIn;
    }

    public void setSecurityIdIn(int securityIdIn) {
        this.securityIdIn = securityIdIn;
    }

    public float getNoOfHr() {
        return noOfHr;
    }

    public void setNoOfHr(float noOfHr) {
        this.noOfHr = noOfHr;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getNewOutTime() {
        return newOutTime;
    }

    public void setNewOutTime(String newOutTime) {
        this.newOutTime = newOutTime;
    }

    public String getNewInTime() {
        return newInTime;
    }

    public void setNewInTime(String newInTime) {
        this.newInTime = newInTime;
    }

    public String getActualTimeDifference() {
        return actualTimeDifference;
    }

    public void setActualTimeDifference(String actualTimeDifference) {
        this.actualTimeDifference = actualTimeDifference;
    }

    public int getGatePassStatus() {
        return gatePassStatus;
    }

    public void setGatePassStatus(int gatePassStatus) {
        this.gatePassStatus = gatePassStatus;
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
        return "EmpGatePass{" +
                "gatepassEmpId=" + gatepassEmpId +
                ", empDateOut='" + empDateOut + '\'' +
                ", empDateIn='" + empDateIn + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", empId=" + empId +
                ", empName='" + empName + '\'' +
                ", gatePassType=" + gatePassType +
                ", gatePassSubType=" + gatePassSubType +
                ", purposeId=" + purposeId +
                ", purposeText='" + purposeText + '\'' +
                ", purposeRemark='" + purposeRemark + '\'' +
                ", securityIdOut=" + securityIdOut +
                ", securityIdIn=" + securityIdIn +
                ", noOfHr=" + noOfHr +
                ", outTime='" + outTime + '\'' +
                ", inTime='" + inTime + '\'' +
                ", newOutTime='" + newOutTime + '\'' +
                ", newInTime='" + newInTime + '\'' +
                ", actualTimeDifference='" + actualTimeDifference + '\'' +
                ", gatePassStatus=" + gatePassStatus +
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
