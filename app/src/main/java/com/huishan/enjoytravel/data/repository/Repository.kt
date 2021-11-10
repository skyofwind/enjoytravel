package com.huishan.enjoytravel.data.repository

import com.huishan.enjoytravel.data.remote.BikeResult
import com.huishan.enjoytravel.data.remote.entity.*
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getServerTimeStamp(): Flow<BikeResult<ServerTimeStampEntity?>>
    suspend fun getVerificationCode(map: MutableMap<String, String>): Flow<BikeResult<Nothing?>>
    suspend fun login(map: MutableMap<String, String>): Flow<BikeResult<LoginResponseEntity?>>
    suspend fun getOssInfo(): Flow<BikeResult<OssInfoResponseEntity?>>
    suspend fun getVehicleDetail(map: MutableMap<String, String>): Flow<BikeResult<VehicleDetailEntity?>>
    suspend fun getVehicleList(map: MutableMap<String, String>): Flow<BikeResult<VehicleListEntity?>>
    suspend fun getBatteryList(): Flow<BikeResult<BatteryListEntity?>>
    suspend fun getCurrentService(map: MutableMap<String, String>): Flow<BikeResult<CurrentServiceEntity?>>
    suspend fun getServiceList(map: MutableMap<String, String>): Flow<BikeResult<ServiceListEntity?>>
    suspend fun closeBattery(map: MutableMap<String, String>): Flow<BikeResult<Nothing?>>
    suspend fun getChangeBattery(map: MutableMap<String, String>): Flow<BikeResult<ChangeBatteryEntity?>>
    suspend fun openBattery(map: MutableMap<String, String>): Flow<BikeResult<Nothing?>>
    suspend fun overBattery(map: MutableMap<String, String>): Flow<BikeResult<Nothing?>>
    suspend fun saveChangeBattery(map: MutableMap<String, String>): Flow<BikeResult<Nothing?>>
}