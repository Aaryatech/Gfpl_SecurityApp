package com.ats.gfpl_securityapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmpGatepassCount {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tempGpCount")
    @Expose
    private Integer tempGpCount;
    @SerializedName("dayGpCount")
    @Expose
    private Integer dayGpCount;
    @SerializedName("outEmpCount")
    @Expose
    private Integer outEmpCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTempGpCount() {
        return tempGpCount;
    }

    public void setTempGpCount(Integer tempGpCount) {
        this.tempGpCount = tempGpCount;
    }

    public Integer getDayGpCount() {
        return dayGpCount;
    }

    public void setDayGpCount(Integer dayGpCount) {
        this.dayGpCount = dayGpCount;
    }

    public Integer getOutEmpCount() {
        return outEmpCount;
    }

    public void setOutEmpCount(Integer outEmpCount) {
        this.outEmpCount = outEmpCount;
    }

    @Override
    public String toString() {
        return "EmpGatepassCount{" +
                "id='" + id + '\'' +
                ", tempGpCount=" + tempGpCount +
                ", dayGpCount=" + dayGpCount +
                ", outEmpCount=" + outEmpCount +
                '}';
    }
}
