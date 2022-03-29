package com.yntovi.service;

import com.yntovi.base.PageInfo;
import com.yntovi.base.CommonService;
import com.yntovi.domain.Contract;
import com.yntovi.service.dto.ContractDto;
import com.yntovi.service.dto.ContractQueryParam;
import com.yntovi.utils.R;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author Li_sanqi
* @date 2022-01-18
*/
public interface ContractService extends CommonService<Contract>  {

    static final String CACHE_KEY = "contract";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<ContractDto>
    */
    PageInfo<ContractDto> queryAll(ContractQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<ContractDto>
    */
    List<ContractDto> queryAll(ContractQueryParam query);

    Contract getById(Long id);
    ContractDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    R insert(ContractDto resources);
    int updateById(ContractDto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<ContractDto> all, HttpServletResponse response) throws IOException;
}
