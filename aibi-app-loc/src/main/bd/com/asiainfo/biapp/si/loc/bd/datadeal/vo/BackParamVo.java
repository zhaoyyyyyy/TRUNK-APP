package com.asiainfo.biapp.si.loc.bd.datadeal.vo;

import io.swagger.annotations.ApiParam;

/**
 * Created by pwj on 2018/4/19.
 */
public class BackParamVo {
	
	@ApiParam(value = "数据时间(YYYYMMDD / YYYYMM) ")
    private String dataDate ;//数据时间

	@ApiParam(value = "表名(schema.tablename)")
    private String tableName;//表名

	@ApiParam(value = "专区ID(PXXX)")
    private String configId;//专区id

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }
}
