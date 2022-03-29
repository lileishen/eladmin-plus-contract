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
* @author li_sanqi
* @date 2022-01-05
*/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("tool_weixin_config")
public class WeixinConfig extends CommonModel<WeixinConfig> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(type= IdType.ASSIGN_ID)
    private Long configId;

    @ApiModelProperty(value = "企业ID")
    @NotBlank
    private String corpid;

    @ApiModelProperty(value = "凭证密钥")
    @NotBlank
    private String corpsecret;

    @ApiModelProperty(value = "	应用id")
    @NotBlank
    private String agentid;

    @ApiModelProperty(value = "备注	")
    private String comment;

    @TableField(exist = false)
    private String token;

    public void copyFrom(WeixinConfig source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
