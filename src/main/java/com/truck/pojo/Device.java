package com.truck.pojo;

import java.util.Date;

public class Device {
    private Integer id;

    private Integer adminId;

    private Integer productId;

    private Integer categoryNo;

    private String deviceNo;

    private Date createTime;

    private Date updateTime;

    private String deviceName;

    private String factoryNo;

    public Device(Integer id, Integer adminId, Integer productId, Integer categoryNo, String deviceNo, Date createTime, Date updateTime, String deviceName, String factoryNo) {
        this.id = id;
        this.adminId = adminId;
        this.productId = productId;
        this.categoryNo = categoryNo;
        this.deviceNo = deviceNo;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.deviceName = deviceName;
        this.factoryNo = factoryNo;
    }
    public Device() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(Integer categoryNo) {
        this.categoryNo = categoryNo;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getFactoryNo() {
        return factoryNo;
    }

    public void setFactoryNo(String factoryNo) {
        this.factoryNo = factoryNo;
    }
}