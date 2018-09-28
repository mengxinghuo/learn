package com.truck.vo;

import java.util.Map;

public class DeviceTotalInfoVo {

    private  Map accessTimeProductVoMap;

    private  Map mileageProductVoMap;

    private Map oilVoMap;

    private Map workTimeProductMap;

//    private Integer deviceId;

    public Map getAccessTimeProductVoMap() {
        return accessTimeProductVoMap;
    }

    public void setAccessTimeProductVoMap(Map accessTimeProductVoMap) {
        this.accessTimeProductVoMap = accessTimeProductVoMap;
    }

    public Map getMileageProductVoMap() {
        return mileageProductVoMap;
    }

    public void setMileageProductVoMap(Map mileageProductVoMap) {
        this.mileageProductVoMap = mileageProductVoMap;
    }

    public Map getOilVoMap() {
        return oilVoMap;
    }

    public void setOilVoMap(Map oilVoMap) {
        this.oilVoMap = oilVoMap;
    }

    public Map getWorkTimeProductMap() {
        return workTimeProductMap;
    }

    public void setWorkTimeProductMap(Map workTimeProductMap) {
        this.workTimeProductMap = workTimeProductMap;
    }
}