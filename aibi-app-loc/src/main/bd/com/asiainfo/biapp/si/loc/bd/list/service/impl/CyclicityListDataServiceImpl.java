package com.asiainfo.biapp.si.loc.bd.list.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.bd.common.util.JDBCUtil;
import com.asiainfo.biapp.si.loc.bd.list.service.ICyclicityListDataService;
import com.asiainfo.biapp.si.loc.bd.list.task.RunListDataByConfig;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;

/**
 * 清单周期性生成service实现类
 * @author wanghf5
 * @since 2018-04-18
 *
 */
@Service
@Transactional
public class CyclicityListDataServiceImpl implements ICyclicityListDataService{
	
	private final int dayUpdateCycle = 1;
	
	private final int monthUpdateCycle = 2;

	@Override
	public Map<String, List<String>> getAllDayListId() {
		return null;
	}

	@Override
	public Map<String, List<String>> getAllMonthListId() {
		return null;
	}

	@Override
	public void runAllDayListData() {
		String newDayDate = "";
		Map<String,List<String>> allList = this.getAllListIdAndConfig(newDayDate,dayUpdateCycle,null);
		if(null == allList || allList.isEmpty()){
			return ;
		}
		Set<String> keySet = allList.keySet();
		ExecutorService executorService = Executors.newFixedThreadPool(keySet.size());
		List<Future<String>> resultList = new ArrayList<Future<String>>();
		for (String key : keySet) {
			newDayDate = CocCacheProxy.getCacheProxy().getNewLabelDay(key);
			List<String> listList = allList.get(key);
			// 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中  
            Future<String> future = executorService.submit(new RunListDataByConfig(listList,key,newDayDate));  
            // 将任务执行结果存储到List中  
            resultList.add(future);
		}
		// 遍历任务的结果  
        for (Future<String> fs : resultList) { 
        	try {
        		LogUtil.info("## " + fs.get());
			} catch (InterruptedException | ExecutionException e) {
				LogUtil.error(e);
			}
        }
	}

	@Override
	public void runAllMonthListData() {
		String newDayDate = CocCacheProxy.getCacheProxy().getNewLabelMonth("");
		Map<String,List<String>> allList = this.getAllListIdAndConfig(newDayDate,monthUpdateCycle,null);
		
		if(null == allList || allList.isEmpty()){
			return ;
		}
		Set<String> keySet = allList.keySet();
		ExecutorService executorService = Executors.newFixedThreadPool(keySet.size());
		List<Future<String>> resultList = new ArrayList<Future<String>>();
		
		for (String key : keySet) {
			List<String> listList = allList.get(key);
			// 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中  
            Future<String> future = executorService.submit(new RunListDataByConfig(listList,key,newDayDate));  
            // 将任务执行结果存储到List中  
            resultList.add(future);
		}
		
		// 遍历任务的结果  
        for (Future<String> fs : resultList) { 
        	try {
        		LogUtil.info("## " + fs.get());
			} catch (InterruptedException | ExecutionException e) {
				LogUtil.error(e);
			}
        }
	}

	@Override
	public void runDayListDataByConfigId(String configId) {
		String newDayDate = CocCacheProxy.getCacheProxy().getNewLabelDay(configId);
		Map<String,List<String>> allList = this.getAllListIdAndConfig(newDayDate,dayUpdateCycle,configId);
		if(null == allList || allList.isEmpty()){
			return ;
		}
		Set<String> keySet = allList.keySet();
		ExecutorService executorService = Executors.newFixedThreadPool(keySet.size());
		List<Future<String>> resultList = new ArrayList<Future<String>>();
		
		for (String key : keySet) {
			List<String> listList = allList.get(key);
			// 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中  
            Future<String> future = executorService.submit(new RunListDataByConfig(listList,key,newDayDate));  
            // 将任务执行结果存储到List中  
            resultList.add(future);
		}
		// 遍历任务的结果  
        for (Future<String> fs : resultList) { 
        	try {
        		LogUtil.info("## " + fs.get());
			} catch (InterruptedException | ExecutionException e) {
				LogUtil.error(e);
			}
        }
	}

	@Override
	public void runMonthListDataByConfigId(String configId) {
		String newDayDate = CocCacheProxy.getCacheProxy().getNewLabelMonth(configId);
		Map<String,List<String>> allList = this.getAllListIdAndConfig(newDayDate,monthUpdateCycle,configId);
		
		if(null == allList || allList.isEmpty()){
			return ;
		}
		Set<String> keySet = allList.keySet();
		ExecutorService executorService = Executors.newFixedThreadPool(keySet.size());
		List<Future<String>> resultList = new ArrayList<Future<String>>();
		
		for (String key : keySet) {
			List<String> listList = allList.get(key);
			// 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中  
            Future<String> future = executorService.submit(new RunListDataByConfig(listList,key,newDayDate));  
            // 将任务执行结果存储到List中  
            resultList.add(future);
		}
		
		// 遍历任务的结果  
        for (Future<String> fs : resultList) { 
        	try {
        		LogUtil.info("## " + fs.get());
			} catch (InterruptedException | ExecutionException e) {
				LogUtil.error(e);
			}
        }
	}

	@Override
	public void runDayListDataByConfigId(String configId, String dataDate) {
		if(StringUtils.isBlank(configId) || StringUtils.isBlank(dataDate)){
			return ;
		}else {
			if(dataDate.length()!=8){
				return ;
			}
		}
		Map<String,List<String>> allList = this.getAllListIdAndConfig(dataDate,dayUpdateCycle,configId);
		
		if(null == allList || allList.isEmpty()){
			return ;
		}
		Set<String> keySet = allList.keySet();
		ExecutorService executorService = Executors.newFixedThreadPool(keySet.size());
		List<Future<String>> resultList = new ArrayList<Future<String>>();
		
		for (String key : keySet) {
			List<String> listList = allList.get(key);
			// 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中  
            Future<String> future = executorService.submit(new RunListDataByConfig(listList,key,dataDate));  
            // 将任务执行结果存储到List中  
            resultList.add(future);
		}
		
		// 遍历任务的结果  
        for (Future<String> fs : resultList) { 
        	try {
        		LogUtil.info("##  " + fs.get());
			} catch (InterruptedException | ExecutionException e) {
				LogUtil.error(e);
			}
        }
	}

	@Override
	public void runMonthListDataByConfigId(String configId, String dataDate) {
		if(StringUtils.isBlank(configId) || StringUtils.isBlank(dataDate)){
			return ;
		}else {
			if(dataDate.length()!=6){
				return ;
			}
		}
		Map<String,List<String>> allList = this.getAllListIdAndConfig(dataDate,monthUpdateCycle,configId);
		
		
		if(null == allList || allList.isEmpty()){
			return ;
		}
		Set<String> keySet = allList.keySet();
		ExecutorService executorService = Executors.newFixedThreadPool(keySet.size());
		List<Future<String>> resultList = new ArrayList<Future<String>>();
		
		for (String key : keySet) {
			List<String> listList = allList.get(key);
			// 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中  
            Future<String> future = executorService.submit(new RunListDataByConfig(listList,key,dataDate));  
            // 将任务执行结果存储到List中  
            resultList.add(future);
		}
		
		// 遍历任务的结果  
        for (Future<String> fs : resultList) { 
        	try {
        		LogUtil.info("## " + fs.get());
			} catch (InterruptedException | ExecutionException e) {
				LogUtil.error(e);
			}
        }
	}

	private Map<String,List<String>> getAllListIdAndConfig(String dataDate,int updateCycle,String configId){
		Map<String,List<String>> res = new HashMap<String,List<String>>();
		Connection conn = null;
		PreparedStatement st = null;
        ResultSet rs = null;
		try {
			conn = JDBCUtil.getInstance().getWebConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select t.LABEL_ID,t.CONFIG_ID from loc_label_info t ");
			sb.append("left join  loc_pre_config_info c on c.config_id=t.config_id ");
			sb.append(" where c.config_status=1 and t.GROUP_TYPE=1 ");
			if(StringUtils.isNotBlank(configId)){
				sb.append(" and c.config_id='").append(configId).append("' ");
			}
			sb.append(" and t.UPDATE_CYCLE=").append(Integer.toString(updateCycle)).append(" and t.label_id not in ");
			sb.append("( select group_id from loc_list_info l where l.DATA_STATUS=3 and l.data_date='"+ dataDate + "' )");
			
			LogUtil.info("-- getAllDayListIdAndConfig = " + sb.toString());
			
			st = conn.prepareStatement(sb.toString());
			rs = st.executeQuery();
			ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等
	        int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数
	        
			while (rs.next()) {
				String label_id = "";
	        	String config_id = "";
				for(int i=1;i<=columnCount;i++){
					String columnName = md.getColumnName(i).toUpperCase();
					if(columnName.equals("CONFIG_ID")){
						config_id = rs.getString(columnName);
					}else if(columnName.equals("LABEL_ID")){
						label_id = rs.getString(columnName);
					}
				}
				if(StringUtils.isNoneBlank(label_id) && StringUtils.isNoneBlank(config_id)){
					if(res.containsKey(config_id)){
						res.get(config_id).add(label_id);
					}else{
						List<String> ilist = new ArrayList<String>();
						ilist.add(label_id);
						res.put(config_id, ilist);
					}
				}
				
			}
			LogUtil.info(" res = " + res.toString());
		} catch (Exception e) {
			LogUtil.error(e);
		}finally{
			JDBCUtil.getInstance().free(conn, st, rs);
		}
		return res;
	}
}
