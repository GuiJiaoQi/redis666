package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.MD5Util;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 张凯
 * 2021/6/23
 */
@Controller
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(HttpServletRequest request){
        //  /WEB-INF/pages/settings/qx/user/login.jsp
        //请求获得响应头
        Cookie[] cookies = request.getCookies();
        String loginAct = null;
        String loginPwd = null;
        //遍历cookie
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            //获得与loginAct相等的值
            if ("loginAct".equals(name)){
                loginAct = cookie.getValue();
                //继续
                continue;
            }
            //获得于loginPwd相等的值
            if ("loginPwd".equals(name)){
                loginPwd = cookie.getValue();
            }
        }
        if (loginAct!=null && loginPwd!=null){
            Map<String,Object>map = new HashMap<>();
            map.put("loginAct",loginAct);
            map.put("loginPwd",MD5Util.getMD5(loginPwd));
            User user = service.queryUserByLoginActAndPwd(map);
            request.getSession().setAttribute(Contants.SESSION_USER,user);
            return "redirect:/workbench/index.do";
        }else {
            return "settings/qx/user/login";
        }
    }

    @RequestMapping("/settings/qx/user/login.do")

    public @ResponseBody Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpServletResponse response, HttpSession session){

        //封装参数
        Map<String,Object>map = new HashMap();
        map.put("loginAct",loginAct);
        map.put("loginPwd", MD5Util.getMD5(loginPwd));

        User user = service.queryUserByLoginActAndPwd(map);
        ReturnObject returnObject = new ReturnObject();

        if (user == null){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
            returnObject.setMessage("用户名或密码错误");
        }else {
            //compareTo是字符串比较的
            if (DateUtils.formatDateTime(new Date()).compareTo(user.getExpireTime())>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
            returnObject.setMessage("账号过期");
            }else if ("0".equals(user.getLockState())){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
                returnObject.setMessage("状态被锁定");
            }else if (user.getAllowIps().contains(request.getRemoteAddr())){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
            returnObject.setMessage("ip地址受限");
            }else{
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                session.setAttribute(Contants.SESSION_USER,user);
                //免登陆功能,如果复选框isRemPwd内状态为true
                if ("true".equals(isRemPwd)){
                    Cookie c1 = new Cookie("loginAct",loginAct);
                    c1.setMaxAge(10*24*60*60);
                    response.addCookie(c1);

                    Cookie c2 = new Cookie("loginPwd",loginPwd);
                    c2.setMaxAge(10*24*60*60);
                    response.addCookie(c2);
                    //如果复选框状态为false
                }else{
                    Cookie c1 = new Cookie("loginAct",null);
                    c1.setMaxAge(0);
                    response.addCookie(c1);

                    Cookie c2 = new Cookie("loginPwd",null);
                    c2.setMaxAge(0);
                    response.addCookie(c2);
                }
            }
        }
        return returnObject;
    }

    @RequestMapping("settings/qx/user/logout.do")
    public String logout(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        Cookie c1 = new Cookie("loginAct",null);
        c1.setMaxAge(0);
        response.addCookie(c1);

        Cookie c2 = new Cookie("loginPwd",null);
        c2.setMaxAge(0);
        response.addCookie(c2);
        //立刻释放资源
        session.invalidate();

        return "redirect:/";
    }
}
