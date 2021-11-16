package com.gc.stcc.indexcal.core.feign;

import lombok.Data;

@Data
public class FeignFailResult {
	
	 private int status;
	 private String resp_msg;
}
