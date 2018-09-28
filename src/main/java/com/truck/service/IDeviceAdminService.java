package com.truck.service;

import com.truck.common.ServerResponse;
import com.truck.pojo.DeviceAdmin;

/**
 * Created by ming
 */
public interface IDeviceAdminService {

    ServerResponse add(DeviceAdmin deviceAdmin);
    ServerResponse<String> del(Integer adminId, Integer deviceAdminId);
    ServerResponse update(Integer adminId, DeviceAdmin deviceAdmin);
    ServerResponse noUse(Integer adminId, DeviceAdmin deviceAdmin);
    ServerResponse noUseAll();
    ServerResponse<DeviceAdmin> select(Integer adminId, Integer deviceAdminId);
    ServerResponse selectUsingSelf(Integer adminId);
    ServerResponse list(Integer adminId, Integer deviceId,Integer status,int pageNum, int pageSize);
    ServerResponse listUsing(Integer categoryNo,Integer status,int pageNum, int pageSize);
}
