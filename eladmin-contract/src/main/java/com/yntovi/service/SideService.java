package com.yntovi.service;

import com.yntovi.base.PageInfo;
import com.yntovi.base.CommonService;
import com.yntovi.domain.Side;
import com.yntovi.service.dto.SideDto;
import com.yntovi.service.dto.SideQueryParam;
import com.yntovi.utils.R;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
* @author Li_sanqi
* @date 2022-01-10
*/
public interface SideService extends CommonService<Side>  {

    static final String CACHE_KEY = "side";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<SideDto>
    */
    PageInfo<SideDto> queryAll(SideQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<SideDto>
    */
    List<SideDto> queryAll(SideQueryParam query);

    Side getById(Long id);
    SideDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    R insert(SideDto resources);
    int updateById(SideDto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
     void download(List<SideDto> all, HttpServletResponse response) throws IOException;

//    IPage<Side> page1(IPage<Side> page, Side side);
    PageInfo<SideDto> page1(SideQueryParam query, Pageable pageable);

    /**
       * 查询所有数据不分页
       * @param query 条件参数
       * @return List<SideDto>
       */
     List<SideDto> queryAll1(SideQueryParam query);

    /**
     * 查询所有数据
     * @return
     */
     List<SideDto> selectlist();
}
