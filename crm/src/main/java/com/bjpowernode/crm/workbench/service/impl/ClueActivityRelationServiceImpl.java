package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.mapper.ClueActivityRelationMapper;
import com.bjpowernode.crm.workbench.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 张凯
 * 2021/7/5
 */
@Service
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Override
    public int saveClueActivityRelationByList(List<ClueActivityRelation> relationList) {
        return clueActivityRelationMapper.insertClueActivityRelationByList(relationList);
    }

    @Override
    public int deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation relation) {
        return clueActivityRelationMapper.deleteClueActivityRelationByClueIdActivityId(relation);
    }
}
