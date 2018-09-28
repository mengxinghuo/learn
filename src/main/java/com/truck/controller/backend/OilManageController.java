package com.truck.controller.backend;


import com.github.pagehelper.PageInfo;
import com.truck.common.Const;
import com.truck.common.ResponseCode;
import com.truck.common.ServerResponse;
import com.truck.pojo.Oil;
import com.truck.pojo.Admin;
import com.truck.service.IAdminService;
import com.truck.service.IOilService;
import com.truck.vo.OilVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/oil/")
public class OilManageController {

    @Autowired
    private IOilService iOilService;

    @Autowired
    private IAdminService iAdminService;

    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse add(HttpSession session, Oil oil){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iOilService.add(oil);
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
            return iOilService.del(null,id);
        }
        return iOilService.del(admin.getAdminId(),id);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse update(HttpSession session, Oil oil){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iOilService.update(null,oil);
        }
        return iOilService.update(admin.getAdminId(),oil);
    }

    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<OilVo> select(HttpSession session, Integer id){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iOilService.select(null,id);
        }
        return iOilService.select(admin.getAdminId(),id);
    }


    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "adminId", required = false) Integer adminId,
                               @RequestParam(value = "driverId", required = false) Integer driverId,
                               @RequestParam(value = "deviceId", required = false) Integer deviceId,
                               @RequestParam(value = "categoryNo", required = false) Integer categoryNo,
                               @RequestParam(value = "searchDate", required = false) String searchDate,
                               @RequestParam(value = "beginDate", required = false) String beginDate,
                               @RequestParam(value = "endDate", required = false) String endDate,
                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iOilService.list(adminId,driverId,deviceId,categoryNo,searchDate,beginDate,endDate,pageNum,pageSize);
        }
        return iOilService.list(admin.getAdminId(),driverId,deviceId,categoryNo,searchDate,beginDate,endDate,pageNum,pageSize);
    }



}
