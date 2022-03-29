package com.yntovi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yntovi.utils.DateUtil;
import com.yntovi.utils.FileUtil;
import lombok.AllArgsConstructor;
import com.yntovi.base.PageInfo;
import com.yntovi.base.QueryHelpMybatisPlus;
import com.yntovi.base.impl.CommonServiceImpl;
import com.yntovi.utils.ConvertUtil;
import com.yntovi.utils.PageUtil;
import com.yntovi.domain.RpItem;
import com.yntovi.service.RpItemService;
import com.yntovi.service.dto.RpItemDto;
import com.yntovi.service.dto.RpItemQueryParam;
import com.yntovi.service.mapper.RpItemMapper;
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
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Li_sanqi
 * @date 2022-01-20
 */
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = RpItemService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RpItemServiceImpl extends CommonServiceImpl<RpItemMapper, RpItem> implements RpItemService {

    // private final RedisUtils redisUtils;
    private final RpItemMapper rpItemMapper;

    @Override
    public PageInfo<RpItemDto> queryAll(RpItemQueryParam query, Pageable pageable) {
        IPage<RpItem> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<RpItem> page = rpItemMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, RpItemDto.class);
    }

    @Override
    public List<RpItemDto> queryAll(RpItemQueryParam query) {
        return ConvertUtil.convertList(rpItemMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), RpItemDto.class);
    }

    @Override
    public RpItem getById(Long id) {
        return rpItemMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public RpItemDto findById(Long id) {
        return ConvertUtil.convert(getById(id), RpItemDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(RpItemDto resources) {
        RpItem entity = ConvertUtil.convert(resources, RpItem.class);
        return rpItemMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(RpItemDto resources) {
        RpItem entity = ConvertUtil.convert(resources, RpItem.class);
        int ret = rpItemMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids) {
        // delCaches(ids);
        return rpItemMapper.deleteBatchIds(ids);
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

    /*
        @Override
        public void download(List<RpItemDto> all, HttpServletResponse response) throws IOException {
          List<Map<String, Object>> list = new ArrayList<>();
          for (RpItemDto rpItem : all) {
            Map<String,Object> map = new LinkedHashMap<>();
                  map.put("合同档案id", rpItem.getContractId());
                  map.put("序号", rpItem.getNum());
                  map.put("编号", rpItem.getNo());
                  map.put("收付款项(预付款 进度款 质保金)", rpItem.getName());
                  map.put("收付条件", rpItem.getCondition());
                  map.put("收付比例", rpItem.getProportion());
                  map.put("应收付金额", rpItem.getAmount());
                  map.put("预计收付日期", rpItem.getEstimatedTime());
                  map.put("收付款项全称", rpItem.getNameFull());
                  map.put("逻辑删除", rpItem.getDeleted());
                  map.put("版本", rpItem.getVersion());
                  map.put("创建者", rpItem.getCreateBy());
                  map.put("创建部门", rpItem.getCreateDeptBy());
                  map.put("更新者", rpItem.getUpdateBy());
                  map.put("创建日期", rpItem.getCreateTime());
                  map.put("更新时间", rpItem.getUpdateTime());
            list.add(map);
          }
          FileUtil.downloadExcel(list, response);
        }*/

    /**
     * 下载应收应付项的模版
     *
     * @param type
     */
    @Override
    public void downloadDemo(List<RpItemDto> all,HttpServletResponse response, String type) throws IOException {
        all.add(
                new RpItemDto()
                        .setNum(1)
                        .setName("预付款")
                        .setCondition("合同签订后30天")
                        .setProportion(BigDecimal.valueOf(0.3))
                        .setAmount(BigDecimal.valueOf(50000))
                        .setEstimatedTime(null)
        );
        List<Map<String, Object>> list = new ArrayList<>();
                 for (RpItemDto rpItem : all) {
                   Map<String,Object> map = new LinkedHashMap<>();
                         map.put("序号", rpItem.getNum());
                         map.put("收付款项", rpItem.getName());
                         map.put("收付条件", rpItem.getCondition());
                         map.put("收付比例", rpItem.getProportion());
                         map.put("应收付金额", rpItem.getAmount());
                         map.put("预计收付日期", rpItem.getEstimatedTime());
                   list.add(map);
                 }
                 FileUtil.downloadExcel(list, response);
    }
}
