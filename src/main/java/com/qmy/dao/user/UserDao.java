package com.qmy.dao.user;



import com.qmy.domain.user.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserDao")
public interface UserDao {
    @Select("SELECT * FROM tbl_user")
    List<UserInfo> getObject();
    @Select("<script>SELECT * FROM tbl_user WHERE 1=1" +
            "<if test=\"id!=null and id!=''\">\n" +
            "AND id=#{id}\n" +
            "</if>\n" +
            "<if test=\"type!=null and type!=''\">\n" +
            "type=#{type},\n" +
            "</if>\n" +
            "<if test=\"username!=null and username!=''\">\n" +
            "AND username=#{username}\n" +
            "</if>\n" +
            "<if test=\"password!=null and password!=''\">\n" +
            "AND password=#{password}\n" +
            "</if>\n" +
            "<if test=\"age!=null and age!=''\">\n" +
            "AND age=#{age}\n" +
            "</if>\n" +
            "<if test=\"phone!=null and phone!=''\">\n" +
            "AND phone=#{phone}\n" +
            "</if>\n" +
            "<if test=\"name!=null and name!=''\">\n" +
            "AND name=#{name}\n" +
            "</if>\n"+
            "<if test=\"uuid!=null and uuid!=''\">\n" +
            "AND uuid=#{uuid}\n" +
            "</if>\n"+
    "</script>")
    UserInfo getJwtUser(UserInfo userInfo);
    @Insert("INSERT INTO tbl_user (username,type,password,age,phone,name,uuid) values (#{username},#{type},#{password},#{age},#{phone},#{name},#{uuid})")
    void addRegister(UserInfo register);
    @Update("UPDATE tbl_user set picture=#{picture} where id=#{id}")
     void updateUserPicture(UserInfo userInfo);
    @Select("SELECT * FROM tbl_user where id=#{id}")
     UserInfo getUserPicture(Integer id);
    @Update("<script> UPDATE tbl_user " +
            "<trim prefix=\"set\" suffixOverrides=\",\">" +
            "<if test=\"username!=null and username!=''\">\n" +
            "username=#{username},\n" +
            "</if>\n" +
            "<if test=\"password!=null and password!=''\">\n" +
            "password=#{password},\n" +
            "</if>\n" +
            "<if test=\"type!=null and type!=''\">\n" +
            "type=#{type},\n" +
            "</if>\n" +
            "<if test=\"age!=null and age!=''\">\n" +
            "age=#{age},\n" +
            "</if>\n" +
            "<if test=\"phone!=null and phone!=''\">\n" +
            "phone=#{phone},\n" +
            "</if>\n" +
            "<if test=\"name!=null and name!=''\">\n" +
            "name=#{name},\n" +
            "</if>\n" +
            "</trim>"+
            " WHERE id=#{id}</script>")
    int updateUserInfo(UserInfo userInfo);
    @Select("<script>select * from tbl_user where uuid=#{uuid}</script>")
    UserInfo getUserByUUID(String uuid);
}
