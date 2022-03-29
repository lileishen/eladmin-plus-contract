package com.yntovi.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeixinVo {
    /** 接收消息的多个人 */
      @NotEmpty
      private List<String> tos;

      @NotBlank
      private String subject;

      @NotBlank
      private String content;
}
