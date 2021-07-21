package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.service.DicTypeService;
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
public class DicTypeController {
    @Autowired
    private DicTypeService service;
    //页面展示全部
    @RequestMapping("/settings/dictionary/type/index.do")
    public String index(Model model){
        List<DicType>list = service.queryAllDicTypes();
        model.addAttribute("list",list);
        return "settings/dictionary/type/index";
    }
    /*settings/dictionary/type/toSave.do*/
    @RequestMapping("/settings/dictionary/type/toSave.do")
    public String toSave(){
        return "settings/dictionary/type/save";
    }
        //判断编码合理性
    @RequestMapping("/settings/dictionary/type/checkCode.do")
    public @ResponseBody Object checkCode(String code){
        DicType dicType = service.queryDicTypeByCode(code);
        ReturnObject returnObject = new ReturnObject();
        if (dicType==null){
            //不重复的
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }else {
            //重复的
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
            returnObject.setMessage("编码已存在,请重新输入");
        }
        return returnObject;
    }
    //保存结果处理
    /*settings/dictionary/type/saveCreateDicType.do*/
    @RequestMapping("/settings/dictionary/type/saveCreateDicType.do")
    public @ResponseBody Object saveCreateDicType(DicType dicType){
        int num = service.saveCreateDicType(dicType);
        ReturnObject returnObject = new ReturnObject();
        if (num>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }else{
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
            returnObject.setMessage("编码保存失败");
        }
        return returnObject;
    }

    @RequestMapping("/settings/dictionary/type/editDicType.do")
   public String editDicType(String code,Model model){
        DicType dicType = service.queryDicTypeByCode(code);
        model.addAttribute("dicType",dicType);
        return "settings/dictionary/type/edit";
    }
    //编辑判断处理
    @RequestMapping("/settings/dictionary/type/saveEditDicType.do")
    public @ResponseBody Object saveEditDicType(DicType dicType){
        int num = service.saveEditDicType(dicType);
        ReturnObject returnObject = new ReturnObject();
        if (num>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
            returnObject.setMessage("编辑失败");
        }
        return returnObject;
    }
    //删除判断处理
    @RequestMapping("/settings/dictionary/type/deleteDicTypeByCodes.do")
    public @ResponseBody Object deleteDicTypeByCodes(String[] code){
        int num = service.deleteDicTypeByCodes(code);
        ReturnObject returnObject = new ReturnObject();
        if (num>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
            returnObject.setMessage("删除失败");
        }
        return returnObject;
    }
}
