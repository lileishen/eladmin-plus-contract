package com.yntovi.service.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yntovi.base.CommonMapper;
import com.yntovi.domain.Side;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Li_sanqi
* @date 2022-01-10
*/
@Repository
public interface SideMapper extends BaseMapper<Side> {
    IPage<Side> selectPage1(IPage<Side> queryPage, @Param(Constants.WRAPPER)QueryWrapper<Object> predicate);

    List<Side> selectList1(@Param(Constants.WRAPPER)QueryWrapper<Object> predicate);
}
