package com.truck.vo;

import java.math.BigDecimal;

public class MileageDiffVo {

    private Integer adminId;

    private Integer deviceId;

    private Integer driverId;

    private Long deviceMileageBegin;

    private Long deviceMileageEnd;

    private Long deviceMileageDiff;

    private String startTimeStr;

    private String endTimeStr;


    public MileageDiffVo() {
        super();
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

    public Long getDeviceMileageBegin() {
        return deviceMileageBegin;
    }

    public void setDeviceMileageBegin(Long deviceMileageBegin) {
        this.deviceMileageBegin = deviceMileageBegin;
    }

    public Long getDeviceMileageEnd() {
        return deviceMileageEnd;
    }

    public void setDeviceMileageEnd(Long deviceMileageEnd) {
        this.deviceMileageEnd = deviceMileageEnd;
    }

    public Long getDeviceMileageDiff() {
        return deviceMileageDiff;
    }

    public void setDeviceMileageDiff(Long deviceMileageDiff) {
        this.deviceMileageDiff = deviceMileageDiff;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }
}