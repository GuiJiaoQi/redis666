package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicTypeService;
import com.bjpowernode.crm.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 张凯
 * 2021/6/27
 */
@Controller
public class DicValueController {
    @Autowired
    DicValueService valueService;

    @Autowired
    DicTypeService typeService;
    //字典值展示页面
    @RequestMapping("/settings/dictionary/value/index.do")
    public String index(Model model){
        List<DicValue>list = valueService.queryAllDicValue();
        model.addAttribute("list",list);
        return "settings/dictionary/value/index";
    }
    //下拉列表中需要展示的全部字段
    @RequestMapping("/settings/dictionary/value/toSave.do")
    public String toSave(Model model){
        List<DicType>list = typeService.queryAllDicTypes();
        model.addAttribute("list",list);
        return "settings/dictionary/value/save";
    }
    //保存处理
    @RequestMapping("/settings/dictionary/value/saveCreateDicValue.do")
    public @ResponseBody Object saveCreateDicValue(DicValue dicValue){
        //给编码值添加UUID
        dicValue.setId(UUIDUtils.getUUID());
        int num = valueService.saveCreateDicValue(dicValue);
        ReturnObject returnObject = new ReturnObject();
        if (num>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
            returnObject.setMessage("保存失败");
        }
        return returnObject;
    }

    @RequestMapping("/settings/dictionary/value/editDicValue.do")
    public String editDicValue(String id,Model model){
        DicValue dicValue = valueService.queryDicValueById(id);
        model.addAttribute("dicValue",dicValue);
        return "settings/dictionary/value/edit";
    }

    @RequestMapping("settings/dictionary/value/saveEditDicValue.do")
    public @ResponseBody Object saveEditDicValue(DicValue dicValue){
        int num = valueService.saveEditDicValue(dicValue);
        ReturnObject returnObject = new ReturnObject();
        if (num>0) {
           returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
            returnObject.setMessage("编辑失败");
        }
        return returnObject;
    }

    @RequestMapping("/settings/dictionary/value/deleteDicValueByIds.do")
    public @ResponseBody Object deleteDicValueByIds(String[] id){
        int num = valueService.deleteDicValueByIds(id);
        ReturnObject returnObject = new ReturnObject();
        if (num>0) {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
            returnObject.setMessage("删除失败");
        }
        return returnObject;
    }
}
