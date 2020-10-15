package com.qmy.dao.common;

import com.qmy.domain.common.Menu;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CommonDao")
public interface CommonDao {
    @Select("SELECT * FROM tbl_menu WHERE authority=#{ss}")
    List<Menu> getMenu(int ss);
}
