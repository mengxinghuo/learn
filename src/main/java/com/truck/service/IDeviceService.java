package com.truck.service;

import com.truck.common.ServerResponse;
import com.truck.pojo.Device;
import com.truck.vo.DeviceVo;

/**
 * Created by ming
 */
public interface IDeviceService {

    ServerResponse add(Integer adminId, Device device);
    ServerResponse<String> del(Integer adminId, Integer deviceId);
    ServerResponse update(Integer adminId, Device device);
    ServerResponse<Device> select(Integer adminId, Integer deviceId);
    ServerResponse list(Integer adminId, Integer productId,Integer categoryNo,String deviceNo,int pageNum, int pageSize);
    ServerResponse listNoUsing(Integer adminId, Integer productId,Integer categoryNo,String deviceNo,int pageNum, int pageSize);
    ServerResponse listAllTotal(Integer adminId, Integer driverId,Integer deviceId,String searchDate,String beginDate,String endDate,Integer productId,Integer categoryNo,String deviceNo,int pageNum, int pageSize);
    DeviceVo assembleDeviceVo(Device device);
    ServerResponse listProductShow();
    ServerResponse searchProductShow(Integer categoryNo);
    ServerResponse registerUser(String deviceName);
}
