package com.truck.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.truck.common.Const;
import com.truck.common.ServerResponse;
import com.truck.dao.AccessTimeMapper;
import com.truck.dao.AdminMapper;
import com.truck.dao.DeviceAdminMapper;
import com.truck.dao.DeviceMapper;
import com.truck.pojo.AccessTime;
import com.truck.pojo.Admin;
import com.truck.pojo.DeviceAdmin;
import com.truck.service.IAccessTimeService;
import com.truck.util.BigDecimalUtil;
import com.truck.util.DateTimeUtil;
import com.truck.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("iAccessTimeService")
public class AccessTimeServiceImpl implements IAccessTimeService {

    @Autowired
    private AccessTimeMapper accessTimeMapper;
    @Autowired
    private DeviceAdminMapper deviceAdminMapper;

    public ServerResponse add( Integer adminId,Integer deviceAdminId){
        AccessTime accessTime = new AccessTime();
        if (deviceAdminId== null ) {
            return ServerResponse.createByErrorMessage("请选择设备");
        }
        //driverId 记录中间表里面的卡车id
        accessTime.setDriverId(deviceAdminId);

        accessTime.setAdminId(adminId);
        DeviceAdmin deviceAdmin = deviceAdminMapper.selectByPrimaryKey(deviceAdminId);
        if(deviceAdmin ==null || deviceAdmin.getStatus() == Const.DeviceAdminStatusEnum.NO_USING.getCode()) {
            return ServerResponse.createByErrorMessage("该设备没有用户在使用");
        }

        //previousId 记录中间表里面的挖机id
        DeviceAdmin deviceAdmin1 = deviceAdminMapper.selectByAdminIdStatusCategoryNo(adminId,Const.DeviceAdminStatusEnum.USING.getCode(),Const.DeviceCategoryEnum.DIG_MACHINE.getCode());
        if (deviceAdmin1 ==null){
            return ServerResponse.createByErrorMessage("您必须是挖机司机，必须有正在使用的设备");
        }
        AccessTime accessTime1 = accessTimeMapper.selectLastByDeviceId(deviceAdmin.getDeviceId());
        if(accessTime1!=null){
            //判断时间不能小于十分钟
            if(BigDecimalUtil.div(new Date().getTime()-accessTime1.getCreateTime().getTime(),1000*60).compareTo(new BigDecimal(0.5))<0){
                return ServerResponse.createByErrorMessage("连续记录时间不能小于半分钟");
            }
        }

        deviceAdmin1.setFrequency(deviceAdmin1.getFrequency()+1);
        deviceAdminMapper.updateByPrimaryKeySelective(deviceAdmin1);
        accessTime.setPreviousId(deviceAdmin1.getId());
        accessTime.setDeviceId(deviceAdmin.getDeviceId());
        int rowCount = accessTimeMapper.insertSelective(accessTime);
        if(rowCount > 0){
            BigDecimal remainTime= new BigDecimal(30);
            Map result = Maps.newHashMap();
            result.put("id",accessTime.getId());
            result.put("remainTime",remainTime);
            return ServerResponse.createBySuccess("新建设备出入时间记录成功",result);
        }
        return ServerResponse.createByErrorMessage("新建设备出入时间记录失败");
    }

    public ServerResponse<String> del( Integer adminId,Integer accessTimeId){
        int rowCount = accessTimeMapper.deleteByIdAdminId( adminId,accessTimeId);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("删除设备出入时间记录成功");
        }
        return ServerResponse.createByErrorMessage("删除设备出入时间记录失败");
    }

    public ServerResponse update( Integer adminId,AccessTime accessTime){
        if (adminId != null) {
            accessTime.setAdminId(adminId);
        }
        int rowCount = accessTimeMapper.updateByPrimaryKeySelective(accessTime);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("更新设备出入时间记录成功");
        }
        return ServerResponse.createByErrorMessage("更新设备出入时间记录失败");
    }

    public ServerResponse<AccessTimeVo> select(Integer adminId, Integer id){
        AccessTime accessTime = accessTimeMapper.selectByIdAdminId(adminId,id);

        if(accessTime == null){
            return ServerResponse.createByErrorMessage("无法查询到该设备出入时间记录");
        }
        AccessTimeVo accessTimeVo = assembleAccessTimeVo(accessTime);
        return ServerResponse.createBySuccess("查询设备出入时间记录成功",accessTimeVo);
    }


    public ServerResponse list(Integer adminId,Integer driverId,Integer deviceId,String searchDate,String beginDate,String endDate, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);

        List<AccessTime> accessTimeList = accessTimeMapper.selectByAdminIdDate(adminId,driverId,deviceId,searchDate,beginDate,endDate);

        Set<AccessTime> accessTimeSet = Sets.newHashSet();
        for (AccessTime accessTimeItem : accessTimeList) {
            accessTimeSet.add(accessTimeItem);
        }

        List<AccessTimeProductVo> accessTimeProductVos = Lists.newArrayList();
        BigDecimal AllDeviceTotalRoundTriptimes = BigDecimal.ZERO;

        for (AccessTime accessTime : accessTimeSet) {
            AccessTimeProductVo accessTimeProductVo = new AccessTimeProductVo();
            List<AccessTimeProductDaysVo> accessTimeProductDaysVoList = Lists.newArrayList();
            BigDecimal totalRoundTriptimes = BigDecimal.ZERO;
            //搜索单天
            if (!StringUtils.isEmpty(searchDate)) {

                AccessTimeProductDaysVo accessTimeProductDaysVo = new AccessTimeProductDaysVo();
                List<AccessTime> accessTimeList2 = accessTimeMapper.selectByAdminIdDate(adminId, driverId, accessTime.getDeviceId(), searchDate, beginDate, endDate);
                List<AccessTimeVo> accessTimeVos = Lists.newArrayList();

                for (AccessTime accessTime2 : accessTimeList2) {
                    AccessTimeVo accessTimeVo = assembleAccessTimeVo(accessTime2);
                    accessTimeVos.add(accessTimeVo);
                }

                List<AccessTimeDiffVo> accessTimeDiffVos = assembleAccessTimeDiffVoList(accessTimeList2);

                accessTimeProductDaysVo.setAccessTimeVos(accessTimeVos);
                accessTimeProductDaysVo.setAccessTimeDiffVos(accessTimeDiffVos);
                accessTimeProductDaysVo.setRoundTriptimes(BigDecimalUtil.div(accessTimeVos.size(), 1));
                totalRoundTriptimes = BigDecimalUtil.add(totalRoundTriptimes.doubleValue() ,accessTimeProductDaysVo.getRoundTriptimes().doubleValue());
                accessTimeProductDaysVo.setDate(searchDate);
                accessTimeProductDaysVoList.add(accessTimeProductDaysVo);

                accessTimeProductVo.setAccessTimeProductDaysVos(accessTimeProductDaysVoList);
            }else{
                List<String> stringList = Lists.newArrayList();
                List<Date> dateList = accessTimeMapper.selectDate(accessTime.getDeviceId(),beginDate,endDate);
                for (Date date : dateList) {
                    String dateStr = DateTimeUtil.dateToStr(date,"yyyy-MM-dd");
                    stringList.add(dateStr);
                }
                for (String str : stringList) {
                    AccessTimeProductDaysVo accessTimeProductDaysVo = new AccessTimeProductDaysVo();
                    List<AccessTime> accessTimeList2 = accessTimeMapper.selectByAdminIdDate(adminId, driverId, accessTime.getDeviceId(), str, beginDate, endDate);
                    List<AccessTimeVo> accessTimeVos = Lists.newArrayList();

                    for (AccessTime accessTime2 : accessTimeList2) {
                        AccessTimeVo accessTimeVo = assembleAccessTimeVo(accessTime2);
                        accessTimeVos.add(accessTimeVo);
                    }

                    List<AccessTimeDiffVo> accessTimeDiffVos = assembleAccessTimeDiffVoList(accessTimeList2);

                    accessTimeProductDaysVo.setAccessTimeDiffVos(accessTimeDiffVos);
                    accessTimeProductDaysVo.setAccessTimeVos(accessTimeVos);
                    accessTimeProductDaysVo.setRoundTriptimes(BigDecimalUtil.div(accessTimeVos.size(), 1));
                    totalRoundTriptimes = BigDecimalUtil.add(totalRoundTriptimes.doubleValue() ,accessTimeProductDaysVo.getRoundTriptimes().doubleValue());
                    accessTimeProductDaysVo.setDate(str);
                    accessTimeProductDaysVoList.add(accessTimeProductDaysVo);

                }
                accessTimeProductVo.setAccessTimeProductDaysVos(accessTimeProductDaysVoList);

            }

            accessTimeProductVo.setDeviceId(accessTime.getDeviceId());
            accessTimeProductVo.setTotalRoundTriptimes(totalRoundTriptimes);
            AllDeviceTotalRoundTriptimes = BigDecimalUtil.add(AllDeviceTotalRoundTriptimes.doubleValue() ,accessTimeProductVo.getTotalRoundTriptimes().doubleValue());
            accessTimeProductVos.add(accessTimeProductVo);
        }
        PageInfo pageInfo = new PageInfo(accessTimeProductVos);

        Map map = Maps.newHashMap();
        Integer totalNum = accessTimeMapper.selectNumByAdminIdDate(adminId, driverId, null, searchDate, beginDate, endDate);
        if (totalNum != null) {
            map.put("AllDeviceTotalRoundTriptimes",AllDeviceTotalRoundTriptimes);
            map.put("accessTimeProductVos",pageInfo);
        }
        return ServerResponse.createBySuccess(map);
    }

    //卡车
    public ServerResponse listByAdmin(Integer adminId,String adminName,String searchDate,String beginDate,String endDate, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List accessTimeList = accessTimeMapper.selectByAdminDate(adminId,adminName,searchDate,beginDate,endDate);
        PageInfo pageInfo = new PageInfo(accessTimeList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse listByAdminRank(String searchDate,String beginDate,String endDate, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Map> accessTimeList = accessTimeMapper.selectRankByAdminDate(searchDate,beginDate,endDate);
        Integer count = pageSize*(pageNum-1)+1;

        for (Map map : accessTimeList) {
            map.put("count",count);
            count++;
        }
        PageInfo pageInfo = new PageInfo(accessTimeList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse listByDevice(Integer deviceId,Integer categoryNo,String searchDate,String beginDate,String endDate, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List accessTimeList = accessTimeMapper.selectByDeviceDate(deviceId,categoryNo,searchDate,beginDate,endDate);
        PageInfo pageInfo = new PageInfo(accessTimeList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse listByDeviceRank(Integer categoryNo,String searchDate,String beginDate,String endDate, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Map> accessTimeList = accessTimeMapper.selectRankByDeviceDate(categoryNo,searchDate,beginDate,endDate);
        Integer count = pageSize*(pageNum-1)+1;

        for (Map map : accessTimeList) {
           map.put("count",count);
           count++;
        }
        PageInfo pageInfo = new PageInfo(accessTimeList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    //挖机
    public ServerResponse listWaJiByAdmin(Integer adminId,String adminName,String searchDate,String beginDate,String endDate, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List accessTimeList = accessTimeMapper.selectWaJiByAdminDate(adminId,adminName,searchDate,beginDate,endDate);
        PageInfo pageInfo = new PageInfo(accessTimeList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse listWaJiByAdminRank(String searchDate,String beginDate,String endDate, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Map> accessTimeList = accessTimeMapper.selectWaJiRankByAdminDate(searchDate,beginDate,endDate);
        Integer count = pageSize*(pageNum-1)+1;
        for (Map map : accessTimeList) {
            map.put("count",count);
            count++;
        }
        PageInfo pageInfo = new PageInfo(accessTimeList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse listWaJiByDevice(Integer deviceId,Integer categoryNo,String searchDate,String beginDate,String endDate, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List accessTimeList = accessTimeMapper.selectWaJiByDeviceDate(deviceId,categoryNo,searchDate,beginDate,endDate);
        PageInfo pageInfo = new PageInfo(accessTimeList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse listWaJiByDeviceRank(Integer categoryNo,String searchDate,String beginDate,String endDate, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Map> accessTimeList = accessTimeMapper.selectWaJiRankByDeviceDate(categoryNo,searchDate,beginDate,endDate);
        Integer count = pageSize*(pageNum-1)+1;

        for (Map map : accessTimeList) {
           map.put("count",count);
           count++;
        }
        PageInfo pageInfo = new PageInfo(accessTimeList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public AccessTimeVo assembleAccessTimeVo(AccessTime accessTime) {
        AccessTimeVo accessTimeVo = new AccessTimeVo();
        accessTimeVo.setId(accessTime.getId());
        accessTimeVo.setAdminId(accessTime.getAdminId());
        accessTimeVo.setDeviceId(accessTime.getDeviceId());
        accessTimeVo.setAddress(accessTime.getAddress());
        accessTimeVo.setPreviousId(accessTime.getPreviousId());
        accessTimeVo.setCreateTime(DateTimeUtil.dateToStr(accessTime.getCreateTime()));
        accessTimeVo.setUpdateTime(DateTimeUtil.dateToStr(accessTime.getUpdateTime()));
        return accessTimeVo;
    }

    public List<AccessTimeDiffVo> assembleAccessTimeDiffVoList(List<AccessTime> accessTimeList ){
        List<AccessTimeDiffVo> accessTimeDiffVos = Lists.newArrayList();
        for (int i = 0; i < accessTimeList.size()-1; i++) {

            AccessTimeDiffVo accessTimeDiffVo = new AccessTimeDiffVo();
            accessTimeDiffVo.setAdminId(accessTimeList.get(i).getAdminId());
            accessTimeDiffVo.setDeviceId(accessTimeList.get(i).getDeviceId());
            accessTimeDiffVo.setDriverId(accessTimeList.get(i).getDriverId());
            //地址
            accessTimeDiffVo.setStartAddress(accessTimeList.get(i).getAddress());
            accessTimeDiffVo.setEndAddress(accessTimeList.get(i+1).getAddress());

            accessTimeDiffVo.setStartTimeStr(DateTimeUtil.dateToStr(accessTimeList.get(i).getCreateTime()));
            accessTimeDiffVo.setEndTimeStr(DateTimeUtil.dateToStr(accessTimeList.get(i+1).getCreateTime()));
            //相邻两条记录的时长差
            BigDecimal diffTime = BigDecimalUtil.div(accessTimeList.get(i+1).getCreateTime().getTime()- accessTimeList.get(i).getCreateTime().getTime(),1000*60*60);
            if( diffTime.compareTo(BigDecimal.ZERO)>0){
                accessTimeDiffVo.setTotalTime(diffTime);
            }else{
                accessTimeDiffVo.setTotalTime(BigDecimal.ZERO);
            }

            accessTimeDiffVos.add(accessTimeDiffVo);
        }
        return accessTimeDiffVos;
    }



}
