package com.qmy.dao.mytest;

import com.qmy.domain.mytest.Assess;
import com.qmy.domain.mytest.Product;
import com.qmy.domain.mytest.VideoList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ResourceDao")
public interface ResourceDao {
    @Select("select path from tbl_config where id=1")
    String getProductPath();
    @Insert("INSERT INTO tbl_resource (name,principal,dateTime,tool,type,fileName,scenes,path,other) values " +
            "(#{name},#{principal},#{dateTime},#{tool},#{type},#{fileName},#{scenes},#{path},#{other})")
    void addProduct(Product pro);
    @Select("select path from tbl_config where id=2")
    String getHtmlPath();
    @Select("select id,type,name,fileName,dateTime from tbl_resource where type=1")
    List<VideoList> getVideoMenuList();
    @Insert("INSERT INTO tbl_assess (username,productName,productId,content,rate1,rate2,rate3,rateSum) values " +
            "(#{username},#{productName},#{productId},#{content},#{rate1},#{rate2},#{rate3},#{rateSum})")
    void addUserRate(Assess assess);
    @Select("select * from tbl_assess")
    List<Assess> getRateList();
}
