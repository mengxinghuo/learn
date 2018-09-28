package com.truck.common;

import com.google.common.collect.Sets;
import com.truck.util.BigDecimalUtil;

import java.math.BigDecimal;
import java.util.Set;

public class Const {
    public static final String CURRENT_USER = "currentUser";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String CURRENT_ADMIN = "currentADMIN";
    public static final String ADMINNAME = "adminname";
    public static final Integer START_FREQUENCY = 0;
    public static final BigDecimal NORMALTIME = new BigDecimal(8);


    public interface Role {
        int ROLE_COSTOMER = 0;//普通用户
        int ROLE_ADMIN = 1;//超级管理员
        int ROLE_VIPUSER = 2;//vip用户
    }

    public interface AdminRole {
        int ADMINROLE_ADMIN = 0;//管理员
        int ADMINROLE_SUPERADMIN = 1; //超级管理员
    }

    public interface Cart {
        int CHECKED = 1;//即购物车选中状态
        int UN_CHECKED = 0;//购物车未选中状态

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }


    public interface ProductListOrderBy {
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("Product_Price,desc", "Product_Price", "Product_Price,asc");
    }

    public enum ProductStatusEnum {
        ON_SALE(1, "在线");
        private String value;
        private int code;

        ProductStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum ProductStockStatusEnum {
        OUT_LIMIT(1, "正常"),
        IN_LIMIT(0, "警戒");
        private String value;
        private int code;

        ProductStockStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum DeviceManageStatusEnum {
        NORMAL(1, "正常"),
        UNNORMAL(0, "警戒");
        private String value;
        private int code;

        DeviceManageStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }



    public enum OrderStatusEnum {
        CANCELED(0, "已取消"),
        NO_PAY(10, "未支付"),
        PAID(20, "已付款"),
        DISTRIBUTION(30,"已配货"),
        SHIPPED(40, "已发货"),
        ORDER_SUCCESS(50, "订单完成"),
        ORDER_CLOSE(60, "订单关闭");


        OrderStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static OrderStatusEnum codeOf(int code) {
            for (OrderStatusEnum orderStatusEnum : values()) {
                if (orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("么有找到对应的枚举");
        }
    }

    public interface AlipayCallback {
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }


    public enum PayPlatformEnum {
        ALIPAY(1, "支付宝");

        PayPlatformEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum PaymentTypeEnum {
        ARRIVE_PAY(0, "货到付款"),
        ONLINE_PAY(1, "在线支付");

        PaymentTypeEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }


        public static PaymentTypeEnum codeOf(int code) {
            for (PaymentTypeEnum paymentTypeEnum : values()) {
                if (paymentTypeEnum.getCode() == code) {
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("么有找到对应的枚举");
        }

    }

    public enum DeviceCategoryEnum{
        TRUCK(0,"卡车"),
        DIG_MACHINE(1,"挖掘机"),
        BULLDOZER_MACHINE(2,"推土机"),
        LOADER_MACHINE(3,"装载机");

        private String value;
        private int code;
        DeviceCategoryEnum(int code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static DeviceCategoryEnum codeOf(int code){
            for(DeviceCategoryEnum deviceCategoryEnum : values()){
                if(deviceCategoryEnum.getCode() == code){
                    return deviceCategoryEnum;
                }
            }
            throw new RuntimeException("么有找到对应的枚举");
        }
    }

    public enum DeviceAdminStatusEnum{
        NO_USING(0,"未使用"),
        USING(1,"使用中");

        private String value;
        private int code;
        DeviceAdminStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static DeviceAdminStatusEnum codeOf(int code){
            for(DeviceAdminStatusEnum deviceAdminStatusEnum : values()){
                if(deviceAdminStatusEnum.getCode() == code){
                    return deviceAdminStatusEnum;
                }
            }
            throw new RuntimeException("么有找到对应的枚举");
        }
    }

}
