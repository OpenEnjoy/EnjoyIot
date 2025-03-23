/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */
package com.enjoyiot.eiot.ruleengine.action.http;


import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.ruleengine.action.ScriptService;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;


import java.io.IOException;
import java.util.Map;

import static com.enjoyiot.eiot.common.enums.ErrorCodeConstants.SEND_REQUEST_ERROR;
import static com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil.exception;
import org.apache.commons.beanutils.BeanUtils;


@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class HttpService extends ScriptService {
    private String url;

    private final OkHttpClient httpClient = new OkHttpClient();

    @SneakyThrows
    public String execute(ThingModelMessage msg) {
        //执行转换脚本
        HttpData httpData = execScript(new TypeReference<HttpData>() {
        }, msg);
        if (httpData == null) {
            log.warn("execScript result is null");
            return "execScript result is null";
        }

        //组装http请求
        String url = this.url + httpData.getPath();
        Request.Builder builder = new Request.Builder();
        Map<String, Object> headers = httpData.getHeader();
        if (headers != null) {
            headers.forEach((key, val) -> builder.header(key, val.toString()));
        }
        HttpHeader httpHeader = new HttpHeader();
        if (headers != null && headers.containsKey("contentType")) {
            httpHeader.setContentType(headers.get("contentType").toString());
        }
        BeanUtils.populate(httpHeader, headers);

        builder.url(url);
        Object json = httpData.getBody();
        String bodyStr;
        if (json instanceof String) {
            bodyStr = (String) json;
        } else {
            bodyStr = JsonUtils.toJsonString(json); // 使用工具类序列化为 JSON 字符串
        }

        // 确保 bodyStr 是有效的 JSON
        RequestBody requestBody = RequestBody.create(MediaType.get(httpHeader.getContentType()), bodyStr);


        Request request = builder.method(httpData.getMethod().toUpperCase(), requestBody).build();
        String requestDataStr = JsonUtils.toJsonString(httpData);
        log.info("send http request:{} ,{}", url, requestDataStr);

        String responseBody = "";
        int responseCode;
        //发送请求
        try (Response response = httpClient.newCall(request).execute()) {
            ResponseBody body = response.body();
            responseCode = response.code();
            responseBody = body == null ? "" : body.string();
            log.info("send result,code:{},response:{}", responseCode, responseBody);
        } catch (IOException e) {
            throw exception(SEND_REQUEST_ERROR, e);
        }

        return String.format("send request,url:%s,method:%s,receive response,code:%s,body:%s",
                url, requestDataStr, responseCode, responseBody);

    }

    @Data
    public static class HttpData {
        private String path;
        private String method;
        private Map<String, Object> header;
        private Object body;
    }

    @Data
    public static class HttpHeader {
        private String contentType;
    }

}
