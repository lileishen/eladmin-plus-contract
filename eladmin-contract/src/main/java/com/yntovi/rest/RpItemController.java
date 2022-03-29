package com.yntovi.rest;

import com.yntovi.annotation.AnonymousAccess;
import com.yntovi.annotation.Log;
import com.yntovi.domain.RpItem;
import com.yntovi.service.RpItemService;
import com.yntovi.service.dto.RpItemDto;
import com.yntovi.service.dto.RpItemQueryParam;
//import com.yntovi.utils.ExcelUtil;
import com.yntovi.utils.ExcelUtil;
import com.yntovi.utils.MessageUtils;
import com.yntovi.utils.R;
import com.yntovi.utils.StringUtils;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Li_sanqi
 * @date 2022-01-20
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "合同：应收应付项管理")
@RequestMapping("/api/rpItem")
public class RpItemController {

    private final RpItemService rpItemService;

    @GetMapping
    @Log("查询合同：应收应付项")
    @ApiOperation("查询合同：应收应付项")
    @PreAuthorize("@el.check('rpItem:list')")
    public ResponseEntity query(RpItemQueryParam query, Pageable pageable) {
        return new ResponseEntity<>(rpItemService.queryAll(query, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增合同：应收应付项")
    @ApiOperation("新增合同：应收应付项")
    @PreAuthorize("@el.check('rpItem:add')")
    public ResponseEntity create(@Validated @RequestBody RpItemDto resources) {
        return new ResponseEntity<>(rpItemService.insert(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改合同：应收应付项")
    @ApiOperation("修改合同：应收应付项")
    @PreAuthorize("@el.check('rpItem:edit')")
    public ResponseEntity update(@Validated @RequestBody RpItemDto resources) {
        rpItemService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除合同：应收应付项")
    @ApiOperation("删除合同：应收应付项")
    @PreAuthorize("@el.check('rpItem:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        rpItemService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Log("导出合同：下载应收应付项模版")
    @ApiOperation("导出合同：下载应收应付项模版")
    @GetMapping(value = "/importTemplate/{type}")
    @AnonymousAccess
    public R importTemplate(@PathVariable String type, HttpServletResponse response) throws IOException {
        ExcelUtil<RpItem> util = new ExcelUtil<>(RpItem.class);
        return util.importTemplateExcel("应收应付项模版");
    }

    /**
     * 导入应收应付项数据，不插入数据库
     * @param file
     * @return
     * @throws Exception
     */
    @Log("导出合同：导入应收应付项数据")
    @ApiOperation("导出合同：导入应收应付项数据")
    @RequestMapping(value = "/importData")
    public R importData(MultipartFile file) throws Exception {
        ExcelUtil<RpItem> util = new ExcelUtil<RpItem>(RpItem.class);
        List<RpItem> rpItemList = util.importExcel(file.getInputStream());
        return R.ok(rpItemList);
    }


//    @Log("导出合同：应收应付项")
//    @ApiOperation("导出合同：应收应付项")
//    @GetMapping(value = "/download")
//    @PreAuthorize("@el.check('rpItem:list')")
//    public void download(HttpServletResponse response, RpItemQueryParam query) throws IOException {
//        rpItemService.download(, response);
//    }

}
