package com.truck.service;

import com.truck.common.ServerResponse;
import com.truck.pojo.Admin;

import java.util.List;
import java.util.Map;


public interface IAdminService {
    ServerResponse<Map> login(String adminName, String password);

    ServerResponse<String> register(Admin admin);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse<String> selectQuestion(String adminName);

    ServerResponse<String> checkAnswer(String adminName, String question, String answer);

    ServerResponse<String> forgetResetPassword(String adminName, String passwordNew, String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, Admin admin);

    ServerResponse<Admin> updateInformation(Admin admin);

    ServerResponse<Admin> getInfomartion(Integer adminId);

    ServerResponse checkAdminRole(Admin admin);

    ServerResponse listAllAdmin();

    ServerResponse listHrAllAdmin();

    ServerResponse registerHrAllAdmin();

    ServerResponse listHrWorkingAdmin();

    ServerResponse<String> registerBatch(List<Admin> adminList);


}
