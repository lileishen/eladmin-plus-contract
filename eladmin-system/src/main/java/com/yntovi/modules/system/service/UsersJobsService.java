package com.yntovi.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yntovi.base.CommonService;
import com.yntovi.modules.system.domain.UsersJobs;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-25
*/
public interface UsersJobsService extends CommonService<UsersJobs> {
    List<Long> queryUserIdByJobId(Long id);
    List<Long> queryJobIdByUserId(Long id);
    boolean removeByUserId(Long id);
    boolean removeByJobId(Long id);
}
