package com.truck.dao;

import com.truck.pojo.WorkTime;
import com.truck.pojo.WorkTime;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface WorkTimeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorkTime record);

    int insertSelective(WorkTime record);

    WorkTime selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorkTime record);

    int updateByPrimaryKey(WorkTime record);

    List<WorkTime> selectByAdminIdDate(@Param("adminId") Integer adminId,
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

    List<Date> selectDate(@Param("deviceId")Integer deviceId, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

}