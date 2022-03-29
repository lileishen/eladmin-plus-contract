package com.yntovi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yntovi.domain.ContractUsers;
import com.yntovi.service.ContractUsersService;
import com.yntovi.service.dto.ContractUsersDto;
import com.yntovi.service.dto.ContractUsersQueryParam;
import com.yntovi.service.mapper.ContractUsersMapper;
import lombok.AllArgsConstructor;
import com.yntovi.base.PageInfo;
import com.yntovi.base.QueryHelpMybatisPlus;
import com.yntovi.base.impl.CommonServiceImpl;
import com.yntovi.utils.ConvertUtil;
import com.yntovi.utils.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import java.util.*;

/**
* @author Li_sanqi
* @date 2022-03-29
*/
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = ContractUsersService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ContractUsersServiceImpl extends CommonServiceImpl<ContractUsersMapper, ContractUsers> implements ContractUsersService {

    // private final RedisUtils redisUtils;
    private final ContractUsersMapper contractUsersMapper;

    @Override
    public PageInfo<ContractUsersDto> queryAll(ContractUsersQueryParam query, Pageable pageable) {
        IPage<ContractUsers> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<ContractUsers> page = contractUsersMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, ContractUsersDto.class);
    }

    @Override
    public List<ContractUsersDto> queryAll(ContractUsersQueryParam query){
        return ConvertUtil.convertList(contractUsersMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), ContractUsersDto.class);
    }

    @Override
    public ContractUsers getById(Long id) {
        return contractUsersMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public ContractUsersDto findById(Long id) {
        return ConvertUtil.convert(getById(id), ContractUsersDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(ContractUsersDto resources) {
        ContractUsers entity = ConvertUtil.convert(resources, ContractUsers.class);
        return contractUsersMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(ContractUsersDto resources){
        ContractUsers entity = ConvertUtil.convert(resources, ContractUsers.class);
        int ret = contractUsersMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        return contractUsersMapper.deleteBatchIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeById(Long id){
        Set<Long> set = new HashSet<>(1);
        set.add(id);
        return this.removeByIds(set);
    }

    /*
    private void delCaches(Long id) {
        redisUtils.delByKey(CACHE_KEY + "::id:", id);
    }

    private void delCaches(Set<Long> ids) {
        for (Long id: ids) {
            delCaches(id);
        }
    }*/

    /*
    @Override
    public void download(List<ContractUsersDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (ContractUsersDto contractUsers : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("逻辑删除", contractUsers.getDeleted());
              map.put("版本", contractUsers.getVersion());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
