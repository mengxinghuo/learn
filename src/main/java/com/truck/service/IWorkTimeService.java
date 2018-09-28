package com.truck.service;

import com.truck.common.ServerResponse;
import com.truck.pojo.WorkTime;
import com.truck.vo.WorkTimeVo;

public interface IWorkTimeService {

    ServerResponse add(WorkTime workTime);

    ServerResponse del(Integer adminId, Integer workTimeId);

    ServerResponse update(Integer adminId, WorkTime workTime);

    ServerResponse<WorkTimeVo> select(Integer adminId, Integer contractId);

    ServerResponse list(Integer adminId, Integer driverId, Integer deviceId, String searchDate, String beginDate, String endDate, int pageNum, int pageSize);

    ServerResponse listCondition(Integer adminId,Integer driverId,Integer deviceId,String searchDate,String beginDate,String endDate, int pageNum, int pageSize);

}
