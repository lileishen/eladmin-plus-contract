package com.yntovi.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yntovi.domain.vo.WeixinVo;
import com.yntovi.utils.*;
import lombok.AllArgsConstructor;
import com.yntovi.base.PageInfo;
import com.yntovi.base.QueryHelpMybatisPlus;
import com.yntovi.base.impl.CommonServiceImpl;
import com.yntovi.domain.WeixinConfig;
import com.yntovi.service.WeixinConfigService;
import com.yntovi.service.dto.WeixinConfigDto;
import com.yntovi.service.dto.WeixinConfigQueryParam;
import com.yntovi.service.mapper.WeixinConfigMapper;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author li_sanqi
 * @date 2022-01-05
 */
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = WeixinConfigService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WeixinConfigServiceImpl extends CommonServiceImpl<WeixinConfigMapper, WeixinConfig> implements WeixinConfigService {

    private final RedisUtils redisUtils;
    private final WeixinConfigMapper weixinConfigMapper;
    @Resource
    private final WeixinUtils weixinUtils;


    @Override
    public PageInfo<WeixinConfigDto> queryAll(WeixinConfigQueryParam query, Pageable pageable) {
        IPage<WeixinConfig> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<WeixinConfig> page = weixinConfigMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, WeixinConfigDto.class);
    }

    @Override
    public List<WeixinConfigDto> queryAll(WeixinConfigQueryParam query) {
        return ConvertUtil.convertList(weixinConfigMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), WeixinConfigDto.class);
    }

    @Override
    public WeixinConfig getById(Long id) {
        return weixinConfigMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public WeixinConfigDto findById(Long id) {
        return ConvertUtil.convert(getById(id), WeixinConfigDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(WeixinConfig weixinConfig) {
//        WeixinConfig entity = ConvertUtil.convert(resources, WeixinConfig.class);
        return weixinConfigMapper.insert(weixinConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(WeixinConfigDto resources) {
        WeixinConfig entity = ConvertUtil.convert(resources, WeixinConfig.class);
        int ret = weixinConfigMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids) {
        // delCaches(ids);
        return weixinConfigMapper.deleteBatchIds(ids);
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
    public void download(List<WeixinConfigDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (WeixinConfigDto weixinConfig : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("企业ID", weixinConfig.getCorpid());
              map.put("凭证密钥", weixinConfig.getCorpsecret());
              map.put("	应用id", weixinConfig.getAgentid());
              map.put("备注	", weixinConfig.getComment());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/

    /**
     * 配置企业微信
     *
     * @param weixinConfig
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R config(WeixinConfig weixinConfig) {
        // 测试企业微信是否连接成功
        JSONObject jsonObject = weixinUtils.getToken(weixinConfig);
//        {
//           "errcode": 0,  出错返回码，为0表示成功，非0表示调用失败
//           "errmsg": "ok", 返回码提示语
//           "access_token": "accesstoken000001",   获取到的凭证，最长为512字节
//           "expires_in": 7200 凭证的有效时间（秒）
//        }
        Integer errcode = (Integer) jsonObject.get("errcode");
        if (!Integer.valueOf("0").equals(errcode)) {
            return R.failed("测试失败: 情况1：过于频繁请求 情况2：配置信息有误");
        }
//        将企业微信配置信息保存到数据库中
        WeixinConfig config = find(); //原来的配置
        if (ObjectUtil.isNotNull(config)) {    // 已经添加过企业微信配置
            updateById(weixinConfig);
        } else {   // 没有企业微信配置
            weixinConfig.setConfigId(1L);
            insert(weixinConfig);
        }
        weixinUtils.saveToken(weixinConfig);
        return R.ok("测试成功");
    }

    /**
     * 查看企业微信配置
     *
     * @return
     */
    @Override
    public WeixinConfig find() {
        return weixinConfigMapper.selectById(1L);
    }

    public boolean exits() {
        WeixinConfig weixinConfig = find();
        return ObjectUtil.isNotNull(weixinConfig) ? true : false;
    }

    /*
     *      企业微信应用发送消息
     * */
    @Override
    public R sendMsg(WeixinVo weixinVo) {
//        1. 判断企业微信配置是否存在
        boolean exits = exits();
        if (exits == false) return R.failed("请先配置，再操作");
        weixinUtils.sendMessagearkdown(weixinVo, getWeixinConfig()); //发送企业微信应用消息
        return R.ok("消息发送成功");
    }

    /**
     * 从redis 获取 weixinConfig 和重数据库中获取
     *
     * @return
     */
    public WeixinConfig getWeixinConfig() {
        WeixinConfig weixinConfig = null;
        long ttl = redisUtils.getExpire("weixin_access_token");
        if (ttl > 0) {
            //  redis 中获取 weixin_access_token
            weixinConfig = (WeixinConfig) redisUtils.get("weixin_access_token");
        } else {
            // 先获取企业微信配置
            weixinConfig = find();
            weixinConfig = weixinUtils.saveToken(weixinConfig);
        }
        return weixinConfig;
    }


}
