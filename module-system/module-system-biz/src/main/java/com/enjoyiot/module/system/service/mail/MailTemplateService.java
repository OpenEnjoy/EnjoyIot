
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
package com.enjoyiot.module.system.service.mail;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.system.controller.admin.mail.vo.template.MailTemplatePageReqVO;
import com.enjoyiot.module.system.controller.admin.mail.vo.template.MailTemplateSaveReqVO;
import com.enjoyiot.module.system.dal.dataobject.mail.MailTemplateDO;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 邮件模版 Service 接口
 *
 * @author wangjingyi
 * @since 2022-03-21
 */
public interface MailTemplateService {

    /**
     * 邮件模版创建
     *
     * @param createReqVO 邮件信息
     * @return 编号
     */
    Long createMailTemplate(@Valid MailTemplateSaveReqVO createReqVO);

    /**
     * 邮件模版修改
     *
     * @param updateReqVO 邮件信息
     */
    void updateMailTemplate(@Valid MailTemplateSaveReqVO updateReqVO);

    /**
     * 邮件模版删除
     *
     * @param id 编号
     */
    void deleteMailTemplate(Long id);

    /**
     * 获取邮件模版
     *
     * @param id 编号
     * @return 邮件模版
     */
    MailTemplateDO getMailTemplate(Long id);

    /**
     * 获取邮件模版分页
     *
     * @param pageReqVO 模版信息
     * @return 邮件模版分页信息
     */
    PageResult<MailTemplateDO> getMailTemplatePage(MailTemplatePageReqVO pageReqVO);

    /**
     * 获取邮件模板数组
     *
     * @return 模版数组
     */
    List<MailTemplateDO> getMailTemplateList();

    /**
     * 从缓存中获取邮件模版
     *
     * @param code 模板编码
     * @return 邮件模板
     */
    MailTemplateDO getMailTemplateByCodeFromCache(String code);

    /**
     * 邮件模版内容合成
     *
     * @param content 邮件模版
     * @param params 合成参数
     * @return 格式化后的内容
     */
    String formatMailTemplateContent(String content, Map<String, Object> params);

    /**
     * 获得指定邮件账号下的邮件模板数量
     *
     * @param accountId 账号编号
     * @return 数量
     */
    long getMailTemplateCountByAccountId(Long accountId);

}
