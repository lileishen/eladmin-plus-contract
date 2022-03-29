package com.yntovi.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yntovi.base.CommonMapper;
import com.yntovi.domain.File;
import com.yntovi.domain.LocalStorage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Li_sanqi
* @date 2022-01-13
*/
@Repository
public interface FileMapper extends BaseMapper<File> {

    List<LocalStorage> listFileByrelationId(Long relationId);


}
