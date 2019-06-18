package com.ats.gfpl_securityapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupGatepassCount {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("supTempCount")
    @Expose
    private Integer supTempCount;
    @SerializedName("supDayCount")
    @Expose
    private Integer supDayCount;
    @SerializedName("supOutEmpCount")
    @Expose
    private Integer supOutEmpCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSupTempCount() {
        return supTempCount;
    }

    public void setSupTempCount(Integer supTempCount) {
        this.supTempCount = supTempCount;
    }

    public Integer getSupDayCount() {
        return supDayCount;
    }

    public void setSupDayCount(Integer supDayCount) {
        this.supDayCount = supDayCount;
    }

    public Integer getSupOutEmpCount() {
        return supOutEmpCount;
    }

    public void setSupOutEmpCount(Integer supOutEmpCount) {
        this.supOutEmpCount = supOutEmpCount;
    }

    @Override
    public String toString() {
        return "SupGatepassCount{" +
                "id='" + id + '\'' +
                ", supTempCount=" + supTempCount +
                ", supDayCount=" + supDayCount +
                ", supOutEmpCount=" + supOutEmpCount +
                '}';
    }
}
