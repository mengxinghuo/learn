package com.truck.vo;

import java.util.List;

public class MileageProductVo {

    private Integer deviceId;

    private DeviceVo deviceVo;

    private List<MileageProductDaysVo> mileageProductDaysVos;

    private Long deviceMileageDays;

    public MileageProductVo() {
        super();
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public List<MileageProductDaysVo> getMileageProductDaysVos() {
        return mileageProductDaysVos;
    }

    public void setMileageProductDaysVos(List<MileageProductDaysVo> mileageProductDaysVos) {
        this.mileageProductDaysVos = mileageProductDaysVos;
    }

    public Long getDeviceMileageDays() {
        return deviceMileageDays;
    }

    public void setDeviceMileageDays(Long deviceMileageDays) {
        this.deviceMileageDays = deviceMileageDays;
    }

    public DeviceVo getDeviceVo() {
        return deviceVo;
    }

    public void setDeviceVo(DeviceVo deviceVo) {
        this.deviceVo = deviceVo;
    }
}