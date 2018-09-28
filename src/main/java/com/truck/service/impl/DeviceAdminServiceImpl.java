package com.truck.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.truck.common.Const;
import com.truck.common.ServerResponse;
import com.truck.dao.AccessTimeMapper;
import com.truck.dao.DeviceAdminMapper;
import com.truck.dao.DeviceMapper;
import com.truck.pojo.AccessTime;
import com.truck.pojo.Device;
import com.truck.pojo.DeviceAdmin;
import com.truck.service.*;
import com.truck.util.BigDecimalUtil;
import com.truck.util.DateTimeUtil;
import com.truck.vo.DeviceAdminVo;
import com.truck.vo.DeviceVo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ming
 */
@Service("iDeviceAdminService")
public class DeviceAdminServiceImpl implements IDeviceAdminService {


    @Autowired
    private DeviceAdminMapper deviceAdminMapper;
    @Autowired
    private AccessTimeMapper accessTimeMapper;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private IDeviceService iDeviceService;

    public ServerResponse add( DeviceAdmin deviceAdmin){
        if (deviceAdmin.getAdminId()==null) {
            return ServerResponse.createByErrorMessage("请选择员工");
        }
        int count = deviceAdminMapper.checkDeviceAdmin(deviceAdmin.getAdminId());
        if (count>0)
            return ServerResponse.createByErrorMessage("已经在操作一台设备了");
        deviceAdmin.setStatus(Const.DeviceAdminStatusEnum.USING.getCode());
        deviceAdmin.setFrequency(Const.START_FREQUENCY);
        if (deviceAdmin.getDeviceId()== null ) {
            return ServerResponse.createByErrorMessage("请选择设备");
        }
        int count2 = deviceAdminMapper.checkDevice(deviceAdmin.getDeviceId());
        if (count2>0)
            return ServerResponse.createByErrorMessage("该设备已经有人在使用了");
        int rowCount = deviceAdminMapper.insertSelective(deviceAdmin);
        if(rowCount > 0){
            Map result = Maps.newHashMap();
            result.put("deviceAdminId",deviceAdmin.getId());
            return ServerResponse.createBySuccess("新建设备用户记录成功",result);
        }
        return ServerResponse.createByErrorMessage("新建设备用户记录失败");
    }

    public ServerResponse<String> del(Integer adminId, Integer deviceAdminId){
        int resultCount = deviceAdminMapper.deleteByPrimaryKey(deviceAdminId);
        if(resultCount > 0){
            return ServerResponse.createBySuccess("删除设备用户记录成功");
        }
        return ServerResponse.createByErrorMessage("删除设备用户记录失败");
    }


    public ServerResponse update(Integer adminId, DeviceAdmin deviceAdmin){
        if (adminId != null) {
            deviceAdmin.setAdminId(adminId);
        }
        int rowCount = deviceAdminMapper.updateByPrimaryKeySelective(deviceAdmin);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("更新设备用户记录成功");
        }
        return ServerResponse.createByErrorMessage("更新设备用户记录失败");
    }

    public ServerResponse noUse(Integer adminId, DeviceAdmin deviceAdmin){
        if (adminId != null) {
            deviceAdmin.setAdminId(adminId);
        }
        deviceAdmin.setStatus(Const.DeviceAdminStatusEnum.NO_USING.getCode());
        int rowCount = deviceAdminMapper.updateByPrimaryKeySelective(deviceAdmin);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("取消设备用户记录成功");
        }
        return ServerResponse.createByErrorMessage("取消设备用户记录失败");
    }

    public ServerResponse noUseAll(){
        DeviceAdmin deviceAdmin =new DeviceAdmin();
        deviceAdmin.setStatus(Const.DeviceAdminStatusEnum.NO_USING.getCode());
        int rowCount = deviceAdminMapper.updateNoUseAll(deviceAdmin);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("取消所有设备用户记录成功");
        }
        return ServerResponse.createByErrorMessage("取消所有设备用户记录失败");
    }

    public ServerResponse<DeviceAdmin> select(Integer adminId, Integer deviceAdminId){
        DeviceAdmin deviceAdmin = deviceAdminMapper.selectByIdAdminId(adminId,deviceAdminId);
        if(deviceAdmin == null){
            return ServerResponse.createByErrorMessage("无法查询到该设备用户记录");
        }
        return ServerResponse.createBySuccess("查询设备用户记录成功",deviceAdmin);
    }

    public ServerResponse selectUsingSelf(Integer adminId){
        Map map = Maps.newHashMap();
        List<DeviceAdmin> deviceAdminList = deviceAdminMapper.selectByAdminIdStatus(adminId,null,Const.DeviceAdminStatusEnum.USING.getCode());
        if (deviceAdminList.size()==1) {
            map.put("deviceAdmin",deviceAdminList.get(0));
            Device device=deviceMapper.selectByPrimaryKey(deviceAdminList.get(0).getDeviceId());
            if (device != null) {
                DeviceVo deviceVo =iDeviceService.assembleDeviceVo(device);
                map.put("deviceVo",deviceVo);
            }
            return ServerResponse.createBySuccess("查询正在使用的设备成功",map);
        }
        return ServerResponse.createByErrorMessage("没有查询到正在使用的设备");
    }


    public ServerResponse list(Integer adminId, Integer deviceId,Integer status,int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<DeviceAdmin> deviceAdminList = deviceAdminMapper.selectByAdminIdStatus(adminId,deviceId,status);
        List<DeviceAdminVo> deviceAdminVoList = Lists.newArrayList();
        for(DeviceAdmin deviceAdmin :deviceAdminList){
            DeviceAdminVo deviceAdminVo = new DeviceAdminVo();
            deviceAdminVo = assembleDeviceAdminVo(deviceAdmin);
            deviceAdminVoList.add(deviceAdminVo);
        }
        PageInfo pageInfo = new PageInfo(deviceAdminVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse listUsing(Integer categoryNo,Integer status,int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<DeviceAdmin> deviceAdminList = deviceAdminMapper.selectByCategoryNoStatus(categoryNo,status);
        List<DeviceAdminVo> deviceAdminVoList = Lists.newArrayList();
        for(DeviceAdmin deviceAdmin :deviceAdminList){
            DeviceAdminVo deviceAdminVo = new DeviceAdminVo();
            deviceAdminVo = assembleDeviceAdminVo(deviceAdmin);
            if(deviceAdminVo.getId()!=1){
                deviceAdminVoList.add(deviceAdminVo);
            }
        }
        PageInfo pageInfo = new PageInfo(deviceAdminVoList);

        return ServerResponse.createBySuccess(pageInfo);
    }

    public DeviceAdminVo assembleDeviceAdminVo(DeviceAdmin deviceAdmin) {
        DeviceAdminVo deviceAdminVo = new DeviceAdminVo();
        deviceAdminVo.setId(deviceAdmin.getId());
        deviceAdminVo.setAdminId(deviceAdmin.getAdminId());
        deviceAdminVo.setDeviceId(deviceAdmin.getDeviceId());
        deviceAdminVo.setStatus(deviceAdmin.getStatus());
        deviceAdminVo.setFrequency(deviceAdmin.getFrequency());
        deviceAdminVo.setCreateTime(DateTimeUtil.dateToStr(deviceAdmin.getCreateTime()));
        deviceAdminVo.setUpdateTime(DateTimeUtil.dateToStr(deviceAdmin.getUpdateTime()));
        deviceAdminVo.setDateStr(DateTimeUtil.dateToStr(deviceAdmin.getCreateTime(),"yyyy-MM-dd"));
        AccessTime accessTime1 = accessTimeMapper.selectLastByDeviceId(deviceAdmin.getDeviceId());
        deviceAdminVo.setRemainTime(BigDecimal.ZERO);
        if(accessTime1!=null){
//            BigDecimal remainTime = BigDecimalUtil.sub(new BigDecimal(600).doubleValue(),BigDecimalUtil.div(new Date().getTime()-accessTime1.getCreateTime().getTime(),1000).doubleValue());
            BigDecimal remainTime = BigDecimalUtil.subInt(30,BigDecimalUtil.div(new Date().getTime()-accessTime1.getCreateTime().getTime(),1000).intValue());
   /*         Date time = DateUtils.addMinutes(new Date(),-(accessTime1.getCreateTime().getDate()));
            BigDecimal time1 =BigDecimalUtil.div(time.getTime(),1000);*/
            if(remainTime.compareTo(BigDecimal.ZERO)>0)
                deviceAdminVo.setRemainTime(remainTime);
            }
        Device device = deviceMapper.selectByPrimaryKey(deviceAdmin.getDeviceId());
        if (device != null) {
            DeviceVo deviceVo = iDeviceService.assembleDeviceVo(device);
            deviceAdminVo.setDeviceVo(deviceVo);
        }
        return deviceAdminVo;
    }





}
