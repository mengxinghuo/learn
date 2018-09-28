package com.truck.pojo;

import com.truck.util.BigDecimalUtil;

import java.util.Date;
import java.util.Objects;

public class AccessTime {
    private Integer id;

    private Integer adminId;

    private Integer deviceId;

    private Integer driverId;

    private Integer previousId;

    private String address;

    private Date createTime;

    private Date updateTime;

    public AccessTime(Integer id, Integer adminId, Integer deviceId, Integer driverId, Integer previousId, String address,  Date createTime, Date updateTime) {
        this.id = id;
        this.adminId = adminId;
        this.deviceId = deviceId;
        this.driverId = driverId;
        this.previousId = previousId;
        this.address = address;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public AccessTime() {
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

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Integer getPreviousId() {
        return previousId;
    }

    public void setPreviousId(Integer previousId) {
        this.previousId = previousId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessTime that = (AccessTime) o;
        return Objects.equals(deviceId, that.deviceId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(deviceId);
    }
}