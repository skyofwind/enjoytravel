package com.huishan.enjoytravel.bluetooth

import android.Manifest
import android.util.SparseArray

class BluetoothConstant {
    companion object {
        val perms = arrayOf(Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        val MAC = "864908050932234"
        private var token = 0x0A0A0505
        private var tokenBytes = NumberUtil.longToByteArrayBigEndian(token.toLong(), 4)

        val BLE_ERROR = SparseArray<BleError>()

        init {
            BLE_ERROR.append(0, BleError(0, "操作成功"))
            BLE_ERROR.append(1, BleError(1, "token错误"))
            BLE_ERROR.append(2, BleError(2, "消息内容错误"))
            BLE_ERROR.append(3, BleError(3, "消息命令错误"))
            BLE_ERROR.append(4, BleError(4, "操作失败"))
            BLE_ERROR.append(5, BleError(5, "命令不支持"))
            BLE_ERROR.append(6, BleError(6, "车辆不在静止状态"))
            BLE_ERROR.append(7, BleError(7, "操作无权限"))
            BLE_ERROR.append(8, BleError(8, "脚撑没有到位"))
            BLE_ERROR.append(9, BleError(9, "操作过于频繁"))
        }

        fun resetToken(t: Int) {
            token = t
            tokenBytes = NumberUtil.longToByteArrayBigEndian(token.toLong(), 4)
        }

        fun getTokenBytes(): ByteArray {
            return tokenBytes
        }
    }
    object UUID {
        /**server UUID*/
        const val SERVICE_UUID = "0783B03E-8535-B5A0-7140-A304D2495CB7"
        /**write UUID*/
        const val SERVER_TX_UUID = "0783B03E-8535-B5A0-7140-A304D2495CB8"
        /**read UUID*/
        const val SERVER_RX_UUID = "0783B03E-8535-B5A0-7140-A304D2495CBA"

        val server_uuid = java.util.UUID.fromString(SERVICE_UUID).toString()
        val server_tx_uuid = java.util.UUID.fromString(SERVER_TX_UUID).toString()
        val server_rx_uuid = java.util.UUID.fromString(SERVER_RX_UUID).toString()
    }
    object CMD {
        /**重启*/
        const val REBOOT: Byte = 0x26

        /**播放语音*/
        const val PLAY_VOICE: Byte = 0x28

        /**查询状态信息*/
        const val QUERY_STATUS_INFORMATION: Byte = 0x2A

        /**设防*/
        const val FORTIFY: Byte = 0x2B

        /**启动*/
        const val START_UP: Byte = 0x2C

        /**熄火*/
        const val FLAME_OUT: Byte = 0x2D

        /**后轮锁解锁*/
        const val UNLOCK_REAR_WHEEL: Byte = 0x2E

        /**后轮锁上锁*/
        const val LOCK_REAR_WHEEL: Byte = 0x2F

        /**关机*/
        const val SHUT_DOWN: Byte = 0x30

        /**查询GPS信息*/
        const val QUERY_GPS: Byte = 0x32

        /**查询设备IMSI*/
        const val QUERY_IMSI: Byte = 0x33

        /**电池仓锁解锁*/
        const val UNLOCK_BATTERY_COMPARTMENT_LOCK: Byte = 0x34

        /**电池仓锁上锁*/
        const val LOCK_BATTERY_COMPARTMENT: Byte = 0x35

        /**配置测速参数*/
        const val CONFIGURE_SPEED_MEASUREMENT_PARAMETERS: Byte = 0x36

        /**配置勿扰模式*/
        const val CONFIGURE_DO_NOT_DISTURB_MODE: Byte = 0x37

        /**自动设防配置*/
        const val AUTOMATIC_DEFENSE_CONFIGURATION: Byte = 0x39

        /**查询自动落锁配置*/
        const val QUERY_AUTOMATIC_LOCK_CONFIGURATION: Byte = 0x3A

        /**查询测速参数*/
        const val QUERY_SPEED_MEASUREMENT_PARAMETERS: Byte = 0x3B

        /**查询设备CCID*/
        const val QUERY_CCID: Byte = 0x3D

        /**配置APN*/
        const val CONFIGURE_APN: Byte = 0x3E

        /**查询网络状态*/
        const val QUERY_NETWORK_STATUS: Byte = 0x3F

        /**进入存储模式*/
        const val ENTER_STORAGE_MODE: Byte = 0x40

        /**查询状态信息_V3*/
        const val QUERY_STATUS_INFORMATION_V3: Byte = 0x41

        /**查询上次道钉信息*/
        const val QUERY_LAST_SPIKE_INFORMATION: Byte = 0x42

        /**头盔锁解锁*/
        const val HELMET_LOCK_UNLOCK: Byte = 0x43

        /**有道钉设防*/
        const val FORTIFICATION_WITH_SPIKES: Byte = 0x44

        /**有道钉启动*/
        const val START_WITH_SPIKES: Byte = 0x45

        /**打开电池锁*/
        const val OPEN_BATTERY_LOCK: Byte = 0x46

        /**启动车辆(指定类型)*/
        const val START_VEHICLE_SPECIFIED_TYPE: Byte = 0x4A

        /**设防车辆(指定类型)*/
        const val FORTIFIED_VEHICLES_SPECIFIED_TYPES: Byte = 0x52

        /**头盔锁控制（带参数）*/
        const val HELMET_LOCK_CONTROL_WITH_PARAMETERS: Byte = 0x68

    }

    object Result {
        /**操作成功*/
        const val CODE_SUCCESS = 0

        /**token错误*/
        const val CODE_ERROR_TOKEN = 1

        /**消息内容错误*/
        const val CODE_ERROR_CONTENT = 2

        /**消息命令错误*/
        const val CODE_ERROR_CMD = 3

        /**操作失败*/
        const val CODE_ERROR_OPREATION = 4

        /**命令不支持*/
        const val CODE_ERROR_UNSUPPORT = 5

        /**车辆不在静止状态*/
        const val CODE_ERROR_NOTSTOP = 6

        /**操作无权限*/
        const val CODE_ERROR_ACCESSDENY = 7

        /**脚撑没有到位*/
        const val CODE_ERROR_KICK_ERROR = 8

        /**操作过于频繁*/
        const val CODE_ERROR_CONTINUALLY = 9
    }

    object Voice {
        /**车辆设防提示音【锁车成功】*/
        const val VOICE_FORTIFY = 1

        /**车辆撤防提示音【开锁成功】*/
        const val VOICE_DISARM = 2

        /**车辆启动提示音【开始启动】*/
        const val VOICE_START = 3

        /**扫码成功提示音【欢迎使用电单车】*/
        const val VOICE_SUCCESS = 5

        /**临时锁车提示音【临时锁车成功】*/
        const val VOICE_LOCK_CAR = 7

        /**驶出服务区提示音【车辆已驶出服务区，请尽快骑回】*/
        const val VOICE_DRIVING_OUT_OF_SERVICE_AREA = 8

        /**寻车音【我在这里】*/
        const val VOICE_CAR_SEARCH = 9

        /**临近服务区提示音【临近服务区，驶出将断电】*/
        const val VOICE_NEAR_SERVICE_AREA = 10

        /**超速提示音*/
        const val VOICE_SPEEDING = 15

        /**车辆报修提示音【车辆已报修，请试试其他车】*/
        const val VOICE_VEHICLE_REPAIR = 16

        /**低电量提示音【电量过低，请试试其他车】*/
        const val VOICE_LOW_POWER = 17

        /**车辆占用提示音【车辆已被使用，请试试其他车】*/
        const val VOICE_VEHICLE_OCCUPANCY = 18

        /**倾倒告警提示音【我跌倒啦】*/
        const val VOICE_DUMP_WARNING = 33
    }

    class BleError(val code: Byte, val msg: String) {

    }
}