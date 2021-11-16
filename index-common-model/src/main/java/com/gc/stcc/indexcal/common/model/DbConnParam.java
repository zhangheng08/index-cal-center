package com.gc.stcc.indexcal.common.model;

import lombok.Data;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: DbConnParam
 * @Author: ZhangHeng
 * @Date: 2021/8/3 16:08
 * @Description:
 * @Version:
 */
@Data
public class DbConnParam {

    private String ip;
    private String port;
    private String dbName;
    private String driveClassName;
    private String username;
    private String password;
    private String dbType;

}
