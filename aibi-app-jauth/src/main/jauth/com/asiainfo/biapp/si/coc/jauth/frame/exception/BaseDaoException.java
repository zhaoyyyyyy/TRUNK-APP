package com.asiainfo.biapp.si.coc.jauth.frame.exception;

public class BaseDaoException extends RuntimeException
{
  /**
	 * 
	 */
	private static final long serialVersionUID = -960750460037466937L;
private String sql;
  private Object[] params;

  public BaseDaoException(String sql, Object[] params, Exception e)
  {
    super(e);
    this.sql = sql;
    this.params = params;
  }

  public String getMessage()
  {
    StringBuffer sb = new StringBuffer("执行更新语句出错，使用的更新语句是:[");
    sb.append(this.sql).append("];");
    if ((this.params == null) && (this.params.length == 0)) {
      sb.append("无查询参数");
    } else {
      sb.append("\r\t查询参数是：[");
      for (int i = 0; i < this.params.length; ++i) {
        if (i == 0)
          sb.append(this.params[i].toString());
        else {
          sb.append(",").append(this.params[i].toString());
        }
      }
    }

    return sb.toString();
  }
}