package com.yntovi.rest;

import com.yntovi.annotation.AnonymousAccess;
import com.yntovi.annotation.Log;
import com.yntovi.domain.AlipayConfig;
import com.yntovi.utils.ChineseCharacterUtil;
import com.yntovi.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chineseCharacter")
@Api(tags = "工具：提取字符串")
public class ChineseCharacterController {
    private final ChineseCharacterUtil characterUtil;

    @Log("提取首字母")
    @ApiOperation("提取首字母")
    @GetMapping
    @AnonymousAccess
    public R updateConfig(String name) {
        String aCase = characterUtil.getUpperCase(name, false);
        return R.ok(aCase);
    }

}
