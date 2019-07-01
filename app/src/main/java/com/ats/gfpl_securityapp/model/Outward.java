package com.ats.gfpl_securityapp.model;

public class Outward {

    private int gpOutwardId;
    private String dateOut;
    private String dateInExpected;
    private String dateIn;
    private int empId;
    private String empName;
    private String outwardName;
    private String toName;
    private int secIdOut	;
    private int secIdIn;
    private String outTime;
    private String inTime;
    private int status;
    private String outPhoto;
    private String inPhoto;
    private int delStatus;
    private int isUsed;
    private int exInt1;
    private int exInt2;
    private int exInt3;
    private String exVar1;
    private String exVar2;
    private String exVar3;

    public Outward(int gpOutwardId, String dateOut, String dateInExpected, String dateIn, int empId, String empName, String outwardName, String toName, int secIdOut, int secIdIn, String outTime, String inTime, int status, String outPhoto, String inPhoto, int delStatus, int isUsed, int exInt1, int exInt2, int exInt3, String exVar1, String exVar2, String exVar3) {
        this.gpOutwardId = gpOutwardId;
        this.dateOut = dateOut;
        this.dateInExpected = dateInExpected;
        this.dateIn = dateIn;
        this.empId = empId;
        this.empName = empName;
        this.outwardName = outwardName;
        this.toName = toName;
        this.secIdOut = secIdOut;
        this.secIdIn = secIdIn;
        this.outTime = outTime;
        this.inTime = inTime;
        this.status = status;
        this.outPhoto = outPhoto;
        this.inPhoto = inPhoto;
        this.delStatus = delStatus;
        this.isUsed = isUsed;
        this.exInt1 = exInt1;
        this.exInt2 = exInt2;
        this.exInt3 = exInt3;
        this.exVar1 = exVar1;
        this.exVar2 = exVar2;
        this.exVar3 = exVar3;
    }

    public int getGpOutwardId() {
        return gpOutwardId;
    }

    public void setGpOutwardId(int gpOutwardId) {
        this.gpOutwardId = gpOutwardId;
    }

    public String getDateOut() {
        return dateOut;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }

    public String getDateInExpected() {
        return dateInExpected;
    }

    public void setDateInExpected(String dateInExpected) {
        this.dateInExpected = dateInExpected;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
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

    public String getOutwardName() {
        return outwardName;
    }

    public void setOutwardName(String outwardName) {
        this.outwardName = outwardName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public int getSecIdOut() {
        return secIdOut;
    }

    public void setSecIdOut(int secIdOut) {
        this.secIdOut = secIdOut;
    }

    public int getSecIdIn() {
        return secIdIn;
    }

    public void setSecIdIn(int secIdIn) {
        this.secIdIn = secIdIn;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOutPhoto() {
        return outPhoto;
    }

    public void setOutPhoto(String outPhoto) {
        this.outPhoto = outPhoto;
    }

    public String getInPhoto() {
        return inPhoto;
    }

    public void setInPhoto(String inPhoto) {
        this.inPhoto = inPhoto;
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
        return "Onward{" +
                "gpOutwardId=" + gpOutwardId +
                ", dateOut='" + dateOut + '\'' +
                ", dateInExpected='" + dateInExpected + '\'' +
                ", dateIn='" + dateIn + '\'' +
                ", empId=" + empId +
                ", empName='" + empName + '\'' +
                ", outwardName='" + outwardName + '\'' +
                ", toName='" + toName + '\'' +
                ", secIdOut=" + secIdOut +
                ", secIdIn=" + secIdIn +
                ", outTime='" + outTime + '\'' +
                ", inTime='" + inTime + '\'' +
                ", status=" + status +
                ", outPhoto='" + outPhoto + '\'' +
                ", inPhoto='" + inPhoto + '\'' +
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
