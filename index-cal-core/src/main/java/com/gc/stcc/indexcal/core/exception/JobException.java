package com.gc.stcc.indexcal.core.exception;

/**
 * 调度任务 异常
 * @author hsd
 */
public class JobException extends BaseException {

	private static final long serialVersionUID = -6024310062604645567L;

	public JobException(String msg) {
	  super(msg);
	}

	public JobException(Exception e){
	  this(e.getMessage());
	}
}
