package com.qmy.services.user;


import com.qmy.domain.user.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserInfo> getObject();

    UserInfo getJwtUser(UserInfo userInfo);

    String addRegister(UserInfo register);

    String updateUserPicture(MultipartFile file, HttpServletRequest request) throws IOException;

    Object getUserPicture(String id, HttpServletResponse response) throws IOException;

    int updateUserInfo(UserInfo userInfo);

    int updatePassword(Map<String,Object> map);

    boolean checkPassword(String password);

    UserInfo getUserByUUID(String uuid);
}
