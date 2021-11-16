package com.gc.stcc.indexcal.core.exception;

/**
 * 调度任务 参数解析异常
 * @author hsd
 */
public class ParamParseException extends JobException {

	private static final long serialVersionUID = -6384105147653646263L;

	public ParamParseException(String msg) {
	  super(msg);
	}

	public ParamParseException(Exception e){
	  this(e.getMessage());
	}
}
