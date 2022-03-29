package com.yntovi.domain;

import com.yntovi.annotation.Excel;
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
@TableName("cm_rp_item")
public class RpItem extends CommonModel<RpItem> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Long rpiId;

    @ApiModelProperty(value = "合同档案id")
    @NotNull
    private Long contractId;

    @ApiModelProperty(value = "序号")
    @NotNull
    @Excel(name = "序号", type = Excel.Type.ALL)
    private Integer num;

    @ApiModelProperty(value = "编号")
    @NotBlank
    private String no;

    @ApiModelProperty(value = "收付款项")
    @NotBlank
    @Excel(name = "收付款项", type = Excel.Type.ALL)
    private String name;

    @ApiModelProperty(value = "收付条件")
    @NotBlank
    @Excel(name = "收付条件", type = Excel.Type.ALL)
    private String condition;

    @ApiModelProperty(value = "收付比例")
    @NotNull
    @Excel(name = "收付比例", type = Excel.Type.ALL)
    private BigDecimal proportion;

    @ApiModelProperty(value = "应收付金额")
    @NotNull
    @Excel(name = "应收付金额", type = Excel.Type.ALL)
    private BigDecimal amount;

    @ApiModelProperty(value = "预计收付日期")
    @NotNull
    @Excel(name = "预计收付日期", type = Excel.Type.ALL)
    private Date estimatedTime;

    @ApiModelProperty(value = "收付款项全称")
    @NotBlank
    private String nameFull;

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

    public void copyFrom(RpItem source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
