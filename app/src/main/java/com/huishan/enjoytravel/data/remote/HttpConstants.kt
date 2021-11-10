package com.huishan.enjoytravel.data.remote

class HttpConstants {
    companion object {
        const val BASE_URL = "http://120.79.133.173:9000/"

        const val CONNECT_TIMEOUT = 15L
        const val READ_TIMEOUT = 15L
        const val WRITE_TIMEOUT = 15L

        const val IS_LOG = true
    }

    object Header {
        const val TOKEN = "x-token"
        var token = ""

        const val CLIENT_ID = "x-client-id"
        const val clientId = "op_app"
    }

    object Url {
        //获取验证码
        const val GET_VERIFICATION_CODE = "auth/code"
        //获取系统时间1970毫秒数
        const val GET_SYSTEM_TIME = "auth/getSystemTime"
        //登录
        const val LOGIN = "auth/login"
        //获取阿里云OSS文件上传信息
        const val OSS_INFO = "auth/ossInfo"

        //获得车辆详情
        const val GET_VEHICLE_DETAIL= "api/vehicle/getVehicleDetail"
        //查询车辆列表
        const val VEHICLE_LIST = "api/vehicle/vehicleList"

        //电池方案列表
        const val BATTERY_LIST = "api/service/batteryList"
        //根据经纬度获取所在服务区
        const val GET_CURRENT_SERVICE = "api/service/getCurrentService"
        //查询服务区列表
        const val SERVICE_LIST = "api/service/serviceList"

        //关闭电池仓
        const val CLOSE_BATTERY = "api/otp/closeBattery"
        //获取换电阔值
        const val GET_CHANGE_BATTERY = "api/otp/getChangeBattery"
        //打开电池仓
        const val OPEN_BATTERY = "api/otp/openBattery"
        //完成换电
        const val OVER_BATTERY = "api/otp/overBattery"
        //修改换电阔值
        const val SAVE_CHANGE_BATTERY = "api/otp/saveChangeBattery"

    }
    object LoginType {
        //账号密码登录
        const val PASSWORD = "password"
        //手机验证码登录
        const val CAPTCHA = "captcha"
        //刷新token登录
        const val REFRESH_TOKEN = "refresh_token"
        //:小程序jscode登录
        const val JS_CODE = "js_code"
    }
}