package com.truck.dao;

import com.truck.pojo.Hour;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface HourMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Hour record);

    int insertSelective(Hour record);

    Hour selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hour record);

    int updateByPrimaryKey(Hour record);


    int deleteByIdAdminId(@Param("adminId") Integer adminId, @Param("id") Integer id);

    Hour selectByIdAdminId(@Param("adminId") Integer adminId, @Param("id") Integer id);

    List<Hour> selectByAdminIdDate(@Param("adminId") Integer adminId,
                                   @Param("driverId") Integer driverId,
                                   @Param("deviceId") Integer deviceId,
                                   @Param("searchDate") String searchDate,
                                   @Param("beginDate") String beginDate,
                                   @Param("endDate") String endDate);


    Integer selectNumByAdminIdDate(@Param("adminId") Integer adminId,
                                   @Param("driverId") Integer driverId,
                                   @Param("deviceId") Integer deviceId,
                                   @Param("searchDate") String searchDate,
                                   @Param("beginDate") String beginDate,
                                   @Param("endDate") String endDate);

    List<Date> selectDate(@Param("deviceId") Integer deviceId, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    Hour checkDeviceHour(@Param("adminId") Integer adminId, @Param("deviceId") Integer deviceId);

}