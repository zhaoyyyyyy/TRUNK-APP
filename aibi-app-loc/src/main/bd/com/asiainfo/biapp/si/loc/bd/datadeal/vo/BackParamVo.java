package com.asiainfo.biapp.si.loc.bd.datadeal.vo;

import io.swagger.annotations.ApiParam;

/**
 * Created by pwj on 2018/4/19.
 */
public class BackParamVo {
	
	@ApiParam(value = "数据时间(YYYYMMDD / YYYYMM) ")
    private String data ;//数据时间

	@ApiParam(value = "表名(schema.tablename)")
    private String tableName;//表名

	@ApiParam(value = "专区ID(PXXX)")
    private String prefectureId;//专区id

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPrefectureId() {
        return prefectureId;
    }

    public void setPrefectureId(String prefectureId) {
        this.prefectureId = prefectureId;
    }


}
