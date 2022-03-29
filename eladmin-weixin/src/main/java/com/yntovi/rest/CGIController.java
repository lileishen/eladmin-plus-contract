package com.yntovi.rest;
// 企业微信 通讯录模块

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yntovi.annotation.AnonymousAccess;
import com.yntovi.annotation.Log;
import com.yntovi.domain.WeixinConfig;
import com.yntovi.service.WeixinConfigService;
import com.yntovi.utils.R;
import com.yntovi.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/api/weixin")
@RequiredArgsConstructor
@Api(tags = "企业微信：通讯录管理")
public class CGIController {
    @Resource
    private WeixinConfigService weixinConfigService;
    @Resource
    private RedisUtils redisUtils;
    @Log("获取企业微信部门列表")
    @ApiOperation("企业微信部门列表获取")
    @GetMapping(value = "/department/list/{id}")
    @AnonymousAccess
    public R departmentList(@PathVariable Long id) throws Exception {
        WeixinConfig weixinConfig = weixinConfigService.getWeixinConfig();
        String url="https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token="+weixinConfig.getToken()+"&id="+id;
        String res = HttpUtil.get(url);//返回的部门列表
        JSONObject obj = JSONUtil.parseObj(res);
        Integer errcode = (Integer) obj.get("errcode");
        if(!Integer.valueOf("0").equals(errcode)) return R.failed(null,"配置文件有误");
        return R.ok(obj.get("department"),"获取部门列表成功");
    }

    @Log("获取企业微信部门成员列表")
    @ApiOperation("获取企业微信部门成员列表")
    @GetMapping(value = "/user/list/{department_id}/{fetch_child}")
    public R userList(@PathVariable Long department_id, @PathVariable String fetch_child) throws Exception {
        WeixinConfig weixinConfig = weixinConfigService.getWeixinConfig();
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=" + weixinConfig.getToken() + "&department_id=" + department_id + "&fetch_child=" + fetch_child;
        String res = HttpUtil.get(url);//
        JSONObject obj = JSONUtil.parseObj(res);
        Integer errcode = (Integer) obj.get("errcode");
        if (!Integer.valueOf("0").equals(errcode)) return R.failed(null, "配置文件有误");
        return R.ok(obj.get("userlist"), "获取成员列表成功");
    }
}
