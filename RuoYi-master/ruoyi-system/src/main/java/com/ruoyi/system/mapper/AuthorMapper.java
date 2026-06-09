package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Author;

/**
 * 作者 数据层
 *
 * @author ruoyi
 */
public interface AuthorMapper
{
    /**
     * 查询作者信息
     *
     * @param authorId 作者ID
     * @return 作者信息
     */
    public Author selectAuthorById(Long authorId);

    /**
     * 查询作者列表
     *
     * @param author 作者信息
     * @return 作者集合
     */
    public List<Author> selectAuthorList(Author author);

    /**
     * 新增作者
     *
     * @param author 作者信息
     * @return 结果
     */
    public int insertAuthor(Author author);

    /**
     * 修改作者
     *
     * @param author 作者信息
     * @return 结果
     */
    public int updateAuthor(Author author);

    /**
     * 批量删除作者
     *
     * @param authorIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAuthorByIds(String[] authorIds);
}
