/*
 * *********************************************************
 *   author   colin
 *   email    wanglin2046@126.com
 *   date     20-3-12 下午4:45
 * ********************************************************
 */

package com.zcolin.frame.http.okhttp.builder;

import java.util.Map;

/**
 * Created by zhy on 16/3/1.
 */
public interface HasParamsable {
    OkHttpRequestBuilder params(Map<String, String> params);

    OkHttpRequestBuilder addParams(String key, String val);
}
