package com.truck.controller.portal;

import com.truck.common.Const;
import com.truck.common.ResponseCode;
import com.truck.common.ServerResponse;
import com.truck.pojo.Admin;
import com.truck.pojo.Device;
import com.truck.pojo.User;
import com.truck.service.IAdminService;
import com.truck.service.IDeviceService;
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
@RequestMapping("/device")
public class DeviceController {


    @Autowired
    private IDeviceService iDeviceService;

    @Autowired
    private IAdminService iAdminService;

    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<Device> select(HttpSession session, Integer id){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
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
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iDeviceService.list(null,productId,categoryNo,deviceNo,pageNum,pageSize);
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
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iDeviceService.listAllTotal(null,driverId,deviceId,searchDate,beginDate,endDate,productId,categoryNo,deviceNo,pageNum,pageSize);
    }














}
