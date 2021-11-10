package com.huishan.enjoytravel.util;

import android.text.TextUtils;
import android.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.huishan.enjoytravel.data.remote.entity.CommonParameters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SignUtils {
    private static final String TAG = "SignUtils";
    public static final String MY_SECRET = "test123456";

    public static ArrayMap<String, String> getSignMap(Object obj) {
        ArrayMap<String, String> mapCommon = JSONObject.parseObject(JSONObject.toJSONString(new CommonParameters()), ArrayMap.class);
        ArrayMap<String, String> params = JSONObject.parseObject(JSONObject.toJSONString(obj), ArrayMap.class);

        params.putAll(mapCommon);
        String signString = sign(params);
        params.put("_sign", signString);
        LogUtil.e(TAG, "map： " + JSON.toJSONString(params));
        return params;
    }

    public static String sign(Map<String, String> params) {
        Set<String> keys = params.keySet();
        List<String> sortKeys = new ArrayList<>(keys);
        Collections.sort(sortKeys);
        StringBuilder builder = new StringBuilder();
        for (String key : sortKeys) {
            String value = String.valueOf(params.get(key));
            if (!TextUtils.isEmpty(value)) {
                builder.append(value);
            }
        }
        builder.append(MY_SECRET);
        return MD5Util.Companion.encrypt(builder.toString());
    }
}
