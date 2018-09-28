package com.truck.vo;

import java.math.BigDecimal;

public class HourVo {
    private Integer id;

    private Integer adminId;

    private Integer deviceId;

    private Integer driverId;

    private Long deviceHour;

    private String createTime;

    private String updateTime;

    private BigDecimal normalTime;

    public HourVo() {
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

    public Long getDeviceHour() {
        return deviceHour;
    }

    public void setDeviceHour(Long deviceHour) {
        this.deviceHour = deviceHour;
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