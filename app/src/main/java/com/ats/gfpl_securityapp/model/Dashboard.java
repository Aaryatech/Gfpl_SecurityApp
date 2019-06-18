package com.ats.gfpl_securityapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dashboard {
    @SerializedName("visAndMaintGatepassCount")
    @Expose
    private VisAndMaintGatepassCount visAndMaintGatepassCount;
    @SerializedName("empGatepassCount")
    @Expose
    private EmpGatepassCount empGatepassCount;
    @SerializedName("supGatepassCount")
    @Expose
    private SupGatepassCount supGatepassCount;
    @SerializedName("matGatepassCount")
    @Expose
    private MatGatepassCount matGatepassCount;
    @SerializedName("matGatepassEmpWiseCount")
    @Expose
    private MatGatepassEmpWiseCount matGatepassEmpWiseCount;

    public VisAndMaintGatepassCount getVisAndMaintGatepassCount() {
        return visAndMaintGatepassCount;
    }

    public void setVisAndMaintGatepassCount(VisAndMaintGatepassCount visAndMaintGatepassCount) {
        this.visAndMaintGatepassCount = visAndMaintGatepassCount;
    }

    public EmpGatepassCount getEmpGatepassCount() {
        return empGatepassCount;
    }

    public void setEmpGatepassCount(EmpGatepassCount empGatepassCount) {
        this.empGatepassCount = empGatepassCount;
    }

    public SupGatepassCount getSupGatepassCount() {
        return supGatepassCount;
    }

    public void setSupGatepassCount(SupGatepassCount supGatepassCount) {
        this.supGatepassCount = supGatepassCount;
    }

    public MatGatepassCount getMatGatepassCount() {
        return matGatepassCount;
    }

    public void setMatGatepassCount(MatGatepassCount matGatepassCount) {
        this.matGatepassCount = matGatepassCount;
    }

    public MatGatepassEmpWiseCount getMatGatepassEmpWiseCount() {
        return matGatepassEmpWiseCount;
    }

    public void setMatGatepassEmpWiseCount(MatGatepassEmpWiseCount matGatepassEmpWiseCount) {
        this.matGatepassEmpWiseCount = matGatepassEmpWiseCount;
    }

    @Override
    public String toString() {
        return "Dashboard{" +
                "visAndMaintGatepassCount=" + visAndMaintGatepassCount +
                ", empGatepassCount=" + empGatepassCount +
                ", supGatepassCount=" + supGatepassCount +
                ", matGatepassCount=" + matGatepassCount +
                ", matGatepassEmpWiseCount=" + matGatepassEmpWiseCount +
                '}';
    }
}
