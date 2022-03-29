package com.yntovi.modules.system.service.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yntovi.base.CommonMapper;
import com.yntovi.modules.system.domain.DictDetail;
import com.yntovi.modules.system.service.dto.DictDetailDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-24
*/
@Repository
public interface DictDetailMapper extends CommonMapper<DictDetail> {

    List<DictDetailDto> getDictDetailsByDictName(@Param("dictName") String dictName);
    IPage<DictDetailDto> getDictDetailsByDictName(@Param("dictName") String dictName, IPage<DictDetailDto> page);
}
