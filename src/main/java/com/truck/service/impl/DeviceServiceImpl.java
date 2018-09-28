package com.truck.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.truck.common.Const;
import com.truck.common.ServerResponse;
import com.truck.dao.DeviceAdminMapper;
import com.truck.dao.DeviceMapper;
import com.truck.pojo.*;
import com.truck.service.*;
import com.truck.util.*;
import com.truck.vo.*;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by ming
 */
@Service("iDeviceService")
public class DeviceServiceImpl implements IDeviceService {


    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private DeviceAdminMapper deviceAdminMapper;
    @Autowired
    private IOilService iOilService;
    @Autowired
    private IMileageService iMileageService;
    @Autowired
    private IAccessTimeService iAccessTimeService;
    @Autowired
    private IWorkTimeService iWorkTimeService;

    private FileService fileService;

    private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

    public ServerResponse add(Integer adminId, Device device){
        device.setAdminId(adminId);
        if (device.getCategoryNo()== null ) {
            return ServerResponse.createByErrorMessage("请添加设备类型");
        }
        if (device.getProductId()== null ) {
            return ServerResponse.createByErrorMessage("请选择设备");
        }
        ProductDetailVo productDetailVo = (ProductDetailVo)this.searchByIdProductShow(device.getProductId()).getData();
        device.setDeviceName(productDetailVo.getProductTitle());
        if (!StringUtils.isEmpty(device.getDeviceNo())) {
            int count = deviceMapper.checkDeviceNo(device.getDeviceNo(),device.getCategoryNo());
            if (count>0)
                return ServerResponse.createByErrorMessage("设备编号已存在");
        }
  /*      ServerResponse serverResponse = this.registerUser(device.getDeviceName());
        if(!serverResponse.isSuccess()){
            return serverResponse;
        }*/
        int rowCount = deviceMapper.insertSelective(device);
        if(rowCount > 0){
            Map result = Maps.newHashMap();
            result.put("deviceId",device.getId());
            return ServerResponse.createBySuccess("新建设备记录成功",result);
        }
        return ServerResponse.createByErrorMessage("新建设备记录失败");
    }

    public ServerResponse<String> del(Integer adminId, Integer deviceId){
        int resultCount = deviceMapper.deleteByDeviceIdAdminId(adminId,deviceId);
        List<DeviceAdmin> deviceAdminList =  deviceAdminMapper.selectByAdminIdStatus(adminId,deviceId,Const.DeviceAdminStatusEnum.USING.getCode());
        if (deviceAdminList.size()>0){
            return ServerResponse.createByErrorMessage("该设备正在有人使用，无法删除");
        }
        if(resultCount > 0){
            return ServerResponse.createBySuccess("删除设备记录成功");
        }
        return ServerResponse.createByErrorMessage("删除设备记录失败");
    }


    public ServerResponse update(Integer adminId, Device device){
        if (adminId != null) {
            device.setAdminId(adminId);
        }
        Device device1 = deviceMapper.selectByPrimaryKey(device.getId());
        if(device1 ==null){
            return ServerResponse.createByErrorMessage("没有该设备");
        }
        if (!StringUtils.isEmpty(device.getDeviceNo())) {
            int count = deviceMapper.checkDeviceNo(device.getDeviceNo(),device1.getCategoryNo());
            if (count>0)
                return ServerResponse.createByErrorMessage("设备编号已存在");
        }
        int rowCount = deviceMapper.updateByPrimaryKeySelective(device);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("更新设备记录成功");
        }
        return ServerResponse.createByErrorMessage("更新设备记录失败");
    }

    public ServerResponse<Device> select(Integer adminId, Integer deviceId){
        Device device = deviceMapper.selectByIdAdminId(adminId,deviceId);

        if(device == null){
            return ServerResponse.createByErrorMessage("无法查询到该设备记录");
        }
        return ServerResponse.createBySuccess("查询设备记录成功",device);
    }


    public ServerResponse list(Integer adminId, Integer productId,Integer categoryNo,String deviceNo,int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Device> deviceList = deviceMapper.selectByAdminIdNo(adminId,productId,categoryNo,deviceNo);
        List<DeviceVo> deviceVoList = Lists.newArrayList();
        for(Device device :deviceList){
            DeviceVo deviceVo = new DeviceVo();
            deviceVo = assembleDeviceVo(device);
            if(deviceVo.getId()!=1){
                deviceVoList.add(deviceVo);
            }
        }
        PageInfo pageInfo = new PageInfo(deviceVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse listNoUsing(Integer adminId, Integer productId,Integer categoryNo,String deviceNo,int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Device> deviceList = deviceMapper.selectByAdminIdNoStatusNoUsing(adminId,productId,categoryNo,deviceNo);
        List<DeviceVo> deviceVoList = Lists.newArrayList();
        for(Device device :deviceList){
            DeviceVo deviceVo = new DeviceVo();
            deviceVo = assembleDeviceVo(device);
            deviceVoList.add(deviceVo);
        }
        PageInfo pageInfo = new PageInfo(deviceVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse listAllTotal(Integer adminId, Integer driverId, Integer deviceId,String searchDate,String beginDate,String endDate,Integer productId,Integer categoryNo,String deviceNo,int pageNum, int pageSize){

        List<Device> deviceList = deviceMapper.selectByAdminIdNo(adminId,productId,categoryNo,deviceNo);
        List<DeviceTotalInfoVo> deviceTotalInfoVos = Lists.newArrayList();
        BigDecimal allAllDeviceTotalRoundTriptimes = BigDecimal.ZERO;
        BigDecimal allAllDeviceTotalMileage = BigDecimal.ZERO;
        BigDecimal allAllDeviceTotalNum = BigDecimal.ZERO;
        BigDecimal allAllDeviceTotalPrice = BigDecimal.ZERO;
        BigDecimal allAllDeviceTotalWorkTime = BigDecimal.ZERO;
//        BigDecimal allAllDeviceTotalLoadTimes = BigDecimal.ZERO;
        for (Device device : deviceList) {
            DeviceTotalInfoVo deviceTotalInfoVo = new DeviceTotalInfoVo();
            //油量设备都用
            Map oilVoMap = (Map)iOilService.list(adminId,driverId,device.getId(),categoryNo,searchDate,beginDate,endDate,pageNum,pageSize).getData();
            if (oilVoMap.get("totalOilNum")!=null){
                allAllDeviceTotalNum = BigDecimalUtil.add(allAllDeviceTotalNum.doubleValue() ,((BigDecimal)oilVoMap.get("totalOilNum")).doubleValue());
                if (oilVoMap.get("totalOilPrice")!=null)
                allAllDeviceTotalPrice = BigDecimalUtil.add(allAllDeviceTotalPrice.doubleValue() ,((BigDecimal)oilVoMap.get("totalOilPrice")).doubleValue());
                deviceTotalInfoVo.setOilVoMap(oilVoMap);
            }
            //只有卡车才统计来回趟数，里程
            if( device.getCategoryNo()==Const.DeviceCategoryEnum.TRUCK.getCode()){
                Map accessTimeProductVoMap = (Map)iAccessTimeService.list(adminId,driverId,device.getId(),searchDate,beginDate,endDate,pageNum,pageSize).getData();
                if (!accessTimeProductVoMap.get("AllDeviceTotalRoundTriptimes").equals(BigDecimal.ZERO)){
                    allAllDeviceTotalRoundTriptimes = BigDecimalUtil.add(allAllDeviceTotalRoundTriptimes.doubleValue() ,((BigDecimal)accessTimeProductVoMap.get("AllDeviceTotalRoundTriptimes")).doubleValue());
                    deviceTotalInfoVo.setAccessTimeProductVoMap(accessTimeProductVoMap);
                }
                Map mileageProductVoMap = (Map)iMileageService.listDaysCondition(adminId,driverId,device.getId(),searchDate,beginDate,endDate,pageNum,pageSize).getData();
                if (mileageProductVoMap.size()>0){
                    allAllDeviceTotalMileage = BigDecimalUtil.add(allAllDeviceTotalMileage.doubleValue() ,((BigDecimal)mileageProductVoMap.get("AllDeviceTotalMileage")).doubleValue());
                    deviceTotalInfoVo.setMileageProductVoMap(mileageProductVoMap);
                }
            }

            //挖机 工作时间，装载次数
            if( device.getCategoryNo()==Const.DeviceCategoryEnum.DIG_MACHINE.getCode()){
                Map workTimeProductVoMap = (Map)iWorkTimeService.list(adminId,driverId,device.getId(),searchDate,beginDate,endDate,pageNum,pageSize).getData();
                if (workTimeProductVoMap.size()>0){
                    allAllDeviceTotalWorkTime = BigDecimalUtil.add(allAllDeviceTotalWorkTime.doubleValue() ,((BigDecimal)workTimeProductVoMap.get("AllDeviceTotalWorkTime")).doubleValue());
                    deviceTotalInfoVo.setWorkTimeProductMap(workTimeProductVoMap);
                }
            }


            if(deviceTotalInfoVo.getAccessTimeProductVoMap()!=null || deviceTotalInfoVo.getMileageProductVoMap()!=null || deviceTotalInfoVo.getOilVoMap()!=null || deviceTotalInfoVo.getMileageProductVoMap()!=null)
            deviceTotalInfoVos.add(deviceTotalInfoVo);
        }
            Integer  allAllDeviceTotalLoadTimes = deviceAdminMapper.selectTotalFrequencyAllDevice(searchDate,beginDate,endDate);
            Map map = Maps.newHashMap();
            map.put("allAllDeviceTotalRoundTriptimes",allAllDeviceTotalRoundTriptimes);
//            map.put("allAllDeviceTotalMileage",allAllDeviceTotalMileage);
            map.put("allAllDeviceTotalNum",allAllDeviceTotalNum);
            map.put("allAllDeviceTotalPrice",allAllDeviceTotalPrice);

            Map oilTruckVoMap = (Map)iOilService.list(adminId,driverId,null,Const.DeviceCategoryEnum.TRUCK.getCode(),searchDate,beginDate,endDate,pageNum,pageSize).getData();
            if (oilTruckVoMap.get("totalOilNum")!=null){
                map.put("truckTotalNum",oilTruckVoMap.get("totalOilNum"));
                if (oilTruckVoMap.get("totalOilPrice")!=null) {
                    map.put("truckTotalPrice",oilTruckVoMap.get("totalOilPrice"));
                }
            }else{
                map.put("truckTotalNum",0);
                map.put("truckTotalPrice",0);
            }
            Map oilWaJiVoMap = (Map)iOilService.list(adminId,driverId,null,Const.DeviceCategoryEnum.DIG_MACHINE.getCode(),searchDate,beginDate,endDate,pageNum,pageSize).getData();
            if (oilWaJiVoMap.get("totalOilNum")!=null){
                map.put("waJiTotalNum",oilWaJiVoMap.get("totalOilNum"));
                if (oilWaJiVoMap.get("totalOilPrice")!=null) {
                    map.put("waJiTotalPrice",oilWaJiVoMap.get("totalOilPrice"));
                }
            }else{
                map.put("waJiTotalNum",0);
                map.put("waJiTotalPrice",0);
            }
//            map.put("allAllDeviceTotalWorkTime",allAllDeviceTotalWorkTime);
//            map.put("allAllDeviceTotalLoadTimes",allAllDeviceTotalLoadTimes、);
//            map.put("deviceTotalInfoVos",deviceTotalInfoVos);、
        return ServerResponse.createBySuccess(map);
    }

    public DeviceVo assembleDeviceVo(Device device) {
        DeviceVo deviceVo = new DeviceVo();
        deviceVo.setId(device.getId());
        deviceVo.setAdminId(device.getAdminId());
        deviceVo.setProductId(device.getProductId());
        deviceVo.setCategoryNo(device.getCategoryNo());
        deviceVo.setDeviceNo(device.getDeviceNo());
        deviceVo.setCategoryDesc(Const.DeviceCategoryEnum.codeOf(device.getCategoryNo()).getValue());
        deviceVo.setCreateTime(DateTimeUtil.dateToStr(device.getCreateTime()));
        deviceVo.setUpdateTime(DateTimeUtil.dateToStr(device.getUpdateTime()));
        List<DeviceAdmin> deviceAdminList = deviceAdminMapper.selectByAdminIdStatus(null,device.getId(),Const.DeviceAdminStatusEnum.USING.getCode());
        if (deviceAdminList.size()>0) {
            deviceVo.setStatus(Const.DeviceAdminStatusEnum.USING.getCode());
        }else{
            deviceVo.setStatus(Const.DeviceAdminStatusEnum.NO_USING.getCode());
        }
        deviceVo.setStatusDesc(Const.DeviceAdminStatusEnum.codeOf(deviceVo.getStatus()).getValue());
        deviceVo.setDeviceName(device.getDeviceName());
        deviceVo.setFactoryNo(device.getFactoryNo());
        if (deviceVo.getProductId() != null) {
            deviceVo.setProductSubtitle("http://product-show.ayotrust.com:9001/#/productDetail/"+deviceVo.getProductId());
        }
            deviceVo.setBuyUrl("http://inventory-management.ayotrust.com:9004/#/productlist/"+deviceVo.getDeviceName());
        return deviceVo;
    }

    public ServerResponse listProductShow() {
//        String url = "http://101.132.172.240:8098/manage/product/selectList.do";
        String url = "http://localhost:8098/manage/product/selectList.do";
//        String str = Post4.connectionUrl(url, null);
        String str = Post4.connectionUrl(url, null,null);
        JSONObject jsonObject = JSONObject.fromObject(str);

            String productListStr = jsonObject.get("data").toString();
            List<Product> productList = JsonUtil.string2Obj(productListStr, List.class,Product.class);
        if (productList.size()>0) {
            return ServerResponse.createBySuccess("产品列表", productList);
        }
        return ServerResponse.createBySuccess("没有产品");
    }

    public ServerResponse searchProductShow(Integer categoryNo) {
//        String url = "http://101.132.172.240:8098/manage/product/search.do";
          String url = "http://localhost:8098/manage/product/search.do";
        StringBuffer sb = new StringBuffer();
        if (categoryNo != null) {
            if(categoryNo>3)
                return ServerResponse.createBySuccessMessage("categoryNo不正确{}"+categoryNo);
            String categoryDesc = Const.DeviceCategoryEnum.codeOf(categoryNo).getValue();
            sb.append("categoryKeyword=").append(categoryDesc);
        }
//        String str = Post4.connectionUrl(url, sb);
        String str = Post4.connectionUrl(url, sb,null);
        JSONObject jsonObject = JSONObject.fromObject(str);

            String productListStr = jsonObject.get("data").toString();
            List<ProductListVo> productListVos = JsonUtil.string2Obj(productListStr, List.class,ProductListVo.class);
        if (productListVos.size()>0) {
            Iterator<ProductListVo> it = productListVos.iterator();
            while (it.hasNext()) {
                ProductListVo productListVo = it.next();
                List<Device> deviceList = deviceMapper.selectByAdminIdNo(null,productListVo.getProductId(),null,null);
                if (CollectionUtils.isNotEmpty(deviceList)) {
                    it.remove();
                    continue;
                }
                //productTitle 存设备识别名称  productSubtitle 存对应链接
                productListVo.setProductSubtitle("http://product-show.ayotrust.com:9001/#/productDetail/"+productListVo.getProductId());
            }
            if (productListVos.size()>0) {
                return ServerResponse.createBySuccess("产品列表", productListVos);
            }
        }
        return ServerResponse.createBySuccess("没有产品");
    }

    public ServerResponse searchByIdProductShow(Integer productId) {
//        String url = "http://39.104.139.229:8082/product/selectById.do";
        String url = "http://localhost:8098/product/selectById.do";
        StringBuffer sb = new StringBuffer();
        sb.append("productId=").append(productId);
//        String str = Post4.connectionUrl(url, sb);
        String str = Post4.connectionUrl(url, sb,null);
        JSONObject jsonObject = JSONObject.fromObject(str);

        String productListStr = jsonObject.get("data").toString();
        ProductDetailVo productDetailVo = JsonUtil.string2Obj(productListStr,ProductDetailVo.class);
        if (productDetailVo !=null) {
            return ServerResponse.createBySuccess("产品", productDetailVo);
        }
        return ServerResponse.createBySuccess("没有产品");
    }

/*    public ServerResponse toKuCun() {
        String url = "http://101.132.172.240:8094/user/login.do";
        StringBuffer sb = new StringBuffer();
        sb.append("userName=").append("admin234").append("&password=123456789");
        String str = Post4.connectionUrl(url, sb);
        JSONObject jsonObject = JSONObject.fromObject(str);

        String productListStr = jsonObject.get("data").toString();
        ProductDetailVo productDetailVo = JsonUtil.string2Obj(productListStr,ProductDetailVo.class);
        if (productDetailVo !=null) {
            return ServerResponse.createBySuccess("产品", productDetailVo);
        }
        return ServerResponse.createBySuccess("没有产品");
    }*/

    //库存系统注册
    public ServerResponse registerUser(String deviceName) {
        String url = "http://39.104.139.229:8083/user/registerLoginSession.do";
        StringBuffer sb = new StringBuffer();
        sb.append("userName=").append(deviceName).append("&password=123456").append("&email=").append(UUID.randomUUID().toString()).append("&phone=88888888");
        String str = Post4.connectionUrl(url, sb,null);
        if (str.equals("error")) {
            return ServerResponse.createByErrorMessage("库存系统异常，注册用户失败");
        }
        //库存系统注册直接登录
        JSONObject jsonObject = JSONObject.fromObject(str);
        String status = jsonObject.get("status").toString();
        if (status.equals("1")) {
            String errMsg = jsonObject.get("msg").toString();
            return ServerResponse.createByErrorMessage(errMsg);
        }
        String Str = jsonObject.get("data").toString();
        Map map1 = JsonUtil.string2Obj(Str,Map.class,String.class,Object.class);
        String strs = JsonUtil.string2Obj(map1.get("sessionId").toString(),String.class);
        String url2 = "http://39.104.139.229:8083/company/save.do";
        StringBuffer sb2 = new StringBuffer();
        sb2.append("companyName=").append(UUID.randomUUID().toString()).append("&companyEmail=").append(UUID.randomUUID().toString()).append("&dutyId=").append(UUID.randomUUID().toString()).append("&officeAddress=yin").append("&companyAddress=yin").append("&companyFax=yin").append("&companyContactor=yin").append("&companyAccount=http://cdn.ayotrust.com/upload/d9531a77-8dd4-4c17-8b6d-ec8f809902a6.jpg").append("&companyTaxCard=http://cdn.ayotrust.com/upload/d9531a77-8dd4-4c17-8b6d-ec8f809902a6.jpg").append("&companySppkp=http://cdn.ayotrust.com/upload/d9531a77-8dd4-4c17-8b6d-ec8f809902a6.jpg").append("&companyLicence=http://cdn.ayotrust.com/upload/d9531a77-8dd4-4c17-8b6d-ec8f809902a6.jpg").append("&companyPhone=12365458");
        String str2 = Post4.connectionUrl(url2, sb2,strs);
        if (str2.equals("error")) {
            return ServerResponse.createByErrorMessage("库存系统异常，注册公司失败");
        }
        return ServerResponse.createBySuccess("注册成功，添加公司信息成功");
    }


}
