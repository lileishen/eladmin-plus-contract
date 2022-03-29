package com.yntovi.service;

import com.yntovi.base.PageInfo;
import com.yntovi.base.CommonService;
import com.yntovi.domain.ContractUsers;
import com.yntovi.service.dto.ContractUsersDto;
import com.yntovi.service.dto.ContractUsersQueryParam;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author Li_sanqi
* @date 2022-03-29
*/
public interface ContractUsersService extends CommonService<ContractUsers>  {

    static final String CACHE_KEY = "contractUsers";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<ContractUsersDto>
    */
    PageInfo<ContractUsersDto> queryAll(ContractUsersQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<ContractUsersDto>
    */
    List<ContractUsersDto> queryAll(ContractUsersQueryParam query);

    ContractUsers getById(Long id);
    ContractUsersDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    int insert(ContractUsersDto resources);
    int updateById(ContractUsersDto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<ContractUsersDto> all, HttpServletResponse response) throws IOException;
}
