package com.yntovi.utils;

import cn.hutool.http.HttpStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应信息主体
 * 参照mybatis-plus R类
 * @param <T>
 * @author
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "响应信息主体")
public class R<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@ApiModelProperty(value = "返回标记：成功=0，失败=1")
	private int code;

	@Getter
	@Setter
	@ApiModelProperty(value = "返回信息")
	private String msg;


	@Getter
	@Setter
	@ApiModelProperty(value = "数据")
	private T data;

	public Boolean isOk() {
		if(code!= 0){
			return false;
		}else{
			return true;
		}
	}

	public static <T> R<T> ok() {
		return restResult(null, HttpStatus.HTTP_OK, null);
	}

	public static <T> R<T> ok(T data) {
		return restResult(data, HttpStatus.HTTP_OK, null);
	}

    public static <T> R<T> ok(String msg) {
    		return restResult(null, HttpStatus.HTTP_OK, msg);
    	}

	public static <T> R<T> ok(T data, String msg) {
		return restResult(data, HttpStatus.HTTP_OK, msg);
	}

	public static <T> R<T> failed() {
		return restResult(null, HttpStatus.HTTP_PRECON_FAILED, null);
	}

	public static <T> R<T> failed(String msg) {
		return restResult(null, HttpStatus.HTTP_PRECON_FAILED, msg);
	}

	public static <T> R<T> failed(T data) {
		return restResult(data, HttpStatus.HTTP_PRECON_FAILED, null);
	}

	public static <T> R<T> failed(T data, String msg) {
		return restResult(data, HttpStatus.HTTP_PRECON_FAILED, msg);
	}

	public static <T> R<T> failed(T data, int code, String msg) {
		return restResult(data, code, msg);
	}

	public static <T> R<T> failed(int code, String msg) {
		return restResult(null, code, msg);
	}

	private static <T> R<T> restResult(T data, int code, String msg) {
		R<T> apiResult = new R<>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setMsg(msg);
		return apiResult;
	}
}