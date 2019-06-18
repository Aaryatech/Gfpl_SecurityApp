package com.ats.gfpl_securityapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MatGatepassCount {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("inwardCount")
    @Expose
    private Integer inwardCount;
    @SerializedName("parcelCount")
    @Expose
    private Integer parcelCount;
    @SerializedName("deptPendingCount")
    @Expose
    private Integer deptPendingCount;
    @SerializedName("deptApprovedCount")
    @Expose
    private Integer deptApprovedCount;
    @SerializedName("deptRejectedCount")
    @Expose
    private Integer deptRejectedCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getInwardCount() {
        return inwardCount;
    }

    public void setInwardCount(Integer inwardCount) {
        this.inwardCount = inwardCount;
    }

    public Integer getParcelCount() {
        return parcelCount;
    }

    public void setParcelCount(Integer parcelCount) {
        this.parcelCount = parcelCount;
    }

    public Integer getDeptPendingCount() {
        return deptPendingCount;
    }

    public void setDeptPendingCount(Integer deptPendingCount) {
        this.deptPendingCount = deptPendingCount;
    }

    public Integer getDeptApprovedCount() {
        return deptApprovedCount;
    }

    public void setDeptApprovedCount(Integer deptApprovedCount) {
        this.deptApprovedCount = deptApprovedCount;
    }

    public Integer getDeptRejectedCount() {
        return deptRejectedCount;
    }

    public void setDeptRejectedCount(Integer deptRejectedCount) {
        this.deptRejectedCount = deptRejectedCount;
    }

    @Override
    public String toString() {
        return "MatGatepassCount{" +
                "id='" + id + '\'' +
                ", inwardCount=" + inwardCount +
                ", parcelCount=" + parcelCount +
                ", deptPendingCount=" + deptPendingCount +
                ", deptApprovedCount=" + deptApprovedCount +
                ", deptRejectedCount=" + deptRejectedCount +
                '}';
    }
}
