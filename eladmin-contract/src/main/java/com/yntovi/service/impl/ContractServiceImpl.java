package com.yntovi.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yntovi.domain.File;
import com.yntovi.domain.LocalStorage;
import com.yntovi.exception.EntityExistException;
import com.yntovi.service.mapper.FileMapper;
import com.yntovi.utils.R;
import lombok.AllArgsConstructor;
import com.yntovi.base.PageInfo;
import com.yntovi.base.QueryHelpMybatisPlus;
import com.yntovi.base.impl.CommonServiceImpl;
import com.yntovi.utils.ConvertUtil;
import com.yntovi.utils.PageUtil;
import com.yntovi.domain.Contract;
import com.yntovi.service.ContractService;
import com.yntovi.service.dto.ContractDto;
import com.yntovi.service.dto.ContractQueryParam;
import com.yntovi.service.mapper.ContractMapper;
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
* @date 2022-01-18
*/
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = ContractService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ContractServiceImpl extends CommonServiceImpl<ContractMapper, Contract> implements ContractService {

    // private final RedisUtils redisUtils;
    private final ContractMapper contractMapper;
    private final FileMapper fileMapper;

    @Override
    public PageInfo<ContractDto> queryAll(ContractQueryParam query, Pageable pageable) {
        IPage<Contract> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<Contract> page = contractMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, ContractDto.class);
    }

    @Override
    public List<ContractDto> queryAll(ContractQueryParam query){
        return ConvertUtil.convertList(contractMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), ContractDto.class);
    }

    @Override
    public Contract getById(Long id) {
        return contractMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public ContractDto findById(Long id) {
        return ConvertUtil.convert(getById(id), ContractDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R insert(ContractDto resources) {
        Contract entity = ConvertUtil.convert(resources, Contract.class);
        // 合同编号自动生成
        String s = UUID.randomUUID().toString();
        entity.setNo(s);
        Long insert = Long.valueOf(contractMapper.insert(entity));   // 获取合同编号
        //2. 保存附件
        if (ObjectUtil.isNull(resources.getFiles())) return R.ok();
        for (LocalStorage localStorage : resources.getFiles()) {
            File file = new File();
            file.setStorageId(localStorage.getId());
            file.setRelationId(entity.getContractId()); // 合同id
            fileMapper.insert(file);
        }
//      3. 添加 应收应付项

//      4.添加 合同协助人
        return R.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(ContractDto resources){
        Contract entity = ConvertUtil.convert(resources, Contract.class);
        int ret = contractMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        return contractMapper.deleteBatchIds(ids);
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
    public void download(List<ContractDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (ContractDto contract : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("合同名称", contract.getName());
              map.put("合同编号", contract.getNo());
              map.put("合同甲方", contract.getPartyA());
              map.put("甲方负责人", contract.getChargeA());
              map.put("合同乙方", contract.getPartyB());
              map.put("乙方负责人", contract.getChargeB());
              map.put("合同类型（0 合同类型a 1 合同类型b 2 合同类型c ……）", contract.getType());
              map.put("部门/项目部", contract.getDept());
              map.put("合同收付类型 （  0 付款 1 收款 2无）", contract.getRecPay());
              map.put("合同金额/元", contract.getAmount());
              map.put("合同状态（0 拟稿中 1 履行中 2 已完成 3 已作废 4 已归档）", contract.getStatus());
              map.put("签订日期", contract.getSignTime());
              map.put("合同生效日期", contract.getEffectTime());
              map.put("合同结束日期", contract.getEndTime());
              map.put("关键条款", contract.getKeyTerms());
              map.put("备注", contract.getComment());
              map.put("排序", contract.getSort());
              map.put("置顶 (0 否 1 是)", contract.getTop());
              map.put("逻辑删除", contract.getDeleted());
              map.put("版本", contract.getVersion());
              map.put("创建者", contract.getCreateBy());
              map.put("创建部门", contract.getCreateDeptBy());
              map.put("更新者", contract.getUpdateBy());
              map.put("创建日期", contract.getCreateTime());
              map.put("更新时间", contract.getUpdateTime());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
