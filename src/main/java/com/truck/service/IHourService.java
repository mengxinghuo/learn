package com.truck.service;

import com.truck.common.ServerResponse;
import com.truck.pojo.Hour;
import com.truck.vo.HourVo;

public interface IHourService {

    ServerResponse add( Hour hour);

    ServerResponse del(Integer adminId, Integer hourId);

    ServerResponse update(Integer adminId, Hour hour);

    ServerResponse<HourVo> select(Integer adminId, Integer contractId);

//    ServerResponse list(Integer adminId, Integer driverId, Integer deviceId, String searchDate, String beginDate, String endDate, int pageNum, int pageSize);


    ServerResponse listDaysCondition(Integer adminId, Integer driverId, Integer deviceId, String searchDate, String beginDate, String endDate, int pageNum, int pageSize);

    ServerResponse listCondition(Integer adminId, Integer driverId, Integer deviceId, String searchDate, String beginDate, String endDate, int pageNum, int pageSize);

}
