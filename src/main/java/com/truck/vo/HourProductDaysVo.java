package com.truck.vo;

import java.util.List;

public class HourProductDaysVo {

    private String date;

    private List<HourVo> hourVos;

    private List<HourDiffVo> hourDiffVos;

    private Long deviceHourDay;

    public HourProductDaysVo() {
        super();
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Long getDeviceHourDay() {
        return deviceHourDay;
    }

    public void setDeviceHourDay(Long deviceHourDay) {
        this.deviceHourDay = deviceHourDay;
    }
}