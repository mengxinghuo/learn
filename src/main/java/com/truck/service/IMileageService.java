package com.truck.service;

import com.github.pagehelper.PageInfo;
import com.truck.common.ServerResponse;
import com.truck.pojo.Mileage;
import com.truck.vo.MileageVo;

public interface IMileageService {

    ServerResponse add( Mileage mileage);

    ServerResponse del(Integer adminId,Integer mileageId);

    ServerResponse update(Integer adminId,Mileage mileage);

    ServerResponse<MileageVo> select(Integer adminId, Integer contractId);

//    ServerResponse list(Integer adminId, Integer driverId, Integer deviceId, String searchDate, String beginDate, String endDate, int pageNum, int pageSize);


    ServerResponse listDaysCondition(Integer adminId,Integer driverId,Integer deviceId,String searchDate,String beginDate,String endDate, int pageNum, int pageSize);

    ServerResponse listCondition(Integer adminId,Integer driverId,Integer deviceId,String searchDate,String beginDate,String endDate, int pageNum, int pageSize);

}
