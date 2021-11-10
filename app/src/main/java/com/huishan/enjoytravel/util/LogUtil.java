package com.huishan.enjoytravel.util;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

public class LogUtil {

    private final static String TAG = "TravelBike#";
    private static final String MYLOG_PATH_SDCARD_DIR = Environment.getExternalStorageDirectory().getPath() + "/EnjoyTravelBike/log";// 日志文件在sdcard中的路径
    //	Environment.getExternalStorageDirectory() + "/tradebook", "cache"
//	private static int SDCARD_LOG_FILE_SAVE_DAYS = 10;// sd卡中日志文件的最多保存天数
    private static final String MYLOGFILEName = "TravelBikeLog.txt";// 本类输出的日志文件名称
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 日志的输出格式
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式
    public static boolean IS_WRITE_LOG_TO_LOCAL = false;//是否写入到本地

    private static File logDir;


    public static void i(String tag, String msg) {
        Log.i(TAG, tag + " " + msg);

        if (IS_WRITE_LOG_TO_LOCAL) {
            writeLogtoFile("III", TAG + " " + tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        Log.e(TAG, tag + " " + msg);
        if (IS_WRITE_LOG_TO_LOCAL) {
            writeLogtoFile("III", TAG + " " + tag, msg);
        }
    }

    /**
     * 打开日志文件并写入日志
     *
     * @return
     **/
    private static void writeLogtoFile(String mylogtype, String tag, String text) {// 新建或打开日志文件
        Date nowtime = new Date();
        String needWriteFiel = logfile.format(nowtime);
        String needWriteMessage = myLogSdf.format(nowtime) + "    " + mylogtype
                + "    " + tag + "    " + text;
        File f = new File(MYLOG_PATH_SDCARD_DIR);

        if (!f.exists()) {
            f.mkdirs();
        }

        File file = new File(MYLOG_PATH_SDCARD_DIR, needWriteFiel
                + "_" + MYLOGFILEName);

        try {
            FileWriter filerWriter = new FileWriter(file, true);//后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除制定的日志文件
     */
    public static void delFile() {// 删除日志文件
        Date nowtime = new Date();
        String needWriteFiel = logfile.format(nowtime);
        File file = new File(MYLOG_PATH_SDCARD_DIR, needWriteFiel
                + "_" + MYLOGFILEName);
        if (file.exists()) {
            file.delete();
        }
    }

}
