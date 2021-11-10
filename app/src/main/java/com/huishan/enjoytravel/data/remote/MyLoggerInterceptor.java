package com.huishan.enjoytravel.data.remote;

import android.text.TextUtils;

import com.huishan.enjoytravel.util.LogUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


public class MyLoggerInterceptor implements Interceptor {
    public static final String TAG = "CommonLoggerInterceptor";
    private String tag;
    private boolean showResponse;
    private boolean isShowRequest = true;

    public MyLoggerInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    public MyLoggerInterceptor(String tag) {
        this(tag, false);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader(HttpConstants.Header.CLIENT_ID, HttpConstants.Header.clientId)
                .addHeader(HttpConstants.Header.TOKEN, HttpConstants.Header.INSTANCE.getToken())
                .build();
        logForRequest(request);
        //执行请求，计算请求时间
        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            //EventBus.getDefault().post(new ShowHttpsErrorTipsEvent(e,request.url()));
            LogUtil.e("MyOkHttpUtils", "<-- HTTP FAILED: " + e.getMessage());
            throw e;
        }
        long responseTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        //响应日志拦截
        return logForResponse(response, responseTime);
    }

    private Response logForResponse(Response response, long responseTime) {
        try {
            //===>response log
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
//            if ((clone.request().url() + "").contains("getmarketindex")) {
//                showResponse = false;
//            } else {
//                showResponse = true;
//            }

//            Log.e(tag, "code : " + clone.code());
//            Log.e(tag, "protocol : " + clone.protocol());
//            if (!TextUtils.isEmpty(clone.message()))
//                Log.e(tag, "message : " + clone.message());
//
            if (showResponse) {
                LogUtil.e(tag, "========response'log=======start");
                LogUtil.e(tag, "(" + responseTime + "ms)" + "url : " + clone.request().url());
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        LogUtil.e(tag, "responseBody's contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();
                            LogUtil.e(tag, "responseBody's content : " + resp);
                            body = ResponseBody.create(mediaType, resp);
                            return response.newBuilder().body(body).build();
                        } else {
                            LogUtil.e(tag, "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
                LogUtil.e(tag, "========response'log=======end");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();
            if (url.contains("getmarketindex")) {
                isShowRequest = false;
            } else {
                isShowRequest = true;
            }

            if (isShowRequest) {
                LogUtil.e(tag, "========request'log=======start");
                LogUtil.e(tag, "method : " + request.method());
                LogUtil.e(tag, "url : " + url);
                if (headers != null && headers.size() > 0) {
                    LogUtil.e(tag, "headers : " + headers.toString());
                }
                RequestBody requestBody = request.body();
                if (requestBody != null) {
                    MediaType mediaType = requestBody.contentType();
                    if (mediaType != null) {
//                    Log.e(tag, "requestBody's contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            LogUtil.e(tag, "requestBody's content : " + bodyToString(request));
                        } else {
                            LogUtil.e(tag, "requestBody's content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
                LogUtil.e(tag, "========request'log=======end");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")) {
                return true;
            }

        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
