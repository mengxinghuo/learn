package com.truck.vo;

import java.math.BigDecimal;
import java.util.List;

public class MileageProductDaysVo {

    private String date;

    private List<MileageVo> mileageVos;

    private List<MileageDiffVo> mileageDiffVos;

    private Long deviceMileageDay;

    public MileageProductDaysVo() {
        super();
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Long getDeviceMileageDay() {
        return deviceMileageDay;
    }

    public void setDeviceMileageDay(Long deviceMileageDay) {
        this.deviceMileageDay = deviceMileageDay;
    }
}