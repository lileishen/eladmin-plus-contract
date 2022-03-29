package com.yntovi.service.dto;

import com.yntovi.domain.LocalStorage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
* @author Li_sanqi
* @date 2022-01-10
*/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SideDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long sideId;

    @ApiModelProperty(value = "相对方名称")
    private String name;

    @ApiModelProperty(value = "编号")
    private String no;

    @ApiModelProperty(value = "类型（0 已方 1 客户 2 供应商 3 合作伙伴 4 其他）")
    private Long type;

    @ApiModelProperty(value = "联系人")
    private String conPer;

    @ApiModelProperty(value = "联系电话")
    private String conTel;

    @ApiModelProperty(value = "开户银行")
    private String bank;

    @ApiModelProperty(value = "银行账号")
    private String bankAcc;

    @ApiModelProperty(value = "地址")
    private String[] address;

    @ApiModelProperty(value = "详细地址")
    private String addrDetail;

    @ApiModelProperty(value = "备注")
    private String comment;

    @ApiModelProperty(value = "状态( 0 关闭 1 启用 )")
    private String enabled;

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

    @ApiModelProperty(value = "附件")
    private List<LocalStorage> files;
    @ApiModelProperty(value = "供应商类型")
    private String typeVal;
}
