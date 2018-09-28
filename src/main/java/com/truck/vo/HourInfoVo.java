package com.truck.vo;

import java.util.List;

public class HourInfoVo {

    private Integer deviceId;

    private DeviceVo deviceVo;

    private List<HourVo> hourVos;

    private List<HourDiffVo> hourDiffVos;

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceVo getDeviceVo() {
        return deviceVo;
    }

    public void setDeviceVo(DeviceVo deviceVo) {
        this.deviceVo = deviceVo;
    }

    public List<HourVo> getHourVos() {
        return hourVos;
    }

    public void setHourVos(List<HourVo> hourVos) {
        this.hourVos = hourVos;
    }

    public List<HourDiffVo> getHourDiffVos() {
        return hourDiffVos;
    }

    public void setHourDiffVos(List<HourDiffVo> hourDiffVos) {
        this.hourDiffVos = hourDiffVos;
    }
}