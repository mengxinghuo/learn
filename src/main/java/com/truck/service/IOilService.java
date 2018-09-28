package com.truck.service;

import com.truck.common.ServerResponse;
import com.truck.pojo.Oil;
import com.truck.vo.OilVo;

public interface IOilService {

    ServerResponse add(Oil oil);

    ServerResponse del(Integer adminId,Integer oilId);

    ServerResponse update(Integer adminId,Oil oil);

    ServerResponse<OilVo> select(Integer adminId, Integer contractId);

    ServerResponse list(Integer adminId, Integer driverId, Integer deviceId, Integer categoryNo,String searchDate, String beginDate, String endDate, int pageNum, int pageSize);

}
