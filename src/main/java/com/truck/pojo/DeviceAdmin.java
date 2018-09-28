package com.truck.pojo;

import java.util.Date;

public class DeviceAdmin {
    private Integer id;

    private Integer adminId;

    private Integer deviceId;

    private Integer status;

    private Integer frequency;

    private Date createTime;

    private Date updateTime;

    public DeviceAdmin(Integer id, Integer adminId, Integer deviceId, Integer status, Integer frequency, Date createTime, Date updateTime) {
        this.id = id;
        this.adminId = adminId;
        this.deviceId = deviceId;
        this.status = status;
        this.frequency = frequency;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public DeviceAdmin() {
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

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
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
}