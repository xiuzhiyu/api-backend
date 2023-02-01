package com.yupi.yuapiinterface.controller;


import com.yupi.yupiclientsdk.model.User;
import com.yupi.yupiclientsdk.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 查询模拟接口
 */
@RestController
@RequestMapping("/name")
public class NameController {
    @GetMapping("/get")
    public String getNameByGet(String name) {
        return "Get name +" + name;

    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name) {
        return "Post Name " + name;
    }

    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user, HttpServletRequest httpServerRequest) {
        String accseeKey = httpServerRequest.getHeader("accessKey");
        //String sercetKey = httpServerRequest.getHeader("sercetKey");
        String nonce = httpServerRequest.getHeader("nonce");
        String body= httpServerRequest.getHeader("body");
        String timeStamp= httpServerRequest.getHeader("timeStamp");
        String sign = httpServerRequest.getHeader("sign");
        if(!accseeKey.equals("lhj"))
        {
            throw new RuntimeException("您的权限不足！");
        }
   if(Long.parseLong(nonce)>10000)
{
    throw new RuntimeException("您的权限不足！");
}
        if(Long.parseLong(nonce)>10000)
        {
            throw new RuntimeException("您的权限不足！");
        }
//        if(timeStamp)
//        {
//            //自己写
//        }
        String sign1 = SignUtils.getSign(body, "abcdefg");
        if(!sign.equals(sign1))
        {throw new RuntimeException("您的权限不足！");

        }
        String result = "Post 用户名字是 " +user.getUserName();


        return result;
    }
}
