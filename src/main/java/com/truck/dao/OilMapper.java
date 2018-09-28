package com.truck.dao;

import com.truck.pojo.AccessTime;
import com.truck.pojo.Oil;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface OilMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Oil record);

    int insertSelective(Oil record);

    Oil selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Oil record);

    int updateByPrimaryKey(Oil record);


    int deleteByIdAdminId(@Param("adminId")Integer adminId,@Param("id")Integer id);

    Oil selectByIdAdminId(@Param("adminId")Integer adminId, @Param("id")Integer id);

    List<Oil> selectByAdminIdDate(@Param("adminId") Integer adminId,
                                         @Param("driverId") Integer driverId,
                                         @Param("deviceId") Integer deviceId,
                                        @Param("categoryNo") Integer categoryNo,
                                         @Param("searchDate") String searchDate,
                                         @Param("beginDate") String beginDate,
                                         @Param("endDate") String endDate);


    Map selectNumByAdminIdDate(@Param("adminId") Integer adminId,
                               @Param("driverId") Integer driverId,
                               @Param("deviceId") Integer deviceId,
                               @Param("categoryNo") Integer categoryNo,
                               @Param("searchDate") String searchDate,
                               @Param("beginDate") String beginDate,
                               @Param("endDate") String endDate);
}