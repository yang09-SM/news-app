package com.ruoyi.web.controller.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.NewsArticle;
import com.ruoyi.system.domain.UserComment;
import com.ruoyi.system.domain.UserDislikedNews;
import com.ruoyi.system.domain.UserFavorite;
import com.ruoyi.system.service.INewsArticleService;
import com.ruoyi.system.service.IUserFavoriteService;
import com.ruoyi.system.service.IUserCommentService;
import com.ruoyi.system.service.IUserDislikedNewsService;
import com.ruoyi.system.service.IRecommendationService;
import com.ruoyi.system.service.INewsSearchService;

@Controller
@RequestMapping("/api/news")
@Anonymous
public class NewsApiController extends BaseController
{
    @Autowired
    private INewsArticleService newsArticleService;

    @Autowired
    private IUserFavoriteService userFavoriteService;

    @Autowired
    private IUserCommentService userCommentService;

    @Autowired
    private IUserDislikedNewsService userDislikedNewsService;

    @Autowired
    private IRecommendationService recommendationService;

    @Autowired
    private INewsSearchService newsSearchService;

    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(@RequestParam(value = "categoryId", required = false) Long categoryId)
    {
        startPage();
        NewsArticle newsArticle = new NewsArticle();
        if (categoryId != null)
        {
            newsArticle.setCategoryId(categoryId);
        }
        List<NewsArticle> list = newsArticleService.selectPublishedNewsList(newsArticle);
        return getDataTable(list);
    }

    @GetMapping("/{articleId}")
    @ResponseBody
    public AjaxResult getInfo(@PathVariable("articleId") Long articleId)
    {
        NewsArticle newsArticle = newsArticleService.selectNewsArticleDetail(articleId);
        if (newsArticle == null)
        {
            return AjaxResult.error("新闻不存在");
        }
        return AjaxResult.success(newsArticle);
    }

    @PostMapping("/{articleId}/view")
    @ResponseBody
    public AjaxResult incrementViewCount(@PathVariable("articleId") Long articleId)
    {
        int result = newsArticleService.incrementViewCount(articleId);
        if (result > 0)
        {
            return AjaxResult.success("浏览记录成功");
        }
        return AjaxResult.error("浏览记录失败");
    }

    @PostMapping("/{articleId}/favorite")
    @ResponseBody
    public AjaxResult addFavorite(@PathVariable("articleId") Long articleId, @RequestBody Map<String, Object> params)
    {
        Long userId = Long.valueOf(params.get("userId").toString());
        
        UserFavorite existing = userFavoriteService.selectFavoriteByUserAndArticle(userId, articleId);
        if (existing != null)
        {
            return AjaxResult.error("已收藏该新闻");
        }

        NewsArticle newsArticle = newsArticleService.selectNewsArticleById(articleId);
        if (newsArticle == null)
        {
            return AjaxResult.error("新闻不存在");
        }

        UserFavorite userFavorite = new UserFavorite();
        userFavorite.setUserId(userId);
        userFavorite.setArticleId(articleId);
        userFavorite.setArticleTitle(newsArticle.getTitle());
        userFavorite.setArticleCover(newsArticle.getCoverImage());
        userFavorite.setFavoriteTime(new Date());
        userFavorite.setCreateBy("api");

        int result = userFavoriteService.insertFavorite(userFavorite);
        if (result > 0)
        {
            return AjaxResult.success("收藏成功");
        }
        return AjaxResult.error("收藏失败");
    }

    @DeleteMapping("/{articleId}/favorite")
    @ResponseBody
    public AjaxResult removeFavorite(@PathVariable("articleId") Long articleId, @RequestBody Map<String, Object> params)
    {
        Long userId = Long.valueOf(params.get("userId").toString());
        int result = userFavoriteService.deleteFavoriteByUserAndArticle(userId, articleId);
        if (result > 0)
        {
            return AjaxResult.success("取消收藏成功");
        }
        return AjaxResult.error("取消收藏失败");
    }

    @GetMapping("/favorites")
    @ResponseBody
    public AjaxResult getFavorites(@RequestParam("userId") Long userId)
    {
        List<UserFavorite> favorites = userFavoriteService.selectFavoritesByUserId(userId);
        return AjaxResult.success(favorites);
    }

    @PostMapping("/{articleId}/like")
    @ResponseBody
    public AjaxResult addLike(@PathVariable("articleId") Long articleId, @RequestBody Map<String, Object> params)
    {
        Long userId = Long.valueOf(params.get("userId").toString());
        
        UserDislikedNews existing = userDislikedNewsService.selectLikedNewsByUserAndArticle(userId, articleId);
        if (existing != null)
        {
            return AjaxResult.error("已点赞该新闻");
        }

        UserDislikedNews userLikedNews = new UserDislikedNews();
        userLikedNews.setUserId(userId);
        userLikedNews.setArticleId(articleId);
        userLikedNews.setDislikeTime(new Date());
        userLikedNews.setCreateBy("api");

        int result = userDislikedNewsService.insertDislikedNews(userLikedNews);
        if (result > 0)
        {
            return AjaxResult.success("点赞成功");
        }
        return AjaxResult.error("点赞失败");
    }

    @DeleteMapping("/{articleId}/like")
    @ResponseBody
    public AjaxResult removeLike(@PathVariable("articleId") Long articleId, @RequestBody Map<String, Object> params)
    {
        Long userId = Long.valueOf(params.get("userId").toString());
        int result = userDislikedNewsService.deleteLikedNewsByUserAndArticle(userId, articleId);
        if (result > 0)
        {
            return AjaxResult.success("取消点赞成功");
        }
        return AjaxResult.error("取消点赞失败");
    }

    @GetMapping("/{articleId}/comments")
    @ResponseBody
    public AjaxResult getComments(@PathVariable("articleId") Long articleId)
    {
        List<UserComment> comments = userCommentService.selectCommentsByArticleId(articleId);
        Map<String, Object> result = new HashMap<>();
        result.put("comments", comments);
        
        for (UserComment comment : comments)
        {
            List<UserComment> replies = userCommentService.selectRepliesByParentId(comment.getCommentId());
            result.put("replies_" + comment.getCommentId(), replies);
        }
        
        return AjaxResult.success(result);
    }

    @PostMapping("/{articleId}/comments")
    @ResponseBody
    public AjaxResult addComment(@PathVariable("articleId") Long articleId, @RequestBody Map<String, Object> params)
    {
        Long userId = Long.valueOf(params.get("userId").toString());
        String content = params.get("content").toString();
        String userName = params.get("userName") != null ? params.get("userName").toString() : "";
        String userAvatar = params.get("userAvatar") != null ? params.get("userAvatar").toString() : "";

        UserComment userComment = new UserComment();
        userComment.setArticleId(articleId);
        userComment.setUserId(userId);
        userComment.setUserName(userName);
        userComment.setUserAvatar(userAvatar);
        userComment.setContent(content);
        userComment.setLikeCount(0);
        userComment.setReplyCount(0);
        userComment.setIsTop("0");
        userComment.setStatus("1");
        userComment.setCreateBy("api");

        int result = userCommentService.insertComment(userComment);
        if (result > 0)
        {
            return AjaxResult.success(userComment);
        }
        return AjaxResult.error("评论失败");
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseBody
    public AjaxResult deleteComment(@PathVariable("commentId") Long commentId)
    {
        int result = userCommentService.deleteCommentByIds(String.valueOf(commentId));
        if (result > 0)
        {
            return AjaxResult.success("删除评论成功");
        }
        return AjaxResult.error("删除评论失败");
    }

    @PostMapping("/comments/{commentId}/like")
    @ResponseBody
    public AjaxResult likeComment(@PathVariable("commentId") Long commentId)
    {
        int result = userCommentService.incrementLikeCount(commentId);
        if (result > 0)
        {
            return AjaxResult.success("点赞评论成功");
        }
        return AjaxResult.error("点赞评论失败");
    }

    @PostMapping("/comments/{commentId}/reply")
    @ResponseBody
    public AjaxResult replyComment(@PathVariable("commentId") Long commentId, @RequestBody Map<String, Object> params)
    {
        Long userId = Long.valueOf(params.get("userId").toString());
        String content = params.get("content").toString();
        String userName = params.get("userName") != null ? params.get("userName").toString() : "";
        String userAvatar = params.get("userAvatar") != null ? params.get("userAvatar").toString() : "";
        Long replyToUserId = params.get("replyToUserId") != null ? Long.valueOf(params.get("replyToUserId").toString()) : null;
        String replyToUserName = params.get("replyToUserName") != null ? params.get("replyToUserName").toString() : "";

        UserComment parentComment = userCommentService.selectCommentById(commentId);
        if (parentComment == null)
        {
            return AjaxResult.error("评论不存在");
        }

        UserComment userComment = new UserComment();
        userComment.setArticleId(parentComment.getArticleId());
        userComment.setUserId(userId);
        userComment.setUserName(userName);
        userComment.setUserAvatar(userAvatar);
        userComment.setContent(content);
        userComment.setParentId(commentId);
        userComment.setReplyToUserId(replyToUserId);
        userComment.setReplyToUserName(replyToUserName);
        userComment.setLikeCount(0);
        userComment.setReplyCount(0);
        userComment.setIsTop("0");
        userComment.setStatus("1");
        userComment.setCreateBy("api");

        int result = userCommentService.insertComment(userComment);
        if (result > 0)
        {
            userCommentService.incrementReplyCount(commentId);
            return AjaxResult.success(userComment);
        }
        return AjaxResult.error("回复评论失败");
    }

    @GetMapping("/hot")
    @ResponseBody
    public AjaxResult getHotNews()
    {
        List<NewsArticle> hotNews = newsArticleService.selectHotNewsList();
        return AjaxResult.success(hotNews);
    }

    @GetMapping("/category/{categoryId}")
    @ResponseBody
    public TableDataInfo getNewsByCategory(@PathVariable("categoryId") Long categoryId)
    {
        startPage();
        List<NewsArticle> list = newsArticleService.selectNewsByCategory(categoryId);
        return getDataTable(list);
    }

    @GetMapping("/search")
    @ResponseBody
    public TableDataInfo searchNews(@RequestParam("keyword") String keyword,
                                     @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize)
    {
        // 优先使用Elasticsearch搜索
        if (newsSearchService.isAvailable())
        {
            Map<String, Object> esResult = newsSearchService.search(keyword, pageNum, pageSize);
            List<?> list = (List<?>) esResult.get("list");

            // 如果ES返回了结果，直接使用ES结果
            if (list != null && !list.isEmpty())
            {
                TableDataInfo rspData = new TableDataInfo();
                rspData.setCode(200);
                rspData.setMsg("查询成功");
                rspData.setTotal(((Number) esResult.get("total")).longValue());
                rspData.setRows(list);
                return rspData;
            }
            else
            {
                // ES结果为空，降级到MySQL搜索
                logger.info("ES搜索无结果或失败，降级为MySQL搜索: keyword={}", keyword);
            }
        }

        // 降级方案：使用MySQL LIKE搜索
        startPage();
        List<NewsArticle> list = newsArticleService.searchNews(keyword);
        return getDataTable(list);
    }

    /**
     * 搜索联想建议（基于Elasticsearch）
     */
    @GetMapping("/search/suggest")
    @ResponseBody
    public AjaxResult searchSuggest(@RequestParam("keyword") String keyword,
                                     @RequestParam(value = "count", defaultValue = "10") int count)
    {
        if (!newsSearchService.isAvailable())
        {
            return AjaxResult.error("Elasticsearch未启用");
        }

        List<String> suggestions = newsSearchService.suggest(keyword, count);
        return AjaxResult.success(suggestions);
    }

    /**
     * 搜索热词榜（基于Elasticsearch）
     */
    @GetMapping("/search/hot-words")
    @ResponseBody
    public AjaxResult getHotWords(@RequestParam(value = "count", defaultValue = "20") int count)
    {
        if (!newsSearchService.isAvailable())
        {
            return AjaxResult.error("Elasticsearch未启用");
        }

        List<Map<String, Object>> hotWords = newsSearchService.getHotKeywords(count);
        return AjaxResult.success(hotWords);
    }

    @GetMapping("/recommended")
    @ResponseBody
    public AjaxResult getRecommendedNews(@RequestParam(value = "userId", required = false) Long userId,
                                         @RequestParam(value = "limit", defaultValue = "10") int limit)
    {
        List<NewsArticle> recommendedNews = recommendationService.recommendCombined(userId, limit);
        return AjaxResult.success(recommendedNews);
    }
}
