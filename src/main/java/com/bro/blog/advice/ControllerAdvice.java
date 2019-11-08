package com.bro.blog.advice;


import com.bro.blog.exception.ParamException;
import com.dreambai.api.pojo.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

	private static final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

	/**
	 * spring 参数异常
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(BindException.class)
	public JsonResult<String> bindException(BindException bex) {
		logger.error("参数格式异常", bex);
		String msg = bex.getFieldErrors().get(0).getDefaultMessage();
		return JsonResult.error(-1, msg);
	}

	/**
	 * spring 参数异常
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public JsonResult<String> methodArgumentNotValidException(MethodArgumentNotValidException mex) {
		logger.error("参数不能为空", mex);
		String msg = mex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
		return JsonResult.error(-1, msg);
	}


	/**
	 * spring 参数异常
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(ParamException.class)
	public JsonResult<String> bindException(ParamException pex) {
		logger.error("数据校验异常", pex);
		String msg = pex.getMessage();
		return JsonResult.error(-1, msg);
	}


}
