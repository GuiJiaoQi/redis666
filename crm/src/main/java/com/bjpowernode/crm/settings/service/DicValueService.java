package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.DicValue;

import java.util.List;

/**
 * 张大帅
 * 2021/6/27
 */
public interface DicValueService {
    List<DicValue> queryAllDicValue();

    DicValue queryDicValueById(String id);

    int saveCreateDicValue(DicValue dicValue);

    int deleteDicValueByIds(String[] id);

    int saveEditDicValue(DicValue dicValue);

    List<DicValue> queryDicValueByTypeCode(String typeCode);
}
