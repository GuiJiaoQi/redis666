package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.mapper.DicValueMapper;
import com.bjpowernode.crm.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 张凯
 * 2021/6/27
 */
@Service
public class DicValueServiceImpl implements DicValueService {
    @Autowired
    private DicValueMapper mapper;

    @Override
    public List<DicValue> queryDicValueByTypeCode(String typeCode) {
        return mapper.selectDicValueByTypeCode(typeCode);
    }

    @Override
    public List<DicValue> queryAllDicValue() {
        return mapper.selectAllDicValues();
    }



    @Override
    public int saveCreateDicValue(DicValue dicValue) {
        return mapper.insertDicValue(dicValue);
    }

    @Override
    public DicValue queryDicValueById(String id) {
        return mapper.selectDicValueById(id);
    }

    @Override
    public int deleteDicValueByIds(String[] typeCodes) {
        return mapper.deleteDicValueByIds(typeCodes);
    }

    @Override
    public int saveEditDicValue(DicValue dicValue) {
        return mapper.updateDicValue(dicValue);
    }
}
