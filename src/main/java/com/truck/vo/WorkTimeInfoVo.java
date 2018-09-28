package com.truck.vo;

import java.math.BigDecimal;
import java.util.List;

public class WorkTimeInfoVo {

    private Integer deviceId;

    private DeviceVo deviceVo;

    private List<WorkTimeVo> workTimeVos;

    private List<WorkTimeDiffVo> workTimeDiffVos;

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

    public List<WorkTimeVo> getWorkTimeVos() {
        return workTimeVos;
    }

    public void setWorkTimeVos(List<WorkTimeVo> workTimeVos) {
        this.workTimeVos = workTimeVos;
    }

    public List<WorkTimeDiffVo> getWorkTimeDiffVos() {
        return workTimeDiffVos;
    }

    public void setWorkTimeDiffVos(List<WorkTimeDiffVo> workTimeDiffVos) {
        this.workTimeDiffVos = workTimeDiffVos;
    }


}