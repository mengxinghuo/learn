package com.truck.vo;

import java.math.BigDecimal;
import java.util.Date;

public class WorkTimeVo {
    private Integer id;

    private Integer adminId;

    private Integer deviceId;

    private Integer driverId;

    private Integer previousId;

    private String address;

    private String createTime;

    private String updateTime;

    private BigDecimal normalTime;

    public WorkTimeVo(Integer id, Integer adminId, Integer deviceId, Integer driverId, Integer previousId, String address, String createTime, String updateTime, BigDecimal normalTime) {
        this.id = id;
        this.adminId = adminId;
        this.deviceId = deviceId;
        this.driverId = driverId;
        this.previousId = previousId;
        this.address = address;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.normalTime = normalTime;
    }

    public WorkTimeVo() {
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getNormalTime() {
        return normalTime;
    }

    public void setNormalTime(BigDecimal normalTime) {
        this.normalTime = normalTime;
    }
}