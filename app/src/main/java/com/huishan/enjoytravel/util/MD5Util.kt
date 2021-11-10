package com.huishan.enjoytravel.util

import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MD5Util {
    companion object {
        fun encrypt(msg: String): String {
            LogUtil.i("MD5Util", "加密前 $msg")
            try {
                //获取md5加密对象
                val instance: MessageDigest = MessageDigest.getInstance("MD5")
                //对字符串加密，返回字节数组
                val digest:ByteArray = instance.digest(msg.toByteArray())
                var sb : StringBuffer = StringBuffer()
                for (b in digest) {
                    //获取低八位有效值
                    var i :Int = b.toInt() and 0xff
                    //将整数转化为16进制
                    var hexString = Integer.toHexString(i)
                    if (hexString.length < 2) {
                        //如果是一位的话，补0
                        hexString = "0$hexString"
                    }
                    sb.append(hexString)
                }
                val result = sb.toString()
                LogUtil.i("MD5Util", "加密后 $result")
                return result

            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }

            return ""
        }
    }
}