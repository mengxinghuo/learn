package com.truck.dao;

import com.truck.pojo.AccessTime;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AccessTimeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccessTime record);

    int insertSelective(AccessTime record);

    AccessTime selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccessTime record);

    int updateByPrimaryKey(AccessTime record);


    int deleteByIdAdminId(@Param("adminId")Integer adminId,@Param("id")Integer id);


    AccessTime selectByIdAdminId(@Param("adminId")Integer adminId,@Param("id")Integer id);

    AccessTime selectLastByDeviceId(@Param("deviceId")Integer deviceId);

    List<AccessTime> selectByAdminIdDate(@Param("adminId") Integer adminId,
                                         @Param("driverId") Integer driverId,
                                         @Param("deviceId") Integer deviceId,
                                         @Param("searchDate") String searchDate,
                                         @Param("beginDate") String beginDate,
                                         @Param("endDate") String endDate);

    List<Date> selectDate(@Param("deviceId")Integer deviceId, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    Integer selectNumByAdminIdDate(@Param("adminId") Integer adminId,
                                   @Param("driverId") Integer driverId,
                                   @Param("deviceId") Integer deviceId,
                                   @Param("searchDate") String searchDate,
                                   @Param("beginDate") String beginDate,
                                   @Param("endDate") String endDate);
         // 卡车
    List selectByAdminDate(@Param("adminId") Integer adminId,
                                 @Param("adminName") String adminName,
                                 @Param("searchDate") String searchDate,
                                 @Param("beginDate") String beginDate,
                                 @Param("endDate") String endDate);

    List<Map> selectRankByAdminDate(
            @Param("searchDate") String searchDate,
            @Param("beginDate") String beginDate,
            @Param("endDate") String endDate);

    List selectByDeviceDate(
                            @Param("deviceId") Integer deviceId,
                            @Param("categoryNo") Integer categoryNo,
                             @Param("searchDate") String searchDate,
                             @Param("beginDate") String beginDate,
                             @Param("endDate") String endDate);

    List<Map> selectRankByDeviceDate(
                             @Param("categoryNo") Integer categoryNo,
                             @Param("searchDate") String searchDate,
                             @Param("beginDate") String beginDate,
                             @Param("endDate") String endDate);
         // 挖掘机
    List selectWaJiByAdminDate(@Param("adminId") Integer adminId,
                                 @Param("adminName") String adminName,
                                 @Param("searchDate") String searchDate,
                                 @Param("beginDate") String beginDate,
                                 @Param("endDate") String endDate);

    List<Map> selectWaJiRankByAdminDate(
            @Param("searchDate") String searchDate,
            @Param("beginDate") String beginDate,
            @Param("endDate") String endDate);

    List selectWaJiByDeviceDate(
                            @Param("deviceId") Integer deviceId,
                            @Param("categoryNo") Integer categoryNo,
                             @Param("searchDate") String searchDate,
                             @Param("beginDate") String beginDate,
                             @Param("endDate") String endDate);

    List<Map> selectWaJiRankByDeviceDate(
                             @Param("categoryNo") Integer categoryNo,
                             @Param("searchDate") String searchDate,
                             @Param("beginDate") String beginDate,
                             @Param("endDate") String endDate);
}