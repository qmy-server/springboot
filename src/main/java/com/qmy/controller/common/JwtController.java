package com.qmy.controller.common;

import com.google.gson.Gson;
import com.qmy.domain.user.UserInfo;
import com.qmy.services.user.UserService;
import com.qmy.utils.MapUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/jwt")

public class JwtController {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private Gson gson;
    @RequestMapping(value = "/hello",method = RequestMethod.POST)
    public String hello(@RequestBody int i) {
       if(i==1){
           return gson.toJson(MapUtils.success(null));
       }else{
           return gson.toJson(MapUtils.error(401,"无权限"));
       }
    }
    public JwtController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        this.userService=userService;
    }
    //注册
    @RequestMapping(method = RequestMethod.POST)
    public String add(@RequestBody UserInfo register){
        String result=this.userService.addRegister(register);
        return result;
    }
    //管理员密码验证
    @RequestMapping(value = "/checkPassword",method = RequestMethod.POST)
    public Boolean add(@RequestBody Map<String,String> map){
        return this.userService.checkPassword(map.get("password"));
    }
    //登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestBody UserInfo userInfo){
        Map<String,Object> resultMap=new ConcurrentHashMap<>();
        UserInfo u=this.userService.getJwtUser(userInfo);
        if(u!=null){

            //验证通过，生成token；
            String token = Jwts.builder()
                    .setSubject(u.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                    .signWith(SignatureAlgorithm.HS512, "QmyJwtSecret")
                    .compact();
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = dateformat.format(System.currentTimeMillis());
            String endDate=dateformat.format(System.currentTimeMillis() + 60 * 60 * 24 * 1000);
            resultMap.put("username",u.getUsername());
            resultMap.put("id",u.getId());
            resultMap.put("type",u.getType());
            resultMap.put("name",u.getName());
            resultMap.put("token",token);
            resultMap.put("createTime",dateStr);
            resultMap.put("validityPeriod",endDate);
            if(u.getPicture()!=null){
                resultMap.put("picture",200);
            }else{
                resultMap.put("picture",404);
            }
            return gson.toJson(resultMap);
        }
        else{
            resultMap.put("code","401");
            return gson.toJson(resultMap);

        }
    }
}
