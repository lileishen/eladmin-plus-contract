package com.yntovi.service.mapper;

import com.yntovi.base.CommonMapper;
import com.yntovi.domain.QiniuConfig;
import org.springframework.stereotype.Repository;

/**
* @author jinjin
* @date 2020-09-27
*/
@Repository
public interface QiniuConfigMapper extends CommonMapper<QiniuConfig> {

    int updateType(String type);
}
