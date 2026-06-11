package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.PushRecord;

/**
 * 推送记录Mapper接口
 *
 * @author ruoyi
 */
public interface PushRecordMapper
{
    /**
     * 查询推送记录
     *
     * @param pushId 推送ID
     * @return 推送记录
     */
    public PushRecord selectPushRecordById(Long pushId);

    /**
     * 查询推送记录列表
     *
     * @param pushRecord 推送记录
     * @return 推送记录集合
     */
    public List<PushRecord> selectPushRecordList(PushRecord pushRecord);

    /**
     * 查询待发送的推送记录
     *
     * @return 待发送的推送记录集合
     */
    public List<PushRecord> selectPendingPushes();

    /**
     * 新增推送记录
     *
     * @param pushRecord 推送记录
     * @return 结果
     */
    public int insertPushRecord(PushRecord pushRecord);

    /**
     * 修改推送记录
     *
     * @param pushRecord 推送记录
     * @return 结果
     */
    public int updatePushRecord(PushRecord pushRecord);

    /**
     * 删除推送记录（逻辑删除）
     *
     * @param pushId 推送ID
     * @return 结果
     */
    public int deletePushRecordById(Long pushId);

    /**
     * 批量删除推送记录（逻辑删除）
     *
     * @param pushIds 需要删除的数据ID数组
     * @return 结果
     */
    public int deletePushRecordByIds(Long[] pushIds);
}
