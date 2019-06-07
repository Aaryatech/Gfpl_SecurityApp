package com.ats.gfpl_securityapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("itemId")
    @Expose
    private Integer itemId;
    @SerializedName("itemCode")
    @Expose
    private String itemCode;
    @SerializedName("itemDesc")
    @Expose
    private String itemDesc;
    @SerializedName("itemDesc2")
    @Expose
    private Object itemDesc2;
    @SerializedName("itemDesc3")
    @Expose
    private String itemDesc3;
    @SerializedName("itemUom")
    @Expose
    private String itemUom;
    @SerializedName("itemUom2")
    @Expose
    private String itemUom2;
    @SerializedName("catId")
    @Expose
    private Integer catId;
    @SerializedName("grpId")
    @Expose
    private Integer grpId;
    @SerializedName("subGrpId")
    @Expose
    private Integer subGrpId;
    @SerializedName("itemOpRate")
    @Expose
    private float itemOpRate;
    @SerializedName("itemOpQty")
    @Expose
    private float itemOpQty;
    @SerializedName("itemClRate")
    @Expose
    private float itemClRate;
    @SerializedName("itemClQty")
    @Expose
    private float itemClQty;
    @SerializedName("itemDate")
    @Expose
    private String itemDate;
    @SerializedName("itemWt")
    @Expose
    private float itemWt;
    @SerializedName("itemMinLevel")
    @Expose
    private float itemMinLevel;
    @SerializedName("itemMaxLevel")
    @Expose
    private float itemMaxLevel;
    @SerializedName("itemRodLevel")
    @Expose
    private float itemRodLevel;
    @SerializedName("itemLocation")
    @Expose
    private String itemLocation;
    @SerializedName("itemAbc")
    @Expose
    private String itemAbc;
    @SerializedName("itemIsCritical")
    @Expose
    private float itemIsCritical;
    @SerializedName("itemIsCons")
    @Expose
    private float itemIsCons;
    @SerializedName("itemIsCapital")
    @Expose
    private float itemIsCapital;
    @SerializedName("itemSchd")
    @Expose
    private String itemSchd;
    @SerializedName("itemLife")
    @Expose
    private String itemLife;
    @SerializedName("isUsed")
    @Expose
    private Integer isUsed;
    @SerializedName("createdIn")
    @Expose
    private Integer createdIn;
    @SerializedName("deletedIn")
    @Expose
    private Integer deletedIn;
    @SerializedName("catDesc")
    @Expose
    private String catDesc;
    @SerializedName("grpCode")
    @Expose
    private String grpCode;
    @SerializedName("subgrpDesc")
    @Expose
    private String subgrpDesc;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Object getItemDesc2() {
        return itemDesc2;
    }

    public void setItemDesc2(Object itemDesc2) {
        this.itemDesc2 = itemDesc2;
    }

    public String getItemDesc3() {
        return itemDesc3;
    }

    public void setItemDesc3(String itemDesc3) {
        this.itemDesc3 = itemDesc3;
    }

    public String getItemUom() {
        return itemUom;
    }

    public void setItemUom(String itemUom) {
        this.itemUom = itemUom;
    }

    public String getItemUom2() {
        return itemUom2;
    }

    public void setItemUom2(String itemUom2) {
        this.itemUom2 = itemUom2;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getGrpId() {
        return grpId;
    }

    public void setGrpId(Integer grpId) {
        this.grpId = grpId;
    }

    public Integer getSubGrpId() {
        return subGrpId;
    }

    public void setSubGrpId(Integer subGrpId) {
        this.subGrpId = subGrpId;
    }

    public float getItemOpRate() {
        return itemOpRate;
    }

    public void setItemOpRate(float itemOpRate) {
        this.itemOpRate = itemOpRate;
    }

    public float getItemOpQty() {
        return itemOpQty;
    }

    public void setItemOpQty(float itemOpQty) {
        this.itemOpQty = itemOpQty;
    }

    public float getItemClRate() {
        return itemClRate;
    }

    public void setItemClRate(float itemClRate) {
        this.itemClRate = itemClRate;
    }

    public float getItemClQty() {
        return itemClQty;
    }

    public void setItemClQty(float itemClQty) {
        this.itemClQty = itemClQty;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public float getItemWt() {
        return itemWt;
    }

    public void setItemWt(float itemWt) {
        this.itemWt = itemWt;
    }

    public float getItemMinLevel() {
        return itemMinLevel;
    }

    public void setItemMinLevel(float itemMinLevel) {
        this.itemMinLevel = itemMinLevel;
    }

    public float getItemMaxLevel() {
        return itemMaxLevel;
    }

    public void setItemMaxLevel(float itemMaxLevel) {
        this.itemMaxLevel = itemMaxLevel;
    }

    public float getItemRodLevel() {
        return itemRodLevel;
    }

    public void setItemRodLevel(float itemRodLevel) {
        this.itemRodLevel = itemRodLevel;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }

    public String getItemAbc() {
        return itemAbc;
    }

    public void setItemAbc(String itemAbc) {
        this.itemAbc = itemAbc;
    }

    public float getItemIsCritical() {
        return itemIsCritical;
    }

    public void setItemIsCritical(float itemIsCritical) {
        this.itemIsCritical = itemIsCritical;
    }

    public float getItemIsCons() {
        return itemIsCons;
    }

    public void setItemIsCons(float itemIsCons) {
        this.itemIsCons = itemIsCons;
    }

    public float getItemIsCapital() {
        return itemIsCapital;
    }

    public void setItemIsCapital(float itemIsCapital) {
        this.itemIsCapital = itemIsCapital;
    }

    public String getItemSchd() {
        return itemSchd;
    }

    public void setItemSchd(String itemSchd) {
        this.itemSchd = itemSchd;
    }

    public String getItemLife() {
        return itemLife;
    }

    public void setItemLife(String itemLife) {
        this.itemLife = itemLife;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public Integer getCreatedIn() {
        return createdIn;
    }

    public void setCreatedIn(Integer createdIn) {
        this.createdIn = createdIn;
    }

    public Integer getDeletedIn() {
        return deletedIn;
    }

    public void setDeletedIn(Integer deletedIn) {
        this.deletedIn = deletedIn;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    public String getGrpCode() {
        return grpCode;
    }

    public void setGrpCode(String grpCode) {
        this.grpCode = grpCode;
    }

    public String getSubgrpDesc() {
        return subgrpDesc;
    }

    public void setSubgrpDesc(String subgrpDesc) {
        this.subgrpDesc = subgrpDesc;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemCode='" + itemCode + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                ", itemDesc2=" + itemDesc2 +
                ", itemDesc3='" + itemDesc3 + '\'' +
                ", itemUom='" + itemUom + '\'' +
                ", itemUom2='" + itemUom2 + '\'' +
                ", catId=" + catId +
                ", grpId=" + grpId +
                ", subGrpId=" + subGrpId +
                ", itemOpRate=" + itemOpRate +
                ", itemOpQty=" + itemOpQty +
                ", itemClRate=" + itemClRate +
                ", itemClQty=" + itemClQty +
                ", itemDate='" + itemDate + '\'' +
                ", itemWt=" + itemWt +
                ", itemMinLevel=" + itemMinLevel +
                ", itemMaxLevel=" + itemMaxLevel +
                ", itemRodLevel=" + itemRodLevel +
                ", itemLocation='" + itemLocation + '\'' +
                ", itemAbc='" + itemAbc + '\'' +
                ", itemIsCritical=" + itemIsCritical +
                ", itemIsCons=" + itemIsCons +
                ", itemIsCapital=" + itemIsCapital +
                ", itemSchd='" + itemSchd + '\'' +
                ", itemLife='" + itemLife + '\'' +
                ", isUsed=" + isUsed +
                ", createdIn=" + createdIn +
                ", deletedIn=" + deletedIn +
                ", catDesc='" + catDesc + '\'' +
                ", grpCode='" + grpCode + '\'' +
                ", subgrpDesc='" + subgrpDesc + '\'' +
                '}';
    }
}
