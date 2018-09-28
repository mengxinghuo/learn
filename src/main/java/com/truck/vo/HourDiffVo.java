package com.truck.vo;

public class HourDiffVo {

    private Integer adminId;

    private Integer deviceId;

    private Integer driverId;

    private Long deviceHourBegin;

    private Long deviceHourEnd;

    private Long deviceHourDiff;

    private String startTimeStr;

    private String endTimeStr;


    public HourDiffVo() {
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

    public Long getDeviceHourBegin() {
        return deviceHourBegin;
    }

    public void setDeviceHourBegin(Long deviceHourBegin) {
        this.deviceHourBegin = deviceHourBegin;
    }

    public Long getDeviceHourEnd() {
        return deviceHourEnd;
    }

    public void setDeviceHourEnd(Long deviceHourEnd) {
        this.deviceHourEnd = deviceHourEnd;
    }

    public Long getDeviceHourDiff() {
        return deviceHourDiff;
    }

    public void setDeviceHourDiff(Long deviceHourDiff) {
        this.deviceHourDiff = deviceHourDiff;
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