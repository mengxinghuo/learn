package com.truck.dao;

import com.truck.pojo.Mileage;
import com.truck.pojo.Mileage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MileageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Mileage record);

    int insertSelective(Mileage record);

    Mileage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Mileage record);

    int updateByPrimaryKey(Mileage record);


    int deleteByIdAdminId(@Param("adminId")Integer adminId,@Param("id")Integer id);

    Mileage selectByIdAdminId(@Param("adminId")Integer adminId, @Param("id")Integer id);

    List<Mileage> selectByAdminIdDate(@Param("adminId") Integer adminId,
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

    Mileage checkDeviceMileage(@Param("adminId")Integer adminId, @Param("deviceId")Integer deviceId);

}