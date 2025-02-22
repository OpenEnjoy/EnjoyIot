
/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com | Tel: 19918996474
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot] | Tel: 19918996474
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
package com.enjoyiot.framework.xss.core.clean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

/**
 * 基于 JSONP 实现 XSS 过滤字符串
 */
public class JsoupXssCleaner implements XssCleaner {

    private final Safelist safelist;

    /**
     * 用于在 src 属性使用相对路径时，强制转换为绝对路径。 为空时不处理，值应为绝对路径的前缀（包含协议部分）
     */
    private final String baseUri;

    /**
     * 无参构造，默认使用 {@link JsoupXssCleaner#buildSafelist} 方法构建一个安全列表
     */
    public JsoupXssCleaner() {
        this.safelist = buildSafelist();
        this.baseUri = "";
    }

    /**
     * 构建一个 Xss 清理的 Safelist 规则。
     * 基于 Safelist#relaxed() 的基础上:
     * 1. 扩展支持了 style 和 class 属性
     * 2. a 标签额外支持了 target 属性
     * 3. img 标签额外支持了 data 协议，便于支持 base64
     *
     * @return Safelist
     */
    private Safelist buildSafelist() {
        // 使用 jsoup 提供的默认的
        Safelist relaxedSafelist = Safelist.relaxed();
        // 富文本编辑时一些样式是使用 style 来进行实现的
        // 比如红色字体 style="color:red;", 所以需要给所有标签添加 style 属性
        // 注意：style 属性会有注入风险 <img STYLE="background-image:url(javascript:alert('XSS'))">
        relaxedSafelist.addAttributes(":all", "style", "class");
        // 保留 a 标签的 target 属性
        relaxedSafelist.addAttributes("a", "target");
        // 支持img 为base64
        relaxedSafelist.addProtocols("img", "src", "data");

        // 保留相对路径, 保留相对路径时，必须提供对应的 baseUri 属性，否则依然会被删除
        // WHITELIST.preserveRelativeLinks(false);

        // 移除 a 标签和 img 标签的一些协议限制，这会导致 xss 防注入失效，如 <img src=javascript:alert("xss")>
        // 虽然可以重写 WhiteList#isSafeAttribute 来处理，但是有隐患，所以暂时不支持相对路径
        // WHITELIST.removeProtocols("a", "href", "ftp", "http", "https", "mailto");
        // WHITELIST.removeProtocols("img", "src", "http", "https");
        return relaxedSafelist;
    }

    @Override
    public String clean(String html) {
        return Jsoup.clean(html, baseUri, safelist, new Document.OutputSettings().prettyPrint(false));
    }

}

