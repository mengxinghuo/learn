package com.truck.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Mileage {
    private Integer id;

    private Integer adminId;

    private Integer deviceId;

    private Integer driverId;

    private Long deviceMileage;

    private Date createTime;

    private Date updateTime;

    private BigDecimal normalTime;

    public Mileage(Integer id, Integer adminId, Integer deviceId, Integer driverId, Long deviceMileage, Date createTime, Date updateTime, BigDecimal normalTime) {
        this.id = id;
        this.adminId = adminId;
        this.deviceId = deviceId;
        this.driverId = driverId;
        this.deviceMileage = deviceMileage;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.normalTime = normalTime;
    }

    public Mileage() {
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

    public Long getDeviceMileage() {
        return deviceMileage;
    }

    public void setDeviceMileage(Long deviceMileage) {
        this.deviceMileage = deviceMileage;
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

    public BigDecimal getNormalTime() {
        return normalTime;
    }

    public void setNormalTime(BigDecimal normalTime) {
        this.normalTime = normalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mileage mileage = (Mileage) o;
        return Objects.equals(deviceId, mileage.deviceId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(deviceId);
    }
}