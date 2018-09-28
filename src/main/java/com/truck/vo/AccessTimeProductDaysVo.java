package com.truck.vo;

import java.math.BigDecimal;
import java.util.List;

public class AccessTimeProductDaysVo {

    private String date;

    private List<AccessTimeVo> accessTimeVos;

    private List<AccessTimeDiffVo> accessTimeDiffVos;

    private BigDecimal roundTriptimes;



    public AccessTimeProductDaysVo() {
        super();
    }

    public List<AccessTimeVo> getAccessTimeVos() {
        return accessTimeVos;
    }

    public void setAccessTimeVos(List<AccessTimeVo> accessTimeVos) {
        this.accessTimeVos = accessTimeVos;
    }

    public List<AccessTimeDiffVo> getAccessTimeDiffVos() {
        return accessTimeDiffVos;
    }

    public void setAccessTimeDiffVos(List<AccessTimeDiffVo> accessTimeDiffVos) {
        this.accessTimeDiffVos = accessTimeDiffVos;
    }

    public BigDecimal getRoundTriptimes() {
        return roundTriptimes;
    }

    public void setRoundTriptimes(BigDecimal roundTriptimes) {
        this.roundTriptimes = roundTriptimes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}