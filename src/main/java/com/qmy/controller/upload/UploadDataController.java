package com.qmy.controller.upload;

import com.google.gson.Gson;
import com.qmy.domain.upload.UploadLog;
import com.qmy.domain.user.UserInfo;
import com.qmy.services.upload.UploadService;
import com.qmy.services.user.UserService;
import com.qmy.utils.GsonTypeAdapter;
import com.qmy.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by qiemengyan on 2019/5/17
 */
@RestController
@RequestMapping("/upload_data")
public class UploadDataController {
    @Autowired
    private Gson gson;
    @Autowired
    private UserService userService;
    @Autowired
    private UploadService uploadService;

    /**
     * @apiGroup CCF
     * @api {post} /upload_data 结构化数据上报接口
     * @apiDescription 结构化数据上报接口
     * @apiHeader Authorization UUID(接入权限认证)
     * @apiHeader Content-Type application/json(传输数据类型)
     * @apiParam {String} type 信息类型 (人员类:"01",位置类:"02",物品类:"03",案(事)件类:"04",机构类:"05",日期/时间类:"06",公文类:"07",其他类:"08")
     * @apiParam {String} place 场所信息 (外滩:"Y01",虹桥枢纽:"Y02",静安电站:"Y03",金山化工:"Y04",人民广场:"Y05",一体化平台:"Y06")
     * @apiParam {DateTime} time 上报时间 (格式：2019-05-01 08:52:23)
     * @apiParam {Json} data  具体分析结果数据 ({"key1:"value1"，"key2":"value2"，"key3":"value3"})
     * @apiParamExample {Json} 示例
     * {
     * "type":"01",
     * "place":"Y04",
     * "time":"2019-05-01 08:52:23",
     * "data":{
     *     "key1":"value1",
     *     "key2":"value2",
     *     "key3":"value3",
     *     ......
     * }
     * }
     * @apiSuccess (正常返回) {Json} data  具体消息 ({"key1:"value1"，"key2":"value2"，"key3":"value3"})
     * @apiError (错误返回) {String} data  错误原因 ("error reason")
     */
    //预警信息上报接口
    @RequestMapping(method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public String HelloWord2(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        UserInfo user=this.userService.getUserByUUID(request.getHeader("Authorization"));
        Map<String, Object> map1 = GsonTypeAdapter.getGson().fromJson(map.get("data").toString(), HashMap.class);
        System.out.println(map.get("type") + "," + map.get("place") + "," + map.get("time") + "," + map.get("data")); //时间
        System.out.println(map1.toString());
        UploadLog uploadLog=new UploadLog();
        uploadLog.setName(user.getName());
        uploadLog.setPort("结构化数据上报接口");
        uploadLog.setType(map.get("type").toString());
        uploadLog.setPlace(map.get("place").toString());
        uploadLog.setData(map1.toString());
        uploadLog.setTime(Timestamp.valueOf(map.get("time").toString()));
        this.uploadService.add(uploadLog);
        return gson.toJson(MapUtils.success("success"));
    }




}
