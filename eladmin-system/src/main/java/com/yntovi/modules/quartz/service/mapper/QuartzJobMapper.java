package com.yntovi.modules.quartz.service.mapper;

import com.yntovi.base.CommonMapper;
import com.yntovi.modules.quartz.domain.QuartzJob;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-27
*/
@Repository
public interface QuartzJobMapper extends CommonMapper<QuartzJob> {

    List<QuartzJob> findByIsPauseIsFalse();
}
