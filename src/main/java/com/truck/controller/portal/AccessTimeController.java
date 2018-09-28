package com.truck.controller.portal;


import com.truck.common.Const;
import com.truck.common.ResponseCode;
import com.truck.common.ServerResponse;
import com.truck.pojo.AccessTime;
import com.truck.pojo.Admin;
import com.truck.pojo.User;
import com.truck.service.IAccessTimeService;
import com.truck.vo.AccessTimeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/accessTime/")
public class AccessTimeController {

    @Autowired
    private IAccessTimeService iAccessTimeService;

    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<AccessTimeVo> select(HttpSession session, Integer id){
         User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iAccessTimeService.select(null,id);
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
         User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iAccessTimeService.list(null,driverId,deviceId,searchDate,beginDate,endDate,pageNum,pageSize);
    }


}
