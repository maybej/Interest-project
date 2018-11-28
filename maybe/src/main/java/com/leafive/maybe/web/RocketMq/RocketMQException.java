package com.leafive.maybe.web.RocketMq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author jj
 * @Date 2018/11/28 10:06
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RocketMQException extends RuntimeException{

	private String errorCode;

	private String errorMsg;

	private boolean success;
}
