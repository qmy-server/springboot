package com.qmy.services.common;

import com.qmy.domain.common.Menu;

import java.util.List;
import java.util.Map;

public interface CommonService {
    List<Menu> getMenu(Map<String,Object> maps);
}
