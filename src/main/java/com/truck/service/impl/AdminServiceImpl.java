package com.truck.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.truck.common.Const;
import com.truck.common.ServerResponse;
import com.truck.common.TokenCache;
import com.truck.dao.AdminMapper;
import com.truck.dao.DeviceAdminMapper;
import com.truck.dao.DeviceMapper;
import com.truck.pojo.Admin;
import com.truck.pojo.Device;
import com.truck.pojo.DeviceAdmin;
import com.truck.service.IAdminService;
import com.truck.service.IDeviceService;
import com.truck.util.JsonUtil;
import com.truck.util.MD5Util;
import com.truck.util.Post3;
import com.truck.util.Post4;
import com.truck.vo.DeviceVo;
import com.truck.vo.EmployeeDTO;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("iAdminService")
public class AdminServiceImpl implements IAdminService {

    @Autowired
    AdminMapper adminMapper;
    @Autowired
    DeviceAdminMapper deviceAdminMapper;
    @Autowired
    DeviceMapper deviceMapper;
    @Autowired
    IDeviceService iDeviceService;

    @Override
    public ServerResponse<Map> login(String adminName, String password) {
        int resultCount=adminMapper.checkAdminName(adminName);
        if (resultCount==0)
            return ServerResponse.createByErrorMessage("用户名不存在");
        String md5Password= MD5Util.MD5EncodeUtf8(password);
        Admin admin=adminMapper.selectLogin(adminName,md5Password);
        if (admin==null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
             admin.setPassword(StringUtils.EMPTY);
        Map map = Maps.newHashMap();
        map.put("admin",admin);
        List<DeviceAdmin> deviceAdminList =deviceAdminMapper.selectByAdminIdStatus(admin.getAdminId(),null,Const.DeviceAdminStatusEnum.USING.getCode());
        if (deviceAdminList.size()==1) {
            map.put("deviceAdmin",deviceAdminList.get(0));
        }
        return ServerResponse.createBySuccess("登陆成功", map);
    }

    @Override
    public ServerResponse<String> register(Admin admin) {
        ServerResponse validResponse=this.checkValid(admin.getAdminName(), Const.ADMINNAME);
        if (!validResponse.isSuccess())
            return validResponse;


        validResponse=this.checkValid(admin.getEmail(),Const.EMAIL);
        if (!validResponse.isSuccess())
            return validResponse;

        admin.setRole(Const.AdminRole.ADMINROLE_ADMIN);

        //md5加密
        admin.setPassword(MD5Util.MD5EncodeUtf8(admin.getPassword()));

        int resultCount=adminMapper.insertSelective(admin);
        if(resultCount==0)
            return ServerResponse.createByErrorMessage("注册失败");
        return ServerResponse.createBySuccess("注册成功");
    }

    @Override
    public ServerResponse<String> registerBatch(List<Admin> adminList) {
        for (Admin admin : adminList) {
            ServerResponse validResponse=this.checkValid(admin.getAdminName(), Const.ADMINNAME);
            if (!validResponse.isSuccess())
                return validResponse;

            validResponse=this.checkValid(admin.getEmail(),Const.EMAIL);
            if (!validResponse.isSuccess())
                return validResponse;

            admin.setRole(Const.AdminRole.ADMINROLE_ADMIN);

            //md5加密
            admin.setPassword(MD5Util.MD5EncodeUtf8(admin.getPassword()));
        }
        adminMapper.batchInsert(adminList);
        return ServerResponse.createBySuccess("批量注册成功");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)){
            //开始校验
            if (Const.ADMINNAME.equals(type)){
                int resultCount=adminMapper.checkAdminName(str);
                if (resultCount>0)
                    return ServerResponse.createByErrorMessage("用户名已存在");
            }

            if (Const.EMAIL.equals(type)){
                int resultCount=adminMapper.checkEmail(str);
                if (resultCount>0)
                    return ServerResponse.createByErrorMessage("email已存在");
            }

        }else {
            return ServerResponse.createByErrorMessage("参数错误");

        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse<String> selectQuestion(String adminName) {
        ServerResponse<String> validResponse =this.checkValid(adminName,Const.ADMINNAME);
        if(validResponse.isSuccess())
            return ServerResponse.createByErrorMessage("用户名不存在");
        String question=adminMapper.selectQuestionByAdminName(adminName);
        if (StringUtils.isNotBlank(question))
            return ServerResponse.createBySuccess(question);
        return ServerResponse.createByErrorMessage("找回密码的问题是空");
    }

    @Override
    public ServerResponse<String> checkAnswer(String adminName, String question, String answer) {
        int resultCount =adminMapper.checkAnswer(adminName,question,answer);
        if (resultCount>0) {
            String forgetToken= UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+adminName,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);

        }
        return ServerResponse.createByErrorMessage("问题答案错误");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String adminName, String passwordNew, String forgetToken) {
        if (StringUtils.isBlank(forgetToken))
            return ServerResponse.createByErrorMessage("参数错误，token需要传递");
        ServerResponse validResponse=this.checkValid(adminName,Const.ADMINNAME);
        if(validResponse.isSuccess())
            return ServerResponse.createByErrorMessage("用户名不存在");
        String token=TokenCache.getKey(TokenCache.TOKEN_PREFIX+adminName);
        if (StringUtils.isBlank(token))
            return ServerResponse.createByErrorMessage("token无效或者过期");

        if (StringUtils.equals(forgetToken,token)){
            String md5Password=MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount=adminMapper.updatePasswordByAdminName(adminName,md5Password);
            if (rowCount>0)
                return ServerResponse.createBySuccessMessage("修改密码成功");
        }else {
            return ServerResponse.createByErrorMessage("token错误，请重新获取重置密码的token");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, Admin admin) {
        String md5Password=MD5Util.MD5EncodeUtf8(passwordOld);
        int resultCOunt=adminMapper.checkPassword(md5Password,admin.getAdminId());
        if (resultCOunt==0)
            return ServerResponse.createByErrorMessage("密码错误");
        admin.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount=adminMapper.updateByPrimaryKeySelective(admin);
        if (updateCount>0)
            return ServerResponse.createBySuccessMessage("修改密码成功");

        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    @Override
    public ServerResponse<Admin> updateInformation(Admin admin) {
        //ADMINNAME不能被跟新
        //email也要进行一个校验，校验新的email是不是已经存在，并且存在的email如果相同的话，不能是我们当前的这个用户的
        int resultCount=adminMapper.checkEmailByAdminId(admin.getEmail(),admin.getAdminId());
        if (resultCount>0)
            return  ServerResponse.createByErrorMessage("email已存在，请更换email再尝试更新");
        int updateCount=adminMapper.updateByPrimaryKeySelective(admin);
        Admin admin1 = adminMapper.selectByPrimaryKey(admin.getAdminId());
        admin1.setPassword(StringUtils.EMPTY);
        if (updateCount>0)
            return ServerResponse.createBySuccess("更新成功",admin1);
        return ServerResponse.createByErrorMessage("更新失败");
    }

    @Override
    public ServerResponse<Admin> getInfomartion(Integer userid) {
        Admin admin=adminMapper.selectByPrimaryKey(userid);
        if (admin==null)
            return ServerResponse.createByErrorMessage("找不到当前用户");
        admin.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(admin);
    }


    //backend

    /**
     * 校验是否是超级管理员
     * @param admin
     * @return
     */
    public ServerResponse checkAdminRole(Admin admin){
        if (admin!=null && admin.getRole().intValue()==Const.AdminRole.ADMINROLE_SUPERADMIN){
            return ServerResponse.createBySuccess();
        }
            System.out.println(admin.getRole());
        return ServerResponse.createByErrorMessage("不是超级管理员");
    }

    public ServerResponse listAllAdmin( ){
        this.listHrAllAdmin();
     List<Admin> adminList = adminMapper.selectAllAdmin();
     List<Map> adminAndDeviceList = Lists.newArrayList();
        for (Admin admin : adminList) {
            List<DeviceAdmin> deviceAdminList =deviceAdminMapper.selectByAdminIdStatus(admin.getAdminId(),null,Const.DeviceAdminStatusEnum.USING.getCode());
            Map map = Maps.newHashMap();
            admin.setPassword(StringUtils.EMPTY);
            if (admin.getAdminId() == 2) {
                continue;
            }
            map.put("admin",admin);
            if (deviceAdminList.size()==1) {
                map.put("deviceAdmin",deviceAdminList.get(0));
                Device device=deviceMapper.selectByPrimaryKey(deviceAdminList.get(0).getDeviceId());
                if (device != null) {
                    DeviceVo deviceVo =iDeviceService.assembleDeviceVo(device);
                    map.put("deviceVo",deviceVo);
                }
            }
            adminAndDeviceList.add(map);
        }
     return ServerResponse.createBySuccess(adminAndDeviceList);
    }

    public ServerResponse registerHrAllAdmin() {
        String url2 = "http://39.104.139.229:8084/manage/employee/get_employee_list.do";
//        String url2 = "http://47.74.253.22:8089/manage/employee/get_employee_list.do";
//        String url2 = "http://localhost:8089/manage/employee/get_employee_list.do";
//        String str2 = Post4.connectionUrl(url2, null);
        String str2 = Post4.connectionUrl(url2, null,null);
        if (str2.equals("error")) {
            return ServerResponse.createByErrorMessage("hr系统异常");
        }
        JSONObject jsonObject = JSONObject.fromObject(str2);

        if (Integer.parseInt(jsonObject.get("status").toString()) == 0) {
           String employListStr = jsonObject.get("data").toString();
            List<EmployeeDTO> employList =JsonUtil.string2Obj(employListStr,List.class, EmployeeDTO.class);
//            EmployeeDTO employeeDTO =JsonUtil.string2Obj(obj,EmployeeDTO);
//            List<EmployeeDTO> employList = (List<EmployeeDTO>) (jsonObject.get("data"));
            List<Admin> adminList = Lists.newArrayList();
            for (EmployeeDTO employeeDTO : employList) {
                Admin admin2 = adminMapper.selectByEmployeeId(employeeDTO.getEmployeeId());
                if (admin2 !=null){
                   /* if(admin2.getAdminName().equals(employeeDTO.getEmployeeName())
                            && admin2.getEmployeeNo().equals(employeeDTO.getEmployeeNo())
                            && admin2.getEmployeeAddress().equals(employeeDTO.getEmployeeAddress())
                            && admin2.getEmployeeAge().equals(employeeDTO.getEmployeeAge())
                            && admin2.getPhone().equals(employeeDTO.getPhone())
                            && admin2.getPosition().equals(employeeDTO.getPosition())){
                        continue;
                    }else{
                        admin2.setPhone(employeeDTO.getPhone());
                        admin2.setEmployeeNo(employeeDTO.getEmployeeNo());
                        if(employeeDTO.getEmployeeAge() !=null)
                        admin2.setEmployeeAge(employeeDTO.getEmployeeAge());
                        if(StringUtils.isNotBlank(employeeDTO.getEmployeeAddress()))
                        admin2.setEmployeeAddress(employeeDTO.getEmployeeAddress());
                        admin2.setPosition(employeeDTO.getPosition());
                        admin2.setAdminName(employeeDTO.getEmployeeName());
                        admin2.setPassword(MD5Util.MD5EncodeUtf8(admin2.getEmployeeNo()+admin2.getEmployeeId()));
                        adminMapper.updateByPrimaryKeySelective(admin2);
                        continue;
                    }*/
                    continue;
                }
                Admin admin = new Admin();
                admin.setEmployeeId(employeeDTO.getEmployeeId());
                admin.setPhone(employeeDTO.getPhone());
                admin.setEmployeeNo(employeeDTO.getEmployeeNo());
                if(employeeDTO.getEmployeeAge() !=null)
                admin.setEmployeeAge(employeeDTO.getEmployeeAge());
                if(StringUtils.isNotBlank(employeeDTO.getEmployeeAddress()))
                admin.setEmployeeAddress(employeeDTO.getEmployeeAddress());
                admin.setPosition(employeeDTO.getPosition());
                admin.setAdminName(employeeDTO.getEmployeeName());
                admin.setPassword(MD5Util.MD5EncodeUtf8(admin.getEmployeeNo()+admin.getEmployeeId()));
                admin.setRole(Const.AdminRole.ADMINROLE_ADMIN);
                adminList.add(admin);
            }
            if(CollectionUtils.isNotEmpty(adminList)){
                adminMapper.batchInsert(adminList);
                return ServerResponse.createBySuccess("查询hr并批量注册成功");
            }
        }
        return ServerResponse.createBySuccess("没有查询hr员工信息");

    }

    public ServerResponse listHrAllAdmin() {
        ServerResponse serverResponse = this.registerHrAllAdmin();
        List<Map> adminAndDeviceList = Lists.newArrayList();
        List<Admin> adminList = adminMapper.selectAllHrAdmin();
        for (Admin admin : adminList) {
            List<DeviceAdmin> deviceAdminList =deviceAdminMapper.selectByAdminIdStatus(admin.getAdminId(),null,Const.DeviceAdminStatusEnum.USING.getCode());
            Map map = Maps.newHashMap();
            admin.setPassword(StringUtils.EMPTY);
            if (admin.getAdminId() == 2) {
                continue;
            }

            map.put("admin",admin);
            if (deviceAdminList.size()==1) {
                map.put("deviceAdmin",deviceAdminList.get(0));
                Device device=deviceMapper.selectByPrimaryKey(deviceAdminList.get(0).getDeviceId());
                if (device != null) {
                    DeviceVo deviceVo =iDeviceService.assembleDeviceVo(device);
                    map.put("deviceVo",deviceVo);
                }
            }
            adminAndDeviceList.add(map);
        }
        String msg = StringUtils.EMPTY;
        msg = serverResponse.isSuccess() ? "请求到hr系统" : "没有请求到hr系统";
        return ServerResponse.createBySuccess(msg, adminAndDeviceList);
    }

    public ServerResponse listHrWorkingAdmin() {
             this.registerHrAllAdmin();
            String url = "http://39.104.139.229:8084/manage/employee/find_onduty_employee.do";
//            String url = "http://47.74.253.22:8089/manage/employee/find_onduty_employee.do";
//            String url = "http://localhost:8089/manage/employee/find_onduty_employee.do";
//            String str = Post4.connectionUrl(url, null);
            String str = Post4.connectionUrl(url, null,null);
            JSONObject jsonObject = JSONObject.fromObject(str);

            if (Integer.parseInt(jsonObject.get("status").toString()) == 0) {
                String employListStr = jsonObject.get("data").toString();
                List<EmployeeDTO> employList = JsonUtil.string2Obj(employListStr, List.class, EmployeeDTO.class);
                List<Admin> adminList = Lists.newArrayList();
                for (EmployeeDTO employeeDTO : employList) {
                    Admin admin = adminMapper.selectByEmployeeId(employeeDTO.getEmployeeId());
                    if (admin != null) {
                        admin.setPassword(StringUtils.EMPTY);
                        adminList.add(admin);
                    }
                }
                List<Map> adminAndDeviceList = Lists.newArrayList();
                for (Admin admin : adminList) {
                    List<DeviceAdmin> deviceAdminList =deviceAdminMapper.selectByAdminIdStatus(admin.getAdminId(),null,Const.DeviceAdminStatusEnum.USING.getCode());
                    Map map = Maps.newHashMap();
                    admin.setPassword(StringUtils.EMPTY);
                    if (admin.getAdminId() == 2) {
                        continue;
                    }

                    map.put("admin",admin);
                    if (deviceAdminList.size()==1) {
                        map.put("deviceAdmin",deviceAdminList.get(0));
                        Device device=deviceMapper.selectByPrimaryKey(deviceAdminList.get(0).getDeviceId());
                        if (device != null) {
                            DeviceVo deviceVo =iDeviceService.assembleDeviceVo(device);
                            map.put("deviceVo",deviceVo);
                        }
                    }
                    adminAndDeviceList.add(map);
                }
                return ServerResponse.createBySuccess("上班员工", adminAndDeviceList);
            }
            return ServerResponse.createBySuccess("没有上班人员");
    }

}
