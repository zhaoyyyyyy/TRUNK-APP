/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.frame.dao;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;

/**
 * @author zhougz
 * @date 2013-5-27
 */
public class HibernateDaoHelper {
	  public static int countResult(Query query, Object[] values)
	  {
	    try
	    {
	      return Integer.parseInt(String.valueOf(getQuery(query, values).uniqueResult()));
	    } catch (Exception e) {
	      throw new RuntimeException("Query can't be auto count, queryString is:" + query.getQueryString(), e);
	    }
	  }

	  public static Query getQuery(Query query, Object[] values)
	  {
	    if ((values != null) && (values.length > 0)) {
	      for (int i = 0; i < values.length; ++i) {
	    	  
	    	if(   values[i] instanceof Map){
	    		 Iterator it = ((Map)values[i]).entrySet().iterator();
	    		  while (it.hasNext()) {
	    		   Map.Entry entry = (Map.Entry) it.next();
	    		   Object key = entry.getKey();
	    		   Object value = entry.getValue();
	    		   query.setString(key.toString(), value.toString());
	    		  }
	    	}else{
	    		 query.setParameter(i, values[i]);
	    	}
	       
	      }
	    }
	    return query;
	  }
	  
	  public String buildCountQueryString(String sqlOrHql) {
		  
		  sqlOrHql = sqlOrHql.replace("?", "?0");
		  
		    String prefix = "";

		    int fromIndex = StringUtils.indexOfIgnoreCase(sqlOrHql, "from");
		    int orderIndex = StringUtils.lastIndexOfIgnoreCase(sqlOrHql, "order by");
		    if ((StringUtils.isNotEmpty(sqlOrHql)) && (sqlOrHql.toUpperCase().trim().startsWith("SELECT"))) {
		      prefix = sqlOrHql.substring(StringUtils.indexOfIgnoreCase(sqlOrHql, "select") + 6, fromIndex);
		    }

		    if (orderIndex == -1) {
		      if (StringUtils.indexOfIgnoreCase(prefix, "distinct") != -1) {
		        return "SELECT COUNT(*) as CT FROM (" + sqlOrHql + ")";
		      }
		      return "SELECT COUNT(*) as CT " + StringUtils.substring(sqlOrHql, fromIndex);
		    }
		    if (StringUtils.indexOfIgnoreCase(prefix, "distinct") != -1) {
		      return "SELECT COUNT(*) as CT FROM (" + StringUtils.substring(sqlOrHql, 0, orderIndex) + ")";
		    }
		    return "SELECT COUNT(*) as CT " + StringUtils.substring(sqlOrHql, fromIndex, orderIndex);
		  }
}
