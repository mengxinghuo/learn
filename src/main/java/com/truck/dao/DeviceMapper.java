package com.truck.dao;

import com.truck.pojo.AccessTime;
import com.truck.pojo.Device;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);


    int deleteByDeviceIdAdminId(@Param("adminId")Integer adminId, @Param("id")Integer id);

    Device selectByIdAdminId(@Param("adminId")Integer adminId, @Param("id")Integer id);

    List<Device> selectByAdminIdDate(@Param("adminId") Integer adminId,
                                       @Param("searchDate") String searchDate,
                                       @Param("beginDate") String beginDate,
                                       @Param("endDate") String endDate);

    List<Device> selectByAdminIdNo(@Param("adminId") Integer adminId,
                                   @Param("productId") Integer productId,
                                   @Param("categoryNo") Integer categoryNo,
                                   @Param("deviceNo") String deviceNo);

    List<Device> selectByAdminIdNoStatusNoUsing(@Param("adminId") Integer adminId,
                                               @Param("productId") Integer productId,
                                               @Param("categoryNo") Integer categoryNo,
                                               @Param("deviceNo") String deviceNo);

    int checkDeviceNo(@Param("deviceNo") String deviceNo,@Param("categoryNo") Integer categoryNo);

}