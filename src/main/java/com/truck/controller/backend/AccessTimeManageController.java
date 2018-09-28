package com.truck.controller.backend;


import com.github.pagehelper.PageInfo;
import com.truck.common.Const;
import com.truck.common.ResponseCode;
import com.truck.common.ServerResponse;
import com.truck.pojo.Admin;
import com.truck.pojo.AccessTime;
import com.truck.service.IAccessTimeService;
import com.truck.service.IAdminService;
import com.truck.vo.AccessTimeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/accessTime/")
public class AccessTimeManageController {

    @Autowired
    private IAccessTimeService iAccessTimeService;
    @Autowired
    private IAdminService iAdminService;

    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse add(HttpSession session, Integer deviceAdminId){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return ServerResponse.createByErrorMessage("超级用户登陆，无法增加信息");
        }
        return iAccessTimeService.add(admin.getAdminId(),deviceAdminId);
    }

    @RequestMapping("del.do")
    @ResponseBody
    public ServerResponse del(HttpSession session, Integer id){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iAccessTimeService.del(null,id);
        }
        return iAccessTimeService.del(admin.getAdminId(),id);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse update(HttpSession session, AccessTime accessTime){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iAccessTimeService.update(null,accessTime);
        }
        return iAccessTimeService.update(admin.getAdminId(),accessTime);
    }


    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<AccessTimeVo> select(HttpSession session, Integer id){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iAccessTimeService.select(null,id);
        }
        return iAccessTimeService.select(admin.getAdminId(),id);
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
            return iAccessTimeService.list(adminId,driverId,deviceId,searchDate,beginDate,endDate,pageNum,pageSize);
        }
        return iAccessTimeService.list(admin.getAdminId(),driverId,deviceId,searchDate,beginDate,endDate,pageNum,pageSize);
    }

    //卡车
    /**
     *新的查询次数  按人查  卡车
     * @param session
     * @param adminId
     * @param searchDate
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("listTruckByAdmin.do")
    @ResponseBody
    public ServerResponse listTruckByAdmin(HttpSession session,
                               @RequestParam(value = "adminId", required = false) Integer adminId,
                               @RequestParam(value = "adminName", required = false) String adminName,
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
            return iAccessTimeService.listByAdmin(adminId,adminName,searchDate,beginDate,endDate,pageNum,pageSize);
        }
        return iAccessTimeService.listByAdmin(admin.getAdminId(),adminName,searchDate,beginDate,endDate,pageNum,pageSize);
    }

    /**
     *新的查询次数  按人查 排行  卡车
     * @param session
     * @param adminId
     * @param searchDate
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("listTruckRankByAdmin.do")
    @ResponseBody
    public ServerResponse listByAdminRank(HttpSession session,
                               @RequestParam(value = "adminId", required = false) Integer adminId,
                               @RequestParam(value = "searchDate", required = false) String searchDate,
                               @RequestParam(value = "beginDate", required = false) String beginDate,
                               @RequestParam(value = "endDate", required = false) String endDate,
                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
            return iAccessTimeService.listByAdminRank(searchDate,beginDate,endDate,pageNum,pageSize);
    }

  /**
     *新的查询次数  按卡车查
     * @param session
     * @param deviceId
     * @param searchDate
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
      * */

    @RequestMapping("listTruckByDevice.do")
    @ResponseBody
    public ServerResponse listTruckByDevice(HttpSession session,
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
            return iAccessTimeService.listByDevice(deviceId,categoryNo,searchDate,beginDate,endDate,pageNum,pageSize);
    }

  /**
     *新的查询次数  按卡车查
     * @param session
     * @param searchDate
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
      * */

    @RequestMapping("listTruckRankByDevice.do")
    @ResponseBody
    public ServerResponse listTruckRankByDevice(HttpSession session,
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
            return iAccessTimeService.listByDeviceRank(categoryNo,searchDate,beginDate,endDate,pageNum,pageSize);
    }


    //挖掘机
    /**
     *新的查询次数  按人查  挖掘机
     * @param session
     * @param adminId
     * @param searchDate
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("listWaJiByAdmin.do")
    @ResponseBody
    public ServerResponse listWaJiByAdmin(HttpSession session,
                                      @RequestParam(value = "adminId", required = false) Integer adminId,
                                      @RequestParam(value = "adminName", required = false) String adminName,
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
            return iAccessTimeService.listWaJiByAdmin(adminId,adminName,searchDate,beginDate,endDate,pageNum,pageSize);
        }
        return iAccessTimeService.listWaJiByAdmin(admin.getAdminId(),adminName,searchDate,beginDate,endDate,pageNum,pageSize);
    }

    /**
     *新的查询次数  按人查 排行  挖掘机
     * @param session
     * @param searchDate
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("listWaJiRankByAdmin.do")
    @ResponseBody
    public ServerResponse listWaJiRankByAdmin(HttpSession session,
                                          @RequestParam(value = "searchDate", required = false) String searchDate,
                                          @RequestParam(value = "beginDate", required = false) String beginDate,
                                          @RequestParam(value = "endDate", required = false) String endDate,
                                          @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        return iAccessTimeService.listWaJiByAdminRank(searchDate,beginDate,endDate,pageNum,pageSize);
    }

    /**
     *新的查询次数  按挖掘机查
     * @param session
     * @param deviceId
     * @param searchDate
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     * */

    @RequestMapping("listWaJiByDevice.do")
    @ResponseBody
    public ServerResponse listWaJiByDevice(HttpSession session,
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
        return iAccessTimeService.listWaJiByDevice(deviceId,categoryNo,searchDate,beginDate,endDate,pageNum,pageSize);
    }

    /**
     *新的查询次数  按挖掘机查
     * @param session
     * @param searchDate
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     * */

    @RequestMapping("listWaJiRankByDevice.do")
    @ResponseBody
    public ServerResponse listWaJiRankByDevice(HttpSession session,
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
        return iAccessTimeService.listWaJiByDeviceRank(categoryNo,searchDate,beginDate,endDate,pageNum,pageSize);
    }



}
