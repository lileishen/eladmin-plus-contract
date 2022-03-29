package com.yntovi.rest;

import com.yntovi.annotation.AnonymousAccess;
import com.yntovi.annotation.Log;
import com.yntovi.domain.Contract;
import com.yntovi.service.ContractService;
import com.yntovi.service.dto.ContractDto;
import com.yntovi.service.dto.ContractQueryParam;
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
* @author Li_sanqi
* @date 2022-01-18
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "合同：合同档案管理")
@RequestMapping("/api/cm/contract")
public class ContractController {

    private final ContractService contractService;

    @GetMapping
    @Log("查询合同：合同档案")
    @ApiOperation("查询合同：合同档案")
    @PreAuthorize("@el.check('contract:list')")
    public ResponseEntity query(ContractQueryParam query, Pageable pageable){
        return new ResponseEntity<>(contractService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增合同：合同档案")
    @ApiOperation("新增合同：合同档案")
    @PreAuthorize("@el.check('contract:add')")
    public R create(@Validated @RequestBody ContractDto resources){
        return contractService.insert(resources);
    }

    @PutMapping
    @Log("修改合同：合同档案")
    @ApiOperation("修改合同：合同档案")
    @PreAuthorize("@el.check('contract:edit')")
    public ResponseEntity update(@Validated @RequestBody ContractDto resources){
        contractService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除合同：合同档案")
    @ApiOperation("删除合同：合同档案")
    @PreAuthorize("@el.check('contract:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        contractService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出合同：合同档案")
    @ApiOperation("导出合同：合同档案")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('contract:list')")
    public void download(HttpServletResponse response, ContractQueryParam query) throws IOException {
        contractService.download(contractService.queryAll(query), response);
    }*/


}
