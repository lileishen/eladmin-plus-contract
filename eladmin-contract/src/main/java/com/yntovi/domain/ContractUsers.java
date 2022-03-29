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
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

/**
* @author Li_sanqi
* @date 2022-03-29
*/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("cm_contract_users")
public class ContractUsers extends CommonModel<ContractUsers> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "合同档案id")
    @TableId(type= IdType.ASSIGN_ID)
    private Long contractId;

    @ApiModelProperty(value = "协作人id")
    @TableId(type= IdType.ASSIGN_ID)
    private Long userId;

    @ApiModelProperty(value = "逻辑删除")
    private Integer deleted;

    @ApiModelProperty(value = "版本")
    private Integer version;

    public void copyFrom(ContractUsers source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
