package com.yupi.yupiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SignUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yupi.yupiclientsdk.model.User;



import java.util.HashMap;
import java.util.Map;

import static com.yupi.yupiclientsdk.utils.SignUtils.getSign;


/**
 * 调用第三方接口客户端
 *
 * @Author lhj
 */

public class YupiClient {
    private String accessKey;
    private String secretKey;

    private Map<String,String> getHeaderMap(String body)
    {
        Map<String,String> hashMap=new HashMap<>();
        hashMap.put("accessKey",accessKey);
        //一定不要发送给后台
      //  hashMap.put("secretKey",secretKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body",body);

        hashMap.put("timeStamp", String.valueOf(System.currentTimeMillis()/1000));
        hashMap.put("sign",getSign(body,secretKey));
        return hashMap;
    }


    public YupiClient() {
    }

    public YupiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

   private static final String GATEWAY_HOST="http://localhost:8090";

    public String getNameByGet(String name) {
        //可以单都传入http参数，遮掩参数会自动url编码，拼接在url中
        HashMap<String,Object> paramMap=new HashMap<>();
        paramMap.put("name",name);
        String result= HttpUtil.get(GATEWAY_HOST+"/api/name/",paramMap);
        System.out.println(result);
        return result;

    }


    public String getNameByPost( String name) {
        //可以单都传入http参数，遮掩参数会自动url编码，拼接在url中
        HashMap<String,Object> paramMap=new HashMap<>();
        paramMap.put("name",name);
        String result= HttpUtil.post(GATEWAY_HOST+"/api/name/",paramMap);
        System.out.println(result);
        return result;
    }

    public String getNameByUrlPost(User user) {
        //可以单都传入http参数，遮掩参数会自动url编码，拼接在url中
        String json= JSONUtil.toJsonStr(user);

        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST+"/api/name/user")
                .addHeaders(getHeaderMap(json))
                .body(json).execute();
           String result=httpResponse.body();

        return result;
    }
}
