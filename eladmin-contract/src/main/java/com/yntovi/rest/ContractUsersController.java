package com.yntovi.rest;
import com.yntovi.annotation.Log;
import com.yntovi.service.ContractUsersService;
import com.yntovi.service.dto.ContractUsersDto;
import com.yntovi.service.dto.ContractUsersQueryParam;
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
* @date 2022-03-29
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "合同协助人管理")
@RequestMapping("/api/contractUsers")
public class ContractUsersController {

    private final ContractUsersService contractUsersService;

    @GetMapping
    @Log("查询合同协助人")
    @ApiOperation("查询合同协助人")
    @PreAuthorize("@el.check('contractUsers:list')")
    public ResponseEntity query(ContractUsersQueryParam query, Pageable pageable){
        return new ResponseEntity<>(contractUsersService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增合同协助人")
    @ApiOperation("新增合同协助人")
    @PreAuthorize("@el.check('contractUsers:add')")
    public ResponseEntity create(@Validated @RequestBody ContractUsersDto resources){
        return new ResponseEntity<>(contractUsersService.insert(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改合同协助人")
    @ApiOperation("修改合同协助人")
    @PreAuthorize("@el.check('contractUsers:edit')")
    public ResponseEntity update(@Validated @RequestBody ContractUsersDto resources){
        contractUsersService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除合同协助人")
    @ApiOperation("删除合同协助人")
    @PreAuthorize("@el.check('contractUsers:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        contractUsersService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出合同协助人")
    @ApiOperation("导出合同协助人")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('contractUsers:list')")
    public void download(HttpServletResponse response, ContractUsersQueryParam query) throws IOException {
        contractUsersService.download(contractUsersService.queryAll(query), response);
    }*/

}
