package com.qmy.controller.common;

import com.qmy.services.common.CommonService;
import com.qmy.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@RestController
@RequestMapping("/menu")

public class MenuController {
    @Autowired
    private CommonService commonService;

    //根据用户权限获取菜单列表
    @RequestMapping(method = RequestMethod.POST)
    public Object getMenu(@RequestBody Map<String,Object> maps){
        return MapUtils.success(this.commonService.getMenu(maps));

    }
}
