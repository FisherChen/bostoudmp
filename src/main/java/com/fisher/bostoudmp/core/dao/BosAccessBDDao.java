package com.fisher.bostoudmp.core.dao;

import com.fisher.bostoudmp.core.bean.BosImageBatchInfo;
import com.fisher.bostoudmp.core.exception.BosRowIDException;

import java.util.List;
/**
 * Created by fisher on 17-3-24.
 */
public interface BosAccessBDDao {

    /**
     * 获得需要进行的上传的批次的信息
     * @param pageSize 每次获取的批次的大小
     * @return 返回一个BosImageBatchInfo 的List
     */

    public List<BosImageBatchInfo> getBosImageInfoList(int pageSize);

    /**
     * 上传的准备操作
     * @param bosImageBatchInfo 以一个批次为单位进行准备
     * @return 返回一个boolean 值用于标记是否长传成功
     */

    public boolean prepareUpLoadFile(BosImageBatchInfo bosImageBatchInfo);

    /**
     * 上传的准备操作
     * @deprecated 建议一个个批次上传准备，不建议再批量的上传
     * @param blist 一个的BosImageBatchInfo 的List
     */
    public void prepareUpLoadFile(List<BosImageBatchInfo> blist) ;

    /**
     * 上传后的更新结束操作
     * @param bosRowID 批次的ID主键
     * @param docID udmp 系统返回的docID
     * @param flag FlagEnum中映射的标记
     * @throws BosRowIDException 抛出当批次的主键ID为空的时候
     */
    public int updateBosImageToUdmp(String bosRowID, int flag, String docID) throws BosRowIDException;
}
