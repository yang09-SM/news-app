package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.NewsCategory;
import com.ruoyi.system.mapper.NewsCategoryMapper;
import com.ruoyi.system.service.INewsCategoryService;

/**
 * 新闻分类 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class NewsCategoryServiceImpl implements INewsCategoryService
{
    @Autowired
    private NewsCategoryMapper categoryMapper;

    /**
     * 查询新闻分类信息
     * 
     * @param categoryId 新闻分类ID
     * @return 新闻分类信息
     */
    @Override
    public NewsCategory selectCategoryById(Long categoryId)
    {
        return categoryMapper.selectCategoryById(categoryId);
    }

    /**
     * 查询新闻分类列表
     * 
     * @param newsCategory 新闻分类信息
     * @return 新闻分类集合
     */
    @Override
    public List<NewsCategory> selectCategoryList(NewsCategory newsCategory)
    {
        return categoryMapper.selectCategoryList(newsCategory);
    }

    /**
     * 新增新闻分类
     * 
     * @param newsCategory 新闻分类信息
     * @return 结果
     */
    @Override
    public int insertCategory(NewsCategory newsCategory)
    {
        return categoryMapper.insertCategory(newsCategory);
    }

    /**
     * 修改新闻分类
     * 
     * @param newsCategory 新闻分类信息
     * @return 结果
     */
    @Override
    public int updateCategory(NewsCategory newsCategory)
    {
        return categoryMapper.updateCategory(newsCategory);
    }

    /**
     * 删除新闻分类对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCategoryByIds(String ids)
    {
        return categoryMapper.deleteCategoryByIds(Convert.toStrArray(ids));
    }

    @Override
    public List<NewsCategory> selectEnabledCategoryList()
    {
        return categoryMapper.selectEnabledCategoryList();
    }
}
