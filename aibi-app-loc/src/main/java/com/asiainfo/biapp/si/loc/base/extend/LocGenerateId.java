
package com.asiainfo.biapp.si.loc.base.extend;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;

/**
 * 
 * Title : LOC主键生成器,对数据有依赖
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月2日    Administrator        Created</pre>
 * <p/>
 *
 * @author  Administrator
 * @version 1.0.0.2018年1月2日
 */
public class LocGenerateId implements IdentifierGenerator,Configurable  {  
	
	/**
	 * 自增变量业务名称
	 * ZJ_KHQ_CODE_SEQ  专区
	 * ZJ_KHQ_CODE_SEQ  标签
	 * ZJ_KHQ_CODE_SEQ  指标
	 * ZJ_KHQ_CODE_SEQ  客户群
	 */
	public String name;  
	
	/**
	 * 前缀
	 */
	public String prefix;
	
	/**
	 * 补位长度  比如当前值是0，补4位=0004
	 */ 
	public Integer size = 4;
	
	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		 this.name = params.getProperty("name");  
		 this.prefix = params.getProperty("prefix");
		 if(StringUtil.isNotEmpty(params.getProperty("size"))){
			 this.size = Integer.valueOf(params.getProperty("size"));
		 }
	}
	
	@Override
    public Serializable generate(SessionImplementor session, Object object)
            throws HibernateException {
        Connection connection = session.connection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement("SELECT nextval('"+name+"') as nextval");
            rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("nextval");
                String code = prefix + StringUtils.leftPad("" + id,size, '0');
                return code;
            }
        } catch (SQLException e) {
            throw new HibernateException(
                    "Unable to generate Stock Code Sequence");
        }finally {
            if (null != rs) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LogUtil.error("ResultSet 关闭异常！", e);
                } 
            }
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LogUtil.error("PreparedStatement 关闭异常！", e);
                } 
            }
            if (null != connection) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LogUtil.error("connection 关闭异常！", e);
                } 
            }
        }
        return null;
    }

	
  
}  