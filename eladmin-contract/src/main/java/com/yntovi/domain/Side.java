package com.yntovi.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.yntovi.base.CommonModel;
import com.yntovi.rest.LocalStorageController;
import com.yntovi.typehandler.ArrayStringTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.apache.ibatis.type.JdbcType;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

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
@TableName("cm_side")
public class Side extends CommonModel<Side> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(type= IdType.AUTO)
    private Long sideId;

    @ApiModelProperty(value = "相对方名称")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "编号")
    @NotBlank
    private String no;

    @ApiModelProperty(value = "类型（0 已方 1 客户 2 供应商 3 合作伙伴 4 其他）")
    @NotNull
    private Long type;

    @ApiModelProperty(value = "联系人")
    @NotBlank
    private String conPer;

    @ApiModelProperty(value = "联系电话")
    @NotBlank
    private String conTel;

    @ApiModelProperty(value = "开户银行")
    private String bank;

    @ApiModelProperty(value = "银行账号")
    private String bankAcc;

    @ApiModelProperty(value = "地址")
    @TableField(typeHandler = ArrayStringTypeHandler.class, jdbcType= JdbcType.VARCHAR)
    @NotBlank
    private String[] address;

    @ApiModelProperty(value = "详细地址")
    @NotBlank
    private String addrDetail;

    @ApiModelProperty(value = "备注")
    private String comment;

    @ApiModelProperty(value = "状态( 0 关闭 1 启用 )")
    @NotBlank
    private String enabled;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "版本")
    @Version
    private Integer version;

    @ApiModelProperty(value = "创建者")
    @TableField(fill= FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "创建部门")
//    @TableField(fill= FieldFill.INSERT)
    private String createDeptBy;

    @ApiModelProperty(value = "更新者")
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "创建日期")
    @TableField(fill= FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private List<LocalStorage> files;

    @TableField(exist = false)
    private String typeVal;

    public void copyFrom(Side source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
