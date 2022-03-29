package com.yntovi.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yntovi.domain.WeixinConfig;
import com.yntovi.domain.vo.WeixinVo;
import com.yntovi.service.WeixinConfigService;
import com.yntovi.service.impl.WeixinConfigServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 获取企业微信token
 */

@Component
public class WeixinUtils {
    @Resource
    private  RedisUtils redisUtils;
    public String access_token;
    public  String SEND_MESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";

    /*
     * 测试企业微信是否成功
     * */
    public  JSONObject getToken(WeixinConfig weixinConfig) {
       String  get_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + weixinConfig.getCorpid().trim() + "&corpsecret=" + weixinConfig.getCorpsecret().trim();
//        System.out.println("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ww37fd21a628964cac&corpsecret=9FPFTCoWeCL5t_FEZe_M8mnkCkGP8OZ84oIBM-iywDQ".equals(get_token_url));
       //        String  get_token_url = " https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ww37fd21a628964cac&corpsecret=9FPFTCoWeCL5t_FEZe_M8mnkCkGP8OZ84oIBM-iywDQ "  ;
        String s = HttpUtil.createGet(get_token_url).execute().charset("utf-8").body();
        JSONObject jsonObject = JSONUtil.parseObj(s);
        return jsonObject;
    }

    /**
     * 将企业微信获取的token保存到redis中
     */

    public  WeixinConfig saveToken(WeixinConfig weixinConfig) {
        if(ObjectUtil.isNull(weixinConfig)){
            throw new RuntimeException("请先配置企业微信");
        }
        JSONObject jsonObject = getToken(weixinConfig);
        Integer errcode = (Integer) jsonObject.get("errcode");
        if (!Integer.valueOf("0").equals(errcode)) {
            throw new RuntimeException("获取企业微信token失败: 情况1：过于频繁请求 情况2：配置信息有误");
        }
        //清空
        redisUtils.del("weixin_access_token");
        weixinConfig.setToken((String) jsonObject.get("access_token"));
        redisUtils.set("weixin_access_token", weixinConfig);   // redis 添加企业微信访问token
        Integer expires_in = (Integer) jsonObject.get("expires_in");  // 设置企业微信token的有效期
        redisUtils.expire("weixin_access_token",expires_in);
        return weixinConfig;
    }



    //    发送应用消息
    public  boolean sendMessage(WeixinVo weixinVo,WeixinConfig weixinConfig) {
//        redis 获取企业微信token
        JSONObject obj = JSONUtil.createObj();
//        指定接收消息的成员，成员ID列表（多个接收者用‘|’分隔，最多支持1000个）。
//        特殊情况：指定为”@all”，则向该企业应用的全部成员发送
        String touser="";
        for (String to : weixinVo.getTos()) {
            touser+= to+"|";
        }
        obj.putOnce("touser",touser);
//        指定接收消息的部门，部门ID列表，多个接收者用‘|’分隔，最多支持100个。
//        当touser为”@all”时忽略本参数
        obj.putOnce("toparty","");
//        指定接收消息的标签，标签ID列表，多个接收者用‘|’分隔，最多支持100个。
//        当touser为”@all”时忽略本参数
        obj.putOnce("totag","");
//        消息类型，此时固定为：text
        obj.putOnce("msgtype","text");
//        企业应用的id
        obj.putOnce("agentid",weixinConfig.getAgentid());
//        消息内容，最长不超过2048个字节
        JSONObject text = JSONUtil.createObj();
        text.putOnce("content",weixinVo.getContent());
        obj.putOnce("text",text);
//        表示是否是保密消息，0表示可对外分享，1表示不能分享且内容显示水印，默认为0
        obj.putOnce("safe",0);
//        表示是否开启id转译，0表示否，1表示是，默认0。仅第三方应用需要用到，企业自建应用可以忽略。
        obj.putOnce("enable_id_trans",0);
//        表示是否开启重复消息检查，0表示否，1表示是，默认0
        obj.putOnce("enable_duplicate_check",0);
//        表示是否重复消息检查的时间间隔，默认1800s，最大不超过4小时
        obj.putOnce("duplicate_check_interval",1800);
        String s1 = JSONUtil.toJsonStr(obj);
        String s = HttpUtil.post(SEND_MESSAGE_URL + weixinConfig.getToken().trim(), JSONUtil.toJsonStr(obj));

//        String json ="{\n" +                                                                                 
//                "   \"touser\" : \"GuLi|\",\n" +
//                "   \"toparty\" : \"\",\n" +
//                "   \"totag\" : \"\",\n" +
//                "   \"msgtype\" : \"text\",\n" +
//                "   \"agentid\" : 1000007,\n" +
//                "   \"text\" : {\n" +
//                "       \"content\" : \"测试数据3。\"\n" +
//                "   },\n" +
//                "   \"safe\":0,\n" +
//                "   \"enable_id_trans\": 0,\n" +
//                "   \"enable_duplicate_check\": 0,\n" +
//                "   \"duplicate_check_interval\": 1800\n" +
//                "}";
//
//        String post = HttpUtil.post(SEND_MESSAGE_URL + weixinConfig.getToken(), json);
        return true;
    }


    //    发送应用消息
    public  boolean sendMessagearkdown(WeixinVo weixinVo,WeixinConfig weixinConfig) {
//        redis 获取企业微信token
        JSONObject obj = JSONUtil.createObj();
//        指定接收消息的成员，成员ID列表（多个接收者用‘|’分隔，最多支持1000个）。
//        特殊情况：指定为”@all”，则向该企业应用的全部成员发送
        String touser="";
        for (String to : weixinVo.getTos()) {
            touser+= to+"|";
        }
        obj.putOnce("touser",touser);
//        指定接收消息的部门，部门ID列表，多个接收者用‘|’分隔，最多支持100个。
//        当touser为”@all”时忽略本参数
        obj.putOnce("toparty","");
//        指定接收消息的标签，标签ID列表，多个接收者用‘|’分隔，最多支持100个。
//        当touser为”@all”时忽略本参数
        obj.putOnce("totag","");
//        消息类型，此时固定为：text
        obj.putOnce("msgtype","markdown");
//        企业应用的id
        obj.putOnce("agentid",weixinConfig.getAgentid());
//        消息内容，最长不超过2048个字节
        JSONObject markdown = JSONUtil.createObj();
        markdown.putOnce("content",weixinVo.getContent());
        obj.putOnce("markdown",markdown);
//        表示是否是保密消息，0表示可对外分享，1表示不能分享且内容显示水印，默认为0
        obj.putOnce("safe",0);
//        表示是否开启id转译，0表示否，1表示是，默认0。仅第三方应用需要用到，企业自建应用可以忽略。
        obj.putOnce("enable_id_trans",0);
//        表示是否开启重复消息检查，0表示否，1表示是，默认0
        obj.putOnce("enable_duplicate_check",0);
//        表示是否重复消息检查的时间间隔，默认1800s，最大不超过4小时
        obj.putOnce("duplicate_check_interval",1800);
        String s1 = JSONUtil.toJsonStr(obj);
        String s = HttpUtil.post(SEND_MESSAGE_URL + weixinConfig.getToken(), JSONUtil.toJsonStr(obj));

//        String json ="{\n" +
//                "   \"touser\" : \"GuLi|\",\n" +
//                "   \"toparty\" : \"\",\n" +
//                "   \"totag\" : \"\",\n" +
//                "   \"msgtype\" : \"text\",\n" +
//                "   \"agentid\" : 1000007,\n" +
//                "   \"text\" : {\n" +
//                "       \"content\" : \"测试数据3。\"\n" +
//                "   },\n" +
//                "   \"safe\":0,\n" +
//                "   \"enable_id_trans\": 0,\n" +
//                "   \"enable_duplicate_check\": 0,\n" +
//                "   \"duplicate_check_interval\": 1800\n" +
//                "}";
//
//        String post = HttpUtil.post(SEND_MESSAGE_URL + weixinConfig.getToken(), json);
        return true;
    }


}
