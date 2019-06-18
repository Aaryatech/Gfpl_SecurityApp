package com.ats.gfpl_securityapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VisAndMaintGatepassCount {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("visitor_pending")
    @Expose
    private Integer visitorPending;
    @SerializedName("visitor_approved")
    @Expose
    private Integer visitorApproved;
    @SerializedName("visitor_rejected")
    @Expose
    private Integer visitorRejected;
    @SerializedName("visitor_completed")
    @Expose
    private Integer visitorCompleted;
    @SerializedName("visitor_in_comp")
    @Expose
    private Integer visitorInComp;
    @SerializedName("visitor_total")
    @Expose
    private Integer visitorTotal;
    @SerializedName("emp_visitor_pending")
    @Expose
    private Integer empVisitorPending;
    @SerializedName("emp_visitor_approved")
    @Expose
    private Integer empVisitorApproved;
    @SerializedName("emp_visitor_rejected")
    @Expose
    private Integer empVisitorRejected;
    @SerializedName("emp_visitor_completed")
    @Expose
    private Integer empVisitorCompleted;
    @SerializedName("emp_visitor_total")
    @Expose
    private Integer empVisitorTotal;
    @SerializedName("maint_pending")
    @Expose
    private Integer maintPending;
    @SerializedName("maint_approved")
    @Expose
    private Integer maintApproved;
    @SerializedName("maint_rejected")
    @Expose
    private Integer maintRejected;
    @SerializedName("maint_completed")
    @Expose
    private Integer maintCompleted;
    @SerializedName("maint_total")
    @Expose
    private Integer maintTotal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVisitorPending() {
        return visitorPending;
    }

    public void setVisitorPending(Integer visitorPending) {
        this.visitorPending = visitorPending;
    }

    public Integer getVisitorApproved() {
        return visitorApproved;
    }

    public void setVisitorApproved(Integer visitorApproved) {
        this.visitorApproved = visitorApproved;
    }

    public Integer getVisitorRejected() {
        return visitorRejected;
    }

    public void setVisitorRejected(Integer visitorRejected) {
        this.visitorRejected = visitorRejected;
    }

    public Integer getVisitorCompleted() {
        return visitorCompleted;
    }

    public void setVisitorCompleted(Integer visitorCompleted) {
        this.visitorCompleted = visitorCompleted;
    }

    public Integer getVisitorInComp() {
        return visitorInComp;
    }

    public void setVisitorInComp(Integer visitorInComp) {
        this.visitorInComp = visitorInComp;
    }

    public Integer getVisitorTotal() {
        return visitorTotal;
    }

    public void setVisitorTotal(Integer visitorTotal) {
        this.visitorTotal = visitorTotal;
    }

    public Integer getEmpVisitorPending() {
        return empVisitorPending;
    }

    public void setEmpVisitorPending(Integer empVisitorPending) {
        this.empVisitorPending = empVisitorPending;
    }

    public Integer getEmpVisitorApproved() {
        return empVisitorApproved;
    }

    public void setEmpVisitorApproved(Integer empVisitorApproved) {
        this.empVisitorApproved = empVisitorApproved;
    }

    public Integer getEmpVisitorRejected() {
        return empVisitorRejected;
    }

    public void setEmpVisitorRejected(Integer empVisitorRejected) {
        this.empVisitorRejected = empVisitorRejected;
    }

    public Integer getEmpVisitorCompleted() {
        return empVisitorCompleted;
    }

    public void setEmpVisitorCompleted(Integer empVisitorCompleted) {
        this.empVisitorCompleted = empVisitorCompleted;
    }

    public Integer getEmpVisitorTotal() {
        return empVisitorTotal;
    }

    public void setEmpVisitorTotal(Integer empVisitorTotal) {
        this.empVisitorTotal = empVisitorTotal;
    }

    public Integer getMaintPending() {
        return maintPending;
    }

    public void setMaintPending(Integer maintPending) {
        this.maintPending = maintPending;
    }

    public Integer getMaintApproved() {
        return maintApproved;
    }

    public void setMaintApproved(Integer maintApproved) {
        this.maintApproved = maintApproved;
    }

    public Integer getMaintRejected() {
        return maintRejected;
    }

    public void setMaintRejected(Integer maintRejected) {
        this.maintRejected = maintRejected;
    }

    public Integer getMaintCompleted() {
        return maintCompleted;
    }

    public void setMaintCompleted(Integer maintCompleted) {
        this.maintCompleted = maintCompleted;
    }

    public Integer getMaintTotal() {
        return maintTotal;
    }

    public void setMaintTotal(Integer maintTotal) {
        this.maintTotal = maintTotal;
    }

    @Override
    public String toString() {
        return "VisAndMaintGatepassCount{" +
                "id='" + id + '\'' +
                ", visitorPending=" + visitorPending +
                ", visitorApproved=" + visitorApproved +
                ", visitorRejected=" + visitorRejected +
                ", visitorCompleted=" + visitorCompleted +
                ", visitorInComp=" + visitorInComp +
                ", visitorTotal=" + visitorTotal +
                ", empVisitorPending=" + empVisitorPending +
                ", empVisitorApproved=" + empVisitorApproved +
                ", empVisitorRejected=" + empVisitorRejected +
                ", empVisitorCompleted=" + empVisitorCompleted +
                ", empVisitorTotal=" + empVisitorTotal +
                ", maintPending=" + maintPending +
                ", maintApproved=" + maintApproved +
                ", maintRejected=" + maintRejected +
                ", maintCompleted=" + maintCompleted +
                ", maintTotal=" + maintTotal +
                '}';
    }
}
