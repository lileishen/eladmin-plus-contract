package com.yntovi.service;

import com.yntovi.base.PageInfo;
import com.yntovi.base.CommonService;
import com.yntovi.domain.RpItem;
import com.yntovi.service.dto.RpItemDto;
import com.yntovi.service.dto.RpItemQueryParam;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
* @author Li_sanqi
* @date 2022-01-20
*/
public interface RpItemService extends CommonService<RpItem>  {

    static final String CACHE_KEY = "rpItem";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<RpItemDto>
    */
    PageInfo<RpItemDto> queryAll(RpItemQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<RpItemDto>
    */
    List<RpItemDto> queryAll(RpItemQueryParam query);

    RpItem getById(Long id);
    RpItemDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    int insert(RpItemDto resources);
    int updateById(RpItemDto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

//    下载应收应付项模版
    void downloadDemo(List<RpItemDto> all,HttpServletResponse response,String type) throws IOException;


    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<RpItemDto> all, HttpServletResponse response) throws IOException;

}
