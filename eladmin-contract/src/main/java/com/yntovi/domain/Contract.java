package com.yntovi.domain;

import com.yntovi.base.CommonModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

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
@TableName("cm_contract")
public class Contract extends CommonModel<Contract> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Long contractId;

    @ApiModelProperty(value = "合同名称")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "合同编号")
    @NotBlank
    private String no;

    @ApiModelProperty(value = "合同甲方")
    @NotNull
    private Long partyA;

    @ApiModelProperty(value = "甲方负责人")
    @NotBlank
    private String chargeA;

    @ApiModelProperty(value = "合同乙方")
    @NotNull
    private Long partyB;

    @ApiModelProperty(value = "乙方负责人")
    @NotBlank
    private String chargeB;

    @ApiModelProperty(value = "合同类型（0 合同类型a 1 合同类型b 2 合同类型c ……）")
    private Long type;

    @ApiModelProperty(value = "部门/项目部")
    @NotNull
    private Long dept;

    @ApiModelProperty(value = "合同收付类型 （  0 付款 1 收款 2无）")
    @NotBlank
    private String recPay;

    @ApiModelProperty(value = "合同金额/元")
    @NotNull
    private BigDecimal amount;

    @ApiModelProperty(value = "合同状态（0 拟稿中 1 履行中 2 已完成 3 已作废 4 已归档）")
    @NotNull
    private Long status;

    @ApiModelProperty(value = "签订日期")
    @NotNull
    private Date signTime;

    @ApiModelProperty(value = "合同生效日期")
    @NotNull
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
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "创建部门")
//    @TableField(fill= FieldFill.INSERT)
    private String createDeptBy;

    @ApiModelProperty(value = "更新者")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "创建日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private List<LocalStorage> files;

    @TableField(exist = false)
    private List<RpItem> rpItems;

    public void copyFrom(Contract source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
