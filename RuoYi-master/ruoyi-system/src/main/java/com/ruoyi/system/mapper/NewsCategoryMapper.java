package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NewsCategory;

/**
 * 新闻分类 数据层
 * 
 * @author ruoyi
 */
public interface NewsCategoryMapper
{
    /**
     * 查询新闻分类信息
     * 
     * @param categoryId 新闻分类ID
     * @return 新闻分类信息
     */
    public NewsCategory selectCategoryById(Long categoryId);

    /**
     * 查询新闻分类列表
     * 
     * @param newsCategory 新闻分类信息
     * @return 新闻分类集合
     */
    public List<NewsCategory> selectCategoryList(NewsCategory newsCategory);

    /**
     * 新增新闻分类
     * 
     * @param newsCategory 新闻分类信息
     * @return 结果
     */
    public int insertCategory(NewsCategory newsCategory);

    /**
     * 修改新闻分类
     * 
     * @param newsCategory 新闻分类信息
     * @return 结果
     */
    public int updateCategory(NewsCategory newsCategory);

    /**
     * 批量删除新闻分类
     * 
     * @param categoryIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCategoryByIds(String[] categoryIds);

    public List<NewsCategory> selectEnabledCategoryList();
}
