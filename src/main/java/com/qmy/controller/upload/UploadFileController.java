package com.qmy.controller.upload;

import com.google.gson.Gson;
import com.qmy.domain.upload.UploadLog;
import com.qmy.domain.user.UserInfo;
import com.qmy.services.upload.UploadService;
import com.qmy.services.user.UserService;
import com.qmy.utils.FastDFSClient;
import org.csource.common.MyException;
import org.csource.fastdfs.TrackerGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by qiemengyan on 2019/5/21
 */
@RestController
@RequestMapping("/upload_file")
public class UploadFileController {
     @Autowired
     private UserService userService;
     @Autowired
     private UploadService uploadService;
    @Value("${fdfs.tracker_server1}")
    private String tracker_server1;
    @Value("${fdfs.tracker_server2}")
    private String tracker_server2;
    @Value("${fdfs.connect_timeout}")
    private int connect_timeout;
    @Value("${fdfs.network_timeout}")
    private int network_timeout;
    @Value("${fdfs.charset}")
    private String charset;
    /**
     * @apiGroup CCF
     * @api {post} /upload_file 非结构化数据上传接口
     * @apiDescription 非结构化数据上传接口
     * @apiHeader Authorization UUID(接入权限认证)
     * @apiHeader Content-Type multipart/form-data(传输数据类型)
     * @apiParam {File} file 图片文件
     * @apiSuccess (正常返回) {Json} data  返回的具体消息 ({"fileUrI:"group1/M00/00/00/wKgAyVzjr2uAKsXhAADTKOaLeyk568.jpg"})
     * @apiError (错误返回) {String} data  错误原因 ("error reason")
     */

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> resultMap1 = new HashMap<>();
        Gson gson = new Gson();
        // 获取文件后缀名称
        String[] extName = file.getOriginalFilename().split("\\.");
        // 配置fastdfs 获取参数g_tracker_group
        String[] szTrackerServers = {tracker_server1,tracker_server2};
        InetSocketAddress[] tracker_servers = new InetSocketAddress[szTrackerServers.length];
        for(int i = 0; i < szTrackerServers.length; ++i) {
            String[] parts = szTrackerServers[i].split("\\:", 2);
            if(parts.length != 2) {
                throw new MyException("the value of item \"tracker_server\" is invalid, the correct format is host:port");
            }
            tracker_servers[i] = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
        }
        TrackerGroup g_tracker_group = new TrackerGroup(tracker_servers);
        // 配置fastdfs 获取参数g_connect_timeout
        int g_connect_timeout = connect_timeout;
        g_connect_timeout *= 1000;
        // 配置fastdfs 获取参数g_network_timeout
        int g_network_timeout = network_timeout;
        network_timeout *= 1000;
        // 配置fastdfs 获取参数g_charset
        String g_charset =  charset;
        // 初始化fastdfs相关参数
        FastDFSClient client = new FastDFSClient(g_tracker_group,g_connect_timeout,g_network_timeout,g_charset);
        // 上传文件
        String result = client.uploadFile(file.getBytes(),extName[1]);

        //插入日志
        UserInfo user=this.userService.getUserByUUID(request.getHeader("Authorization"));
        UploadLog uploadLog=new UploadLog();
        uploadLog.setName(user.getName());
        uploadLog.setPort("非结构化数据上报接口");
        uploadLog.setType("未知");
        uploadLog.setPlace("未知");
        uploadLog.setData("文件");
        this.uploadService.add(uploadLog);
        resultMap1.put("imgUrl",result);

        resultMap1.put("fileUrI",result);

        resultMap.put("data",resultMap1);
        return gson.toJson(resultMap);
    }
}
