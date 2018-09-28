package com.truck.vo;

import java.math.BigDecimal;
import java.util.List;

public class AccessTimeProductVo {

    private Integer deviceId;

    private BigDecimal totalRoundTriptimes;

    private List<AccessTimeProductDaysVo> accessTimeProductDaysVos;

    public AccessTimeProductVo() {
        super();
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public List<AccessTimeProductDaysVo> getAccessTimeProductDaysVos() {
        return accessTimeProductDaysVos;
    }

    public void setAccessTimeProductDaysVos(List<AccessTimeProductDaysVo> accessTimeProductDaysVos) {
        this.accessTimeProductDaysVos = accessTimeProductDaysVos;
    }

    public BigDecimal getTotalRoundTriptimes() {
        return totalRoundTriptimes;
    }

    public void setTotalRoundTriptimes(BigDecimal totalRoundTriptimes) {
        this.totalRoundTriptimes = totalRoundTriptimes;
    }
}