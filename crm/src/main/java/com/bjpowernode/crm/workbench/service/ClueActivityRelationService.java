package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

/**
 * 张大帅
 * 2021/7/5
 */
public interface ClueActivityRelationService {
    int saveClueActivityRelationByList(List<ClueActivityRelation> relationList);

    int deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation relation);
}
