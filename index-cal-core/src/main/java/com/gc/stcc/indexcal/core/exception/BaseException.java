package com.gc.stcc.indexcal.core.exception;

/**
 * 基本异常，系统定义的所有异常都需要继承这个基本类
 * blog: https://blog.51cto.com/13005375 
 * code: https://gitee.com/owenwangwen/open-capacity-platform
 */
public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7859712770754900356L;

	public BaseException(String msg) {
	  super(msg);
	}

	public BaseException(Exception e){
	  this(e.getMessage());
	}
}
