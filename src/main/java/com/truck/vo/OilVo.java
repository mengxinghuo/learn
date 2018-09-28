package com.truck.vo;

import java.math.BigDecimal;
import java.util.Date;

public class OilVo {
    private Integer id;

    private Integer adminId;

    private Integer deviceId;

    private Integer driverId;

    private Long deviceOil;

    private String createTime;

    private String updateTime;

    private BigDecimal price;

    public OilVo() {
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

    public Long getDeviceOil() {
        return deviceOil;
    }

    public void setDeviceOil(Long deviceOil) {
        this.deviceOil = deviceOil;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}