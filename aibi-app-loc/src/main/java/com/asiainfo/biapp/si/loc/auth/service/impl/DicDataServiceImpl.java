package com.asiainfo.biapp.si.loc.auth.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.auth.model.DicData;
import com.asiainfo.biapp.si.loc.auth.service.IDicDataService;
import com.asiainfo.biapp.si.loc.base.LocCacheBase;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.JauthServerException;
import com.asiainfo.biapp.si.loc.base.exception.LocCacheException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.utils.HttpUtil;
import com.asiainfo.biapp.si.loc.base.utils.JsonUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;


@Service("dicDataService")
@Transactional
public class DicDataServiceImpl implements IDicDataService{

	@Value("${jauth-url}")  
    private String jauthUrl; 
	
	@Override
	public List<DicData> queryDataListByCode(String code) throws BaseException {
		if(StringUtil.isEmpty(code)){
			throw new ParamRequiredException("数据字典编码必须填写");
		}
		//throw new LocCacheException("获取数据字典缓存失败:"+code);
		try{
			List<DicData> dic =  LocCacheBase.getInstance().getDicDataList(code);
			return dic;
		}catch(Exception e){
			throw new LocCacheException("获取数据字典缓存失败:"+code);
		}
	}

	
	@Override
	public List<DicData> queryAllDicData() throws BaseException {
		List<DicData> allDicDataList = null;
		try {
            String dataJson = HttpUtil.sendPost(jauthUrl + "/api/datadic/dicdatas/query", null);
            allDicDataList = (List<DicData>) JsonUtil.json2CollectionBean(dataJson, List.class, DicData.class);
        } catch (Exception e) {
            throw new JauthServerException("批量读取数据字典数据异常");
        }
		return allDicDataList;
	}
}
