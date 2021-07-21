package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.mapper.DicTypeMapper;
import com.bjpowernode.crm.settings.service.DicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 张凯
 * 2021/6/26
 */
@Service
public class DicTypeServiceImpl implements DicTypeService {

    @Autowired
    private DicTypeMapper mapper;

    @Override
    public List<DicType> queryAllDicTypes() {
        return mapper.selectAllDicTypes();
    }

    @Override
    public DicType queryDicTypeByCode(String code) {
        return mapper.selectDicTypeByCode(code);
    }

    @Override
    public int saveCreateDicType(DicType dicType) {
        return mapper.insertDicType(dicType);
    }

    @Override
    public int deleteDicTypeByCodes(String[] codes) {
        return mapper.deleteDicType(codes);
    }

    @Override
    public int saveEditDicType(DicType dicType) {
        return mapper.updateDicType(dicType);
    }
}
