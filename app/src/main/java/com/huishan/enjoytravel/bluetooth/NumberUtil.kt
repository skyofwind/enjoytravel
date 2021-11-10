package com.huishan.enjoytravel.bluetooth

import java.lang.StringBuilder
import kotlin.experimental.and

class NumberUtil {
    companion object {
        /**
         * Int转ByteArray,按大端字节序
         * @param value 整形的数值
         * @param arrLen 所需byte数组长度，根据实际需求来，如int实际存储数值为uint16,则arrLen传2,uint8则传1
         */
        fun intToByteArrayBigEndian(value: Int, arrLen: Int): ByteArray {
            val buf = ByteArray(arrLen)
            for (i in 0 until arrLen) {
                buf[i] = (value shr (arrLen - 1 - i) * 8).toByte()
            }

            return buf
        }

        /**
         * 跟上面的是一样的，只是uint32不能用int来存储，换成用long来存储转换
         */
        fun longToByteArrayBigEndian(value: Long, arrLen: Int): ByteArray {
            val buf = ByteArray(arrLen)
            for (i in 0 until arrLen) {
                buf[i] = (value shr (arrLen - 1 - i) * 8).toByte()
            }

            return buf
        }

        fun floatToByteArrayBidEndian(value: Float): ByteArray {
            val fbit = value.toBits()
            val byteArray = ByteArray(4)
            for (i in 0..3) {
                byteArray[i] = (fbit shr (3 - i) * 8).toByte()
            }

            return byteArray
        }

        fun byteArrayToFloatBidEndian(byteArray: ByteArray): Float {
            var t: Int = 0
            for (i in byteArray.indices) {
                t = if (i == 0) {
                    byteArray[0].toInt() and 0xff
                } else {
                    (t shl 8) or (byteArray[i].toInt() and 0xff)
                }
            }

            return Float.fromBits(t)

        }

        fun byteArrayToUIntBigEndian(byteArray: ByteArray): UInt {
            var t: UInt = 0u
            for (i in byteArray.indices) {
                t = if (i == 0) {
                    byteArray[0].toUInt() and 0xffu
                } else {
                    (t shl 8) or (byteArray[i].toUInt() and 0xffu)
                }
            }

            return t
        }

        /**
         * Int转ByteArray,按小端字节序
         * @param value 整形的数值
         * @param arrLen 所需byte数组长度，根据实际需求来，如int实际存储数值为uint16,则arrLen传2
         */
        fun intToByteArrayLittleEndian(value: Int, arrLen: Int): ByteArray {
            val buf = ByteArray(arrLen)
            for (i in 0 until arrLen) {
                buf[i] = (value shr i * 8).toByte()
            }

            return buf
        }

        /**
         * 跟上面的是一样的，只是uint32不能用int来存储，换成用long来存储转换
         */
        fun longToByteArrayLittleEndian(value: Long, arrLen: Int): ByteArray {
            val buf = ByteArray(arrLen)
            for (i in 0 until arrLen) {
                buf[i] = (value shr i * 8).toByte()
            }

            return buf
        }

        /**
         * 校验和为命令字，数据长度和数据的算术累加和，忽略进位
         * @param cmd 命令字
         * @param dataLen 数据长度
         * @param data 数据
         */
        fun getCheckSum(cmd: Byte, dataLen: Byte, data: ByteArray): Byte {
            var checkSum: Byte = (cmd + dataLen).toByte()
            for (i in data) {
                checkSum = (checkSum + i).toByte()
            }

            return checkSum
        }


        fun packData(cmd: Byte, data: ByteArray, dataLen: Byte): ByteArray {
            val array = ByteArray(data.size + 3)
            array[0] = cmd
            array[1] = dataLen
            data.copyInto(array, 2)
            array[array.size - 1] = getCheckSum(cmd, dataLen, data)

            return array
        }

        fun getSumByteArray(vararg byteArray: ByteArray): ByteArray {
            var len = 0
            for (a in byteArray) {
                len += a.size
            }
            val bytes = ByteArray(len)
            len = 0
            for (a in byteArray) {
                a.copyInto(bytes, 0, len, len + a.size)
                len += a.size
            }

            return bytes
        }

        /**
         * 字节数组转字符串
         */
        fun byteArrayToString(byteArray: ByteArray): String {
            val sb = StringBuilder()
            for (b in byteArray) {
                sb.append(b.toInt().toChar())
            }

            return sb.toString()
        }

    }
}