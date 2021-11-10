package com.huishan.enjoytravel.data.repository

import com.huishan.enjoytravel.data.remote.BikeResult
import com.huishan.enjoytravel.data.remote.BikeService
import com.huishan.enjoytravel.data.remote.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * 统一数据来源，现在只有从网络获取，后期有使用本地数据库数据，可在此处加入判断，是从本地数据源获取还是从网络中获取
 */
class BikeRepositoryImpl(
    val api: BikeService
    ) : Repository {
    override suspend fun getServerTimeStamp(): Flow<BikeResult<ServerTimeStampEntity?>> {
        return flow{ 
            try {
                val entity = api.getServerTimeStamp()
                emit(BikeResult.Success(entity.data))
            } catch (e: Exception) {
                emit(BikeResult.Failure(e.cause))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getVerificationCode(map: MutableMap<String, String>): Flow<BikeResult<Nothing?>> {
        return flow{
            try {
                val entity = api.getVerificationCode(map)
                emit(BikeResult.Success(entity.data))
            } catch (e: Exception) {
                emit(BikeResult.Failure(e.cause))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun login(map: MutableMap<String, String>): Flow<BikeResult<LoginResponseEntity?>> {
        return flow{
            try {
                val entity = api.login(map)
                emit(BikeResult.Success(entity.data))
            } catch (e: Exception) {
                emit(BikeResult.Failure(e.cause))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getOssInfo(): Flow<BikeResult<OssInfoResponseEntity?>> {
        return flow{
            try {
                val entity = api.getOssInfo()
                emit(BikeResult.Success(entity.data))
            } catch (e: Exception) {
                emit(BikeResult.Failure(e.cause))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getVehicleDetail(map: MutableMap<String, String>): Flow<BikeResult<VehicleDetailEntity?>> {
        return flow{
            try {
                val entity = api.getVehicleDetail(map)
                emit(BikeResult.Success(entity.data))
            } catch (e: Exception) {
                emit(BikeResult.Failure(e.cause))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getVehicleList(map: MutableMap<String, String>): Flow<BikeResult<VehicleListEntity?>> {
        return flow{
            try {
                val entity = api.getVehicleList(map)
                emit(BikeResult.Success(entity.data))
            } catch (e: Exception) {
                emit(BikeResult.Failure(e.cause))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getBatteryList(): Flow<BikeResult<BatteryListEntity?>> {
        return flow{
            try {
                val entity = api.getBatteryList()
                emit(BikeResult.Success(entity.data))
            } catch (e: Exception) {
                emit(BikeResult.Failure(e.cause))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrentService(map: MutableMap<String, String>): Flow<BikeResult<CurrentServiceEntity?>> {
        return flow{
            try {
                val entity = api.getCurrentService(map)
                emit(BikeResult.Success(entity.data))
            } catch (e: Exception) {
                emit(BikeResult.Failure(e.cause))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getServiceList(map: MutableMap<String, String>): Flow<BikeResult<ServiceListEntity?>> {
        return flow{
            try {
                val entity = api.getServiceList(map)
                emit(BikeResult.Success(entity.data))
            } catch (e: Exception) {
                emit(BikeResult.Failure(e.cause))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun closeBattery(map: MutableMap<String, String>): Flow<BikeResult<Nothing?>> {
        return flow{
            try {
                val entity = api.closeBattery(map)
                emit(BikeResult.Success(entity.data))
            } catch (e: Exception) {
                emit(BikeResult.Failure(e.cause))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getChangeBattery(map: MutableMap<String, String>): Flow<BikeResult<ChangeBatteryEntity?>> {
        return flow{
            try {
                val entity = api.getChangeBattery(map)
                emit(BikeResult.Success(entity.data))
            } catch (e: Exception) {
                emit(BikeResult.Failure(e.cause))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun openBattery(map: MutableMap<String, String>): Flow<BikeResult<Nothing?>> {
        return flow{
            try {
                val entity = api.openBattery(map)
                emit(BikeResult.Success(entity.data))
            } catch (e: Exception) {
                emit(BikeResult.Failure(e.cause))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun overBattery(map: MutableMap<String, String>): Flow<BikeResult<Nothing?>> {
        return flow{
            try {
                val entity = api.overBattery(map)
                emit(BikeResult.Success(entity.data))
            } catch (e: Exception) {
                emit(BikeResult.Failure(e.cause))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveChangeBattery(map: MutableMap<String, String>): Flow<BikeResult<Nothing?>> {
        return flow{
            try {
                val entity = api.saveChangeBattery(map)
                emit(BikeResult.Success(entity.data))
            } catch (e: Exception) {
                emit(BikeResult.Failure(e.cause))
            }
        }.flowOn(Dispatchers.IO)
    }

}