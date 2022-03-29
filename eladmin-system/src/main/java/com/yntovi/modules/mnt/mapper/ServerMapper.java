package com.yntovi.modules.mnt.mapper;

import com.yntovi.base.CommonMapper;
import com.yntovi.modules.mnt.domain.Server;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-27
*/
@Repository
public interface ServerMapper extends CommonMapper<Server> {
    List<Server> selectAllByDeployId(Long id);
}
