package com.yntovi.service.dto;

import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Date;
import com.yntovi.annotation.Query;
import org.springframework.format.annotation.DateTimeFormat;

/**
* @author Li_sanqi
* @date 2022-01-10
*/
@Data
@Getter
@Setter
public class SideQueryParam{
    @Query(blurry = "name,no")
    private String blurry;

    /** 精确 */
    @Query
    private String enabled;

    /** 中模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String conPer;

    /** 精确 */
    @Query(type = Query.Type.INNER_LIKE) 
    private String conTel;

    /** BETWEEN */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Query(type = Query.Type.BETWEEN)
    private List<Date> createTime;

}
