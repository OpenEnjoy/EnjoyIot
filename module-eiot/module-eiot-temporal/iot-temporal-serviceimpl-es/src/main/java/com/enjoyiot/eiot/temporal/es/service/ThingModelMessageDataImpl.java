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
package com.enjoyiot.eiot.temporal.es.service;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.enjoyiot.eiot.IThingModelMessageData;
import com.enjoyiot.eiot.TimeData;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.temporal.es.dao.ThingModelMessageRepository;
import com.enjoyiot.eiot.temporal.es.convert.EsThingModelMessageConvert;
import com.enjoyiot.eiot.temporal.es.document.DocThingModelMessage;
import com.enjoyiot.framework.common.pojo.PageResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThingModelMessageDataImpl implements IThingModelMessageData {

    @Autowired
    private ElasticsearchRestTemplate template;
    @Autowired
    private ThingModelMessageRepository thingModelMessageRepository;

    @Override
    public PageResult<ThingModelMessage> findByTypeAndIdentifier(Long deviceId, String type,
                                                                 String identifier,
                                                                 int page, int size) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(QueryBuilders.termQuery("deviceId", deviceId));
        if (StrUtil.isNotBlank(type)) {
            builder.must(QueryBuilders.termQuery("type", type));
        }
        if (StrUtil.isNotBlank(identifier)) {
            builder.must(QueryBuilders.matchPhraseQuery("identifier", identifier));
        }
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(builder)
                .withPageable(PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("time"))))
                .build();
        SearchHits<DocThingModelMessage> result = template.search(query, DocThingModelMessage.class);
        return new PageResult<>( result.getSearchHits().stream()
                .map(m -> EsThingModelMessageConvert.INSTANCE.convert(m.getContent()))
                .collect(Collectors.toList()),result.getTotalHits());
    }

    @Override
    public PageResult<ThingModelMessage> findByTypeAndDeviceIds(List<Long> deviceIds, String type,
                                                             String identifier,
                                                             int page, int size) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(QueryBuilders.termQuery("type", type));
        if (deviceIds.size()>0) {
            builder.must(QueryBuilders.termsQuery("deviceId", deviceIds));
        }
        if (StrUtil.isNotBlank(identifier)) {
            builder.must(QueryBuilders.matchPhraseQuery("identifier", identifier));
        }
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(builder)
                .withPageable(PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("time"))))
                .build();
        SearchHits<DocThingModelMessage> result = template.search(query, DocThingModelMessage.class);
        return new PageResult<>(result.getSearchHits().stream()
                .map(m -> EsThingModelMessageConvert.INSTANCE.convert(m.getContent()))
                .collect(Collectors.toList()),result.getTotalHits());
    }

    @Override
    public List<TimeData> getDeviceMessageStatsWithUid(String uid, long start, long end) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("time")
                        .from(start, true).to(end, true));
        if (uid != null) {
            queryBuilder =
                    queryBuilder.must(QueryBuilders.termQuery("uid", uid));
        }

        //按小时统计消息数量
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withAggregations(AggregationBuilders.dateHistogram("agg")
                        .field("time")
                        .calendarInterval(DateHistogramInterval.HOUR)
                        .calendarInterval(DateHistogramInterval.hours(1))
                )
                .build();

        ElasticsearchAggregations result = (ElasticsearchAggregations) template
                .search(query, DocThingModelMessage.class).getAggregations();
        ParsedDateHistogram histogram = result.aggregations().get("agg");

        List<TimeData> data = new ArrayList<>();
        for (Histogram.Bucket bucket : histogram.getBuckets()) {
            long seconds = ((ZonedDateTime) bucket.getKey()).toInstant().getEpochSecond();
            data.add(new TimeData(seconds * 1000, bucket.getDocCount()));
        }

        return data;
    }

    @Override
    public List<TimeData> getDeviceUpMessageStatsWithUid(String uid, Long start, Long end) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (ObjectUtil.isNotEmpty(start) && ObjectUtil.isNotEmpty(end)) {
            queryBuilder.must(QueryBuilders.rangeQuery("time")
                    .from(start, true).to(end, true));
        }

        if ( ObjectUtil.isNotEmpty(uid) ) {
            queryBuilder =
                    queryBuilder.must(QueryBuilders.termQuery("uid", uid));
        }

        // 查询字段type='property' and identifier='report', 或者 type='event' 的数据
        queryBuilder = queryBuilder.must(QueryBuilders.boolQuery()
                .should(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("type", "property"))
                        .must(QueryBuilders.termQuery("identifier", "report")))
                .should(QueryBuilders.termQuery("type", "event")));

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withAggregations(AggregationBuilders.dateHistogram("agg")
                        .field("time")
                        .calendarInterval(DateHistogramInterval.HOUR)
                        .calendarInterval(DateHistogramInterval.hours(1))
                )
                .build();

        ElasticsearchAggregations result = (ElasticsearchAggregations) template
                .search(query, DocThingModelMessage.class).getAggregations();
        ParsedDateHistogram histogram = result.aggregations().get("agg");

        List<TimeData> data = new ArrayList<>();
        for (Histogram.Bucket bucket : histogram.getBuckets()) {
            long seconds = ((ZonedDateTime) bucket.getKey()).toInstant().getEpochSecond();
            data.add(new TimeData(seconds * 1000, bucket.getDocCount()));
        }

        return data;
    }

    @Override
    public List<TimeData> getDeviceDownMessageStatsWithUid(String uid, Long start, Long end) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (ObjectUtil.isNotEmpty(start) && ObjectUtil.isNotEmpty(end)) {
            queryBuilder.must(QueryBuilders.rangeQuery("time")
                    .from(start, true).to(end, true));
        }

        if ( ObjectUtil.isNotEmpty(uid) ) {
            queryBuilder =
                    queryBuilder.must(QueryBuilders.termQuery("uid", uid));
        }

        // 查询字段type='property' and identifie!='report',  或者 type='service' 或者 type= 'config'
        queryBuilder = queryBuilder.must(QueryBuilders.boolQuery()
                .should(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("type", "property"))
                        .must(QueryBuilders.boolQuery()
                                .mustNot(QueryBuilders.termQuery("identifier", "report"))))
                .should(QueryBuilders.termQuery("type", "service"))
                .should(QueryBuilders.termQuery("type", "config")));

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withAggregations(AggregationBuilders.dateHistogram("agg")
                        .field("time")
                        .calendarInterval(DateHistogramInterval.HOUR)
                        .calendarInterval(DateHistogramInterval.hours(1))
                )
                .build();

        ElasticsearchAggregations result = (ElasticsearchAggregations) template
                .search(query, DocThingModelMessage.class).getAggregations();
        ParsedDateHistogram histogram = result.aggregations().get("agg");

        List<TimeData> data = new ArrayList<>();
        for (Histogram.Bucket bucket : histogram.getBuckets()) {
            long seconds = ((ZonedDateTime) bucket.getKey()).toInstant().getEpochSecond();
            data.add(new TimeData(seconds * 1000, bucket.getDocCount()));
        }

        return data;
    }

    @Override
    public void add(ThingModelMessage msg) {
        thingModelMessageRepository.save(EsThingModelMessageConvert.INSTANCE.convertDoc(msg));
    }

    @Override
    public long count() {
        return thingModelMessageRepository.count();
    }
}
