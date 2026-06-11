package com.ruoyi.system.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.ruoyi.common.config.ElasticsearchConfig.ElasticsearchProperties;
import com.ruoyi.system.domain.NewsArticle;
import com.ruoyi.system.domain.es.NewsArticleDocument;
import com.ruoyi.system.service.INewsSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 新闻搜索服务实现类（基于Elasticsearch）
 * 使用elasticsearch-java原生客户端，ES不可用时自动降级为MySQL搜索
 */
@Service
public class NewsSearchServiceImpl implements INewsSearchService
{
    private static final Logger log = LoggerFactory.getLogger(NewsSearchServiceImpl.class);

    private static final String INDEX_NAME = "news_article";

    @Autowired(required = false)
    private ElasticsearchClient elasticsearchClient;

    @Autowired
    private ElasticsearchProperties elasticsearchProperties;

    @Override
    public Map<String, Object> search(String keyword, int page, int size)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("total", 0);
        result.put("list", Collections.emptyList());
        result.put("source", "mysql");

        if (!isAvailable() || keyword == null || keyword.trim().isEmpty())
        {
            return result;
        }

        try
        {
            Query query = BoolQuery.of(b -> b
                    .must(m -> m
                            .multiMatch(mm -> mm
                                    .fields("title^3", "summary^2", "content")
                                    .query(keyword)
                            )
                    )
                    .filter(f -> f
                            .term(t -> t
                                    .field("status")
                                    .value(FieldValue.of("1"))
                            )
                    )
            )._toQuery();

            int from = (page - 1) * size;

            SearchResponse<NewsArticleDocument> response = elasticsearchClient.search(s -> s
                            .index(INDEX_NAME)
                            .query(query)
                            .from(from)
                            .size(size)
                            .sort(sort -> sort
                                    .field(f -> f
                                            .field("create_time")
                                            .order(SortOrder.Desc)
                                    )
                            )
                            .highlight(h -> h
                                    .preTags("<em>")
                                    .postTags("</em>")
                                    .fields("title", hf -> hf)
                                    .fields("summary", hf -> hf)
                                    .fields("content", hf -> hf
                                            .fragmentSize(150)
                                            .numberOfFragments(3)
                                    )
                            ),
                    NewsArticleDocument.class
            );

            List<Map<String, Object>> resultList = new ArrayList<>();
            for (Hit<NewsArticleDocument> hit : response.hits().hits())
            {
                Map<String, Object> item = new HashMap<>();
                NewsArticleDocument doc = hit.source();

                if (doc != null)
                {
                    item.put("articleId", doc.getArticleId());
                    item.put("title", doc.getTitle());
                    item.put("summary", doc.getSummary());
                    item.put("categoryId", doc.getCategoryId());
                    item.put("viewCount", doc.getViewCount());
                    item.put("createTime", doc.getCreateTime());
                    item.put("newsType", doc.getNewsType());
                    item.put("source", doc.getSource());

                    if (hit.highlight() != null)
                    {
                        if (hit.highlight().get("title") != null && !hit.highlight().get("title").isEmpty())
                        {
                            item.put("highlightTitle", hit.highlight().get("title").get(0));
                        }
                        else
                        {
                            item.put("highlightTitle", doc.getTitle());
                        }

                        if (hit.highlight().get("summary") != null && !hit.highlight().get("summary").isEmpty())
                        {
                            item.put("highlightSummary", hit.highlight().get("summary").get(0));
                        }
                        else
                        {
                            item.put("highlightSummary", doc.getSummary());
                        }

                        if (hit.highlight().get("content") != null && !hit.highlight().get("content").isEmpty())
                        {
                            item.put("highlightContent", String.join("...", hit.highlight().get("content")));
                        }
                    }

                    resultList.add(item);
                }
            }

            result.put("total", response.hits().total() != null ? response.hits().total().value() : 0);
            result.put("list", resultList);
            result.put("source", "elasticsearch");

            log.debug("ES搜索完成: keyword={}, total={}", keyword, result.get("total"));
        }
        catch (Exception e)
        {
            log.error("Elasticsearch搜索失败，将降级为MySQL搜索: {}", e.getMessage());
        }

        return result;
    }

    @Override
    public List<String> suggest(String keyword, int count)
    {
        // 联想搜索：基于ES前缀查询title字段（简化实现）
        if (!isAvailable() || keyword == null || keyword.trim().isEmpty())
        {
            return Collections.emptyList();
        }

        try
        {
            // 使用prefix query获取标题联想建议
            SearchResponse<NewsArticleDocument> response = elasticsearchClient.search(s -> s
                            .index(INDEX_NAME)
                            .query(q -> q
                                    .prefix(p -> p
                                            .field("title.keyword")
                                            .value(keyword)
                                    )
                            )
                            .size(count)
                            .sort(sort -> sort
                                    .field(f -> f.field("view_count").order(SortOrder.Desc))
                            ),
                    NewsArticleDocument.class
            );

            Set<String> suggestions = new LinkedHashSet<>();
            for (Hit<NewsArticleDocument> hit : response.hits().hits())
            {
                if (hit.source() != null && hit.source().getTitle() != null)
                {
                    suggestions.add(hit.source().getTitle());
                }
            }

            return new ArrayList<>(suggestions);
        }
        catch (Exception e)
        {
            log.error("Elasticsearch联想搜索失败: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Map<String, Object>> getHotKeywords(int count)
    {
        if (!isAvailable())
        {
            return Collections.emptyList();
        }

        try
        {
            SearchResponse<NewsArticleDocument> response = elasticsearchClient.search(s -> s
                            .index(INDEX_NAME)
                            .size(0)
                            .aggregations("hot_keywords", Aggregation.of(a -> a
                                    .terms(t -> t
                                            .field("title.keyword")
                                            .size(count)
                                    )
                            )),
                    NewsArticleDocument.class
            );

            List<Map<String, Object>> hotWords = new ArrayList<>();
            if (response.aggregations() != null && response.aggregations().containsKey("hot_keywords"))
            {
                var termsAgg = response.aggregations().get("hot_keywords").sterms();
                if (termsAgg != null && termsAgg.buckets() != null)
                {
                    for (var bucket : termsAgg.buckets().array())
                    {
                        Map<String, Object> word = new HashMap<>();
                        word.put("keyword", bucket.key());
                        word.put("count", bucket.docCount());
                        hotWords.add(word);
                    }
                }
            }

            return hotWords;
        }
        catch (Exception e)
        {
            log.error("Elasticsearch获取热词失败: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void indexArticle(NewsArticle article)
    {
        if (!isAvailable() || article == null)
        {
            return;
        }

        try
        {
            NewsArticleDocument document = convertToDocument(article);

            elasticsearchClient.index(i -> i
                    .index(INDEX_NAME)
                    .id(String.valueOf(article.getArticleId()))
                    .document(document)
            );

            log.debug("文章已同步到ES: articleId={}", article.getArticleId());
        }
        catch (Exception e)
        {
            log.error("同步文章到Elasticsearch失败: articleId={}, error={}",
                    article.getArticleId(), e.getMessage());
        }
    }

    @Override
    public void deleteArticle(Long articleId)
    {
        if (!isAvailable() || articleId == null)
        {
            return;
        }

        try
        {
            elasticsearchClient.delete(d -> d
                    .index(INDEX_NAME)
                    .id(String.valueOf(articleId))
            );

            log.debug("已从ES删除文章: articleId={}", articleId);
        }
        catch (Exception e)
        {
            log.error("从Elasticsearch删除文章失败: articleId={}, error={}",
                    articleId, e.getMessage());
        }
    }

    @Override
    public void batchIndex(List<NewsArticle> articles)
    {
        if (!isAvailable() || articles == null || articles.isEmpty())
        {
            return;
        }

        try
        {
            // 逐条索引（避免bulk API版本兼容问题）
            for (NewsArticle article : articles)
            {
                try
                {
                    NewsArticleDocument document = convertToDocument(article);
                    elasticsearchClient.index(i -> i
                            .index(INDEX_NAME)
                            .id(String.valueOf(article.getArticleId()))
                            .document(document)
                    );
                }
                catch (Exception ex)
                {
                    log.warn("批量同步单篇文章到ES失败: articleId={}", article.getArticleId());
                }
            }

            log.info("批量同步{}篇文章到ES完成", articles.size());
        }
        catch (Exception e)
        {
            log.error("批量同步文章到Elasticsearch失败: error={}", e.getMessage());
        }
    }

    @Override
    public boolean isAvailable()
    {
        return elasticsearchProperties.isEnabled()
                && elasticsearchClient != null;
    }

    private NewsArticleDocument convertToDocument(NewsArticle article)
    {
        NewsArticleDocument document = new NewsArticleDocument();
        document.setArticleId(article.getArticleId());
        document.setTitle(article.getTitle());
        document.setSummary(article.getSummary());
        document.setContent(article.getContent());
        document.setStatus(article.getStatus());
        document.setCategoryId(article.getCategoryId());
        document.setViewCount(article.getViewCount());
        document.setCreateTime(article.getCreateTime());
        document.setNewsType(article.getNewsType());
        document.setSource(article.getSource());
        document.setAuthorId(article.getAuthorId());
        return document;
    }
}
