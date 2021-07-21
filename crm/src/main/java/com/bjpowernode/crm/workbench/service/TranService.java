package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.FunnelVO;
import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

/**
 * 张凯
 * 2021/7/8
 */
public interface TranService {

    int saveCreateTran(Map<String,Object>map);
    //根据交易Id查询交易详情
    Tran queryTranForDetailById(String id);
    //销售漏斗图
    List<FunnelVO>queryCountOfTranGroupByStage();
}
