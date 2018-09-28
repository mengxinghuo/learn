package com.truck.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.truck.common.Const;
import com.truck.common.ServerResponse;
import com.truck.dao.DeviceAdminMapper;
import com.truck.dao.DeviceMapper;
import com.truck.dao.HourMapper;
import com.truck.pojo.Device;
import com.truck.pojo.DeviceAdmin;
import com.truck.pojo.Hour;
import com.truck.pojo.Mileage;
import com.truck.service.IDeviceService;
import com.truck.service.IHourService;
import com.truck.util.BigDecimalUtil;
import com.truck.util.DateTimeUtil;
import com.truck.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

@Service("iHourService")
public class HourServiceImpl implements IHourService {

    @Autowired
    private HourMapper hourMapper;
    @Autowired
    private DeviceAdminMapper deviceAdminMapper;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private IDeviceService iDeviceService;

    public ServerResponse add( Hour hour){
        if(hour.getDeviceId() ==null){
            return ServerResponse.createByErrorMessage("请选择设备");
        }

        List<DeviceAdmin> deviceAdminList = deviceAdminMapper.selectByAdminIdStatus(null,hour.getDeviceId(),Const.DeviceAdminStatusEnum.USING.getCode());
        if (deviceAdminList.size() ==0){
            return ServerResponse.createByErrorMessage("该设备当前没有人使用");
        }
        hour.setAdminId(deviceAdminList.get(0).getAdminId());

        if(hour.getDeviceHour()==null ) {
            return ServerResponse.createByErrorMessage("请设置设备时间数值");
        }

        Hour hour2 = hourMapper.checkDeviceHour(deviceAdminList.get(0).getAdminId(),hour.getDeviceId());
        if (hour2 != null) {
            if (hour2.getDeviceHour()>hour.getDeviceHour())
                return ServerResponse.createByErrorMessage("设备设备时间小于之前设备时间数，参数错误");
        }
        int rowCount = hourMapper.insertSelective(hour);
        if(rowCount > 0){
            Map result = Maps.newHashMap();
            result.put("id",hour.getId());
            return ServerResponse.createBySuccess("新建设备设备时间记录成功",result);
        }
        return ServerResponse.createByErrorMessage("新建设备设备时间记录失败");
    }

    public ServerResponse<String> del(Integer adminId, Integer hourId){
        int rowCount = hourMapper.deleteByIdAdminId( adminId,hourId);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("删除设备设备时间记录成功");
        }
        return ServerResponse.createByErrorMessage("删除设备设备时间记录失败");
    }

    public ServerResponse update(Integer adminId, Hour hour){
        if (adminId != null) {
            hour.setAdminId( adminId);
        }
        Hour hour2 = hourMapper.checkDeviceHour(adminId,hour.getDeviceId());
        if (hour2 != null) {
            if (hour2.getDeviceHour()>hour.getDeviceHour())
                return ServerResponse.createByErrorMessage("设备设备时间参数错误");
        }
        int rowCount = hourMapper.updateByPrimaryKeySelective(hour);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("更新设备设备时间记录成功");
        }
        return ServerResponse.createByErrorMessage("更新设备设备时间记录失败");
    }

    public ServerResponse<HourVo> select(Integer adminId, Integer id){
        Hour hour = hourMapper.selectByIdAdminId(adminId,id);

        if(hour == null){
            return ServerResponse.createByErrorMessage("无法查询到该设备设备时间记录");
        }
        HourVo hourVo = assembleHourVo(hour);
        return ServerResponse.createBySuccess("查询设备设备时间记录成功",hourVo);
    }


    public ServerResponse listDaysCondition(Integer adminId, Integer driverId, Integer deviceId, String searchDate, String beginDate, String endDate, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);

        List<Hour> hourList = hourMapper.selectByAdminIdDate(adminId,driverId,deviceId,searchDate,beginDate,endDate);

        Set<Hour> hourSet = Sets.newHashSet();
        for (Hour hourItem : hourList) {
            hourSet.add(hourItem);
        }

        List<HourProductVo> hourProductVos = Lists.newArrayList();

        for (Hour hour : hourSet) {
            HourProductVo hourProductVo = new HourProductVo();
            List<HourProductDaysVo> hourProductDaysVoList = Lists.newArrayList();
            BigDecimal deviceHourDays = BigDecimal.ZERO;
            //搜索单天
            if (!StringUtils.isEmpty(searchDate)) {
                BigDecimal deviceHourDay = BigDecimal.ZERO;
                HourProductDaysVo hourProductDaysVo = new HourProductDaysVo();
                List<Hour> hourList2 = hourMapper.selectByAdminIdDate(adminId, driverId, hour.getDeviceId(), searchDate, beginDate, endDate);
                List<HourVo> hourVos = Lists.newArrayList();

                for (Hour hour2 : hourList2) {
                    HourVo hourVo = assembleHourVo(hour2);
                    hourVos.add(hourVo);
                    deviceHourDay = BigDecimalUtil.add(deviceHourDay.doubleValue(),hourVo.getDeviceHour().doubleValue());
                }

                List<HourDiffVo> hourDiffVos = assembleHourDiffVoList(hourList2);
                if(hourDiffVos.size()>0)
                hourProductDaysVo.setHourDiffVos(hourDiffVos);
                hourProductDaysVo.setHourVos(hourVos);
                hourProductDaysVo.setDate(searchDate);
                hourProductDaysVo.setDeviceHourDay(deviceHourDay.longValue());
                deviceHourDays = BigDecimalUtil.add(deviceHourDays.doubleValue() ,hourProductDaysVo.getDeviceHourDay().doubleValue());
                hourProductDaysVoList.add(hourProductDaysVo);

                hourProductVo.setHourProductDaysVos(hourProductDaysVoList);
            }else {
                List<String> stringList = Lists.newArrayList();
                List<Date> dateList = hourMapper.selectDate(hour.getDeviceId(),beginDate,endDate);
                for (Date date : dateList) {
                    String dateStr = DateTimeUtil.dateToStr(date,"yyyy-MM-dd");
                    stringList.add(dateStr);
                }
                for (String str : stringList) {
                    BigDecimal deviceHourDay = BigDecimal.ZERO;

                    HourProductDaysVo hourProductDaysVo = new HourProductDaysVo();
                    List<HourVo> hourVos = Lists.newArrayList();
                    List<Hour> hourList2 = hourMapper.selectByAdminIdDate(adminId, driverId, hour.getDeviceId(), str, beginDate, endDate);

                    Calendar calendar = Calendar.getInstance();
                    Date date = DateTimeUtil.strToDate(str,"yyyy-MM-dd");
                    calendar.setTime(date);
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    date = calendar.getTime();
                    String strLast= DateTimeUtil.dateToStr(date,"yyyy-MM-dd");

                    List<Hour> hourList3 = hourMapper.selectByAdminIdDate(adminId, driverId, hour.getDeviceId(), null, null, strLast);

                    if(hourList3.size()>0){
                        hourList2.add(0,hourList3.get(hourList3.size()-1));
                    }

                    for (Hour hour2 : hourList2) {
                        HourVo hourVo = assembleHourVo(hour2);
                        hourVos.add(hourVo);
                    }

                    List<HourDiffVo> hourDiffVos = assembleHourDiffVoList(hourList2);
                    for (HourDiffVo hourDiffVo : hourDiffVos) {
                        deviceHourDay = BigDecimalUtil.add(deviceHourDay.doubleValue(),hourDiffVo.getDeviceHourDiff().doubleValue());
                    }
                    if(hourDiffVos.size()>0)
                    hourProductDaysVo.setHourDiffVos(hourDiffVos);
                    hourProductDaysVo.setHourVos(hourVos);
                    hourProductDaysVo.setDate(str);
                    hourProductDaysVo.setDeviceHourDay(deviceHourDay.longValue());
                    deviceHourDays = BigDecimalUtil.add(deviceHourDays.doubleValue() ,hourProductDaysVo.getDeviceHourDay().doubleValue());
                    hourProductDaysVoList.add(hourProductDaysVo);

                }
                hourProductVo.setHourProductDaysVos(hourProductDaysVoList);

            }

            hourProductVo.setDeviceId(hour.getDeviceId());
            Device device = deviceMapper.selectByPrimaryKey(hour.getDeviceId());
            if (device != null) {
                DeviceVo deviceVo =iDeviceService.assembleDeviceVo(device);
                hourProductVo.setDeviceVo(deviceVo);
            }
            hourProductVo.setDeviceHourDays(deviceHourDays.longValue());
            hourProductVos.add(hourProductVo);
        }
        PageInfo pageInfo = new PageInfo(hourProductVos);

        Map map = Maps.newHashMap();
        BigDecimal AllDeviceTotalHour = BigDecimal.ZERO;
        for (HourProductVo hourProductVo : hourProductVos) {
            AllDeviceTotalHour = BigDecimalUtil.add(AllDeviceTotalHour.doubleValue() ,hourProductVo.getDeviceHourDays().doubleValue());
        }
//        if (AllDeviceTotalHour.compareTo(BigDecimal.ZERO)>0) {
            map.put("AllDeviceTotalHour",AllDeviceTotalHour);
//        }
        map.put("hourProductVos",pageInfo);
        return ServerResponse.createBySuccess(map);
    }

    public ServerResponse listCondition(Integer adminId, Integer driverId, Integer deviceId, String searchDate, String beginDate, String endDate, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<Hour> hourList = hourMapper.selectByAdminIdDate(adminId, driverId, deviceId, searchDate, beginDate, endDate);

        Set<Hour> hourSet = Sets.newHashSet();
        for (Hour hourItem : hourList) {
            hourSet.add(hourItem);
        }

        List<HourInfoVo> hourInfoVos = Lists.newArrayList();

        for (Hour hour : hourSet) {
            HourInfoVo hourInfoVo = new HourInfoVo();
            BigDecimal deviceHourDays = BigDecimal.ZERO;
            BigDecimal deviceHourDay = BigDecimal.ZERO;

            List<HourVo> hourVos = Lists.newArrayList();
            List<Hour> hourList2 = hourMapper.selectByAdminIdDate(adminId, driverId, hour.getDeviceId(), searchDate, beginDate, endDate);

            for (Hour hour2 : hourList2) {
                HourVo hourVo = assembleHourVo(hour2);
                hourVos.add(hourVo);
            }

            List<HourDiffVo> hourDiffVos = assembleHourDiffVoList(hourList2);
            if (hourDiffVos.size() > 0)
                hourInfoVo.setHourDiffVos(hourDiffVos);
            hourInfoVo.setHourVos(hourVos);

            hourInfoVo.setDeviceId(hour.getDeviceId());
            Device device = deviceMapper.selectByPrimaryKey(hour.getDeviceId());
            if (device != null) {
                DeviceVo deviceVo = iDeviceService.assembleDeviceVo(device);
                hourInfoVo.setDeviceVo(deviceVo);
            }
            hourInfoVos.add(hourInfoVo);
        }
        PageInfo pageInfo = new PageInfo(hourInfoVos);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public HourVo assembleHourVo(Hour hour) {
        HourVo hourVo = new HourVo();
        hourVo.setId(hour.getId());
        hourVo.setAdminId(hour.getAdminId());
        hourVo.setDeviceId(hour.getDeviceId());
        hourVo.setDriverId(hour.getDriverId());
        hourVo.setDeviceHour(hour.getDeviceHour());
        hourVo.setCreateTime(DateTimeUtil.dateToStr(hour.getCreateTime()));
        hourVo.setUpdateTime(DateTimeUtil.dateToStr(hour.getUpdateTime()));
        return hourVo;
    }

    public List<HourDiffVo> assembleHourDiffVoList(List<Hour> hourList ){
        List<HourDiffVo> hourDiffVos = Lists.newArrayList();
        for (int i = 0; i < hourList.size()-1; i++) {

            HourDiffVo hourDiffVo = new HourDiffVo();
            hourDiffVo.setAdminId(hourList.get(i).getAdminId());
            hourDiffVo.setDeviceId(hourList.get(i).getDeviceId());
            hourDiffVo.setDriverId(hourList.get(i).getDriverId());
            //设备时间
            if (hourList.get(i).getDeviceHour() != null && hourList.get(i + 1).getDeviceHour() != null) {
                hourDiffVo.setDeviceHourBegin(hourList.get(i).getDeviceHour());
                hourDiffVo.setDeviceHourEnd(hourList.get(i+1).getDeviceHour());
                hourDiffVo.setDeviceHourDiff(hourDiffVo.getDeviceHourEnd() - hourDiffVo.getDeviceHourBegin());
            }

            hourDiffVo.setStartTimeStr(DateTimeUtil.dateToStr(hourList.get(i).getCreateTime()));
            hourDiffVo.setEndTimeStr(DateTimeUtil.dateToStr(hourList.get(i+1).getCreateTime()));

            hourDiffVos.add(hourDiffVo);
        }
        return hourDiffVos;
    }



}
