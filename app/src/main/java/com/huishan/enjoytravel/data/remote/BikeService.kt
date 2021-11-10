package com.huishan.enjoytravel.data.remote

import com.huishan.enjoytravel.data.remote.entity.*
import retrofit2.http.*

interface BikeService {
    /**获取系统时间毫秒数*/
    @GET(HttpConstants.Url.GET_SYSTEM_TIME)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    suspend fun getServerTimeStamp(): BaseResponse<ServerTimeStampEntity>

    /**获取验证码*/
    @FormUrlEncoded
    @POST(HttpConstants.Url.GET_VERIFICATION_CODE)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    suspend fun getVerificationCode(@FieldMap map: MutableMap<String, String>): BaseResponse<Nothing>

    /**账号密码登录*/
    @FormUrlEncoded
    @POST(HttpConstants.Url.LOGIN)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    suspend fun login(@FieldMap map: MutableMap<String, String>): BaseResponse<LoginResponseEntity>

    /**获取阿里云OSS文件上传信息*/
    @GET(HttpConstants.Url.OSS_INFO)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    suspend fun getOssInfo(): BaseResponse<OssInfoResponseEntity>

    /**获取车辆详情*/
    @GET(HttpConstants.Url.GET_VEHICLE_DETAIL)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    suspend fun getVehicleDetail(@QueryMap map: MutableMap<String, String>): BaseResponse<VehicleDetailEntity>

    /**查询车辆列表*/
    @GET(HttpConstants.Url.VEHICLE_LIST)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    suspend fun getVehicleList(@QueryMap map: MutableMap<String, String>): BaseResponse<VehicleListEntity>

    /**电池方案列表*/
    @GET(HttpConstants.Url.BATTERY_LIST)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    suspend fun getBatteryList(): BaseResponse<BatteryListEntity>

    /**根据经纬度获取所在服务区*/
    @GET(HttpConstants.Url.GET_CURRENT_SERVICE)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    suspend fun getCurrentService(@QueryMap map: MutableMap<String, String>): BaseResponse<CurrentServiceEntity>

    /**查询服务区列表*/
    @GET(HttpConstants.Url.SERVICE_LIST)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    suspend fun getServiceList(@QueryMap map: MutableMap<String, String>): BaseResponse<ServiceListEntity>

    /**关闭电池仓*/
    @FormUrlEncoded
    @POST(HttpConstants.Url.CLOSE_BATTERY)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    suspend fun closeBattery(@FieldMap map: MutableMap<String, String>): BaseResponse<Nothing>

    /**获取换电阔值*/
    @GET(HttpConstants.Url.GET_CHANGE_BATTERY)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    suspend fun getChangeBattery(@QueryMap map: MutableMap<String, String>): BaseResponse<ChangeBatteryEntity>

    /**打开电池仓*/
    @FormUrlEncoded
    @POST(HttpConstants.Url.OPEN_BATTERY)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    suspend fun openBattery(@FieldMap map: MutableMap<String, String>): BaseResponse<Nothing>

    /**完成换电*/
    @FormUrlEncoded
    @POST(HttpConstants.Url.OVER_BATTERY)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    suspend fun overBattery(@FieldMap map: MutableMap<String, String>): BaseResponse<Nothing>

    /**修改换电阔值*/
    @FormUrlEncoded
    @POST(HttpConstants.Url.SAVE_CHANGE_BATTERY)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    suspend fun saveChangeBattery(@FieldMap map: MutableMap<String, String>): BaseResponse<Nothing>
}