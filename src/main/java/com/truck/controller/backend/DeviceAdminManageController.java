package com.truck.controller.backend;

import com.truck.common.Const;
import com.truck.common.ResponseCode;
import com.truck.common.ServerResponse;
import com.truck.pojo.Admin;
import com.truck.pojo.DeviceAdmin;
import com.truck.service.IAccessTimeService;
import com.truck.service.IAdminService;
import com.truck.service.IDeviceAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by ming
 */

@Controller
@RequestMapping("/manage/deviceAdmin")
public class DeviceAdminManageController {


    @Autowired
    private IDeviceAdminService iDeviceAdminService;

    @Autowired
    private IAdminService iAdminService;


    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse add(HttpSession session, DeviceAdmin deviceAdmin){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iDeviceAdminService.add(deviceAdmin);
        }
        return ServerResponse.createByErrorMessage("无权限操作");
    }


    @RequestMapping("del.do")
    @ResponseBody
    public ServerResponse del(HttpSession session, Integer id){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iDeviceAdminService.del(null,id);
        }
        return iDeviceAdminService.del(admin.getAdminId(),id);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse update(HttpSession session, DeviceAdmin deviceAdmin){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iDeviceAdminService.update(null,deviceAdmin);
        }
        return iDeviceAdminService.update(admin.getAdminId(),deviceAdmin);
    }

    /**
     * 取消关联
     * @param session
     * @param deviceAdmin
     * @return
     */
    @RequestMapping("noUse.do")
    @ResponseBody
    public ServerResponse noUse(HttpSession session, DeviceAdmin deviceAdmin){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iDeviceAdminService.noUse(null,deviceAdmin);
        }
        return ServerResponse.createByErrorMessage("无权限操作");
    }

    /**
     * 取消关联  一键解绑
     * @param session
     * @return
     */
    @RequestMapping("noUseAll.do")
    @ResponseBody
    public ServerResponse noUseAll(HttpSession session){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iDeviceAdminService.noUseAll();
        }
        return ServerResponse.createByErrorMessage("无权限操作");
    }


    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<DeviceAdmin> select(HttpSession session, Integer id){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iDeviceAdminService.select(null,id);
        }
        return iDeviceAdminService.select(admin.getAdminId(),id);
    }

    /**
     * 查看自己正在用的设备
     * @param session
     * @return
     */
    @RequestMapping("selectUsingSelf.do")
    @ResponseBody
    public ServerResponse selectUsingSelf(HttpSession session){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return ServerResponse.createByErrorMessage("超级用户登陆，没有使用的设备");
        }
        return iDeviceAdminService.selectUsingSelf(admin.getAdminId());
    }


    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "adminId", required = false) Integer adminId,
                               @RequestParam(value = "deviceId", required = false) Integer deviceId,
                               @RequestParam(value = "status", required = false) Integer status,
                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iDeviceAdminService.list(adminId,deviceId,status,pageNum,pageSize);
        }
//            status = Const.DeviceAdminStatusEnum.USING.getCode();
            return iDeviceAdminService.list(admin.getAdminId(),deviceId,status,pageNum,pageSize);
    }

    @RequestMapping("listUsing.do")
    @ResponseBody
    public ServerResponse listUsing(HttpSession session,
                               @RequestParam(value = "status", required = false) Integer status,
                               @RequestParam(value = "categoryNo", required = false) Integer categoryNo,
                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
            status = Const.DeviceAdminStatusEnum.USING.getCode();
            return iDeviceAdminService.listUsing(categoryNo,status,pageNum,pageSize);
    }


}
