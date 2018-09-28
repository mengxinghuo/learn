package com.truck.vo;

import java.util.List;

public class HourProductVo {

    private Integer deviceId;

    private DeviceVo deviceVo;

    private List<HourProductDaysVo> hourProductDaysVos;

    private Long deviceHourDays;

    public HourProductVo() {
        super();
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public List<HourProductDaysVo> getHourProductDaysVos() {
        return hourProductDaysVos;
    }

    public void setHourProductDaysVos(List<HourProductDaysVo> hourProductDaysVos) {
        this.hourProductDaysVos = hourProductDaysVos;
    }

    public Long getDeviceHourDays() {
        return deviceHourDays;
    }

    public void setDeviceHourDays(Long deviceHourDays) {
        this.deviceHourDays = deviceHourDays;
    }

    public DeviceVo getDeviceVo() {
        return deviceVo;
    }

    public void setDeviceVo(DeviceVo deviceVo) {
        this.deviceVo = deviceVo;
    }
}