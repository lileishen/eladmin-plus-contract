package com.yntovi.rest;

import cn.hutool.core.util.ObjectUtil;
import com.yntovi.annotation.Log;
import com.yntovi.domain.AlipayConfig;
import com.yntovi.domain.WeixinConfig;
import com.yntovi.domain.vo.WeixinVo;
import com.yntovi.service.WeixinConfigService;
import com.yntovi.service.dto.WeixinConfigDto;
import com.yntovi.service.dto.WeixinConfigQueryParam;
import com.yntovi.utils.R;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.Set;

/**
 * @author li_sanqi
 * @date 2022-01-05
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "工具：企业微信配置管理")
@RequestMapping("/api/tools/weixin")
public class WeixinConfigController {

    private final WeixinConfigService weixinConfigService;

    @PutMapping
    @Log("配置企业微信配置")
    @ApiOperation("配置企业微信配置")
    public R update(@Validated @RequestBody WeixinConfig weixinConfig) throws Exception {
        return weixinConfigService.config(weixinConfig);
    }

    @GetMapping
    @Log("查看企业微信配置")
    @ApiOperation("查看企业微信配置")
    public R get() {
        WeixinConfig weixinConfig = weixinConfigService.find();
        return R.ok(ObjectUtil.isNotNull(weixinConfig) ? weixinConfig : new WeixinConfig());
    }

    @PostMapping
    @Log("企业微信发送消息配置")
    @ApiOperation("发送消息测试")
    public R sendMsg(@Validated @RequestBody WeixinVo weixinVo) {
        weixinConfigService.sendMsg(weixinVo);
        return R.ok();
    }



    /*
    @Log("导出工具：企业微信配置")
    @ApiOperation("导出工具：企业微信配置")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('weixinConfig:list')")
    public void download(HttpServletResponse response, WeixinConfigQueryParam query) throws IOException {
        weixinConfigService.download(weixinConfigService.queryAll(query), response);
    }*/

}
