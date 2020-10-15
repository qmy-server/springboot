package com.qmy.controller.send;

import com.google.gson.Gson;
import com.qmy.utils.HttpSendUntils;
import com.qmy.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Create by qiemengyan on 2019/5/22
 */
@RestController
@RequestMapping("/send")
public class SendDataController {
    @Autowired
    private Gson gson;
    @Value("${httpUrl_send}")
    private String url;
    //预警信息下发指令接口
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String sendPost(@RequestBody Map<String, String> map) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("data", map.get("type"));
        params.add("data2", map.get("place"));
        return HttpSendUntils.sendPostRequest(url, params);
    }

    //模拟接收下发指令数据
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public String receivePost(HttpServletRequest request) {
        //以表单形式下发参数
        System.out.println(request.getParameter("data"));
        System.out.println(request.getHeader("Authorization"));
        //以json形式下发参数
       /* String param= null;
        try {
            BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            param=responseStrBuilder.toString();
            Map<String, Object> map = new HashMap<String, Object>();
            map=gson.fromJson(param,map.getClass());
            List<String> list=new ArrayList<>();
            list=(List)map.get("data");
            System.out.println(list.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return gson.toJson(MapUtils.success("success"));

    }
}
