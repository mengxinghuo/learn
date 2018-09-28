package com.truck.controller.backend;

import com.truck.common.Const;
import com.truck.common.ResponseCode;
import com.truck.common.ServerResponse;
import com.truck.pojo.Admin;
import com.truck.pojo.Device;
import com.truck.service.IAdminService;
import com.truck.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by ming
 */

@Controller
@RequestMapping("/manage/device")
public class DeviceManageController {


    @Autowired
    private IDeviceService iDeviceService;

    @Autowired
    private IAdminService iAdminService;


    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse add(HttpSession session, Device device){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iDeviceService.add(admin.getAdminId(), device);
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
            return iDeviceService.del(null,id);
        }
        return ServerResponse.createByErrorMessage("无权限操作");
    }

    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse update(HttpSession session, Device device){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iDeviceService.update(null,device);
        }
        return ServerResponse.createByErrorMessage("无权限操作");
    }


    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<Device> select(HttpSession session, Integer id){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
            return iDeviceService.select(null,id);
    }


    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "adminId", required = false) Integer adminId,
                               @RequestParam(value = "productId", required = false) Integer productId,
                               @RequestParam(value = "categoryNo", required = false) Integer categoryNo,
                               @RequestParam(value = "deviceNo", required = false) String deviceNo,
                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
            return iDeviceService.list(null,productId,categoryNo,deviceNo,pageNum,pageSize);
    }

    @RequestMapping("listNoUsing.do")
    @ResponseBody
    public ServerResponse listNoUsing(HttpSession session,
                               @RequestParam(value = "adminId", required = false) Integer adminId,
                               @RequestParam(value = "productId", required = false) Integer productId,
                               @RequestParam(value = "categoryNo", required = false) Integer categoryNo,
                               @RequestParam(value = "deviceNo", required = false) String deviceNo,
                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
            return iDeviceService.listNoUsing(null,productId,categoryNo,deviceNo,pageNum,pageSize);
    }

    @RequestMapping("listAllTotal.do")
    @ResponseBody
    public ServerResponse listAllTotal(HttpSession session,
                                       @RequestParam(value = "adminId", required = false) Integer adminId,
                                       @RequestParam(value = "driverId", required = false) Integer driverId,
                                       @RequestParam(value = "deviceId", required = false) Integer deviceId,
                                       @RequestParam(value = "searchDate", required = false) String searchDate,
                                       @RequestParam(value = "beginDate", required = false) String beginDate,
                                       @RequestParam(value = "endDate", required = false) String endDate,
                                       @RequestParam(value = "productId", required = false) Integer productId,
                                       @RequestParam(value = "categoryNo", required = false) Integer categoryNo,
                                       @RequestParam(value = "deviceNo", required = false) String deviceNo,
                                       @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iDeviceService.listAllTotal(null,driverId,deviceId,searchDate,beginDate,endDate,productId,categoryNo,deviceNo,pageNum,pageSize);
        }
        return ServerResponse.createByErrorMessage("无权限操作");
    }

    /**
     *  查询  ProductShow系统
     * @param session
     * @return
     */
    @RequestMapping(value = "listProductShow.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse listProductShow(HttpSession session){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iDeviceService.listProductShow( );
        }
        return ServerResponse.createByErrorMessage("无权限操作");
    }

    /**
     *  搜索  ProductShow系统   分类
     * @param session
     * @return
     */
    @RequestMapping(value = "searchProductShow.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse searchProductShow(HttpSession session,@RequestParam(value = "categoryNo", required = false) Integer categoryNo){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iDeviceService.searchProductShow(categoryNo);
        }
        return ServerResponse.createByErrorMessage("无权限操作");
    }











}
