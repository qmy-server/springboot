package com.qmy.services.user.impl;




import com.qmy.dao.user.UserDao;
import com.qmy.domain.user.UserInfo;
import com.qmy.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/*两种方法都可以进行数据库切换。
       第一种根据注解，手动标注;
       第二种根据方法名称选择数据库;
       写操作全部在主数据库ds_master；读操作全部在从数据库ds_slave*/
@Service("UserService")
public class IUserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    //根据用户uuid判断用户是否存在
    @Override
    public UserInfo getUserByUUID(String uuid) {
        return this.userDao.getUserByUUID(uuid);
    }

    //注册服务
    @Override
    public String addRegister(UserInfo register) {
        //查询人员是否已注册过
        UserInfo userInfo=new UserInfo();
        userInfo.setUsername(register.getUsername());
        UserInfo userInfo1=this.userDao.getJwtUser(userInfo);
        if(userInfo1!=null){
            return "EXIST";
        }else {
            register.setUuid("ccf_"+UUID.randomUUID().toString());
            this.userDao.addRegister(register);
            return "OK";
        }
    }

    @Override
    public boolean checkPassword(String password) {
        if(password.equals("111111")) {
            return true;
        }else{
            return false;
        }
    }

    //获取个人照片
    @Override
    public Object getUserPicture(String ids, HttpServletResponse response) throws IOException{
        Integer id=Integer.parseInt(ids);
        UserInfo userInfo=this.userDao.getUserPicture(id);
        //判断照片是否为空
        if(userInfo.getPicture()!=null) {
            response.setContentType("image/jpeg");
            ServletOutputStream out = response.getOutputStream();
            out.write((byte[]) userInfo.getPicture());
            out.flush();
            out.close();
        }else{
            return null;
        }
        return "OK";
    }

    //人员图片上传
    @Override
    public String updateUserPicture(MultipartFile file, HttpServletRequest request)  throws IOException {
        InputStream imageByte=file.getInputStream();
        UserInfo userInfo=new UserInfo();
        userInfo.setId(Integer.parseInt(request.getParameter("id")));
        userInfo.setPicture(file.getBytes());
        this.userDao.updateUserPicture(userInfo);
        return "OK";
    }

    //查询个人信息
    @Override
    public UserInfo getJwtUser(UserInfo userInfo) {
        return this.userDao.getJwtUser(userInfo);
    }
    //获取人员列表
    @Override
    public List<UserInfo> getObject() {
        return this.userDao.getObject();
    }
    //修改密码
    @Override
    public int updatePassword(Map<String, Object> map) {
        UserInfo userInfo=new UserInfo();
        userInfo.setId(Integer.parseInt(map.get("id").toString()));
        UserInfo u=this.userDao.getJwtUser(userInfo);
        //如果旧密码输入正确，则更新新密码
        if(u.getPassword().equals(map.get("oldPass").toString())){
            userInfo.setPassword(map.get("newPass").toString());
            return this.userDao.updateUserInfo(userInfo);
        }else{
            return 501;
        }

    }

    //修改个人信息
    @Override
    public int updateUserInfo(UserInfo userInfo) {
        int resullt=this.userDao.updateUserInfo(userInfo);

        return resullt;

    }
}
