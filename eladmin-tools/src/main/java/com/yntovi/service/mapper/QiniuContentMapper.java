package com.yntovi.service.mapper;

import com.yntovi.base.CommonMapper;
import com.yntovi.domain.QiniuContent;
import org.springframework.stereotype.Repository;

/**
* @author jinjin
* @date 2020-09-27
*/
@Repository
public interface QiniuContentMapper extends CommonMapper<QiniuContent> {

    QiniuContent findByKey(String key);
}
