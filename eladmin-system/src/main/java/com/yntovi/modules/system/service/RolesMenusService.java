package com.yntovi.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yntovi.base.CommonService;
import com.yntovi.modules.system.domain.RolesMenus;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-25
*/
public interface RolesMenusService extends CommonService<RolesMenus> {
    List<Long> queryMenuIdByRoleId(Long id);
    List<Long> queryRoleIdByMenuId(Long id);
    boolean removeByRoleId(Long id);
    boolean removeByMenuId(Long id);
}
