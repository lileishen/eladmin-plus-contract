package com.yntovi.service;

import com.yntovi.base.PageInfo;
import com.yntovi.base.CommonService;
import com.yntovi.domain.File;
import com.yntovi.domain.LocalStorage;
import com.yntovi.service.dto.FileDto;
import com.yntovi.service.dto.FileQueryParam;
import com.yntovi.utils.R;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author Li_sanqi
* @date 2022-01-13
*/
public interface FileService extends CommonService<File>  {

    static final String CACHE_KEY = "file";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<FileDto>
    */
    PageInfo<FileDto> queryAll(FileQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<FileDto>
    */
    List<FileDto> queryAll(FileQueryParam query);

    File getById(Long id);
    FileDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    int insert(FileDto resources);
    int updateById(FileDto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    R removeByStorageId(Long id);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<FileDto> all, HttpServletResponse response) throws IOException;
}
