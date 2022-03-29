package com.yntovi.modules.system.service.mapper;

import com.yntovi.base.CommonMapper;
import com.yntovi.modules.system.domain.Job;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
* @author jinjin
* @date 2020-09-25
*/
@Repository
public interface JobMapper extends CommonMapper<Job> {
    @Select("select j.job_id as id, j.* from sys_job where job_id = #{id}")
    Job selectLink(Long id);
}
