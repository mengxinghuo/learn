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
import com.truck.dao.MileageMapper;
import com.truck.pojo.Device;
import com.truck.pojo.DeviceAdmin;
import com.truck.pojo.Mileage;
import com.truck.service.IDeviceService;
import com.truck.service.IMileageService;
import com.truck.service.IMileageService;
import com.truck.util.BigDecimalUtil;
import com.truck.util.DateTimeUtil;
import com.truck.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("iMileageService")
public class MileageServiceImpl implements IMileageService {

    @Autowired
    private MileageMapper mileageMapper;
    @Autowired
    private DeviceAdminMapper deviceAdminMapper;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private IDeviceService iDeviceService;

    public ServerResponse add( Mileage mileage){

        if(mileage.getDeviceId() ==null){
            return ServerResponse.createByErrorMessage("请选择设备");
        }

        List<DeviceAdmin> deviceAdminList = deviceAdminMapper.selectByAdminIdStatus(null,mileage.getDeviceId(),Const.DeviceAdminStatusEnum.USING.getCode());
        if (deviceAdminList.size() ==0){
            return ServerResponse.createByErrorMessage("该设备当前没有人使用");
        }
        mileage.setAdminId(deviceAdminList.get(0).getAdminId());

        if(mileage.getDeviceMileage()==null ) {
            return ServerResponse.createByErrorMessage("请设置里程数值");
        }

        Mileage mileage2 = mileageMapper.checkDeviceMileage(deviceAdminList.get(0).getAdminId(),mileage.getDeviceId());
        if (mileage2 != null) {
            if (mileage2.getDeviceMileage()>mileage.getDeviceMileage())
                return ServerResponse.createByErrorMessage("设备里程小于之前里程数，参数错误");
        }
        int rowCount = mileageMapper.insertSelective(mileage);
        if(rowCount > 0){
            Map result = Maps.newHashMap();
            result.put("id",mileage.getId());
            return ServerResponse.createBySuccess("新建设备里程记录成功",result);
        }
        return ServerResponse.createByErrorMessage("新建设备里程记录失败");
    }

    public ServerResponse<String> del( Integer adminId,Integer mileageId){
        int rowCount = mileageMapper.deleteByIdAdminId( adminId,mileageId);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("删除设备里程记录成功");
        }
        return ServerResponse.createByErrorMessage("删除设备里程记录失败");
    }

    public ServerResponse update( Integer adminId,Mileage mileage){
        if (adminId != null) {
            mileage.setAdminId( adminId);
        }
        Mileage mileage2 = mileageMapper.checkDeviceMileage(adminId,mileage.getDeviceId());
        if (mileage2 != null) {
            if (mileage2.getDeviceMileage()>mileage.getDeviceMileage())
                return ServerResponse.createByErrorMessage("设备里程参数错误");
        }
        int rowCount = mileageMapper.updateByPrimaryKeySelective(mileage);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("更新设备里程记录成功");
        }
        return ServerResponse.createByErrorMessage("更新设备里程记录失败");
    }

    public ServerResponse<MileageVo> select(Integer adminId, Integer id){
        Mileage mileage = mileageMapper.selectByIdAdminId(adminId,id);

        if(mileage == null){
            return ServerResponse.createByErrorMessage("无法查询到该设备里程记录");
        }
        MileageVo mileageVo = assembleMileageVo(mileage);
        return ServerResponse.createBySuccess("查询设备里程记录成功",mileageVo);
    }


    public ServerResponse listDaysCondition(Integer adminId,Integer driverId,Integer deviceId,String searchDate,String beginDate,String endDate, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);

        List<Mileage> mileageList = mileageMapper.selectByAdminIdDate(adminId,driverId,deviceId,searchDate,beginDate,endDate);

        Set<Mileage> mileageSet = Sets.newHashSet();
        for (Mileage mileageItem : mileageList) {
            mileageSet.add(mileageItem);
        }

        List<MileageProductVo> mileageProductVos = Lists.newArrayList();

        for (Mileage mileage : mileageSet) {
            MileageProductVo mileageProductVo = new MileageProductVo();
            List<MileageProductDaysVo> mileageProductDaysVoList = Lists.newArrayList();
            BigDecimal deviceMileageDays = BigDecimal.ZERO;
            //搜索单天
            if (!StringUtils.isEmpty(searchDate)) {
                BigDecimal deviceMileageDay = BigDecimal.ZERO;
                MileageProductDaysVo mileageProductDaysVo = new MileageProductDaysVo();
                List<Mileage> mileageList2 = mileageMapper.selectByAdminIdDate(adminId, driverId, mileage.getDeviceId(), searchDate, beginDate, endDate);
                List<MileageVo> mileageVos = Lists.newArrayList();

                for (Mileage mileage2 : mileageList2) {
                    MileageVo mileageVo = assembleMileageVo(mileage2);
                    mileageVos.add(mileageVo);
                    deviceMileageDay = BigDecimalUtil.add(deviceMileageDay.doubleValue(),mileageVo.getDeviceMileage().doubleValue());
                }

                List<MileageDiffVo> mileageDiffVos = assembleMileageDiffVoList(mileageList2);
                if(mileageDiffVos.size()>0)
                mileageProductDaysVo.setMileageDiffVos(mileageDiffVos);
                mileageProductDaysVo.setMileageVos(mileageVos);
                mileageProductDaysVo.setDate(searchDate);
                mileageProductDaysVo.setDeviceMileageDay(deviceMileageDay.longValue());
                deviceMileageDays = BigDecimalUtil.add(deviceMileageDays.doubleValue() ,mileageProductDaysVo.getDeviceMileageDay().doubleValue());
                mileageProductDaysVoList.add(mileageProductDaysVo);

                mileageProductVo.setMileageProductDaysVos(mileageProductDaysVoList);
            }else {
                List<String> stringList = Lists.newArrayList();
                List<Date> dateList = mileageMapper.selectDate(mileage.getDeviceId(),beginDate,endDate);
                for (Date date : dateList) {
                    String dateStr = DateTimeUtil.dateToStr(date,"yyyy-MM-dd");
                    stringList.add(dateStr);
                }
                for (String str : stringList) {
                    BigDecimal deviceMileageDay = BigDecimal.ZERO;

                    MileageProductDaysVo mileageProductDaysVo = new MileageProductDaysVo();
                    List<MileageVo> mileageVos = Lists.newArrayList();
                    List<Mileage> mileageList2 = mileageMapper.selectByAdminIdDate(adminId, driverId, mileage.getDeviceId(), str, beginDate, endDate);

                    Calendar calendar = Calendar.getInstance();
                    Date date = DateTimeUtil.strToDate(str,"yyyy-MM-dd");
                    calendar.setTime(date);
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    date = calendar.getTime();
                    String strLast=DateTimeUtil.dateToStr(date,"yyyy-MM-dd");

                    List<Mileage> mileageList3 = mileageMapper.selectByAdminIdDate(adminId, driverId, mileage.getDeviceId(), null, null, strLast);

                    if(mileageList3.size()>0){
                        mileageList2.add(0,mileageList3.get(mileageList3.size()-1));
                    }

                    for (Mileage mileage2 : mileageList2) {
                        MileageVo mileageVo = assembleMileageVo(mileage2);
                        mileageVos.add(mileageVo);
                    }

                    List<MileageDiffVo> mileageDiffVos = assembleMileageDiffVoList(mileageList2);
                    for (MileageDiffVo mileageDiffVo : mileageDiffVos) {
                        deviceMileageDay = BigDecimalUtil.add(deviceMileageDay.doubleValue(),mileageDiffVo.getDeviceMileageDiff().doubleValue());
                    }
                    if(mileageDiffVos.size()>0)
                    mileageProductDaysVo.setMileageDiffVos(mileageDiffVos);
                    mileageProductDaysVo.setMileageVos(mileageVos);
                    mileageProductDaysVo.setDate(str);
                    mileageProductDaysVo.setDeviceMileageDay(deviceMileageDay.longValue());
                    deviceMileageDays = BigDecimalUtil.add(deviceMileageDays.doubleValue() ,mileageProductDaysVo.getDeviceMileageDay().doubleValue());
                    mileageProductDaysVoList.add(mileageProductDaysVo);

                }
                mileageProductVo.setMileageProductDaysVos(mileageProductDaysVoList);

            }

            mileageProductVo.setDeviceId(mileage.getDeviceId());
            Device device = deviceMapper.selectByPrimaryKey(mileage.getDeviceId());
            if (device != null) {
                DeviceVo deviceVo =iDeviceService.assembleDeviceVo(device);
                mileageProductVo.setDeviceVo(deviceVo);
            }
            mileageProductVo.setDeviceMileageDays(deviceMileageDays.longValue());
            mileageProductVos.add(mileageProductVo);
        }
        PageInfo pageInfo = new PageInfo(mileageProductVos);

        Map map = Maps.newHashMap();
        BigDecimal AllDeviceTotalMileage = BigDecimal.ZERO;
        for (MileageProductVo mileageProductVo : mileageProductVos) {
            AllDeviceTotalMileage = BigDecimalUtil.add(AllDeviceTotalMileage.doubleValue() ,mileageProductVo.getDeviceMileageDays().doubleValue());
        }
//        if (AllDeviceTotalMileage.compareTo(BigDecimal.ZERO)>0) {
            map.put("AllDeviceTotalMileage",AllDeviceTotalMileage);
//        }
        map.put("mileageProductVos",pageInfo);
        return ServerResponse.createBySuccess(map);
    }

    public ServerResponse listCondition(Integer adminId, Integer driverId, Integer deviceId, String searchDate, String beginDate, String endDate, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<Mileage> mileageList = mileageMapper.selectByAdminIdDate(adminId, driverId, deviceId, searchDate, beginDate, endDate);

        Set<Mileage> mileageSet = Sets.newHashSet();
        for (Mileage mileageItem : mileageList) {
            mileageSet.add(mileageItem);
        }

        List<MileageInfoVo> mileageInfoVos = Lists.newArrayList();

        for (Mileage mileage : mileageSet) {
            MileageInfoVo mileageInfoVo = new MileageInfoVo();
            BigDecimal deviceMileageDays = BigDecimal.ZERO;
            BigDecimal deviceMileageDay = BigDecimal.ZERO;

            List<MileageVo> mileageVos = Lists.newArrayList();
            List<Mileage> mileageList2 = mileageMapper.selectByAdminIdDate(adminId, driverId, mileage.getDeviceId(), searchDate, beginDate, endDate);

            for (Mileage mileage2 : mileageList2) {
                MileageVo mileageVo = assembleMileageVo(mileage2);
                mileageVos.add(mileageVo);
            }

            List<MileageDiffVo> mileageDiffVos = assembleMileageDiffVoList(mileageList2);
            if (mileageDiffVos.size() > 0)
                mileageInfoVo.setMileageDiffVos(mileageDiffVos);
            mileageInfoVo.setMileageVos(mileageVos);

            mileageInfoVo.setDeviceId(mileage.getDeviceId());
            Device device = deviceMapper.selectByPrimaryKey(mileage.getDeviceId());
            if (device != null) {
                DeviceVo deviceVo = iDeviceService.assembleDeviceVo(device);
                mileageInfoVo.setDeviceVo(deviceVo);
            }
            mileageInfoVos.add(mileageInfoVo);
        }
        PageInfo pageInfo = new PageInfo(mileageInfoVos);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public MileageVo assembleMileageVo(Mileage mileage) {
        MileageVo mileageVo = new MileageVo();
        mileageVo.setId(mileage.getId());
        mileageVo.setAdminId(mileage.getAdminId());
        mileageVo.setDeviceId(mileage.getDeviceId());
        mileageVo.setDriverId(mileage.getDriverId());
        mileageVo.setDeviceMileage(mileage.getDeviceMileage());
        mileageVo.setCreateTime(DateTimeUtil.dateToStr(mileage.getCreateTime()));
        mileageVo.setUpdateTime(DateTimeUtil.dateToStr(mileage.getUpdateTime()));
        return mileageVo;
    }

    public List<MileageDiffVo> assembleMileageDiffVoList(List<Mileage> mileageList ){
        List<MileageDiffVo> mileageDiffVos = Lists.newArrayList();
        for (int i = 0; i < mileageList.size()-1; i++) {

            MileageDiffVo mileageDiffVo = new MileageDiffVo();
            mileageDiffVo.setAdminId(mileageList.get(i).getAdminId());
            mileageDiffVo.setDeviceId(mileageList.get(i).getDeviceId());
            mileageDiffVo.setDriverId(mileageList.get(i).getDriverId());
            //里程
            if (mileageList.get(i).getDeviceMileage() != null && mileageList.get(i + 1).getDeviceMileage() != null) {
                mileageDiffVo.setDeviceMileageBegin(mileageList.get(i).getDeviceMileage());
                mileageDiffVo.setDeviceMileageEnd(mileageList.get(i+1).getDeviceMileage());
                mileageDiffVo.setDeviceMileageDiff(mileageDiffVo.getDeviceMileageEnd() - mileageDiffVo.getDeviceMileageBegin());
            }

            mileageDiffVo.setStartTimeStr(DateTimeUtil.dateToStr(mileageList.get(i).getCreateTime()));
            mileageDiffVo.setEndTimeStr(DateTimeUtil.dateToStr(mileageList.get(i+1).getCreateTime()));

            mileageDiffVos.add(mileageDiffVo);
        }
        return mileageDiffVos;
    }



}
