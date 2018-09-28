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
import com.truck.dao.OilMapper;
import com.truck.pojo.Device;
import com.truck.pojo.Oil;
import com.truck.pojo.DeviceAdmin;
import com.truck.pojo.Oil;
import com.truck.service.IDeviceService;
import com.truck.service.IOilService;
import com.truck.util.BigDecimalUtil;
import com.truck.util.DateTimeUtil;
import com.truck.vo.DeviceVo;
import com.truck.vo.OilVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("iOilService")
public class OilServiceImpl implements IOilService {

    @Autowired
    private OilMapper oilMapper;
    @Autowired
    private DeviceAdminMapper deviceAdminMapper;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private IDeviceService iDeviceService;

    public ServerResponse add( Oil oil){
        if(oil.getDeviceId() ==null){
            return ServerResponse.createByErrorMessage("请选择设备");
        }

        List<DeviceAdmin> deviceAdminList = deviceAdminMapper.selectByAdminIdStatus(null,oil.getDeviceId(),Const.DeviceAdminStatusEnum.USING.getCode());
        if (deviceAdminList.size() ==0){
            return ServerResponse.createByErrorMessage("该设备当前没有人使用");
        }
        DeviceAdmin deviceAdmin1 = deviceAdminList.get(0);

        oil.setAdminId(deviceAdmin1.getAdminId());
        if(oil.getDeviceOil()==null ) {
            return ServerResponse.createByErrorMessage("请设置加油量数值");
        }
        if(oil.getPrice() ==null ) {
            return ServerResponse.createByErrorMessage("请设置加油金额");
        }
        int rowCount = oilMapper.insertSelective(oil);
        if(rowCount > 0){
            Map result = Maps.newHashMap();
            result.put("id",oil.getId());
            return ServerResponse.createBySuccess("新建设备加油量记录成功",result);
        }
        return ServerResponse.createByErrorMessage("新建设备加油量记录失败");
    }

    public ServerResponse<String> del( Integer adminId,Integer oilId){
        int rowCount = oilMapper.deleteByIdAdminId(adminId, oilId);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("删除设备加油量记录成功");
        }
        return ServerResponse.createByErrorMessage("删除设备加油量记录失败");
    }

    public ServerResponse update( Integer adminId,Oil oil){
        if (adminId != null) {
            oil.setAdminId(adminId);
        }
        int rowCount = oilMapper.updateByPrimaryKeySelective(oil);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("更新设备加油量记录成功");
        }
        return ServerResponse.createByErrorMessage("更新设备加油量记录失败");
    }

    public ServerResponse<OilVo> select(Integer adminId, Integer id){
        Oil oil = oilMapper.selectByIdAdminId(adminId,id);

        if(oil == null){
            return ServerResponse.createByErrorMessage("无法查询到该设备加油量记录");
        }
        OilVo oilVo = assembleOilVo(oil);
        return ServerResponse.createBySuccess("查询设备加油量记录成功",oilVo);
    }


    public OilVo assembleOilVo(Oil oil) {
        OilVo oilVo = new OilVo();
        oilVo.setId(oil.getId());
        oilVo.setAdminId(oil.getAdminId());
        oilVo.setDeviceId(oil.getDeviceId());
        oilVo.setDriverId(oil.getDriverId());
        oilVo.setDeviceOil(oil.getDeviceOil());
        oilVo.setCreateTime(DateTimeUtil.dateToStr(oil.getCreateTime()));
        oilVo.setUpdateTime(DateTimeUtil.dateToStr(oil.getUpdateTime()));
        oilVo.setPrice(oil.getPrice());
        return oilVo;
    }

    public ServerResponse list(Integer adminId,Integer driverId,Integer deviceId,Integer categoryNo,String searchDate,String beginDate,String endDate, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);

        List<Oil> oilList = oilMapper.selectByAdminIdDate(adminId,driverId,deviceId,categoryNo,searchDate,beginDate,endDate);

        Set<Oil> oilSet = Sets.newHashSet();
        for (Oil oilItem : oilList) {
            oilSet.add(oilItem);
        }
        List<Map<String,Object>> mapList=Lists.newArrayList();
        for (Oil oil : oilSet) {
            Map mapOil = Maps.newHashMap();
            List<Oil> oilList1 = oilMapper.selectByAdminIdDate(adminId,driverId,oil.getDeviceId(),categoryNo,searchDate,beginDate,endDate);
            List<OilVo> oilVos = Lists.newArrayList();
            for (Oil oil1 : oilList1) {
                OilVo oilVo = assembleOilVo(oil1);
                oilVos.add(oilVo);
            }
            Map oilNumPrice = oilMapper.selectNumByAdminIdDate(adminId,driverId,oil.getDeviceId(),categoryNo,searchDate,beginDate,endDate);
            Device device = deviceMapper.selectByPrimaryKey(oil.getDeviceId());
            if (device != null) {
                DeviceVo deviceVo =iDeviceService.assembleDeviceVo(device);
                mapOil.put("deviceVo",deviceVo);
            }
            mapOil.put("deviceId",oil.getDeviceId());
            mapOil.put("oilVoList",oilVos);
            mapOil.put("totalNum",oilNumPrice.get("deviceOil"));
            mapOil.put("totalPrice",oilNumPrice.get("totalPrice"));
            mapList.add(mapOil);

        }

        Map map = Maps.newHashMap();
        Map mapOilNumPrice = oilMapper.selectNumByAdminIdDate(adminId,driverId,deviceId,categoryNo,searchDate,beginDate,endDate);
        if (mapOilNumPrice!=null) {
            map.put("totalOilNum",mapOilNumPrice.get("deviceOil"));
            map.put("totalOilPrice",mapOilNumPrice.get("totalPrice"));
            map.put("mapOil",mapList);
        }
        return ServerResponse.createBySuccess(map);
    }

/*    public List<OilDiffVo> assembleOilDiffVoList(List<Oil> oilList ){
        List<OilDiffVo> oilDiffVos = Lists.newArrayList();
        for (int i = 0; i < oilList.size()-1; i++) {

            OilDiffVo oilDiffVo = new OilDiffVo();
            oilDiffVo.setAdminId(oilList.get(i).getAdminId());
            oilDiffVo.setDeviceId(oilList.get(i).getDeviceId());
            oilDiffVo.setDriverId(oilList.get(i).getDriverId());
            //地址
            oilDiffVo.setStartAddress(oilList.get(i).getAddress());
            oilDiffVo.setEndAddress(oilList.get(i+1).getAddress());

            oilDiffVo.setStartTimeStr(DateTimeUtil.dateToStr(oilList.get(i).getCreateTime()));
            oilDiffVo.setEndTimeStr(DateTimeUtil.dateToStr(oilList.get(i+1).getCreateTime()));
            //相邻两条记录的时长差
            BigDecimal diffTime = BigDecimalUtil.div(oilList.get(i+1).getCreateTime().getTime()- oilList.get(i).getCreateTime().getTime(),1000*60*60);
            if( diffTime.compareTo(BigDecimal.ZERO)>0){
                oilDiffVo.setTotalTime(diffTime);
            }else{
                oilDiffVo.setTotalTime(BigDecimal.ZERO);
            }

            oilDiffVos.add(oilDiffVo);
        }
        return oilDiffVos;
    }*/



}
