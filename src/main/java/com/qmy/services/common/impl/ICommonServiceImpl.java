package com.qmy.services.common.impl;

import com.google.gson.Gson;
import com.qmy.config.dataSource.DataSourceKey;
import com.qmy.config.dataSource.TargetDataSource;
import com.qmy.dao.common.CommonDao;
import com.qmy.domain.common.Menu;
import com.qmy.services.common.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("CommonService")
public class ICommonServiceImpl implements CommonService {

    @Autowired
    private CommonDao commonDao;
    /*手动设置数据源，如果接口方法名称有get，select，query等关键字开头，则该注解失效*/
/*    @TargetDataSource(dataSourceKey = DataSourceKey.ds_master)*/
    @Override
    public List<Menu> getMenu(Map<String, Object> maps) {
        List<Menu> menu=this.commonDao.getMenu(Integer.parseInt(maps.get("type").toString()));
        return menu;
    }
}
