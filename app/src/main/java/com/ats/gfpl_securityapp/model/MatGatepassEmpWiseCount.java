package com.ats.gfpl_securityapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MatGatepassEmpWiseCount {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("empPendingCount")
    @Expose
    private Integer empPendingCount;
    @SerializedName("empApprovedCount")
    @Expose
    private Integer empApprovedCount;
    @SerializedName("empRejectedCount")
    @Expose
    private Integer empRejectedCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getEmpPendingCount() {
        return empPendingCount;
    }

    public void setEmpPendingCount(Integer empPendingCount) {
        this.empPendingCount = empPendingCount;
    }

    public Integer getEmpApprovedCount() {
        return empApprovedCount;
    }

    public void setEmpApprovedCount(Integer empApprovedCount) {
        this.empApprovedCount = empApprovedCount;
    }

    public Integer getEmpRejectedCount() {
        return empRejectedCount;
    }

    public void setEmpRejectedCount(Integer empRejectedCount) {
        this.empRejectedCount = empRejectedCount;
    }

    @Override
    public String toString() {
        return "MatGatepassEmpWiseCount{" +
                "id='" + id + '\'' +
                ", empPendingCount=" + empPendingCount +
                ", empApprovedCount=" + empApprovedCount +
                ", empRejectedCount=" + empRejectedCount +
                '}';
    }
}
