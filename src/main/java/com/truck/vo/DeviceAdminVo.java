package com.truck.vo;

import java.math.BigDecimal;
import java.util.Date;

public class DeviceAdminVo {
    private Integer id;

    private Integer adminId;

    private Integer deviceId;

    private Integer status;

    private Integer frequency;

    private String createTime;

    private String updateTime;

    private String dateStr;

    private DeviceVo deviceVo;

    private BigDecimal remainTime;

    public DeviceAdminVo() {
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

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public DeviceVo getDeviceVo() {
        return deviceVo;
    }

    public void setDeviceVo(DeviceVo deviceVo) {
        this.deviceVo = deviceVo;
    }

    public BigDecimal getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(BigDecimal remainTime) {
        this.remainTime = remainTime;
    }
}