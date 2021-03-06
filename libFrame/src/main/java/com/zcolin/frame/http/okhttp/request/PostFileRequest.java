/*
 * *********************************************************
 *   author   colin
 *   email    wanglin2046@126.com
 *   date     20-3-12 下午4:45
 * ********************************************************
 */

package com.zcolin.frame.http.okhttp.request;


import com.zcolin.frame.http.ZHttp;
import com.zcolin.frame.http.okhttp.OkHttpUtils;
import com.zcolin.frame.http.okhttp.callback.Callback;
import com.zcolin.frame.util.LogUtil;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by zhy on 15/12/14.
 * <p>
 * 文件上传请求
 */
public class PostFileRequest extends OkHttpRequest {
    private static MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    private File      file;
    private MediaType mediaType;

    public PostFileRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, File file, MediaType mediaType, int id) {
        super(url, tag, params, headers, id);
        this.file = file;
        this.mediaType = mediaType;

        if (this.file == null) {
            throw new IllegalArgumentException("the file can not be null !");
        }
        if (this.mediaType == null) {
            this.mediaType = MEDIA_TYPE_STREAM;
        }
    }

    @Override
    protected RequestBody buildRequestBody() {
        if (ZHttp.LOG) {
            LogUtil.i("***Post请求***：", url + "\n文件：" + file.getName() + "\n");
        }
        return RequestBody.create(mediaType, file);
    }

    @Override
    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        if (callback == null) {
            return requestBody;
        }
        CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, (bytesWritten, contentLength) -> OkHttpUtils.getInstance()
                                                                                                                                   .getHandler()
                                                                                                                                   .post(() -> callback.onProgress(bytesWritten * 1.0f / contentLength, contentLength)));
        return countingRequestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.post(requestBody).build();
    }


}
