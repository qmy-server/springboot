package com.qmy.services.mytest;

import com.qmy.domain.mytest.Assess;
import com.qmy.domain.mytest.Product;
import com.qmy.domain.mytest.VideoList;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ResourceService {
    void addProduct(Product pro);

    String getProductPath(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception;

    String getHtmlPath(String fileName);

    List<VideoList> getVideoMenuList();

    String addUserRate(Assess assess);

    List<Assess> getRateList();
}
