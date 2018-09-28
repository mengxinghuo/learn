package com.truck.controller.backend;


import com.truck.common.Const;
import com.truck.common.ResponseCode;
import com.truck.common.ServerResponse;
import com.truck.pojo.Admin;
import com.truck.pojo.WorkTime;
import com.truck.service.IAdminService;
import com.truck.service.IWorkTimeService;
import com.truck.vo.WorkTimeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/workTime/")
public class WorkTimeManageController {

    @Autowired
    private IWorkTimeService iWorkTimeService;

    @Autowired
    private IAdminService iAdminService;

    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse add(HttpSession session, WorkTime workTime){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iWorkTimeService.add(workTime);
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
            return iWorkTimeService.del(null,id);
        }
        return iWorkTimeService.del(admin.getAdminId(),id);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse update(HttpSession session, WorkTime workTime){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iWorkTimeService.update(null,workTime);
        }
        return iWorkTimeService.update(admin.getAdminId(),workTime);
    }

    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<WorkTimeVo> select(HttpSession session, Integer id){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iWorkTimeService.select(null,id);
        }
        return iWorkTimeService.select(admin.getAdminId(),id);
    }


    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "adminId", required = false) Integer adminId,
                               @RequestParam(value = "driverId", required = false) Integer driverId,
                               @RequestParam(value = "deviceId", required = false) Integer deviceId,
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
            return iWorkTimeService.list(adminId,driverId,deviceId,searchDate,beginDate,endDate,pageNum,pageSize);
        }
        return iWorkTimeService.list(admin.getAdminId(),driverId,deviceId,searchDate,beginDate,endDate,pageNum,pageSize);
    }

    @RequestMapping("listNoDate.do")
    @ResponseBody
    public ServerResponse listCondition(HttpSession session,
                                        @RequestParam(value = "adminId", required = false) Integer adminId,
                                        @RequestParam(value = "driverId", required = false) Integer driverId,
                                        @RequestParam(value = "deviceId", required = false) Integer deviceId,
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
            return iWorkTimeService.listCondition(adminId,driverId,deviceId,searchDate,beginDate,endDate,pageNum,pageSize);
        }
        return iWorkTimeService.listCondition(admin.getAdminId(),driverId,deviceId,searchDate,beginDate,endDate,pageNum,pageSize);
    }

}
