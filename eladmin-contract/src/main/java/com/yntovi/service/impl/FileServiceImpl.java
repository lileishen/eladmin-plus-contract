package com.yntovi.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yntovi.annotation.Query;
import com.yntovi.domain.LocalStorage;
import com.yntovi.service.mapper.LocalStorageMapper;
import com.yntovi.utils.R;
import lombok.AllArgsConstructor;
import com.yntovi.base.PageInfo;
import com.yntovi.base.QueryHelpMybatisPlus;
import com.yntovi.base.impl.CommonServiceImpl;
import com.yntovi.utils.ConvertUtil;
import com.yntovi.utils.PageUtil;
import com.yntovi.domain.File;
import com.yntovi.service.FileService;
import com.yntovi.service.dto.FileDto;
import com.yntovi.service.dto.FileQueryParam;
import com.yntovi.service.mapper.FileMapper;
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
* @date 2022-01-13
*/
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = FileService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FileServiceImpl extends CommonServiceImpl<FileMapper, File> implements FileService {

    // private final RedisUtils redisUtils;
    private final FileMapper fileMapper;
    private final LocalStorageMapper localStorageMapper;

    @Override
    public PageInfo<FileDto> queryAll(FileQueryParam query, Pageable pageable) {
        IPage<File> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<File> page = fileMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, FileDto.class);
    }

    @Override
    public List<FileDto> queryAll(FileQueryParam query){
        return ConvertUtil.convertList(fileMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), FileDto.class);
    }

    @Override
    public File getById(Long id) {
        return fileMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public FileDto findById(Long id) {
        return ConvertUtil.convert(getById(id), FileDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(FileDto resources) {
        File entity = ConvertUtil.convert(resources, File.class);
        return fileMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(FileDto resources){
        File entity = ConvertUtil.convert(resources, File.class);
        int ret = fileMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        return fileMapper.deleteBatchIds(ids);
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
    public void download(List<FileDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (FileDto file : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("附件关联id", file.getId());
              map.put("逻辑删除", file.getDeleted());
              map.put("版本", file.getVersion());
              map.put("创建者", file.getCreateBy());
              map.put("创建部门", file.getCreateDeptBy());
              map.put("更新者", file.getUpdateBy());
              map.put("创建日期", file.getCreateTime());
              map.put("更新时间", file.getUpdateTime());
              map.put("本地存储id", file.getStorageId());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/

    @Override
    public R removeByStorageId(Long id) {
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("storage_id")
                    .eq("storage_id",id);
        File file = fileMapper.selectOne(queryWrapper);
        removeById(file.getFileId());
        return R.ok();
    }
}
