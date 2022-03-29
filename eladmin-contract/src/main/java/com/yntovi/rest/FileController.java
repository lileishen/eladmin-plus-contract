package com.yntovi.rest;
import com.yntovi.annotation.AnonymousAccess;
import com.yntovi.annotation.Log;
import com.yntovi.domain.LocalStorage;
import com.yntovi.service.FileService;
import com.yntovi.service.LocalStorageService;
import com.yntovi.service.dto.FileDto;
import com.yntovi.utils.R;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
* @author Li_sanqi
* @date 2022-01-11
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "文件附件上传")
@RequestMapping("/api/cm/file")
public class FileController {
    private final LocalStorageService localStorageService;
    private final FileService fileService;
    @ApiOperation("上传文件")
    @PostMapping("/upload")
    @AnonymousAccess
    public R upload(@RequestParam("file") MultipartFile file) {
        return R.ok(localStorageService.create(file.getOriginalFilename(),file));
    }


       @ApiOperation("删除文件")
       @DeleteMapping("/{id}")
       @AnonymousAccess
       public R upload(@PathVariable Long id) {
           return R.ok(fileService.removeByStorageId(id));
       }

}
