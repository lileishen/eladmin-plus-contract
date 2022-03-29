package com.yntovi.service;

import com.yntovi.base.PageInfo;
import com.yntovi.base.CommonService;
import com.yntovi.domain.AlipayConfig;
import com.yntovi.domain.WeixinConfig;
import com.yntovi.domain.vo.WeixinVo;
import com.yntovi.service.dto.WeixinConfigDto;
import com.yntovi.service.dto.WeixinConfigQueryParam;
import com.yntovi.utils.R;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author li_sanqi
* @date 2022-01-05
*/
public interface WeixinConfigService extends CommonService<WeixinConfig>  {

    static final String CACHE_KEY = "weixinConfig";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<WeixinConfigDto>
    */
    PageInfo<WeixinConfigDto> queryAll(WeixinConfigQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<WeixinConfigDto>
    */
    List<WeixinConfigDto> queryAll(WeixinConfigQueryParam query);

    WeixinConfig getById(Long id);
    WeixinConfigDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    int insert(WeixinConfig weixinConfig);
    int updateById(WeixinConfigDto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<WeixinConfigDto> all, HttpServletResponse response) throws IOException;

    R config(WeixinConfig weixinConfig);


    /**
     * 查询配置
     * @return WeixinConfig
     */
    WeixinConfig find();

    R sendMsg(WeixinVo weixinVo);

//    获取 企业微信token
    WeixinConfig getWeixinConfig();
}
