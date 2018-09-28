package com.truck.vo;

import java.math.BigDecimal;
import java.util.List;

public class WorkTimeProductDaysVo {

    private String date;

    private List<WorkTimeVo> workTimeVos;

    private List<WorkTimeDiffVo> workTimeDiffVos;

    private BigDecimal deviceWorkTimeDay;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public BigDecimal getDeviceWorkTimeDay() {
        return deviceWorkTimeDay;
    }

    public void setDeviceWorkTimeDay(BigDecimal deviceWorkTimeDay) {
        this.deviceWorkTimeDay = deviceWorkTimeDay;
    }
}