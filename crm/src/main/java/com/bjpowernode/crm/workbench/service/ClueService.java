package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Clue;

import java.util.Map;

/**
 * 张大帅
 * 2021/7/3
 */
public interface ClueService {
   /* //保存线索
    int insertClue(Clue clue);
    //根据ID查询线索的详情
    Clue selectClueForDetailById(String id);
    //根据ID查询线索信息
    Clue selectClueById(String id);
    //删除
    int deleteById(String id);*/
    int saveCreate(Clue clue);

    Clue queryClueForDetailById(String id);

    void saveConvert(Map<String,Object>map);
}
