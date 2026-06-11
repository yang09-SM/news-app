package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.PushRecord;

/**
 * 推送记录Service接口
 *
 * @author ruoyi
 */
public interface IPushRecordService
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
     * 批量删除推送记录（逻辑删除）
     *
     * @param ids 需要删除的数据ID数组
     * @return 结果
     */
    public int deletePushRecordByIds(String ids);
}
