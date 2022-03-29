package com.yntovi.utils;

import com.yntovi.constant.Constants;
import com.yntovi.domain.DictDetail;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 字典工具类
 * 
 * @author ruoyi
 */
@Component
public class DictUtils
{
    /**
     * 分隔符
     */
    public static final String SEPARATOR = ",";

    /**
     * 设置字典缓存
     * 
     * @param key 参数键
     * @param dictDatas 字典数据列表
     */
    public static void setDictCache(String key, List<DictDetail> dictDatas)
    {
        CacheUtils.put(getCacheName(), getCacheKey(key), dictDatas);
    }

    /**
     * 获取字典缓存
     * 
     * @param key 参数键
     * @return dictDatas 字典数据列表
     */
    public static List<DictDetail> getDictCache(String key)
    {
        Object cacheObj = CacheUtils.get(getCacheName(), getCacheKey(key));
        if (StringUtils1.isNotNull(cacheObj))
        {
            return StringUtils1.cast(cacheObj);
        }
        return null;
    }

    /**
     * 根据字典类型和字典值获取字典标签
     * 
     * @param dictType 字典类型
     * @param dictValue 字典值
     * @return 字典标签
     */
    public static String getLabel(String dictType, String dictValue)
    {
        return getLabel(dictType, dictValue, SEPARATOR);
    }

    /**
     * 根据字典类型和字典标签获取字典值
     * 
     * @param dictType 字典类型
     * @param dictLabel 字典标签
     * @return 字典值
     */
    public static String getValue(String dictType, String dictLabel)
    {
        return getValue(dictType, dictLabel, SEPARATOR);
    }

    /**
     * 根据字典类型和字典值获取字典标签
     * 
     * @param dictType 字典类型
     * @param dictValue 字典值
     * @param separator 分隔符
     * @return 字典标签
     */
    public static String getLabel(String dictType, String dictValue, String separator)
    {
        StringBuilder propertyString = new StringBuilder();
        List<DictDetail> datas = getDictCache(dictType);

        if (StringUtils1.containsAny(separator, dictValue) && StringUtils1.isNotEmpty(datas))
        {
            for (DictDetail dict : datas)
            {
                for (String value : dictValue.split(separator))
                {
                    if (value.equals(dict.getValue()))
                    {
                        propertyString.append(dict.getLabel()).append(separator);
                        break;
                    }
                }
            }
        }
        else
        {
            for (DictDetail dict : datas)
            {
                if (dictValue.equals(dict.getValue()))
                {
                    return dict.getLabel();
                }
            }
        }
        return StringUtils1.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 根据字典类型和字典标签获取字典值
     * 
     * @param dictType 字典类型
     * @param dictLabel 字典标签
     * @param separator 分隔符
     * @return 字典值
     */
    public static String getValue(String dictType, String dictLabel, String separator)
    {
        StringBuilder propertyString = new StringBuilder();
        List<DictDetail> datas = getDictCache(dictType);

        if (StringUtils1.containsAny(separator, dictLabel) && StringUtils1.isNotEmpty(datas))
        {
            for (DictDetail dict : datas)
            {
                for (String label : dictLabel.split(separator))
                {
                    if (label.equals(dict.getLabel()))
                    {
                        propertyString.append(dict.getValue()).append(separator);
                        break;
                    }
                }
            }
        }
        else
        {
            for (DictDetail dict : datas)
            {
                if (dictLabel.equals(dict.getLabel()))
                {
                    return dict.getValue();
                }
            }
        }
        return StringUtils1.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 删除指定字典缓存
     * 
     * @param key 字典键
     */
    public static void removeDictCache(String key)
    {
        CacheUtils.remove(getCacheName(), getCacheKey(key));
    }

    /**
     * 清空字典缓存
     */
    public static void clearDictCache()
    {
        CacheUtils.removeAll(getCacheName());
    }

    /**
     * 获取cache name
     * 
     * @return 缓存名
     */
    public static String getCacheName()
    {
        return Constants.SYS_DICT_CACHE;
    }

    /**
     * 设置cache key
     * 
     * @param configKey 参数键
     * @return 缓存键key
     */
    public static String getCacheKey(String configKey)
    {
        return Constants.SYS_DICT_KEY + configKey;
    }
}
