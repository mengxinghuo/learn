package com.truck.controller.backend;

import com.truck.common.Const;
import com.truck.common.ResponseCode;
import com.truck.common.ServerResponse;
import com.truck.pojo.Admin;

import com.truck.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    @Autowired
    private IAdminService iAdminService;


    /**
     * 员工登陆
     * @param adminName
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse login(String adminName, String password, HttpSession session){
        ServerResponse<Map> response=iAdminService.login(adminName,password);
        if (response.isSuccess()){
                session.setAttribute(Const.CURRENT_ADMIN,(Admin)(response.getData().get("admin")));
        }
            return response;
    }

    /**
     * 退出登陆
     * @param session
     * @return
     */
    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_ADMIN);
        return ServerResponse.createBySuccess();
    }

    /**
     * 添加员工
     * @param admin
     * @return
     */
    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(Admin admin){
        return iAdminService.register(admin);
    }

    /**
     * 添加员工 批量注册
     * @param adminList
     * @return
     */
    @RequestMapping(value = "registerBatch.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> registerBatch(List<Admin> adminList){
        return iAdminService.registerBatch(adminList);
    }

    /**
     *  查询  hr系统  注册所有员工
     * @param session
     * @return
     */
    @RequestMapping(value = "registerHrAllAdmin.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse registerHrAllAdmin(HttpSession session){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iAdminService.registerHrAllAdmin( );
        }
        return ServerResponse.createByErrorMessage("无权限操作");
    }

    /**
     *  查询  hr系统  注册所有员工
     * @param session
     * @return
     */
    @RequestMapping(value = "listHrAllAdmin.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse listHrAllAdmin(HttpSession session){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iAdminService.listHrAllAdmin( );
        }
        return ServerResponse.createByErrorMessage("无权限操作");
    }

    /**
     *  查询  hr系统  所有上班的员工信息
     * @param session
     * @return
     */
    @RequestMapping(value = "listHrWorkingAdmin.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse listHrWorkingAdmin(HttpSession session){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iAdminService.listHrWorkingAdmin( );
        }
        return ServerResponse.createByErrorMessage("无权限操作");
    }

    /**
     * 校验
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value = "checkValid.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str,String type){
        return iAdminService.checkValid(str,type);
    }

    /**
     * 获取员工登陆信息
     * @param session
     * @return
     */
    @RequestMapping(value = "getAdminInfo.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Admin> getAdminInfo(HttpSession session){
        Admin admin =(Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin!=null)
            return ServerResponse.createBySuccess(admin);
        return ServerResponse.createByErrorMessage("用户没登陆，无法获取当前用户的信息");
    }

    /**
     *获取员工忘记密码提示问题
     * @param adminName
     * @return
     */
    @RequestMapping(value = "forgetGetQuestion.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String adminName){
        return iAdminService.selectQuestion(adminName);
    }

    /**
     * 校验员工忘记密码提示问题的答案
     * @param adminName
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value = "forgetGetAnswer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetAnswer(String adminName,String question,String answer){
        return iAdminService.checkAnswer(adminName,question,answer);
    }

    /**
     * 重置密码
     * @param adminName
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @RequestMapping(value = "forgetResetPassword.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String adminName, String passwordNew, String forgetToken){
        return iAdminService.forgetResetPassword(adminName,passwordNew,forgetToken);
    }

    /**
     * 修改密码
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @RequestMapping(value = "resetPassword.do",method = RequestMethod.POST)
    @ResponseBody
    ServerResponse<String> resetPassword(HttpSession session, String passwordOld, String passwordNew){
        Admin admin=(Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin==null)
            return ServerResponse.createByErrorMessage("用户未登陆");
        return iAdminService.resetPassword(passwordOld,passwordNew,admin);
    }


    /**
     * 更新员工信息
     * @param session
     * @param admin
     * @return
     */
    @RequestMapping(value = "updateInformation.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Admin> updateInformation(HttpSession session, Admin admin){
        Admin currentUser=(Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (currentUser==null)
            return ServerResponse.createByErrorMessage("用户未登陆");
        admin.setAdminId(currentUser.getAdminId());
        ServerResponse<Admin> response=iAdminService.updateInformation(admin);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_ADMIN,response.getData());
        }
        return response;

    }

    /**
     *  超管查询所有员工信息
     * @param session
     * @return
     */
    @RequestMapping(value = "listAllAdmin.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateInformation(HttpSession session){
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "管理员用户未登录，请登录");
        }
        if (iAdminService.checkAdminRole(admin).isSuccess()) {
            return iAdminService.listAllAdmin( );
        }
        return ServerResponse.createByErrorMessage("无权限操作");
    }

}
