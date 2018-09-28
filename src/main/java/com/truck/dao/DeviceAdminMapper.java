package com.truck.dao;

import com.truck.pojo.DeviceAdmin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceAdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceAdmin record);

    int insertSelective(DeviceAdmin record);

    DeviceAdmin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceAdmin record);

    int updateByPrimaryKey(DeviceAdmin record);

    int updateNoUseAll(DeviceAdmin record);

    DeviceAdmin selectByIdAdminId(@Param("adminId")Integer adminId, @Param("id")Integer id);

    DeviceAdmin selectByAdminIdStatusCategoryNo(@Param("adminId")Integer adminId, @Param("status")Integer status,@Param("categoryNo")Integer categoryNo);

    List<DeviceAdmin> selectByAdminIdStatus(@Param("adminId")Integer adminId, @Param("deviceId")Integer deviceId,@Param("status")Integer status);

    List<DeviceAdmin> selectByCategoryNoStatus(@Param("categoryNo")Integer categoryNo,@Param("status")Integer status);


    int checkDeviceAdmin(@Param("adminId")Integer adminId);

    int checkDevice(@Param("deviceId")Integer deviceId);

    Integer selectTotalFrequencyAllDevice( @Param("searchDate") String searchDate,
                                       @Param("beginDate") String beginDate,
                                       @Param("endDate") String endDate);
}