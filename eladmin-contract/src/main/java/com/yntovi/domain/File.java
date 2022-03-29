package com.yntovi.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.yntovi.base.CommonModel;
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
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

/**
* @author Li_sanqi
* @date 2022-01-13
*/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("cm_file")
public class File extends CommonModel<File> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(type= IdType.AUTO)
    private Long fileId;

    @ApiModelProperty(value = "附件关联id")
    @NotNull
    private Long relationId;

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

    @ApiModelProperty(value = "本地存储id")
    @NotNull
    private Long storageId;

    public void copyFrom(File source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
