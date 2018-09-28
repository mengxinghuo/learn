package com.truck.vo;

import java.util.List;

public class MileageInfoVo {

    private Integer deviceId;

    private DeviceVo deviceVo;

    private List<MileageVo> mileageVos;

    private List<MileageDiffVo> mileageDiffVos;

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

    public List<MileageVo> getMileageVos() {
        return mileageVos;
    }

    public void setMileageVos(List<MileageVo> mileageVos) {
        this.mileageVos = mileageVos;
    }

    public List<MileageDiffVo> getMileageDiffVos() {
        return mileageDiffVos;
    }

    public void setMileageDiffVos(List<MileageDiffVo> mileageDiffVos) {
        this.mileageDiffVos = mileageDiffVos;
    }
}