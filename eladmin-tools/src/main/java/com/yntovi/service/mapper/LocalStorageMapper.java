package com.yntovi.service.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yntovi.base.CommonMapper;
import com.yntovi.domain.LocalStorage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-27
*/
@Repository
public interface LocalStorageMapper extends BaseMapper<LocalStorage> {
    List<LocalStorage> listFileByrelationId(Long relationId);
}
