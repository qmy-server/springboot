package com.qmy.services.mytest.impl;

import com.qmy.dao.mytest.ResourceDao;
import com.qmy.domain.mytest.Assess;
import com.qmy.domain.mytest.Product;
import com.qmy.domain.mytest.VideoList;
import com.qmy.services.mytest.ResourceService;
import com.qmy.utils.FileSaveUntils;
import com.qmy.utils.WordToHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

@Service("ResourceService")
public class IResourceServiceImpl implements ResourceService {
    //存入到ehcache.xml文件中缓存名称为qmy的内存中，key设为menu
    private static final String CACHE_KEY="'menu'";
    private static final String CACHE_NAME="qmy";

    @Autowired
    private ResourceDao resourceDao;

    //清除缓存 （插入、删除操作使用）
    //@CacheEvict(value=CACHE_NAME,key = CACHE_KEY)
    //更新缓存（更新操作使用）
    //@CachePut(value = CACHE_NAME,key = CACHE_KEY)
    //存入缓存 （查询操作时使用）
    @Cacheable(value =CACHE_NAME,key = CACHE_KEY)
    @Override
    public List<VideoList> getVideoMenuList() {
        System.out.println("走数据库！");
        return this.resourceDao.getVideoMenuList();
    }

    @Override
    public String getProductPath(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws  Exception{
        String path=this.resourceDao.getProductPath().replace("/",File.separator);
        //上传doc文件
        FileSaveUntils.saveFile(file.getInputStream(),path,file.getOriginalFilename());
        //去掉文件后缀，获取文件名称
        String fileName= null;
        fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().length()-4);
        //上传html文件
        WordToHtml.Word2003ToHtml(path,fileName,".doc");
        return path+ File.separator+fileName+".html";
    }
    @Override
    public void addProduct(Product pro) {
        this.resourceDao.addProduct(pro);
    }

    @Override
    public List<Assess> getRateList() {
        return this.resourceDao.getRateList();
    }

    @Override
    public String addUserRate(Assess assess) {
        float rateSum=(assess.getRate1()+assess.getRate2()+assess.getRate3())/3;
        assess.setRateSum(rateSum);
        this.resourceDao.addUserRate(assess);
        return null;
    }

    @Override
    public String getHtmlPath(String fileName) {
        String path=this.resourceDao.getHtmlPath();
        return path+File.separator+fileName+".html";
    }
}
