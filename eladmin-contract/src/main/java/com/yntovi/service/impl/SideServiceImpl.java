package com.yntovi.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yntovi.domain.File;
import com.yntovi.domain.LocalStorage;
import com.yntovi.service.mapper.FileMapper;
import com.yntovi.utils.*;
import lombok.AllArgsConstructor;
import com.yntovi.base.PageInfo;
import com.yntovi.base.QueryHelpMybatisPlus;
import com.yntovi.base.impl.CommonServiceImpl;
import com.yntovi.domain.Side;
import com.yntovi.service.SideService;
import com.yntovi.service.dto.SideDto;
import com.yntovi.service.dto.SideQueryParam;
import com.yntovi.service.mapper.SideMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author Li_sanqi
 * @date 2022-01-10
 */
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = SideService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SideServiceImpl extends CommonServiceImpl<SideMapper, Side> implements SideService {

    // private final RedisUtils redisUtils;
    private final SideMapper sideMapper;
    private final FileMapper fileMapper;
    private final ChineseCharacterUtil characterUtil;

    @Override
    public PageInfo<SideDto> queryAll(SideQueryParam query, Pageable pageable) {
        IPage<Side> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<Side> page = sideMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, SideDto.class);
    }

    @Override
    public List<SideDto> queryAll(SideQueryParam query) {
        return ConvertUtil.convertList(sideMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), SideDto.class);
    }

    @Override
    public Side getById(Long id) {
        return sideMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public SideDto findById(Long id) {
        return ConvertUtil.convert(getById(id), SideDto.class);
    }

    /**
     * 添加合同相对方
     *
     * @param resources
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R insert(SideDto resources) {
        //TODO
        Side entity = ConvertUtil.convert(resources, Side.class);
//        1. 保存合同相对方信息
        entity.setNo(characterUtil.getUpperCase(entity.getName(), false)); // 提取编号
        int id = sideMapper.insert(entity);   // 合同向对方主键
//        2. 保存附件
        if (ObjectUtil.isNull(resources.getFiles())) return R.ok();
        for (LocalStorage localStorage : resources.getFiles()) {
            File file = new File();
            file.setStorageId(localStorage.getId());
            file.setRelationId(entity.getSideId());
            fileMapper.insert(file);
        }
        return R.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(SideDto resources) {
        Side entity = ConvertUtil.convert(resources, Side.class);
        entity.setNo(characterUtil.getUpperCase(entity.getName(), false)); // 提取编号
        int ret = sideMapper.updateById(entity);
        // delCaches(resources.id);
        if (ObjectUtil.isNull(resources.getFiles())) return ret;
        for (LocalStorage localStorage : resources.getFiles()) {
            File file = new File();
            file.setStorageId(localStorage.getId());
            file.setRelationId(entity.getSideId());
            fileMapper.insert(file);
        }
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids) {
        // delCaches(ids);
        return sideMapper.deleteBatchIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeById(Long id) {
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


    @Override
    public void download(List<SideDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SideDto side : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("相对方名称", side.getName());
            map.put("编号", side.getNo());
            map.put("类型", side.getTypeVal());
            map.put("联系人", side.getConPer());
            map.put("联系电话", side.getConTel());
            map.put("开户银行", side.getBank());
            map.put("银行账号", side.getBankAcc());
            map.put("地址", side.getAddress());
            map.put("详细地址", side.getAddrDetail());
            map.put("备注", side.getComment());
            map.put("状态", side.getEnabled());
            map.put("创建者", side.getCreateBy());
            map.put("创建部门", side.getCreateDeptBy());
            map.put("更新者", side.getUpdateBy());
            map.put("创建日期", side.getCreateTime());
            map.put("更新时间", side.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public PageInfo<SideDto> page1(SideQueryParam query, Pageable pageable) {
        IPage<Side> queryPage = PageUtil.toMybatisPage(pageable);
        QueryWrapper<Object> predicate = QueryHelpMybatisPlus.getPredicate(query);   //查询条件
        predicate.eq("deleted", 0);
        IPage<Side> page = sideMapper.selectPage1(queryPage, predicate);
        return ConvertUtil.convertPage(page, SideDto.class);
    }

    @Override
    public List<SideDto> queryAll1(SideQueryParam query) {
        return ConvertUtil.convertList(sideMapper.selectList1(QueryHelpMybatisPlus.getPredicate(query)), SideDto.class);
    }

    @Override
    public List<SideDto> selectlist() {
        return ConvertUtil.convertList(sideMapper.selectList(null), SideDto.class);
    }
}
