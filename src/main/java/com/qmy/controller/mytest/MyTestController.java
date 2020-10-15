package com.qmy.controller.mytest;

import com.google.gson.Gson;
import com.qmy.domain.mytest.Assess;
import com.qmy.domain.mytest.Product;
import com.qmy.domain.mytest.VideoList;
import com.qmy.services.mytest.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")

public class MyTestController {

    @Autowired
    private ResourceService resourceService;

    //获取视频产品列表
    @RequestMapping(value = "/productVideoList",method = RequestMethod.GET)
    public List<VideoList> getVideoMenuList(){
        List<VideoList> videoLists=this.resourceService.getVideoMenuList();
        return videoLists;
    }
    //添加产品基本信息
    @RequestMapping(value = "/productAdd",method = RequestMethod.POST)
    public String  productAdd(@RequestBody Product pro){
        this.resourceService.addProduct(pro);
        return "OK";
    }
    //上传产品介绍文件
    //将doc文件装为HTML文件，并将doc文件和HTML文件都转存到服务器，返回文件路径
    @RequestMapping(value = "/addFile",method = RequestMethod.POST)
    public String  addFile(@RequestBody MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws  Exception{
        String path=this.resourceService.getProductPath(file,request,response);
        String fileName=file.getOriginalFilename().substring(0,file.getOriginalFilename().length()-4);
        Map<String,String> map=new HashMap<>();
        map.put("path",path);
        map.put("fileName",fileName);
        Gson gson=new Gson();
        return gson.toJson(map);
    }
    //返回doc转换之后html文件路径（产品介绍）
    @RequestMapping(value="getHtmlPath",method = RequestMethod.POST)
    public String getHtmlPath(@RequestBody Map<String,String> map){
         return this.resourceService.getHtmlPath(map.get("name"));
    }
    //用户添加评价
    @RequestMapping(value = "/addUserRate", method = RequestMethod.POST)
    public String addUserRate(@RequestBody Assess access){
        return this.resourceService.addUserRate(access);
    }
    //获取评价列表
    @RequestMapping(value = "/getRateList",method = RequestMethod.GET)
    public List<Assess> getRateList(){
        return this.resourceService.getRateList();
    }
}
