/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.frame.dao;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SystemDateFilter
  implements Filter
{
  private static ThreadLocal<Date> systemDate = new ThreadLocal<Date>();

  private static ThreadLocal<Boolean> fromBrowser = new ThreadLocal<Boolean>();

  public void destroy()
  {
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
  {
    fromBrowser.set(Boolean.valueOf(true));
    chain.doFilter(request, response);

    systemDate.set(null);
  }

  public void init(FilterConfig arg0) throws ServletException
  {
  }

  public static void setSystemDate(Date sysdate)
  {
    if (fromBrowser.get() != null)
      systemDate.set(sysdate);
  }

  public static Date getSystemDate()
  {
    return (Date)systemDate.get();
  }
}