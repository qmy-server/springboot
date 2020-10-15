package com.qmy.controller.user;


import com.google.gson.Gson;
import com.qmy.domain.user.UserInfo;
import com.qmy.services.user.UserService;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 此控制器是进行数据库分离测试
 */
@RestController
@RequestMapping("/user")

public class UserController {
    @Autowired
    private UserService userService;

    //获取用户列表
    @RequestMapping(method = RequestMethod.GET)
    public List<UserInfo> getObject() {
        return this.userService.getObject();
    }

    //获取个人信息
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public String getUserInfo(@RequestBody Map<String, Integer> maps) {
        Map<String, Object> map = new HashMap<>();
        Gson gson = new Gson();
        UserInfo userInfo = new UserInfo();
        userInfo.setId(maps.get("id"));
        UserInfo u = this.userService.getJwtUser(userInfo);
        u.setPicture((byte[]) u.getPicture());
        map.put("data", u);
        map.put("code", 200);
        return gson.toJson(map);
    }

    //人员个人信息照片上传
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String userPicture(@RequestBody MultipartFile file, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String result = this.userService.updateUserPicture(file, request);
        return result;
    }

    //获取个人照片
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public Object getUserPicture(@RequestBody String id, HttpServletResponse response) throws IOException {
        Object result = this.userService.getUserPicture(id, response);
        return result;
    }

    //修改个人信息
    @RequestMapping(value = "/editUserInfo", method = RequestMethod.POST)
    public int updateUserInfo(@RequestBody UserInfo userInfo) {
        int result = this.userService.updateUserInfo(userInfo);
        return result;
    }

    //修改个人密码
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public int updatePassword(@RequestBody Map<String, Object> paramsMap) {
        return this.userService.updatePassword(paramsMap);
    }



}
