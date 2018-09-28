package com.truck.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Oil {
    private Integer id;

    private Integer adminId;

    private Integer deviceId;

    private Integer driverId;

    private Long deviceOil;

    private Date createTime;

    private Date updateTime;

    private BigDecimal price;

    public Oil(Integer id, Integer adminId, Integer deviceId, Integer driverId, Long deviceOil, Date createTime, Date updateTime, BigDecimal price) {
            this.id = id;
            this.adminId = adminId;
            this.deviceId = deviceId;
            this.driverId = driverId;
            this.deviceOil = deviceOil;
            this.createTime = createTime;
            this.updateTime = updateTime;
            this.price = price;
        }

    public Oil() {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Oil oil = (Oil) o;
        return Objects.equals(deviceId, oil.deviceId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(deviceId);
    }
}