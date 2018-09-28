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
import com.truck.dao.WorkTimeMapper;
import com.truck.pojo.*;
import com.truck.pojo.WorkTime;
import com.truck.service.IDeviceService;
import com.truck.service.IWorkTimeService;
import com.truck.util.BigDecimalUtil;
import com.truck.util.DateTimeUtil;
import com.truck.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

@Service("iWorkTimeService")
public class WorkTimeServiceImpl implements IWorkTimeService {

    @Autowired
    private WorkTimeMapper workTimeMapper;
    @Autowired
    private DeviceAdminMapper deviceAdminMapper;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private IDeviceService iDeviceService;

    public ServerResponse add( WorkTime workTime){
        if(workTime.getDeviceId() ==null){
            return ServerResponse.createByErrorMessage("请选择设备");
        }

        List<DeviceAdmin> deviceAdminList = deviceAdminMapper.selectByAdminIdStatus(null,workTime.getDeviceId(),Const.DeviceAdminStatusEnum.USING.getCode());
        if (deviceAdminList.size() ==0){
            return ServerResponse.createByErrorMessage("该设备当前没有人使用");
        }
        workTime.setAdminId(deviceAdminList.get(0).getAdminId());

        int rowCount = workTimeMapper.insertSelective(workTime);
        if(rowCount > 0){
            Map result = Maps.newHashMap();
            result.put("id",workTime.getId());
            return ServerResponse.createBySuccess("新建设备工作时间记录成功",result);
        }
        return ServerResponse.createByErrorMessage("新建设备工作时间记录失败");
    }

    public ServerResponse<String> del( Integer adminId,Integer workTimeId){
        int rowCount = workTimeMapper.deleteByPrimaryKey( workTimeId);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("删除设备工作时间记录成功");
        }
        return ServerResponse.createByErrorMessage("删除设备工作时间记录失败");
    }

    public ServerResponse update( Integer adminId,WorkTime workTime){
        if (adminId != null) {
            workTime.setAdminId(adminId);
        }
        int rowCount = workTimeMapper.updateByPrimaryKeySelective(workTime);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("更新设备工作时间记录成功");
        }
        return ServerResponse.createByErrorMessage("更新设备工作时间记录失败");
    }

    public ServerResponse<WorkTimeVo> select(Integer adminId, Integer id){
        WorkTime workTime = workTimeMapper.selectByPrimaryKey(id);

        if(workTime == null){
            return ServerResponse.createByErrorMessage("无法查询到该设备工作时间记录");
        }
        WorkTimeVo workTimeVo = assembleWorkTimeVo(workTime);
        return ServerResponse.createBySuccess("查询设备工作时间记录成功",workTimeVo);
    }


    public ServerResponse list(Integer adminId,Integer driverId,Integer deviceId,String searchDate,String beginDate,String endDate, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);

        List<WorkTime> workTimeList = workTimeMapper.selectByAdminIdDate(adminId,driverId,deviceId,searchDate,beginDate,endDate);

        Set<WorkTime> workTimeSet = Sets.newHashSet();
        for (WorkTime workTimeItem : workTimeList) {
            workTimeSet.add(workTimeItem);
        }

        List<WorkTimeProductVo> workTimeProductVos = Lists.newArrayList();

        for (WorkTime workTime : workTimeSet) {
            WorkTimeProductVo workTimeProductVo = new WorkTimeProductVo();
            List<WorkTimeProductDaysVo> workTimeProductDaysVoList = Lists.newArrayList();
            BigDecimal deviceWorkTimeDays = BigDecimal.ZERO;
            //搜索单天
            if (!StringUtils.isEmpty(searchDate)) {
                BigDecimal deviceWorkTimeDay = BigDecimal.ZERO;
                WorkTimeProductDaysVo workTimeProductDaysVo = new WorkTimeProductDaysVo();
                List<WorkTime> workTimeList2 = workTimeMapper.selectByAdminIdDate(adminId, driverId, workTime.getDeviceId(), searchDate, beginDate, endDate);
                List<WorkTimeVo> workTimeVos = Lists.newArrayList();

                for (WorkTime workTime2 : workTimeList2) {
                    WorkTimeVo workTimeVo = assembleWorkTimeVo(workTime2);
                    workTimeVos.add(workTimeVo);
                }

                List<WorkTimeDiffVo> workTimeDiffVos = assembleWorkTimeDiffVoList(workTimeList2);
                for (WorkTimeDiffVo workTimeDiffVo : workTimeDiffVos) {
                    deviceWorkTimeDay = BigDecimalUtil.add(deviceWorkTimeDay.doubleValue(),workTimeDiffVo.getTotalTime().doubleValue());
                }
                if(workTimeDiffVos.size()>0)
                workTimeProductDaysVo.setWorkTimeDiffVos(workTimeDiffVos);
                workTimeProductDaysVo.setWorkTimeVos(workTimeVos);
                workTimeProductDaysVo.setDate(searchDate);
                workTimeProductDaysVo.setDeviceWorkTimeDay(deviceWorkTimeDay);
                deviceWorkTimeDays = BigDecimalUtil.add(deviceWorkTimeDays.doubleValue() ,workTimeProductDaysVo.getDeviceWorkTimeDay().doubleValue());
                workTimeProductDaysVoList.add(workTimeProductDaysVo);

                workTimeProductVo.setWorkTimeProductDaysVos(workTimeProductDaysVoList);
            }else {
                List<String> stringList = Lists.newArrayList();
                List<Date> dateList = workTimeMapper.selectDate(workTime.getDeviceId(),beginDate,endDate);
                for (Date date : dateList) {
                    String dateStr = DateTimeUtil.dateToStr(date,"yyyy-MM-dd");
                    stringList.add(dateStr);
                }
                for (String str : stringList) {
                    BigDecimal deviceWorkTimeDay = BigDecimal.ZERO;

                    WorkTimeProductDaysVo workTimeProductDaysVo = new WorkTimeProductDaysVo();
                    List<WorkTimeVo> workTimeVos = Lists.newArrayList();
                    List<WorkTime> workTimeList2 = workTimeMapper.selectByAdminIdDate(adminId, driverId, workTime.getDeviceId(), str, beginDate, endDate);

           /*         Calendar calendar = Calendar.getInstance();
                    Date date = DateTimeUtil.strToDate(str,"yyyy-MM-dd");
                    calendar.setTime(date);
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    date = calendar.getTime();
                    String strLast=DateTimeUtil.dateToStr(date,"yyyy-MM-dd");

                    List<WorkTime> workTimeList3 = workTimeMapper.selectByAdminIdDate(adminId, driverId, workTime.getDeviceId(), null, null, strLast);

                    if(workTimeList3.size()>0){
                        workTimeList2.add(0,workTimeList3.get(workTimeList3.size()-1));
                    }*/

                    for (WorkTime workTime2 : workTimeList2) {
                        WorkTimeVo workTimeVo = assembleWorkTimeVo(workTime2);
                        workTimeVos.add(workTimeVo);
                    }

                    List<WorkTimeDiffVo> workTimeDiffVos = assembleWorkTimeDiffVoList(workTimeList2);
                    for (WorkTimeDiffVo workTimeDiffVo : workTimeDiffVos) {
                        deviceWorkTimeDay = BigDecimalUtil.add(deviceWorkTimeDay.doubleValue(),workTimeDiffVo.getTotalTime().doubleValue());
                    }
                    if(workTimeDiffVos.size()>0)
                    workTimeProductDaysVo.setWorkTimeDiffVos(workTimeDiffVos);
                    workTimeProductDaysVo.setWorkTimeVos(workTimeVos);
                    workTimeProductDaysVo.setDate(str);
                    workTimeProductDaysVo.setDeviceWorkTimeDay(deviceWorkTimeDay);
                    deviceWorkTimeDays = BigDecimalUtil.add(deviceWorkTimeDays.doubleValue() ,workTimeProductDaysVo.getDeviceWorkTimeDay().doubleValue());
                    workTimeProductDaysVoList.add(workTimeProductDaysVo);

                }
                workTimeProductVo.setWorkTimeProductDaysVos(workTimeProductDaysVoList);

            }

            workTimeProductVo.setDeviceId(workTime.getDeviceId());
            Device device = deviceMapper.selectByPrimaryKey(workTime.getDeviceId());
            if (device != null) {
                DeviceVo deviceVo =iDeviceService.assembleDeviceVo(device);
                workTimeProductVo.setDeviceVo(deviceVo);
            }
            workTimeProductVo.setDeviceWorkTimeDays(deviceWorkTimeDays);
            workTimeProductVos.add(workTimeProductVo);
        }
        PageInfo pageInfo = new PageInfo(workTimeProductVos);

        Map map = Maps.newHashMap();
        BigDecimal AllDeviceTotalWorkTime = BigDecimal.ZERO;
        for (WorkTimeProductVo workTimeProductVo : workTimeProductVos) {
            AllDeviceTotalWorkTime = BigDecimalUtil.add(AllDeviceTotalWorkTime.doubleValue() ,workTimeProductVo.getDeviceWorkTimeDays().doubleValue());
        }
//        if (AllDeviceTotalWorkTime.compareTo(BigDecimal.ZERO)>0) {
            map.put("AllDeviceTotalWorkTime",AllDeviceTotalWorkTime);
            map.put("workTimeProductVos",pageInfo);
//        }
        return ServerResponse.createBySuccess(map);
    }

    public ServerResponse listCondition(Integer adminId, Integer driverId, Integer deviceId, String searchDate, String beginDate, String endDate, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<WorkTime> workTimeList = workTimeMapper.selectByAdminIdDate(adminId, driverId, deviceId, searchDate, beginDate, endDate);

        Set<WorkTime> workTimeSet = Sets.newHashSet();
        for (WorkTime workTimeItem : workTimeList) {
            workTimeSet.add(workTimeItem);
        }

        List<WorkTimeInfoVo> workTimeInfoVos = Lists.newArrayList();

        for (WorkTime workTime : workTimeSet) {
            WorkTimeInfoVo workTimeInfoVo = new WorkTimeInfoVo();
            BigDecimal deviceWorkTimeDays = BigDecimal.ZERO;
            BigDecimal deviceWorkTimeDay = BigDecimal.ZERO;

            List<WorkTimeVo> workTimeVos = Lists.newArrayList();
            List<WorkTime> workTimeList2 = workTimeMapper.selectByAdminIdDate(adminId, driverId, workTime.getDeviceId(), searchDate, beginDate, endDate);

            for (WorkTime workTime2 : workTimeList2) {
                WorkTimeVo workTimeVo = assembleWorkTimeVo(workTime2);
                workTimeVos.add(workTimeVo);
            }

            List<WorkTimeDiffVo> workTimeDiffVos = assembleWorkTimeDiffVoList(workTimeList2);
            if (workTimeDiffVos.size() > 0)
                workTimeInfoVo.setWorkTimeDiffVos(workTimeDiffVos);
            workTimeInfoVo.setWorkTimeVos(workTimeVos);

            workTimeInfoVo.setDeviceId(workTime.getDeviceId());
            Device device = deviceMapper.selectByPrimaryKey(workTime.getDeviceId());
            if (device != null) {
                DeviceVo deviceVo = iDeviceService.assembleDeviceVo(device);
                workTimeInfoVo.setDeviceVo(deviceVo);
            }
            workTimeInfoVos.add(workTimeInfoVo);
        }
        PageInfo pageInfo = new PageInfo(workTimeInfoVos);
        return ServerResponse.createBySuccess(pageInfo);
    }

    

    public List<WorkTimeDiffVo> assembleWorkTimeDiffVoList(List<WorkTime> workTimeList ){
        List<WorkTimeDiffVo> workTimeDiffVos = Lists.newArrayList();
        for (int i = 0; i < workTimeList.size()-1; i++) {

            WorkTimeDiffVo workTimeDiffVo = new WorkTimeDiffVo();
            workTimeDiffVo.setAdminId(workTimeList.get(i).getAdminId());
            workTimeDiffVo.setDeviceId(workTimeList.get(i).getDeviceId());
            workTimeDiffVo.setDriverId(workTimeList.get(i).getDriverId());
            //时长
            if (workTimeList.get(i).getCreateTime() != null && workTimeList.get(i + 1).getCreateTime() != null) {
                workTimeDiffVo.setStartTimeStr(DateTimeUtil.dateToStr(workTimeList.get(i).getCreateTime()));
                workTimeDiffVo.setEndTimeStr(DateTimeUtil.dateToStr(workTimeList.get(i+1).getCreateTime()));
                workTimeDiffVo.setTotalTime(BigDecimalUtil.div(workTimeList.get(i+1).getCreateTime().getTime()-workTimeList.get(i).getCreateTime().getTime(),1000*60*60));
            }
            workTimeDiffVos.add(workTimeDiffVo);
        }
        return workTimeDiffVos;
    }

    public WorkTimeVo assembleWorkTimeVo(WorkTime workTime) {
        WorkTimeVo workTimeVo = new WorkTimeVo();
        workTimeVo.setId(workTime.getId());
        workTimeVo.setAdminId(workTime.getAdminId());
        workTimeVo.setDeviceId(workTime.getDeviceId());
        workTimeVo.setDriverId(workTime.getDriverId());
        workTimeVo.setCreateTime(DateTimeUtil.dateToStr(workTime.getCreateTime()));
        workTimeVo.setUpdateTime(DateTimeUtil.dateToStr(workTime.getUpdateTime()));
        return workTimeVo;
    }

}
