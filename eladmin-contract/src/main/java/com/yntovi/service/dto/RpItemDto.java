package com.yntovi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import io.swagger.annotations.ApiModelProperty;

/**
* @author Li_sanqi
* @date 2022-01-20
*/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RpItemDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long rpiId;

    @ApiModelProperty(value = "合同档案id")
    private Long contractId;

    @ApiModelProperty(value = "序号")
    private Integer num;

    @ApiModelProperty(value = "编号")
    private String no;

    @ApiModelProperty(value = "收付款项(预付款 进度款 质保金)")
    private String name;

    @ApiModelProperty(value = "收付条件")
    private String condition;

    @ApiModelProperty(value = "收付比例")
    private BigDecimal proportion;

    @ApiModelProperty(value = "应收付金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "预计收付日期")
    private Date estimatedTime;

    @ApiModelProperty(value = "收付款项全称")
    private String nameFull;

    @ApiModelProperty(value = "逻辑删除")
    private Integer deleted;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建部门")
    private String createDeptBy;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
