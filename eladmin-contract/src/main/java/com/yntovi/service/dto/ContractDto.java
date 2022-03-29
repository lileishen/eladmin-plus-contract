package com.yntovi.service.dto;

import com.yntovi.domain.LocalStorage;
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
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
* @author Li_sanqi
* @date 2022-01-18
*/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ContractDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long contractId;

    @ApiModelProperty(value = "合同名称")
    private String name;

    @ApiModelProperty(value = "合同编号")
    private String no;

    @ApiModelProperty(value = "合同甲方")
    private Long partyA;

    @ApiModelProperty(value = "甲方负责人")
    private String chargeA;

    @ApiModelProperty(value = "合同乙方")
    private Long partyB;

    @ApiModelProperty(value = "乙方负责人")
    private String chargeB;

    @ApiModelProperty(value = "合同类型（0 合同类型a 1 合同类型b 2 合同类型c ……）")
    private Long type;

    @ApiModelProperty(value = "部门/项目部")
    private Long dept;

    @ApiModelProperty(value = "合同收付类型 （  0 付款 1 收款 2无）")
    private String recPay;

    @ApiModelProperty(value = "合同金额/元")
    private BigDecimal amount;

    @ApiModelProperty(value = "合同状态（0 拟稿中 1 履行中 2 已完成 3 已作废 4 已归档）")
    private Long status;

    @ApiModelProperty(value = "签订日期")
    private Date signTime;

    @ApiModelProperty(value = "合同生效日期")
    private Date effectTime;

    @ApiModelProperty(value = "合同结束日期")
    private String endTime;

    @ApiModelProperty(value = "关键条款")
    private String keyTerms;

    @ApiModelProperty(value = "备注")
    private String comment;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "置顶 (0 否 1 是)")
    private String top;

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

    @ApiModelProperty(value = "合同附件")
       private List<LocalStorage> files;
}
