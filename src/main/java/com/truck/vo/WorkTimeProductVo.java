package com.truck.vo;

import java.math.BigDecimal;
import java.util.List;

public class WorkTimeProductVo {

    private Integer deviceId;

    private List<WorkTimeProductDaysVo> workTimeProductDaysVos;

    private BigDecimal deviceWorkTimeDays;

    private DeviceVo deviceVo;

    public WorkTimeProductVo() {
        super();
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public List<WorkTimeProductDaysVo> getWorkTimeProductDaysVos() {
        return workTimeProductDaysVos;
    }

    public void setWorkTimeProductDaysVos(List<WorkTimeProductDaysVo> workTimeProductDaysVos) {
        this.workTimeProductDaysVos = workTimeProductDaysVos;
    }

    public BigDecimal getDeviceWorkTimeDays() {
        return deviceWorkTimeDays;
    }

    public void setDeviceWorkTimeDays(BigDecimal deviceWorkTimeDays) {
        this.deviceWorkTimeDays = deviceWorkTimeDays;
    }

    public DeviceVo getDeviceVo() {
        return deviceVo;
    }

    public void setDeviceVo(DeviceVo deviceVo) {
        this.deviceVo = deviceVo;
    }
}