
package com.asiainfo.biapp.si.loc.base.utils;

/**
 * 
 * Title : DataBaseAdapter
 * <p/>
 * Description : 数据库适配
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 6.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年12月18日    tianxy3        Created
 * </pre>
 * <p/>
 *
 * @author tianxy3
 * @version 1.0.0.2017年12月18日
 */
public class DataBaseAdapter {

	public String getSubString(String strColName, int pos, int len) throws RuntimeException {
		String strRet = "";
		if (len == -1) {
			strRet = "substring(" + strColName + "," + pos + ")";
		} else {
			strRet = "substring(" + strColName + "," + pos + "," + len + ")";
		}
		return strRet;
	}

	public String getColumnToChar(String strColName) {
		String strRet = "";
		strRet = strColName;
		return strRet;
	}

	public String getEnumFromTabel(String tableName, String columnName, int dataType) {
		StringBuffer sReturn = new StringBuffer();
		sReturn.append("select ").append(columnName).append(" from ").append(tableName);
		return sReturn.toString();
	}

}
