package com.truck.service;

import com.truck.common.ServerResponse;
import com.truck.pojo.AccessTime;
import com.truck.vo.AccessTimeVo;


public interface IAccessTimeService {

    ServerResponse add(Integer adminId,Integer deviceAdminId);

    ServerResponse del(Integer adminId,Integer accessTimeId);

    ServerResponse update(Integer adminId,AccessTime accessTime);

    ServerResponse<AccessTimeVo> select(Integer adminId, Integer id);

    ServerResponse list(Integer adminId,Integer driverId,Integer deviceId,String searchDate,String beginDate,String endDate, int pageNum, int pageSize);

    //卡车
    ServerResponse listByAdmin(Integer adminId,String adminName,String searchDate,String beginDate,String endDate, int pageNum, int pageSize);

    ServerResponse listByAdminRank(String searchDate,String beginDate,String endDate, int pageNum, int pageSize);

    ServerResponse listByDevice(Integer deviceId,Integer categoryNo,String searchDate,String beginDate,String endDate, int pageNum, int pageSize);

    ServerResponse listByDeviceRank(Integer categoryNo,String searchDate,String beginDate,String endDate, int pageNum, int pageSize);


    //挖掘机
    ServerResponse listWaJiByAdmin(Integer adminId,String adminName,String searchDate,String beginDate,String endDate, int pageNum, int pageSize);

    ServerResponse listWaJiByAdminRank(String searchDate,String beginDate,String endDate, int pageNum, int pageSize);

    ServerResponse listWaJiByDevice(Integer deviceId,Integer categoryNo,String searchDate,String beginDate,String endDate, int pageNum, int pageSize);

    ServerResponse listWaJiByDeviceRank(Integer categoryNo,String searchDate,String beginDate,String endDate, int pageNum, int pageSize);
}
