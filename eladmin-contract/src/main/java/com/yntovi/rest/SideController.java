package com.yntovi.rest;

import com.yntovi.annotation.AnonymousAccess;
import com.yntovi.annotation.Log;
import com.yntovi.base.PageInfo;
import com.yntovi.domain.Side;
import com.yntovi.service.SideService;
import com.yntovi.service.dto.SideDto;
import com.yntovi.service.dto.SideQueryParam;
import com.yntovi.utils.R;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
* @author Li_sanqi
* @date 2022-01-10
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "合同：合同相对方管理")
@RequestMapping("/api/cm/side")
public class SideController {

    private final SideService sideService;

    @GetMapping
    @Log("查询合同相对方")
    @ApiOperation("查询合同相对方")
    @PreAuthorize("@el.check('side:list')")
    public ResponseEntity query(SideQueryParam query, Pageable pageable){
        return new ResponseEntity<>(sideService.page1(query, pageable),HttpStatus.OK);
    }

//    @GetMapping
//    @Log("查询合同：合同相对方")
//    @ApiOperation("查询合同：合同相对方")
//    @PreAuthorize("@el.check('side:list')")
//        public ResponseEntity query(SideQueryParam query, Pageable pageable){
//        return new ResponseEntity<>(sideService.queryAll(query,pageable),HttpStatus.OK);
//    }


    @PostMapping
    @Log("新增合同相对方")
    @ApiOperation("新增合同相对方")
    @PreAuthorize("@el.check('side:add')")
    public R create(@Validated @RequestBody SideDto resources){
        return sideService.insert(resources);
    }

    @PutMapping
    @Log("修改合同相对方")
    @ApiOperation("修改合同相对方")
    @PreAuthorize("@el.check('side:edit')")
    public ResponseEntity update(@Validated @RequestBody SideDto resources){
        sideService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除合同相对方")
    @ApiOperation("删除合同相对方")
    @PreAuthorize("@el.check('side:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        sideService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Log("导出合同相对方")
    @ApiOperation("导出合同相对方")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('side:list')")
    public void download(HttpServletResponse response, SideQueryParam query) throws IOException {
        sideService.download(sideService.queryAll1(query), response);
    }

    @GetMapping("/list")
    @Log("获取合同相对方列表")
    @ApiOperation("获取合同相对方列表")
    @AnonymousAccess
    public R list(){
        return R.ok(sideService.selectlist());
    }

}
